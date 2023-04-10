package com.macmie.mfoodyex.Controller;

import com.macmie.mfoodyex.JWTHelper.JWTProvider;
import com.macmie.mfoodyex.JWTPayload.LoginRequest;
import com.macmie.mfoodyex.Service.InterfaceService.UserMfoodyInterfaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.security.Principal;

import static com.macmie.mfoodyex.Constant.ViewConstants.*;

@Slf4j
@Transactional
@RestController // = @ResponseBody + @Controller
@RequestMapping(APPLICATION_MFOODY)
public class ApplicationMfoodyController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserMfoodyInterfaceService userMfoodyInterfaceService;

    @Autowired
    JWTProvider jwtProvider;

//    @PostMapping(LOGIN_MFOODY)
//    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest){
//        log.info("Login to ApplicationMfoody by {}", loginRequest.getUserName());
//
//        // Authenticate by Spring Security
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getUserPassword()));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        // Create Token if valid Authentication
//        String jwtToken = jwtProvider.generateTokenIncludeUserName(loginRequest.getUserName());
//        String jwtTokenFullInformation = jwtProvider.generateTokenIncludeObject(userMfoodyInterfaceService.getUserMfoodyByEmail(loginRequest.getUserName()));
//
//        return new ResponseEntity<>(jwtTokenFullInformation, HttpStatus.OK);
//    }

    @PostMapping(LOGIN_MFOODY)
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        log.info("Login to ApplicationMfoody by {}", loginRequest.getUserName());

        try {
            // Authenticate by Spring Security
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getUserPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Create Token if valid Authentication
            String jwtToken = jwtProvider.generateTokenIncludeUserName(loginRequest.getUserName());
            String jwtTokenFullInformation = jwtProvider.generateTokenIncludeObject(userMfoodyInterfaceService.getUserMfoodyByEmail(loginRequest.getUserName()));

            return new ResponseEntity<>(jwtTokenFullInformation, HttpStatus.OK);
        } catch (AuthenticationException e) {
            // Handle authentication failure
            throw new UnauthorizedException("401 UNAUTHORIZED Something wrong when Logging in to the System");
        }
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    public class UnauthorizedException extends RuntimeException {
        public UnauthorizedException(String message) {
            super(message);
        }
    }


    // @Secured({ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY})
    @PostMapping(LOGOUT_MFOODY)
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response, Principal principal) {
        // log.info("Logout of ApplicationMfoody by {}", principal.getName());
        // Get the current authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // If there is an authentication object, log the user out
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
