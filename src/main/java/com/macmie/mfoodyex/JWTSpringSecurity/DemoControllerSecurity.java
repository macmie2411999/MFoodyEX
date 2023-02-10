package com.macmie.mfoodyex.JWTSpringSecurity;

import com.macmie.mfoodyex.JWTHelper.JWTProvider;
import com.macmie.mfoodyex.JWTPayload.LoginRequest;
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

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return "Hello Logout";
    }

    @GetMapping("/test")
    public String test(){
        return "Hello Test";
    }

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTProvider jwtProvider;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest){

        // Manually authenticate
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getUserPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Create Token (method getPrincipal() returns authenticated Object)
        String jwtToken = jwtProvider.generateTokenIncludeUserName(loginRequest.getUserName());

        return new ResponseEntity<>(jwtToken, HttpStatus.OK);
    }
}
