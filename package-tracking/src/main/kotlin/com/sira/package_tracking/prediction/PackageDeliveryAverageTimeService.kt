package com.sira.package_tracking.prediction

import com.sira.package_tracking.event.Address
import java.time.Duration

data class DeliverySegment(
    val from: Address,
    val to: Address,
)

data class PathDeliveryAverageTimeStatistics(
    val allTimeAverage: Duration,
) {

    operator fun plus(other: PathDeliveryAverageTimeStatistics) = PathDeliveryAverageTimeStatistics(
        allTimeAverage + other.allTimeAverage,
    )

    companion object {
        fun empty() = PathDeliveryAverageTimeStatistics(
            Duration.ZERO,
        )
    }
}

fun List<PathDeliveryAverageTimeStatistics>.merge(): PathDeliveryAverageTimeStatistics? {
    if (isEmpty()) return null

    return PathDeliveryAverageTimeStatistics(
        allTimeAverage =  Duration.ofSeconds(sumOf{it.allTimeAverage.seconds} / size)
    )
}


interface PackageDeliveryAverageTimeService {

    fun findAverageTimeStatsForSegments(
        paths: List<DeliverySegment>
    ): Map<DeliverySegment, PathDeliveryAverageTimeStatistics>
}