package com.sira.package_tracking.subsription

import com.sira.package_tracking.event.PackageDeliveryEvent


interface DeliveryPackageNotificationService {

    fun deliverySMSNotification(
        phone: PhoneNumber,
        event: PackageDeliveryEvent
    )

    fun deliveryEmailNotification(
        phone: PhoneNumber,
        event: PackageDeliveryEvent
    )

}