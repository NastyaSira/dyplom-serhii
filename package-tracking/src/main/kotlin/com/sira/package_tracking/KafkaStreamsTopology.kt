package com.sira.package_tracking

import org.apache.kafka.streams.Topology
import java.util.Properties

interface KafkaStreamsTopology {

    fun createTopology(): Topology
}