package com.macmie.mfoodyex.Service.InterfaceService;

import com.macmie.mfoodyex.Model.FeedbackMail;
import com.macmie.mfoodyex.Model.UserMfoody;

import java.util.List;

public interface UserMfoodyInterfaceService {
    public List<UserMfoody> getListUserMfoodys();

    public UserMfoody getUserMfoodyByID(int idUserMfoody);

    public UserMfoody getUserMfoodyByEmail(String emailUserMfoody);

    public UserMfoody getUserMfoodyByPhoneNumber(String phoneNumberUserMfoody);

    public UserMfoody saveUserMfoody(UserMfoody userMfoody);

    public UserMfoody updateUserMfoody(UserMfoody newUserMfoody);

    public void deleteUserMfoodyByID(int idUserMfoody);
}
