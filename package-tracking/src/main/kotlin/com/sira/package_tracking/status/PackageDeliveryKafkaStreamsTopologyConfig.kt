package com.sira.package_tracking.status

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class PackageDeliveryKafkaStreamsTopologyConfig (
    @Value("\${events.events_topic}") val eventsTopic: String,
)
