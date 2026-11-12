package com.nea.candid.api.controllers;

import com.nea.candid.data.dbEnties.ChatsTableEntity;
import com.nea.candid.data.dto.ResponseBody;
import com.nea.candid.services.ChatService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/chat")
public class ChatsController {

    private final ChatService chatService;

    public ChatsController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/getchats")
    public ResponseBody getChats(@RequestBody Map<String, Long> body) {

        return chatService.getChats(body.get("bookingId"));

    }

    @PostMapping("/addchat")
    public ResponseBody addChat(@RequestBody ChatsTableEntity body) {

        return chatService.addChat(body.getChatmessage(), body.getBookingid());

    }

}
