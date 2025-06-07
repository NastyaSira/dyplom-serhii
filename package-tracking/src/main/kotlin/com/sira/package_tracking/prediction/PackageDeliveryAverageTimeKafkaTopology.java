package com.sira.package_tracking.prediction;

import com.sira.package_tracking.KafkaStreamsTopology;
import com.sira.package_tracking.event.PackageDeliveryEventType;
import com.sira.package_tracking.status.PackageDeliveryStatus;
import com.sira.package_tracking.status.PackageDeliveryStatusSerde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.KeyValueStore;
import org.graalvm.collections.Pair;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Objects;

@Component
public class PackageDeliveryAverageTimeKafkaTopology implements KafkaStreamsTopology {

    private final PackageDeliveryAverageTimeKafkaStreamsTopologyConfig config;

    public PackageDeliveryAverageTimeKafkaTopology(
            PackageDeliveryAverageTimeKafkaStreamsTopologyConfig config
    ) {
        this.config = config;
    }

    @Override
    @NotNull
    public Topology createTopology() {
        StreamsBuilder builder = new StreamsBuilder();

        builder
                .stream(config.getStatusTopic(), Consumed.with(Serdes.String(),
                        new PackageDeliveryStatusSerde()))
                .filter((key, status) -> takeLastDuration(status) != null)
                .map((key, value) -> {
                    var lastDuration = Objects.requireNonNull(takeLastDuration(value));
                    return KeyValue.pair(
                            lastDuration.getLeft(),
                            lastDuration.getRight()
                    );
                })
                .groupByKey()
                .aggregate(() -> Pair.create(Duration.ZERO, 0),
                        (locationsPair, duration, aggregate) -> Pair.create(
                                aggregate.getLeft().plus(duration),
                                aggregate.getRight() + 1
                        ),
                        Materialized.<String, Pair<Duration, Integer>, KeyValueStore<Bytes, byte[]>>as("average_durations")
                                .withKeySerde(Serdes.String())
                                .withValueSerde(new PackageDeliveryAverageTimeValueSerde())
                );


        return builder.build();
    }

    private static Pair<String, Duration> takeLastDuration(PackageDeliveryStatus status) {
        var history = status.getHistory();

        if (history.size() < 2) return null;

        var lastEvent = history.getLast();
        var beforeLastEvent = history.get(history.size() - 2);

        if (lastEvent.getEvent() != PackageDeliveryEventType.ARRIVED_TO_LOCATION) return null;
        if (beforeLastEvent.getEvent() != PackageDeliveryEventType.DISPATCHED_FROM_LOCATION) return null;

        return Pair.create(
                beforeLastEvent.getCurrentAddress() + "->" + lastEvent.getCurrentAddress(),
                Duration.between(
                        lastEvent.getTimestamp(),
                        beforeLastEvent.getTimestamp()
                )
        );
    }
}
