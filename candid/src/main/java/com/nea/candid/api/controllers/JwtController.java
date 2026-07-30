package com.nea.candid.api.controllers;

import com.nea.candid.data.dto.ResponseBody;
import com.nea.candid.services.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/jwt")
public class JwtController {

    private final JwtService jwtServices;

    public JwtController(JwtService jwtServices) {
        this.jwtServices = jwtServices;
    }

    @PostMapping("/public/requestjwt")
    public ResponseEntity requestJwt(@RequestBody Map<String, Long> body){

        ResponseBody responseBody = jwtServices.requestJwt(body.get("userId"));

        if(responseBody.isSucsess()){

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);

        }
        else{

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);

        }

    }

}
