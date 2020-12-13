package com.barbulescu.rsocket.server

import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux


@Controller
class RSocketController {

    @MessageMapping("request-response")
    fun requestResponse(request: Message?): Message {
        println("Received request-response request: $request")
        return Message("server1", "RESPONSE")
    }

    @MessageMapping("fire-and-forget")
    fun fireAndForget(request: Message?) {
        println("Received fire-and-forget request: request")
    }

    @MessageMapping("stream")
    fun stream(request: Message?): Flux<Message>? {
        println("Received stream request: request")
        return Flux
            .just(1L,2L,3L)
            .map { index ->
                Message(
                    "server1", "STREAM",
                    index!!
                )
            }
            .log()
    }
}