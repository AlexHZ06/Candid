package com.nea.candid.services.database;

import com.nea.candid.data.dbEnties.ChatsTableEntity;
import com.nea.candid.repositories.ChatsTableRepo;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatsDbService {

    private final ChatsTableRepo chatsTableRepo;

    public ChatsDbService(ChatsTableRepo chatsTableRepo) {
        this.chatsTableRepo = chatsTableRepo;
    }

    public List<ChatsTableEntity> getChats(long bookingId) {

        return  chatsTableRepo.getChats(bookingId);

    }

    public void addChat(String message, long bookingId, LocalDateTime timestamp) {

        int result = chatsTableRepo.addChat(message, bookingId, timestamp);
        if(result == 0) {

            throw new RuntimeException("can not add chat");

        }

    }

}
