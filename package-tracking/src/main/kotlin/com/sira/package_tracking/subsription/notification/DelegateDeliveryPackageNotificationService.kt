package com.sira.package_tracking.subsription.notification

import com.sira.package_tracking.event.PackageDeliveryEvent
import com.sira.package_tracking.subsription.DeliveryPackageNotificationService
import com.sira.package_tracking.subsription.EmailAddress
import com.sira.package_tracking.subsription.PhoneNumber
import org.springframework.stereotype.Service

@Service
class DelegateDeliveryPackageNotificationService(
    private val emailService: EmailNotificationService,
    private val smsService: SmsNotificationService,
) : DeliveryPackageNotificationService {

    override fun sendSMSNotification(
        phone: PhoneNumber,
        event: PackageDeliveryEvent
    ) {
        TODO("Not yet implemented")
    }

    override fun sendEmailNotification(
        email: EmailAddress,
        event: PackageDeliveryEvent
    ) {
        emailService.sendEmailNotification(email, event)
    }
}
