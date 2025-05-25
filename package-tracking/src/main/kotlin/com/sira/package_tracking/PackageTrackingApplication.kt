package com.sira.package_tracking

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PackageTrackingApplication

fun main(args: Array<String>) {
	runApplication<PackageTrackingApplication>(*args)
}
