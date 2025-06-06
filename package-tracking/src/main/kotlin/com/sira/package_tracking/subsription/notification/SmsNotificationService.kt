package com.sira.package_tracking.subsription.notification

import com.sira.package_tracking.event.PackageDeliveryEvent
import com.sira.package_tracking.event.PackageDeliveryEventType
import com.twilio.rest.api.v2010.account.Message
import com.twilio.type.PhoneNumber
import org.springframework.stereotype.Service

@Service
class SmsNotificationService(
    private val twilioConfig: TwilioConfiguration
) {

    fun sendSMS(
        phoneNumber: com.sira.package_tracking.subsription.PhoneNumber,
        event: PackageDeliveryEvent
    ) {

        val message = when (event.event) {
            PackageDeliveryEventType.DELIVERED -> "The package ${event.packagedID} is delivered"
            PackageDeliveryEventType.ARRIVED_TO_LOCATION -> "The package ${event.packagedID} is arrived to location"
            PackageDeliveryEventType.DISPATCHED_FROM_LOCATION -> "The package ${event.packagedID} is dispatched from location"
        }


        Message.creator(
            PhoneNumber(phoneNumber.value),
            PhoneNumber(twilioConfig.phoneNumber),
            message
        ).create()
    }
}