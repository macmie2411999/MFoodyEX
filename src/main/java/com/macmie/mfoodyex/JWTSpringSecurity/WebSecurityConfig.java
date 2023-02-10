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
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    private HttpSessionRequestCache httpSessionRequestCache;

    // Set encryption standard: BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter(){
        return new JWTAuthenticationFilter();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    /*
    * 1. Designate the using of CustomUserDetailsService instead of the default UserDetailsService
    * 2. Compare by encryption standard BCrypt
    * */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // super.configure(auth);
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    // Config Domains
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .anyRequest().permitAll();
        http.csrf().disable().cors().disable().requestCache().requestCache(httpSessionRequestCache).and() // Block attacks from Sessions
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and() // No Session is allowed
                .authorizeHttpRequests()
                .antMatchers("/demo/login").permitAll()
//                .antMatchers("/demo/user").hasRole("USER")
//                .antMatchers("/demo/admin").hasRole("ADMIN")
//                .antMatchers("/demo/guest").hasRole("GUEST")
                .antMatchers("/demo/logout").permitAll()
                .anyRequest().authenticated()
                .and().addFilterBefore(jwtAuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class); // Run the custom filter first
//                .and().logout()
//                .and().httpBasic();
    }
}
