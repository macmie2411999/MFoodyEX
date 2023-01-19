package com.macmie.mfoodyex.Service.ImplementService;

import com.macmie.mfoodyex.Model.FeedbackMail;
import com.macmie.mfoodyex.Model.UserMfoody;
import com.macmie.mfoodyex.Repository.FeedbackMailRepository;
import com.macmie.mfoodyex.Repository.UserMfoodyRepository;
import com.macmie.mfoodyex.Service.InterfaceService.FeedbackMailInterfaceService;
import com.macmie.mfoodyex.Service.InterfaceService.UserMfoodyInterfaceService;
import com.macmie.mfoodyex.Util.StringUtil;
import com.macmie.mfoodyex.Util.TextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j

/* @Transactional: Handle rollback when exceptions occur
* @Slf4j: Spring Boot Logging
* */
public class UserMfoodyImplementService implements UserMfoodyInterfaceService {
    @Autowired
    private UserMfoodyRepository userMfoodyRepository;

    @Autowired
    private StringUtil stringUtil;

    @Autowired
    private TextUtil textUtil;

    @Override
    public List<UserMfoody> getListUserMfoodys() {
        log.info("Fetching all UserMfoodys: ");
        return userMfoodyRepository.findAll();
    }

    @Override
    public UserMfoody getUserMfoodyByID(int ID_UserMfoody) {
        log.info("Fetching UserMfoody with id: {}", ID_UserMfoody);
        return userMfoodyRepository.findById(ID_UserMfoody).orElse(null);
    }

    @Override
    public UserMfoody saveUserMfoody(UserMfoody userMfoody) {
//        feedbackMail.setIdFeedbackMail(feedbackMail.getIdFeedbackMail());
        userMfoody.setEmailUser(stringUtil.parseEmail(userMfoody.getEmailUser()));
        userMfoody.setPasswordUser(stringUtil.parseEmail(userMfoody.getPasswordUser()));
        userMfoody.setNameUser(stringUtil.parseEmail(userMfoody.getNameUser()));
        userMfoody.setPhoneNumberUser(stringUtil.parseEmail(userMfoody.getPhoneNumberUser()));
        userMfoody.setAddressUser(stringUtil.parseEmail(userMfoody.getAddressUser()));
        userMfoody.setRoleUser(stringUtil.parseEmail(userMfoody.getRoleUser()));

        log.info("Saving UserMfoody with id: {}", userMfoody.getIdUser());
        return userMfoodyRepository.save(userMfoody);
    }

    @Override
    public UserMfoody updateUserMfoody(UserMfoody newUserMfoody) {
        UserMfoody userMfoodyToUpdate = userMfoodyRepository.getById(newUserMfoody.getIdUser());
        System.out.println("-------- newUserMfoody: " + newUserMfoody);
        userMfoodyToUpdate.setIdUser((newUserMfoody.getIdUser()));
        userMfoodyToUpdate.setEmailUser((newUserMfoody.getEmailUser()));
        userMfoodyToUpdate.setPasswordUser((newUserMfoody.getPasswordUser()));
        userMfoodyToUpdate.setNameUser((newUserMfoody.getNameUser()));
        userMfoodyToUpdate.setPhoneNumberUser((newUserMfoody.getPhoneNumberUser()));
        userMfoodyToUpdate.setRoleUser((newUserMfoody.getRoleUser()));
        log.info("Updating UserMfoody with id: {}", userMfoodyToUpdate.getIdUser());
        return userMfoodyRepository.save(userMfoodyToUpdate);
    }

    @Override
    public void deleteUserMfoodyByID(int ID_UserMfoody) {
        log.info("Deleting UserMfoody with id: {}", ID_UserMfoody);
        userMfoodyRepository.deleteById(ID_UserMfoody);
    }
}
