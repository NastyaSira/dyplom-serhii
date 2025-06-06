package com.sira.package_tracking.status

import org.apache.kafka.common.serialization.Deserializer
import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.common.serialization.Serializer

class PackageDeliveryStatusSerde: Serde<PackageDeliveryStatus> {
    override fun serializer(): Serializer<PackageDeliveryStatus?>? {
        TODO("Not yet implemented")
    }

    override fun deserializer(): Deserializer<PackageDeliveryStatus?>? {
        TODO("Not yet implemented")
    }
}