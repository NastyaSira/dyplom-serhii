package com.sira.package_tracking.status

import com.sira.package_tracking.event.PackageDeliveryEvent
import java.lang.RuntimeException

class PackageDeliveryStatusEventsProcessor {

    fun processPackageEvent(
        event: PackageDeliveryEvent,
        currentStatus: PackageDeliveryStatus
    ): PackageDeliveryStatus {
        if (event.packagedID != currentStatus.packageID) {
            throw RuntimeException("Mismatching ids of event(${event.packagedID}) and status(${currentStatus.packageID}) for ${event.packagedID})")
        }

        return PackageDeliveryStatus(
            packageID = event.packagedID,
            lastUpdateTimestamp = event.timestamp,
            history = currentStatus.history + event
        )
    }
}