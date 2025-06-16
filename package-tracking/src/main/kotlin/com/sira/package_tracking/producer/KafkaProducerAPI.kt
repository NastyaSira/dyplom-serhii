package com.sira.package_tracking.producer

import com.sira.package_tracking.event.Address
import com.sira.package_tracking.event.PackageDeliveryEvent
import com.sira.package_tracking.event.PackageDeliveryEventType
import com.sira.package_tracking.event.PackageID
import com.sira.package_tracking.subsription.DeliveryPackageSubscriptionEventsProcessor
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

data class PackageDeliveryUpdate(
    val event: PackageDeliveryEventType,
    val timestamp: LocalDateTime,
    val address: Address,
)

data class PackageDeliveryUpdates(
    val packageID: PackageID,
    val lastUpdate: PackageDeliveryEventType,
    val lastUpdateTimestamp: LocalDateTime,

    val currentLocation: Address?,
    val history: List<PackageDeliveryUpdate>?
)

@RestController
class KafkaProducerAPI(
    private val subscriptionProcessor: DeliveryPackageSubscriptionEventsProcessor,
) {

    private val updates = mutableMapOf<PackageID, PackageDeliveryUpdates>()

    @PostMapping("/produce_event")
    fun publishEvent(
        @RequestBody event: PackageDeliveryEvent
    ) {
        subscriptionProcessor.processEvent(event)

        val currentState = updates[event.packageID]

        if (currentState == null) {
            val newState = PackageDeliveryUpdates(
                packageID = event.packageID,
                lastUpdateTimestamp = event.timestamp,
                currentLocation = event.currentAddress,
                lastUpdate = event.event,

                history = listOf(
                    PackageDeliveryUpdate(
                        event = event.event,
                        timestamp = event.timestamp,
                        address = event.currentAddress,
                    )
                )
            )

            updates[event.packageID] = newState
        } else {
            updates[event.packageID] = PackageDeliveryUpdates(
                packageID = event.packageID,
                lastUpdateTimestamp = event.timestamp,
                lastUpdate = event.event,
                currentLocation = event.currentAddress,

                history = (currentState.history ?: listOf()) + PackageDeliveryUpdate(
                    event = event.event,
                    timestamp = event.timestamp,
                    address = event.currentAddress,
                )
            )
        }
    }

    @GetMapping("/package_status")
    fun packageStatus(
        @RequestParam packageId: String
    ): PackageDeliveryUpdates {
        val status = updates[PackageID(packageId)]
        if (status == null) {
            return PackageDeliveryUpdates(
                packageID = PackageID(packageId),
                lastUpdate =  PackageDeliveryEventType.NOT_FOUND,
                lastUpdateTimestamp = LocalDateTime.now(),

                currentLocation = null,
                history = null,
                )
        }

        return status
    }
}