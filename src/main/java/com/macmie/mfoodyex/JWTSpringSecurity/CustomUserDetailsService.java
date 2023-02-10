package com.macmie.mfoodyex.JWTSpringSecurity;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service // to Bean
public class CustomUserDetailsService implements UserDetailsService {

    /*
    * 1. Create a new User (of FW Spring Security) and compare it with user in UserDetail
    * 2. Base on userName to query and get userPassword then automatically compared password from DB with input password
    * */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username.equals("admin")){
            List<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();
            SimpleGrantedAuthority rolesAdmin = new SimpleGrantedAuthority("ROLE_ADMIN");
            roles.add(rolesAdmin);

            return new User("admin", "$2a$12$X0KP6baZ/cjuFoBj4iC8t.XEQqSzq3KP5DSrWie0CWNrU/QeVwCvm", roles);
        }
        throw new UsernameNotFoundException("User no found!");
    }
}
