package com.sira.package_tracking.status

import com.sira.package_tracking.KafkaStreamsTopology
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.Topology
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.Materialized
import org.springframework.stereotype.Service
import java.util.Properties

@Service
class PackageDeliveryStatusKafkaStreamsTopology(
    private val config: PackageDeliveryKafkaStreamsTopologyConfig,
    private val processor: PackageDeliveryStatusEventsProcessor,
) : KafkaStreamsTopology {


    override fun createTopology(): Topology {
        val builder = StreamsBuilder()


        val stream = builder
            .stream(config.eventsTopic, Consumed.with(Serdes.String(),
                PackageDeliveryEventSerde()))

        stream
            .groupByKey()
            .aggregate(
                { EMPTY_STATUS },
                { key, event, status->
                    processor.processPackageEvent(event, status)
                },
                Materialized.with(Serdes.String(), PackageDeliveryStatusSerde())
            )

        return builder.build()
    }
}