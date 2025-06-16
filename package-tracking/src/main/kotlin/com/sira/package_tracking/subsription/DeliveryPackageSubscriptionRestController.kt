package com.sira.package_tracking.subsription

import com.sira.package_tracking.event.Address
import com.sira.package_tracking.event.PackageDeliveryEvent
import com.sira.package_tracking.event.PackageDeliveryEventType
import com.sira.package_tracking.event.PackageID
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.util.UUID

data class EmailSubscriptionRequest(val email: String, val packageID: PackageID)
data class SMSSubscriptionRequest(val phoneNumber: String, val packageID: PackageID)


data class TestEmailRequest(val packageID: PackageID)

@RestController
class DeliveryPackageSubscriptionRestController(
    private val service: DeliveryPackageSubscriptionService,
    private val processor: DeliveryPackageSubscriptionEventsProcessor,
) {

    @GetMapping("/")
    fun index() = "This is package tracking application using event based architecture"

    @GetMapping("/subscriptions")
    fun subscriptions() = service.findAllActiveSubscriptions()

    @PostMapping("/test_email")
    fun testEmail(
        @RequestBody request: TestEmailRequest,
    ) {
        processor.processEvent(PackageDeliveryEvent(
            packageID = request.packageID,
            event = PackageDeliveryEventType.DELIVERED,
            timestamp = LocalDateTime.now(),
            deliveryAddress = Address("43 Uvileyna st", "Krolevets", "41300", "UA", "Home"),
            currentAddress = Address("43 Uvileyna st", "Krolevets", "41300", "UA", "Home"),
        ))
    }


    @PostMapping("/subscribe/email")
    fun subscribeForUpdatesWithEmail(
        @RequestBody  request: EmailSubscriptionRequest
    ) {

        service.registerSubscription(
            EmailDeliverySubscription(
                email = EmailAddress(request.email),
                packageID = request.packageID
            )
        )
    }

    @PostMapping( "/subscribe/sms")
    fun subscribeForUpdatesWithEmail(
        @RequestBody request: SMSSubscriptionRequest
    ) {
        service.registerSubscription(
            PhoneDeliverySubscription(
                phone = PhoneNumber(request.phoneNumber),
                packageID = request.packageID
            )
        )
    }
}