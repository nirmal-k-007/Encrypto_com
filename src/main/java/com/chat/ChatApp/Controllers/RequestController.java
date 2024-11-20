package com.chat.ChatApp.Controllers;

import com.chat.ChatApp.Models.UpdateData;
import com.chat.ChatApp.Models.UserData;
import com.chat.ChatApp.Services.RequestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;



@RestController
@CrossOrigin
public class RequestController {


    @Autowired
    RequestService rs;

    @PostMapping("/generation")
    public String generateOTPController(@RequestBody UserData data) throws JsonProcessingException {
        return rs.generateOTPservice(data);
    }

    @PostMapping("/verification")
    public String verifyUserController(@RequestBody UserData data) throws JsonProcessingException{
        return rs.verifyUserService(data);
    }

    @PostMapping("/registration")
    public String registerUserController(@RequestBody UserData data) throws JsonProcessingException {
        return rs.registerUserService(data);
    }

    @PostMapping("/login")
    public String loginUserController(@RequestBody UserData data) {
        System.out.println(data.getPublic_key());
        return rs.loginUserService(data);
    }

    @PostMapping("/updateCredentials")
    public String updateUserCredentialsController(@RequestBody UpdateData data) {
        return rs.updateUserCredentialsService(data);
    }



}