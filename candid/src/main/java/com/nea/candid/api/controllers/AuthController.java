package com.nea.candid.api.controllers;



import com.nea.candid.services.AuthService;
import com.nea.candid.data.dto.LogInUserBody;
import com.nea.candid.data.dto.ResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/public/login")
    public ResponseEntity logUserIn(@RequestBody LogInUserBody body) {

        ResponseBody responseBody = authService.logInUser(body.getUserName(), body.getPassword());

        if(responseBody.isSucsess()){

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);

        }
        else{

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);

        }

    }

    @PostMapping("/public/logout")
    public ResponseEntity logOutUser(@RequestBody Map<String, Long> body) {

        return ResponseEntity.status(HttpStatus.OK).body(authService.logOut(body.get("userId")));

    }

}
