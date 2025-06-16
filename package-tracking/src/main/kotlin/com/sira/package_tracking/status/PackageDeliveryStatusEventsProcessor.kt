package com.sira.package_tracking.status

import com.sira.package_tracking.event.PackageDeliveryEvent
import org.springframework.stereotype.Service
import java.lang.RuntimeException

@Service
class PackageDeliveryStatusEventsProcessor {

    fun processPackageEvent(
        event: PackageDeliveryEvent,
        currentStatus: PackageDeliveryStatus
    ): PackageDeliveryStatus {
        if (event.packageID != currentStatus.packageID) {
            throw RuntimeException("Mismatching ids of event(${event.packageID}) and status(${currentStatus.packageID}) for ${event.packageID})")
        }

        return PackageDeliveryStatus(
            packageID = event.packageID,
            lastUpdateTimestamp = event.timestamp,
            history = currentStatus.history + event
        )
    }
}