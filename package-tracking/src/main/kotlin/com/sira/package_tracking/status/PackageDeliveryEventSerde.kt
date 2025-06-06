package com.sira.package_tracking.status

import com.sira.package_tracking.event.PackageDeliveryEvent
import org.apache.kafka.common.serialization.Deserializer
import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.common.serialization.Serializer

class PackageDeliveryEventSerde : Serde<PackageDeliveryEvent> {

    override fun serializer(): Serializer<PackageDeliveryEvent?>? {
        TODO("Not yet implemented")
    }

    override fun deserializer(): Deserializer<PackageDeliveryEvent?>? {
        TODO("Not yet implemented")
    }
}