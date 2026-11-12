package com.nea.candid.services;

import com.nea.candid.data.dto.ResponseBody;
import com.nea.candid.services.database.ChatsDbService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class ChatService {

    private final ChatsDbService chatsDbService;

    public ChatService(ChatsDbService chatsDbService) {
        this.chatsDbService = chatsDbService;
    }

    public ResponseBody addChat(String message, long bookingId) {

        LocalDateTime now = LocalDateTime.now();

        try{

            chatsDbService.addChat(message, bookingId, now);
            return ResponseBody.success("added to database", 952);

        }catch(Exception e){

            return ResponseBody.error("chat was not sent", 903);

        }

    }

    public ResponseBody getChats(long bookingId) {

        return ResponseBody.success(chatsDbService.getChats(bookingId), 952);

    }

}
