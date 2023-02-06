package com.macmie.mfoodyex.Service.ImplementService;

import com.macmie.mfoodyex.Model.UserMfoody;
import com.macmie.mfoodyex.Repository.UserMfoodyRepository;
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
    public UserMfoody getUserMfoodyByID(int idUserMfoody) {
        log.info("Fetching UserMfoody with ID: {}", idUserMfoody);
        return userMfoodyRepository.findById(idUserMfoody).orElse(null);
    }

    @Override
    public UserMfoody getUserMfoodyByEmail(String emailUserMfoody) {
        log.info("Fetching UserMfoody with emailUserMfoody: {}", emailUserMfoody);
        return userMfoodyRepository.findByEmailUser(emailUserMfoody);
    }

    @Override
    public UserMfoody getUserMfoodyByPhoneNumber(String phoneNumberUserMfoody) {
        log.info("Fetching UserMfoody with phoneNumberUserMfoody: {}", phoneNumberUserMfoody);
        return userMfoodyRepository.findByPhoneNumberUser(phoneNumberUserMfoody);
    }

    @Override
    public UserMfoody getUserMfoodyByIdCard(int idCard) {
        log.info("Fetching UserMfoody with idCard: {}", idCard);
        return userMfoodyRepository.findUserMfoodyByIdCard(idCard);
    }

    @Override
    public UserMfoody getUserMfoodyByIdComment(int idComment) {
        log.info("Fetching UserMfoody with idComment: {}", idComment);
        return userMfoodyRepository.findUserMfoodyByIdComment(idComment);
    }

    @Override
    public UserMfoody getUserMfoodyByIdCart(int idCart) {
        log.info("Fetching UserMfoody with idCart: {}", idCart);
        return userMfoodyRepository.findUserMfoodyByIdCart(idCart);
    }

    @Override
    public UserMfoody getUserMfoodyByIdOrder(int idOrder) {
        log.info("Fetching UserMfoody with idOrder: {}", idOrder);
        return userMfoodyRepository.findUserMfoodyByIdOrder(idOrder);
    }

    @Override
    public UserMfoody saveUserMfoody(UserMfoody userMfoody) {
//        feedbackMail.setIdFeedbackMail(feedbackMail.getIdFeedbackMail());
//        userMfoody.setEmailUser(stringUtil.parseEmail(userMfoody.getEmailUser()));
//        userMfoody.setPasswordUser(stringUtil.parseEmail(userMfoody.getPasswordUser()));
//        userMfoody.setNameUser(stringUtil.parseEmail(userMfoody.getNameUser()));
//        userMfoody.setPhoneNumberUser(stringUtil.parseEmail(userMfoody.getPhoneNumberUser()));
//        userMfoody.setAddressUser(stringUtil.parseEmail(userMfoody.getAddressUser()));
//        userMfoody.setRoleUser(stringUtil.parseEmail(userMfoody.getRoleUser()));

        log.info("Saving UserMfoody with ID: {}", userMfoody.getIdUser());
        return userMfoodyRepository.save(userMfoody);
    }

    @Override
    public UserMfoody updateUserMfoody(UserMfoody newUserMfoody) {
//        UserMfoody userMfoodyToUpdate = userMfoodyRepository.getById(newUserMfoody.getIdUser());
//        System.out.println("-------- newUserMfoody: " + newUserMfoody);
//        userMfoodyToUpdate.setIdUser((newUserMfoody.getIdUser()));
//        userMfoodyToUpdate.setEmailUser((newUserMfoody.getEmailUser()));
//        userMfoodyToUpdate.setPasswordUser((newUserMfoody.getPasswordUser()));
//        userMfoodyToUpdate.setNameUser((newUserMfoody.getNameUser()));
//        userMfoodyToUpdate.setPhoneNumberUser((newUserMfoody.getPhoneNumberUser()));
//        userMfoodyToUpdate.setRoleUser((newUserMfoody.getRoleUser()));
        log.info("Updating UserMfoody with ID: {}", newUserMfoody.getIdUser());
        return userMfoodyRepository.save(newUserMfoody);
    }

    @Override
    public void deleteUserMfoodyByID(int idUserMfoody) {
        log.info("Deleting UserMfoody with ID: {}", idUserMfoody);
        userMfoodyRepository.deleteById(idUserMfoody);
    }
}
