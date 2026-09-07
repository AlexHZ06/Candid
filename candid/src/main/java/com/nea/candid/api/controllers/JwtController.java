package com.nea.candid.api.controllers;

import com.nea.candid.data.dataObjects.JwtObject;
import com.nea.candid.data.dto.ResponseBody;
import com.nea.candid.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Response;
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

        if(responseBody.isSuccess()){

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);

        }
        else{

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);

        }

    }

    @PostMapping("/public/checkjwtphotographer")
    public ResponseBody checkPhotographerJwt(HttpServletRequest request) {

        String token =  request.getHeader("auth");
        JwtObject jwt = jwtServices.decodeJwt(token);
        if(jwt == null){

            return ResponseBody.error("invalid jwt", 201);

        }
        try{
            return ResponseBody.success(jwt.getExpired() == false && jwt.getInvalid() == false && jwt.getClaims().get("role").equals("photographer"), 251) ;
        }catch(Exception e){

            return ResponseBody.error("invalid jwt", 201);

        }


    }

    @PostMapping("/public/checkjwtclient")
    public ResponseBody checkClientJwt(HttpServletRequest request) {

        String token =  request.getHeader("auth");
        JwtObject jwt = jwtServices.decodeJwt(token);
        if(jwt == null){

            return ResponseBody.error("invalid jwt", 201);

        }
        try{
            return ResponseBody.success(jwt.getExpired() == false &&  jwt.getInvalid() == false && jwt.getClaims().get("role").equals("client"), 251);
        }catch(Exception e){

            return ResponseBody.error("invalid jwt", 201);

        }


    }

}
