package com.sira.package_tracking.prediction

import com.sira.package_tracking.event.Address

data class DeliveryRouteGraphNode(
    val address: Address,
    val nextNodes: List<DeliveryRouteGraphNode>
)

interface PackageDeliveryRouteService {

    fun findKnownRoutes(
        from: Address,
        to : Address
    ): DeliveryRouteGraphNode?
}