package com.sira.package_tracking.prediction;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import org.graalvm.collections.Pair;

import java.time.Duration;

public class PackageDeliveryAverageTimeValueSerde implements Serde<Pair<Duration, Integer>> {

    @Override
    public Serializer<Pair<Duration, Integer>> serializer() {
        return (topic, data) -> (data.getLeft().toMillis() + ":" + data.getRight()).getBytes();
    }

    @Override
    public Deserializer<Pair<Duration, Integer>> deserializer() {
        return (topic, data) -> {
            var chunks = new String(data).split(":");
            return Pair.create(
                    Duration.ofMillis(Long.parseLong(chunks[0])),
                    Integer.parseInt(chunks[1])
            );
        };
    }
}
