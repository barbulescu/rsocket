package com.barbulescu.rsocket.server

import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller


@Controller
class RSocketController {

    @MessageMapping("request-response")
    fun requestResponse(request: Message?): Message {
        println("Received request-response request: $request")
        return Message("server1", "response1")
    }
}