package com.macmie.mfoodyex.JWTSpringSecurity;

import com.macmie.mfoodyex.JWTHelper.JWTAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static com.macmie.mfoodyex.Constant.ViewConstants.*;

@Configuration
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        prePostEnabled = true
) // Allow using Annotations for Authorization
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
                .csrf().disable()
                .cors()

                .and().requestCache().requestCache(getHttpSessionRequestCache())
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeHttpRequests()

                .antMatchers(APPLICATION_MFOODY + LOGIN_MFOODY).permitAll()
                .antMatchers(APPLICATION_MFOODY + LOGOUT_MFOODY).permitAll()
                .antMatchers(APPLICATION_MFOODY + FORGOT_PASSWORD).permitAll()
                .antMatchers(PRODUCT_MFOODY + URL_GET_ALL).permitAll()
                .antMatchers(USER_MFOODY + URL_ADD).permitAll()
                .antMatchers(FEEDBACK_MAIL + URL_ADD).permitAll()
                .antMatchers(SUBSCRIBER + URL_ADD).permitAll()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated()
                .and().logout().logoutUrl(APPLICATION_MFOODY + LOGOUT_MFOODY) // Specify the logout endpoint URL
                .invalidateHttpSession(true) // Invalidate the user's HTTP session when they log out
                .clearAuthentication(true) // Clears the user's security context, effectively logging them out of the application
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                .and().addFilterBefore(jwtAuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class); // Run the custom filter first

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public BasicAuthenticationEntryPoint swaggerAuthenticationEntryPoint() {
        BasicAuthenticationEntryPoint entryPoint = new BasicAuthenticationEntryPoint();
        entryPoint.setRealmName("Swagger Realm");
        return entryPoint;
    }
}
