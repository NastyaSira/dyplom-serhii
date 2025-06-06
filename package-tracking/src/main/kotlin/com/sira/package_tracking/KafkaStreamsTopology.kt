package com.sira.package_tracking

import java.util.Properties

interface KafkaStreamsTopology {

    fun startTopology(props: Properties)
}