package com.barbulescu.rsocket.client

import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean


@SpringBootApplication
class RsocketClientApplication(private val client: RSocketClient) {

	@Bean
	fun clientRunner(): ApplicationRunner? {
		return ApplicationRunner {
			println("Starting client")
			println ("---------------------- Request-Response ------------------------------")
			client.requestResponse()
			println ("---------------------- Fire-Forget ------------------------------")
			client.fireAndForget()
			println ("---------------------- Stream ------------------------------")
			val disposable = client.stream()
			Thread.sleep(5_000)
			disposable?.dispose()
		}
	}
}

fun main(args: Array<String>) {
	runApplication<RsocketClientApplication>(*args)
}

