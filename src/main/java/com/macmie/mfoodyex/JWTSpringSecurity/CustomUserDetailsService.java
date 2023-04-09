package com.macmie.mfoodyex.JWTSpringSecurity;

import com.macmie.mfoodyex.Model.UserMfoody;
import com.macmie.mfoodyex.Service.InterfaceService.UserMfoodyInterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.macmie.mfoodyex.Constant.SecurityConstants.ROLE_SECURITY;

@Service // to Bean
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserMfoodyInterfaceService userMfoodyInterfaceService;
    /*
    * 1. Create a new User (of FW Spring Security) and compare it with user in UserDetail
    * 2. Base on userName to query and get userPassword then automatically compared password from DB with input password
    * */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserMfoody userMfoody = userMfoodyInterfaceService.getUserMfoodyByEmail(username);
        if(userMfoody != null){
            List<SimpleGrantedAuthority> rolesList = new ArrayList<SimpleGrantedAuthority>();
            SimpleGrantedAuthority roleUser = new SimpleGrantedAuthority(ROLE_SECURITY + userMfoody.getRoleUser());
            rolesList.add(roleUser);
            return new User(userMfoody.getEmailUser(), userMfoody.getPasswordUser(), rolesList);
        }
        throw new UsernameNotFoundException("User not found!");
    }
}
