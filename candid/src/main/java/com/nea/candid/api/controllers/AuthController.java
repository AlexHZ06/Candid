package com.nea.candid.api.controllers;



import com.nea.candid.services.AuthService;
import com.nea.candid.data.dto.ResponseBody;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseBody logUserIn(@RequestBody Map<String, String> body) {

        ResponseBody responseBody = authService.logInUser(body.get("userName"), body.get("password"));

        if(responseBody.isSuccess()){

            System.out.println("all good");
            return responseBody;

        }
        else{

            return responseBody;

        }

    }

    @PostMapping("/public/logout")
    public ResponseEntity logOutUser(HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.OK).body(authService.logOut(Long.parseLong(request.getAttribute("userId").toString())));

    }

}
