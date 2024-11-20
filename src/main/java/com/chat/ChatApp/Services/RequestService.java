package com.chat.ChatApp.Services;

import java.security.*;
import java.util.Base64;
import java.util.Random;

import com.chat.ChatApp.Models.UpdateData;
import com.chat.ChatApp.Models.UserData;
import com.chat.ChatApp.Repositary.JDBCRepositary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class RequestService {

    @Autowired
    JDBCRepositary repo;

    @Autowired
    EmailService es;


    String otp;

    public static int generateRandomNumber() {
        Random random = new Random();
        // Generate a random 6-digit number between 100000 and 999999 (inclusive)
        return 100000 + random.nextInt(900000); // 900000 is the range (999999 - 100000 + 1)
    }

    public String generateOTPservice(UserData data) {
        if(!repo.availability(data.getEmail(),"Users","email")) {
            otp = Integer.toString(generateRandomNumber());
            boolean response = es.sendEmail(data.getEmail(), "Chat Application Registration", "Your One-Time password for registration of your Chat Application account is " + otp + ".");
            System.out.println(otp);
            if(response) {
                return("Generated");
            }
            return "Error";
        }
        else {
            return "Already Registered\nKindly Login";
        }
    }

    public String verifyUserService(UserData data) {
        if(otp.equals(data.getOtp()))
        {
            return "OTP Verified";
        }
        else
        {
            return "Wrong OTP\nTry Again Later";
        }
    }

    public String registerUserService(UserData data) {
        if(repo.registerUserRepo(data)>0)
        {
            return "User Created Successfully!!";
        }
        else
        {
            return "Failed to create user\nTry Again Later";
        }
    }

    public String loginUserService(UserData data) {
        return repo.loginUserRepositary(data);
    }

    public String updateUserCredentialsService(UpdateData data) {
        if(repo.updateUserCredentialsRepo(data)>0)
        {
            return "User Updated Successfully!!";
        }
        else {
            return "Failed";
        }
    }
}
