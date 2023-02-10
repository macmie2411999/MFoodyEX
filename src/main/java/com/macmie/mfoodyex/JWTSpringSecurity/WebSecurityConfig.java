package com.macmie.mfoodyex.JWTSpringSecurity;

import com.macmie.mfoodyex.JWTHelper.JWTAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

import static com.macmie.mfoodyex.Constant.ViewConstants.*;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    // Set encryption standard: BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter(){
        return new JWTAuthenticationFilter();
    }

    // Expose the authentication manager bean in the Spring context, so that it can be used for authentication
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    /*
    * Disallow the creation of a new HTTP session if one does not already exist.
    * This means that if a session does not exist, no target URL will be stored in the session
    * */
    @Bean
    public HttpSessionRequestCache getHttpSessionRequestCache() {
        var httpSessionRequestCache = new HttpSessionRequestCache();
        httpSessionRequestCache.setCreateSessionAllowed(false);
        return httpSessionRequestCache;
    }

    /*
    * 1. Designate the using of CustomUserDetailsService instead of the default UserDetailsService
    * 2. Compare by encryption standard BCrypt
    * */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    // Config URLs
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                /*
                * 1. Disables CSRF protection
                * 2. Cross-Site Request Forgery (CSRF) is a type of attack that tricks a user into making
                * an unintended request to a web application. CSRF protection helps to prevent such attacks
                * by requiring that a token be included in a request to verify that the request is legitimate
                * */
                .csrf().disable()

                /*
                * 1. Disables CORS protection
                * 2. Cross-Origin Resource Sharing (CORS) is a security feature that restricts the types of requests
                * that can be made from a web application running in one origin to a resource in another origin
                * */
                .cors().disable()

                /*
                * 1. Sets the request cache to be used by the application
                * 2. The request cache is used to redirect the user to the originally requested resource after they have logged in
                * */
                .requestCache().requestCache(getHttpSessionRequestCache())

                /*
                * 1. Sets the session creation policy to be stateless
                * 2. A stateless session means that the server does not maintain any state about the client, so it
                * does not store any information in a session. Instead, all required information is passed in each request
                * 3. This can improve the security of the application and make it more scalable, since there is no need
                * to manage and store session state on the server
                * */
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                /*
                * Define which requests are allowed and which are not based on the authentication and authorization
                * of the user making the request
                * */
                .and().authorizeHttpRequests()

                .antMatchers(APPLICATION_MFOODY + LOGIN_MFOODY).permitAll()
                .antMatchers(APPLICATION_MFOODY + LOGOUT_MFOODY).permitAll()
                .anyRequest().authenticated()
                .and().logout().logoutUrl(APPLICATION_MFOODY + LOGOUT_MFOODY) // Specify the logout endpoint URL
                .invalidateHttpSession(true) // Invalidate the user's HTTP session when they log out
                .clearAuthentication(true) // Clears the user's security context, effectively logging them out of the application
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                .and().addFilterBefore(jwtAuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class); // Run the custom filter first

    }
}
