package com.sira.package_tracking.prediction

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class PackageDeliveryAverageTimeKafkaStreamsTopologyConfig (
    @Value("\${events.status_topic}") val statusTopic: String,
)
