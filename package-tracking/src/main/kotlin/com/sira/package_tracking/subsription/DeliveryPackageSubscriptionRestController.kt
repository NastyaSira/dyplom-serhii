package com.sira.package_tracking.subsription

import com.sira.package_tracking.event.PackageID
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

data class EmailSubscriptionRequest(val email: String, val packageID: PackageID)
data class SMSSubscriptionRequest(val phoneNumber: String, val packageID: PackageID)

@RestController
class DeliveryPackageSubscriptionRestController(
    private val service: DeliveryPackageSubscriptionService
) {

    @PostMapping("/subscribe/email")
    fun subscribeForUpdatesWithEmail(
        request: EmailSubscriptionRequest
    ) {

        service.registerSubscription(
            EmailDeliverySubscription(
                email = EmailAddress(request.email),
                packageID = request.packageID
            )
        )
    }

    @PostMapping("/subscribe/sms")
    fun subscribeForUpdatesWithEmail(
        request: SMSSubscriptionRequest
    ) {
        service.registerSubscription(
            PhoneDeliverySubscription(
                phone = PhoneNumber(request.phoneNumber),
                packageID = request.packageID
            )
        )
    }
}