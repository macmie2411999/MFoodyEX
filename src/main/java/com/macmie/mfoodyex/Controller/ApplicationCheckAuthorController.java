package com.macmie.mfoodyex.Controller;

import com.macmie.mfoodyex.Model.UserMfoody;
import com.macmie.mfoodyex.Service.InterfaceService.UserMfoodyInterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Principal;

import static com.macmie.mfoodyex.Constant.SecurityConstants.ROLE_ADMIN_SECURITY;
import static com.macmie.mfoodyex.Constant.SecurityConstants.ROLE_SECURITY;

@Component
public class ApplicationCheckAuthorController {
    @Autowired
    private UserMfoodyInterfaceService userMfoodyInterfaceService;

    // Check if the current UserMfoody has role ADMIN or the owner of the ObjectMfoody
    public boolean checkAuthorization(Principal principal, int idUserFromObject){
        UserMfoody userMfoody = userMfoodyInterfaceService.getUserMfoodyByEmail(principal.getName());
        return (ROLE_SECURITY + userMfoody.getRoleUser()).equals(ROLE_ADMIN_SECURITY)
                || userMfoody.getIdUser() == idUserFromObject;
    }
}
