package io.simpolor.websocket.controller;

import io.simpolor.websocket.model.GreetingDto;
import io.simpolor.websocket.model.HelloMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
@RequiredArgsConstructor
public class GreetingController {

    private final SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public GreetingDto greeting(HelloMessageDto request) throws Exception {
        Thread.sleep(1000); // simulated delay

        return new GreetingDto("Hello, " + HtmlUtils.htmlEscape(request.getName()) + "!");
    }
}
