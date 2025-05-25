package com.sira.package_tracking.event

data class PackageDeliveryEvent(
    val packagedID: String,
    val event: PackageDeliveryEventType,

    val deliveryAddress: Address,

    val currentLocation: Address,
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
}
