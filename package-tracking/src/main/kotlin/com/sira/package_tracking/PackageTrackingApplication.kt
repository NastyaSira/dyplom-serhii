package com.sira.package_tracking

import org.apache.kafka.streams.KafkaStreams
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Service
import java.util.Properties

@SpringBootApplication
class PackageTrackingApplication

fun main(args: Array<String>) {
	runApplication<PackageTrackingApplication>(*args)
}

@Service
class KafkaStreamsTopologiesInitializer(
	private val topologies: List<KafkaStreamsTopology>,
) : ApplicationListener<ApplicationReadyEvent> {

	override fun onApplicationEvent(event: ApplicationReadyEvent) {
//		val props = Properties()
//		topologies.forEach {
//			val topology = it.createTopology()
//
//			KafkaStreams(topology, props)
//				.start()
//		}
	}
}
