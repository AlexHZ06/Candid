package com.nea.candid.api.controllers;

import com.nea.candid.data.dataObjects.JwtObject;
import com.nea.candid.data.dto.ResponseBody;
import com.nea.candid.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@RestController
@RequestMapping("/jwt")
public class JwtController {

    private final JwtService jwtServices;

    public JwtController(JwtService jwtServices) {
        this.jwtServices = jwtServices;
    }

    @PostMapping("/public/requestjwt")
    public ResponseEntity requestJwt(HttpServletRequest request) {

        ResponseBody responseBody = jwtServices.requestJwt(Long.parseLong((String) request.getAttribute("userId")), request.getHeader("refreshTokenUUID"));

        if(responseBody.isSucsess()){

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);

        }
        else{

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);

        }

    }

    @PostMapping("/public/checkjwtphotographer")
    public boolean checkPhotographerJwt(HttpServletRequest request) {

        String token =  request.getHeader("auth");
        JwtObject jwt = jwtServices.decodeJwt(token);
        if(jwt == null){

            return false;

        }
        try{
            return jwt.getExpired() == false && jwt.getInvalid() == false && jwt.getClaims().get("role").equals("photographer");
        }catch(Exception e){

            return false;

        }


    }

    @PostMapping("/public/checkjwtclient")
    public boolean checkClientJwt(HttpServletRequest request) {

        String token =  request.getHeader("auth");
        JwtObject jwt = jwtServices.decodeJwt(token);
        try{
            return jwt.getExpired() == false &&  jwt.getInvalid() == false && jwt.getClaims().get("role").equals("client");
        }catch(Exception e){

            return false;

        }


    }

}
