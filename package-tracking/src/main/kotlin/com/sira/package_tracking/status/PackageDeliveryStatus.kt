package com.sira.package_tracking.status

import com.sira.package_tracking.event.PackageDeliveryEvent
import com.sira.package_tracking.event.PackageID
import java.time.LocalDateTime

data class PackageDeliveryStatus (
    val packageID: PackageID,
    val lastUpdateTimestamp: LocalDateTime,


    val history: List<PackageDeliveryEvent>
)

val EMPTY_STATUS = PackageDeliveryStatus (
    packageID = PackageID(""),
    lastUpdateTimestamp = LocalDateTime.now(),
    history = emptyList()
)