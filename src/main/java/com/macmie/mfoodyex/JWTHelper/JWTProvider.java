package com.macmie.mfoodyex.JWTHelper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.macmie.mfoodyex.Model.UserMfoody;
import com.macmie.mfoodyex.POJO.UserMfoodyPOJO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Date;

/*
* 1. Create function creating Token
* 2. Decrypt/decode Token
* 3. Check Token
* */
@Slf4j
@Component
public class JWTProvider {
    /*
    * 1. Secret Key before Encoding: FinalProjectMfoodyMIREAByMacMie154624
    * 2. Link encode base 64: https://www.base64encode.net/
    * */
    private final String SECRET_KEY = "RmluYWxQcm9qZWN0TWZvb2R5TUlSRUFCeU1hY01pZTE1NDYyNA==";
    private final long JWT_EXPIRED = 8 * 60 * 60 * 1000;

    public String generateTokenIncludeObject(UserMfoody userMfoody){
        ObjectMapper objectMapper = new ObjectMapper();
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + JWT_EXPIRED);
//        private Gson gson = new Gson();
//        String jsonUserMfoody = gson.toJson(userMfoody);
//        log.info("jsonUserMfoody: " + userMfoody.toString());
        String userJson = null;
        try {
            userJson = objectMapper.writeValueAsString(userMfoody);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        log.info("jsonObjectUserMfoody by jackSon: " + userJson);

        return Jwts.builder()
                .setSubject(userJson) // Any Data can be attached here to save in Token when Login successfully
                .setIssuedAt(now) // Time issue Token
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // Algorithm and Key for Encoding
                .compact();
    }

    public String generateTokenIncludeUserName(String userName){
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + JWT_EXPIRED);

        return Jwts.builder()
                .setSubject(userName) // Any Data can be attached here to save in Token when Login successfully
                .setIssuedAt(now) // Time issue Token
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // Algorithm and Key for Encoding
                .compact();
    }

    // Check if Token is of this Project
    public boolean validationToken(String token){
        try{
            Jwts.parser()
                    .setSigningKey(SECRET_KEY) // Key for Decoding
                    .parseClaimsJws(token) // Token for Decoding
                    .getBody()
                    .getSubject();
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    /*
    * 1. If the token is valid then decode it (to get User Object or userName in it)
    * 2. The returned String is Object (User) (Json form)
    * */
    public String decodeToken(String token){
        return Jwts.parser()
                .setSigningKey(SECRET_KEY) // Key for Decoding
                .parseClaimsJws(token) // Token for Decoding
                .getBody()// Get data included in Token (User Object or userName)
                .getSubject();
    }

}
