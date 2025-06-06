package com.sira.package_tracking.subsription.notification

import com.twilio.Twilio
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class TwilioConfiguration(
    @Value("\${twilio.account-sid}") val accountSid: String,
    @Value("\${twilio.auth-token}") val authToken: String,
    @Value("\${twilio.phone-number}") val phoneNumber: String
) {
    @PostConstruct
    fun twilioInit() {
        // initializing Twilio
        Twilio.init(
            accountSid,
            authToken
        )
    }
}
