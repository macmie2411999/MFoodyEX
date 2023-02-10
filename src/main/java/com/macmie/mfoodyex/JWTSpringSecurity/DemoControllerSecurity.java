package com.macmie.mfoodyex.JWTSpringSecurity;

import com.macmie.mfoodyex.JWTHelper.JWTProvider;
import com.macmie.mfoodyex.JWTPayload.LoginRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/demo")
public class DemoControllerSecurity {
    @GetMapping("/user")
    public String index(){
        return "Hello Spring Security";
    }

    @GetMapping("/admin")
    public String admin(){
        return "Hello Admin";
    }

    @GetMapping("/guest")
    public String guest(){
        return "Hello Guess";
    }

    @GetMapping("/test")
    public String test(){
        return "Hello Test";
    }


}
