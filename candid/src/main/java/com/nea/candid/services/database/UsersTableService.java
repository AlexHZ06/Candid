package com.nea.candid.services.database;

import com.nea.candid.data.dbEnties.UsersTableEntity;
import com.nea.candid.repositories.UsersTableRepo;
import org.springframework.stereotype.Service;

@Service
public class UsersTableService {

    private final UsersTableRepo usersTableRepo;

    public UsersTableService(UsersTableRepo usersTableRepo) {

        this.usersTableRepo = usersTableRepo;

    }

    public UsersTableEntity getUserByUserName(String userName) {
        return usersTableRepo.getUserByUserName(userName);
    }

    public UsersTableEntity getUserById(long userId) {
        return usersTableRepo.getUserById(userId);
    }

}
