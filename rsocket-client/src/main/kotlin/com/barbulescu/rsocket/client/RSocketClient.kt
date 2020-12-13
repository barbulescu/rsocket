package com.barbulescu.rsocket.client

import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.stereotype.Component
import reactor.core.Disposable

@Component
class RSocketClient(rsocketRequesterBuilder: RSocketRequester.Builder) {
    val rsocketRequester: RSocketRequester? = rsocketRequesterBuilder
        .connectTcp("localhost", 7000)
        .block()

    fun requestResponse() {
        println("\nSending one request. Waiting for one response...")
        rsocketRequester?.let {
            val message = it
                .route("request-response")
                .data(Message("client1", "REQUEST"))
                .retrieveMono(Message::class.java)
                .block()
            println("\nResponse was: $message")
        }
    }

    fun fireAndForget() {
        println("\nFire-And-Forget. Sending one request. Expect no response (check server log)...")
        rsocketRequester?.let {
            it
                .route("fire-and-forget")
                .data(Message("client1", "FIRE_AND_FORGET"))
                .send()
                .block()
        }
    }

    fun stream(): Disposable? {
        println("\nRequest-Stream. Sending one request. Waiting for unlimited responses (Stop process to quit)...")
        return rsocketRequester
            ?.route("stream")
            ?.data(Message("client1", "STREAM"))
            ?.retrieveFlux(Message::class.java)
            ?.subscribe { er: Message? -> println("Response received: $er") }

    }
}