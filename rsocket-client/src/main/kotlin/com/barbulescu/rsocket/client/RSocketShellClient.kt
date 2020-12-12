package com.barbulescu.rsocket.client

import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class RSocketShellClient(rsocketRequesterBuilder: RSocketRequester.Builder) {
    val rsocketRequester: RSocketRequester = rsocketRequesterBuilder
        .tcp("localhost", 7000)

    @ShellMethod("Send one request. One response will be printed.")
    @Throws(InterruptedException::class)
    fun requestResponse() {
        println("\nSending one request. Waiting for one response...")
        val message = rsocketRequester
            .route("request-response")
            .data(Message("client1", "request1"))
            .retrieveMono(Message::class.java)
            .block()
        println("\nResponse was: $message")
    }
}