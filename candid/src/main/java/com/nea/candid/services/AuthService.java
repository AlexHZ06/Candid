package com.nea.candid.services;

import com.nea.candid.data.dbEnties.UsersTableEntity;
import com.nea.candid.data.dto.JwtJwsBody;
import com.nea.candid.data.dto.ResponseBody;
import com.nea.candid.services.database.UsersTableService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsersTableService usersTableService;
    private final JwtService jwtService;


    public AuthService(UsersTableService usersTableService, JwtService jwtService) {
        this.usersTableService = usersTableService;
        this.jwtService = jwtService;
    }

    public ResponseBody logInUser(String username, String password) {

        ResponseBody responseBody = new ResponseBody("Message", 151, true);

        UsersTableEntity record = usersTableService.getUserByUserName(username);

        if(record == null){

            return new ResponseBody("User does not exist", 101, false);

        }
        else{

            if(password.equals(record.getHashedpassword())){

                jwtService.removeJwt(record.getUserid());

                String refreshJwt = jwtService.buildJwt(record.getUserid(), record.getUsertype(), true);

                JwtJwsBody refreshJwtJwsBody = jwtService.decodeJwt(refreshJwt);

                jwtService.addJwt(refreshJwtJwsBody, refreshJwt);

                return new ResponseBody("logged in", 151, true);

            }
            else{

                return new ResponseBody("Incorrect details", 102, false);

            }

        }

    }

    public ResponseBody logOut(long userId){

        jwtService.removeJwt(userId);
        return  new ResponseBody("logged out", 151, true);

    }

}
