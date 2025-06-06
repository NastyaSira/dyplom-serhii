package com.sira.package_tracking.subsription

import com.sira.package_tracking.event.PackageID
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component

@Component
class PersistentDeliveryPackageSubscriptionService(
    private val db: JdbcTemplate
) : DeliveryPackageSubscriptionService {

    override fun registerSubscription(subscription: DeliverySubscription) {
        when (subscription) {
            is EmailDeliverySubscription ->
                db.update("insert into delivery_package_subscriptions (package_id, email)",
                    subscription.packageID.value, subscription.email.value
                )
            is PhoneDeliverySubscription ->
                db.update("insert into delivery_package_subscriptions (package_id, phone_number)",
                    subscription.packageID.value, subscription.phone.value
                )
        }
    }


    override fun findActiveSubscriptionsFor(packageID: PackageID): List<DeliverySubscription> {
        return db.query("select * from delivery_package_subscriptions where package_id = ${packageID.value}",
            { row, rowNum ->
            val packageID = PackageID(row.getString("package_id"))
            val email = row.getString("email")
            val phoneNumber = row.getString("phone_number")

            if (email != null) {
                EmailDeliverySubscription(EmailAddress(email), packageID)
            } else if (phoneNumber != null) {
                PhoneDeliverySubscription(PhoneNumber(phoneNumber), packageID)
            } else {
                null
            }
        }).filterNotNull()
    }
}