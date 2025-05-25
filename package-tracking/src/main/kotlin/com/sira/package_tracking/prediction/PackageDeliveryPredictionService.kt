package com.sira.package_tracking.prediction

import com.sira.package_tracking.status.PackageDeliveryStatus

class PackageDeliveryPredictionService(
    private val routeService: PackageDeliveryRouteService,
    private val averageTimeService: PackageDeliveryAverageTimeService,
) {

    fun predictAverageTimeToDeliver(
        status: PackageDeliveryStatus,
    ): PathDeliveryAverageTimeStatistics? {
        val from = status.history.first().currentAddress
        val to = status.history.first().deliveryAddress

        val startingNode = routeService.findKnownRoutes(from, to) ?: return null

        val stats = calculateTimeStatisticsRoutes(startingNode)
        return calculateAverageDeliveryTime(
            PathDeliveryAverageTimeStatistics.empty(),
            startingNode,
            stats,
        )
    }

    private fun calculateTimeStatisticsRoutes(startingNode: DeliveryRouteGraphNode): Map<DeliverySegment, PathDeliveryAverageTimeStatistics> {
        val segments = generateSequence(
            startingNode.nextNodes.map { nextNode -> startingNode to nextNode }
        ) {
            it.flatMap { (_, to) -> to.nextNodes.map { nextNode -> to to nextNode } }
        }.toList()
            .flatten()
            .map { (from, to) -> DeliverySegment(from.address, to.address) }

        // TODO. We may not have a delivery stats for exact address, but we may have deliveries to the same city.
        //  So for the last step we should load average delivery across all packages in that city
        //  That should be stored probably in the separate storage, something like average_city_delivery_time and will require separate event processor.
        return averageTimeService.findAverageTimeStatsForSegments(segments)
    }


    private fun calculateAverageDeliveryTime(
        currentStats: PathDeliveryAverageTimeStatistics,
        startingNode: DeliveryRouteGraphNode,
        historicStatistics: Map<DeliverySegment, PathDeliveryAverageTimeStatistics>
    ): PathDeliveryAverageTimeStatistics? {

        if (startingNode.nextNodes.isEmpty()) {
            return currentStats
        }

        return startingNode.nextNodes.map {
            val from = startingNode.address
            val to = it.address

            val timeStats = historicStatistics[DeliverySegment(from, to)] ?: return@map null

            calculateAverageDeliveryTime(
                currentStats + timeStats,
                it,
                historicStatistics
            )
        }.filterNotNull()
            .merge()
    }
}