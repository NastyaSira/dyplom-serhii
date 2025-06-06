package com.sira.package_tracking.subsription

import com.sira.package_tracking.event.PackageDeliveryEvent
import org.springframework.stereotype.Service

@Service
class DeliveryPackageSubscriptionEventsProcessor(
    private val notificationService: DeliveryPackageNotificationService,
    private val subscriptionService: DeliveryPackageSubscriptionService
) {

    fun processEvent(
        event: PackageDeliveryEvent,
    ) {
        event.packagedID.let { subscriptionService.findActiveSubscriptionsFor(it)}
            .forEach {
                when (it) {
                    is EmailDeliverySubscription -> notificationService.sendEmailNotification(
                        it.email,
                        event,
                    )
                    is PhoneDeliverySubscription -> notificationService.sendSMSNotification(
                        it.phone,
                        event,
                    )
                }
            }
    }
}