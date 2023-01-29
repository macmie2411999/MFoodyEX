package com.macmie.mfoodyex.Service.ImplementService;

import com.macmie.mfoodyex.Model.CreditCardMfoody;
import com.macmie.mfoodyex.Model.UserMfoody;
import com.macmie.mfoodyex.Repository.CreditCardMfoodyRepository;
import com.macmie.mfoodyex.Repository.UserMfoodyRepository;
import com.macmie.mfoodyex.Service.InterfaceService.CreditCardMfoodyInterfaceService;
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
public class CreditCardMfoodyImplementService implements CreditCardMfoodyInterfaceService {
    @Autowired
    private CreditCardMfoodyRepository creditCardMfoodyRepository;

    @Autowired
    private StringUtil stringUtil;

    @Autowired
    private TextUtil textUtil;

    @Override
    public List<CreditCardMfoody> getListCreditCardMfoodys() {
        log.info("Fetching all CreditCardMfoodys: ");
        return creditCardMfoodyRepository.findAll();
    }

    @Override
    public CreditCardMfoody getCreditCardMfoodyByID(int ID_CreditCardMfoody) {
        log.info("Fetching CreditCardMfoody with ID: {}", ID_CreditCardMfoody);
        return creditCardMfoodyRepository.findById(ID_CreditCardMfoody).orElse(null);
    }

    @Override
    public CreditCardMfoody getCreditCardMfoodyByNumberCard(String CardNumber) {
        log.info("Fetching CreditCardMfoody with CardNumber: {}", CardNumber);
        return creditCardMfoodyRepository.findByNumberCard(CardNumber);
    }

    @Override
    public CreditCardMfoody saveCreditCardMfoody(CreditCardMfoody creditCardMfoody) {
//        feedbackMail.setIdFeedbackMail(feedbackMail.getIdFeedbackMail());
//        creditCardMfoody.setNameUserCard(stringUtil.parseName(creditCardMfoody.getNameUserCard()));

        log.info("Saving CreditCardMfoody with ID: {}", creditCardMfoody.getIdCard());
        return creditCardMfoodyRepository.save(creditCardMfoody);
    }

    @Override
    public CreditCardMfoody updateCreditCardMfoody(CreditCardMfoody newCreditCardMfoody) {
        CreditCardMfoody creditCardMfoodyToUpdate = creditCardMfoodyRepository.getById(newCreditCardMfoody.getIdCard());
//        creditCardMfoodyToUpdate.setIdCard((newCreditCardMfoody.getIdCard()));
        creditCardMfoodyToUpdate.setNameUserCard(stringUtil.parseName(newCreditCardMfoody.getNameUserCard()));
        creditCardMfoodyToUpdate.setNumberCard((newCreditCardMfoody.getNumberCard()));
        creditCardMfoodyToUpdate.setExpirationCard((newCreditCardMfoody.getExpirationCard()));
        creditCardMfoodyToUpdate.setSecurityCodeCard((newCreditCardMfoody.getSecurityCodeCard()));
        creditCardMfoodyToUpdate.setUser((newCreditCardMfoody.getUser()));

        log.info("Updating UserMfoody with ID: {}", creditCardMfoodyToUpdate.getUser().getIdUser());
        return creditCardMfoodyRepository.save(creditCardMfoodyToUpdate);
    }

    @Override
    public void deleteCreditCardMfoodyByID(int ID_CreditCardMfoody) {
        log.info("Deleting CreditCardMfoody with ID: {}", ID_CreditCardMfoody);
        creditCardMfoodyRepository.deleteById(ID_CreditCardMfoody);
    }
}
