package com.nea.candid.services.database;

import com.nea.candid.data.dbEnties.UsersTableEntity;
import com.nea.candid.repositories.UsersTableRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersDbService {

    private final UsersTableRepo usersTableRepo;

    public UsersDbService(UsersTableRepo usersTableRepo) {

        this.usersTableRepo = usersTableRepo;

    }

    public UsersTableEntity getUserByUserName(String userName) {
        return usersTableRepo.getUserByUserName(userName);
    }

    public UsersTableEntity getUserById(long userId) {
        return usersTableRepo.getUserById(userId);
    }

    public List<Long> getPhotographerByDistanceBounds(double minLat, double minLon, double maxLat, double maxLon){

        return usersTableRepo.getPhotographerByDistanceBounds(minLat, minLon, maxLat, maxLon);

    }

    public List<UsersTableEntity> getAllPhotographers(){

        return usersTableRepo.getAllPhotographer();

    }

    public List<UsersTableEntity> getUsersById(List<Long> idList){

        List<UsersTableEntity> result = usersTableRepo.getUsersById(idList);
        if(result.size() == 0 || result == null){

            throw new RuntimeException("no users found");

        }
        else{

            return result;

        }

    }

}
