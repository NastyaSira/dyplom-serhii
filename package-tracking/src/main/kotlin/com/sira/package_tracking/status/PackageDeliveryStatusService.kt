package com.sira.package_tracking.status

import com.sira.package_tracking.event.PackageID

interface PackageDeliveryStatusService {

    fun findPackagesDeliveryStatus(
        packageID: PackageID
    ): PackageDeliveryStatus?
}