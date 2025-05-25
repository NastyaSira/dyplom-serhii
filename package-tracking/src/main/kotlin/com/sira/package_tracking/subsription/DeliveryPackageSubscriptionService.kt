package com.sira.package_tracking.subsription

import com.sira.package_tracking.event.PackageID

@JvmInline
value class PhoneNumber(
    val value: String
)

@JvmInline
value class EmailAddress(
    val value: String
)

sealed class DeliverySubscription(
    val packageID: PackageID
)

class PhoneDeliverySubscription(
    val phone: PhoneNumber,
    packageID : PackageID
): DeliverySubscription(packageID)

class EmailDeliverySubscription(
    val email: EmailAddress,
    packageID : PackageID
): DeliverySubscription(packageID)


interface DeliveryPackageSubscriptionService {

    fun registerSubscription(
        subscription: DeliverySubscription,
    )

    fun findActiveSubscriptionsFor(
        packageID: PackageID
    ): List<DeliverySubscription>
}