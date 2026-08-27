package com.nea.candid.services;

import com.nea.candid.data.dbEnties.UsersTableEntity;
import com.nea.candid.data.dataObjects.JwtObject;
import com.nea.candid.data.dto.LogInResponse;
import com.nea.candid.data.dto.ResponseBody;
import com.nea.candid.services.database.UsersDbService;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsersDbService usersTableService;
    private final JwtService jwtService;


    public AuthService(UsersDbService usersTableService, JwtService jwtService) {
        this.usersTableService = usersTableService;
        this.jwtService = jwtService;
    }

    public ResponseBody logInUser(String username, String password) {

        UsersTableEntity record = usersTableService.getUserByUserName(username);

        if(record == null){

            return ResponseBody.error("User does not exist", 101);

        }
        else{

            if(password.equals(record.getHashedpassword())){

                jwtService.removeJwt(record.getUserid());

                String refreshJwt = jwtService.buildJwt(record.getUserid(), record.getUsertype(), true);
                String requestJwt  = jwtService.buildJwt(record.getUserid(), record.getUsertype(), false);

                JwtObject refreshJwtObject = jwtService.decodeJwt(refreshJwt);

                try{

                    String UUID = jwtService.addJwt(refreshJwtObject, refreshJwt);
                    return ResponseBody.success(new LogInResponse(refreshJwt, UUID), 151);

                }
                catch(Exception e){

                    return ResponseBody.error("Could not save Refresh Token ", 102);

                }

            }
            else{

                return ResponseBody.error("Incorrect details", 102);

            }

        }

    }

    public ResponseBody logOut(long userId){

        jwtService.removeJwt(userId);
        return ResponseBody.success("logged out", 153);

    }

    //TODO implement signup
    public void SignUp(String username, String password){

        //TODO Check if user name exists
        //TODO email validation
        //TODO Preferences saved
        //TODO add all details to database

    }

    //TODO Delete User
    public void deleteUser(){

        //TODO check if user has any bookings if so they cannot delete untill they are cancelled or completled
        //TODO Delete all users search history
        //TODO Delete any uploads
        //TODO Delete any activity
        //TODO Delete user from user table

    }

}
