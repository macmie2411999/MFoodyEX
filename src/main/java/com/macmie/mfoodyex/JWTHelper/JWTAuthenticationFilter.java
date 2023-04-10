package com.macmie.mfoodyex.JWTHelper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.macmie.mfoodyex.JWTSpringSecurity.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.macmie.mfoodyex.Constant.SecurityConstants.AUTHENTICATION_IN_HEADER_SECURITY;
import static com.macmie.mfoodyex.Constant.SecurityConstants.BEARER_SECURITY;

@Slf4j
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    JWTProvider jwtProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    private Gson gson = new Gson();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = getJWTToken(request);
        log.info("JWTAuthenticationFilter: Start");
        // Check if Token is valid (generated by this Project)
        if(jwtProvider.validationToken(token)){
            log.info("JWTAuthenticationFilter: Check Token");
            // Get data (userName) in the valid Token
            String jsonDataUser = jwtProvider.decodeToken(token);
            String jsonDataUserName = getEmailFromJsonStringByJackson(jsonDataUser);

            // Query to get User by userName from DB
            User userDetail = (User) customUserDetailsService.loadUserByUsername(jsonDataUserName);

            // Authenticate Token
            UsernamePasswordAuthenticationToken authenticationTokenDecode =
                    new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
            authenticationTokenDecode.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationTokenDecode);
        }

        log.info("JWTAuthenticationFilter: End");

        // Continue to the URLs
        filterChain.doFilter(request, response);
    }

    private String getJWTToken(HttpServletRequest request){
        // Get Token from User's request
        String authenticationTokenHeader = request.getHeader(AUTHENTICATION_IN_HEADER_SECURITY);
        if(StringUtils.hasText(authenticationTokenHeader) && authenticationTokenHeader.contains(BEARER_SECURITY)){
            // Remove "Bearer " and get Token
            String token = authenticationTokenHeader.substring(7);
            return token;
        }
        return null;
    }

    // Function to get userName (emailUser) for customUserDetailsService.loadUserByUsername(userName)
    private static String getEmailFromJsonString(String jsonString) {
        Pattern emailPattern = Pattern.compile("'emailUser'\\s*:\\s*'([^']*)'");
        Matcher matcher = emailPattern.matcher(jsonString);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public String getEmailFromJsonStringByJackson(String jsonStringByJackson) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = null;
        String emailUser = "";
        try {
            jsonNode = mapper.readTree(jsonStringByJackson);
            emailUser = jsonNode.get("emailUser").asText();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return emailUser;
    }

}
