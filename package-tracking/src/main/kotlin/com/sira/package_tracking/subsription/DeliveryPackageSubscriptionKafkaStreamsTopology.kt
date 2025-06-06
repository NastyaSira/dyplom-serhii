package com.sira.package_tracking.subsription

import com.sira.package_tracking.KafkaStreamsTopology
import com.sira.package_tracking.status.PackageDeliveryEventSerde
import com.sira.package_tracking.status.PackageDeliveryKafkaStreamsTopologyConfig
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.Topology
import org.apache.kafka.streams.kstream.Consumed
import org.springframework.stereotype.Component

@Component
class DeliveryPackageSubscriptionKafkaStreamsTopology(
    private val config: PackageDeliveryKafkaStreamsTopologyConfig,
    private val subscriptionProcessor: DeliveryPackageSubscriptionEventsProcessor
) : KafkaStreamsTopology {

    override fun createTopology(): Topology {
        val builder = StreamsBuilder()


        val stream = builder
            .stream(config.eventsTopic, Consumed.with(Serdes.String(),
                PackageDeliveryEventSerde()))


        stream.foreach { _, event ->
            subscriptionProcessor.processEvent(event)
        }

        return builder.build()
    }
}