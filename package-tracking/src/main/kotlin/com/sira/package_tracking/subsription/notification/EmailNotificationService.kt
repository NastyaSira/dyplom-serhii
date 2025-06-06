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
            PackageDeliveryEventType.DELIVERED -> "The package ${event.packagedID} is delivered"
            PackageDeliveryEventType.ARRIVED_TO_LOCATION -> "The package ${event.packagedID} is arrived to location"
            PackageDeliveryEventType.DISPATCHED_FROM_LOCATION -> "The package ${event.packagedID} is dispatched from location"
        }

        sender.send(SimpleMailMessage().apply {
            setTo(email.value)
            text = message
        })
    }
}