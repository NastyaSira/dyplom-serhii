package com.sira.package_tracking.subsription

import com.sira.package_tracking.event.PackageDeliveryEvent


interface DeliveryPackageNotificationService {

    fun sendSMSNotification(
        phone: PhoneNumber,
        event: PackageDeliveryEvent
    )

    fun sendEmailNotification(
        email: EmailAddress,
        event: PackageDeliveryEvent
    )
}