package com.macmie.mfoodyex.Controller;

import com.macmie.mfoodyex.JWTHelper.JWTProvider;
import com.macmie.mfoodyex.JWTPayload.LoginRequest;
import com.macmie.mfoodyex.Model.UserMfoody;
import com.macmie.mfoodyex.Service.InterfaceService.UserMfoodyInterfaceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
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
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender emailSender;

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

    @PostMapping(FORGOT_PASSWORD)
    public ResponseEntity<?> forgotPassword(@RequestBody LoginRequest loginRequest) {
        log.info("Processing request renew password for user with email: {}", loginRequest.getUserName());
        try {
            // Check if valid Email
            UserMfoody user = userMfoodyInterfaceService.getUserMfoodyByEmail(loginRequest.getUserName());
            if (user == null) {
                return new ResponseEntity<>("NOT_FOUND UserMfoody with email: " + loginRequest.getUserName(), HttpStatus.NOT_FOUND);
            }

            // Generate new random password and send to mail
            String newPassword = generateRandomPassword();
            sendEmailWithNewPassword(loginRequest.getUserName(), newPassword);

            // Update new password to Database
            user.setPasswordUser(passwordEncoder.encode(newPassword));
            userMfoodyInterfaceService.updateUserMfoody(user);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            log.error("An error occurred while processing forgot password request");
            log.error("Detail Error: " + e);
            return new ResponseEntity<>("BAD_REQUEST Something Wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    // Handling Login Error
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    public class UnauthorizedException extends RuntimeException {
        public UnauthorizedException(String message) {
            super(message);
        }
    }

    // Handling Forgot Password
    private String generateRandomPassword() {
        int passwordLength = 8;
        return RandomStringUtils.randomAlphanumeric(passwordLength);
    }

    private void sendEmailWithNewPassword(String email, String newPassword) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(email);
        helper.setSubject("Generate a new password on Mfoody");
        helper.setText("Hello "+ email + "!\n\nYour new password is: " + newPassword +
                "\nPlease use it to login into MFoody Vegetarian Store and update your new password." +
                "\n\nBest regard,\nMfoody Admin.");

        emailSender.send(message);
    }
}
