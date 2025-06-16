package com.sira.package_tracking.subsription.notification

import com.sira.package_tracking.event.PackageDeliveryEvent
import com.sira.package_tracking.event.PackageDeliveryEventType
import com.sira.package_tracking.subsription.EmailAddress
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailNotificationService(
    val sender: JavaMailSender
) {

    fun sendEmailNotification(
        email: EmailAddress,
        event: PackageDeliveryEvent
    ) {

        val message = when (event.event) {
            PackageDeliveryEventType.NOT_FOUND -> "The package ${event.packageID} is not found"
            PackageDeliveryEventType.DELIVERED -> "The package ${event.packageID} is delivered"
            PackageDeliveryEventType.ARRIVED_TO_LOCATION -> "The package ${event.packageID} is arrived to location ${event.currentAddress}"
            PackageDeliveryEventType.DISPATCHED_FROM_LOCATION -> "The package ${event.packageID} is dispatched from location ${event.currentAddress}"
        }

        sender.send(SimpleMailMessage().apply {
            setTo(email.value)
            text = message
        })
    }
}