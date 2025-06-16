package com.sira.package_tracking.event

import java.time.LocalDateTime


@JvmInline
value class PackageID(
    val value: String
)

data class PackageDeliveryEvent(
    val packageID: PackageID,
    val event: PackageDeliveryEventType,
    val timestamp: LocalDateTime,

    val deliveryAddress: Address,

    val currentAddress: Address,
)

data class Address(
    val street: String,
    val city: String,
    val zipCode: String,
    val countryISOCode: String,

    val addressName: String? = null,
)

enum class PackageDeliveryEventType {
    DISPATCHED_FROM_LOCATION,
    ARRIVED_TO_LOCATION,
    DELIVERED,
    NOT_FOUND,
}
