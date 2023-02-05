package com.macmie.mfoodyex.Service.ImplementService;

import com.macmie.mfoodyex.Model.CreditCardMfoody;
import com.macmie.mfoodyex.Repository.CreditCardMfoodyRepository;
import com.macmie.mfoodyex.Service.InterfaceService.CreditCardMfoodyInterfaceService;
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
    public List<CreditCardMfoody> getListCreditCardMfoodysByIdUser(int idUser) {
        log.info("Fetching all CreditCardMfoodys by idUser: {}", idUser);
        return creditCardMfoodyRepository.findAllByIdUser(idUser);
    }

    @Override
    public CreditCardMfoody getCreditCardMfoodyByID(int idCreditCardMfoody) {
        log.info("Fetching CreditCardMfoody with ID: {}", idCreditCardMfoody);
        return creditCardMfoodyRepository.findById(idCreditCardMfoody).orElse(null);
    }

    @Override
    public CreditCardMfoody getCreditCardMfoodyByNumberCard(String cardNumber) {
        log.info("Fetching CreditCardMfoody with CardNumber: {}", cardNumber);
        return creditCardMfoodyRepository.findByNumberCard(cardNumber);
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
//        CreditCardMfoody creditCardMfoodyToUpdate = creditCardMfoodyRepository.getById(newCreditCardMfoody.getIdCard());
//        creditCardMfoodyToUpdate.setIdCard((newCreditCardMfoody.getIdCard()));
//        creditCardMfoodyToUpdate.setNameUserCard(stringUtil.parseName(newCreditCardMfoody.getNameUserCard()));
//        creditCardMfoodyToUpdate.setNumberCard((newCreditCardMfoody.getNumberCard()));
//        creditCardMfoodyToUpdate.setExpirationCard((newCreditCardMfoody.getExpirationCard()));
//        creditCardMfoodyToUpdate.setSecurityCodeCard((newCreditCardMfoody.getSecurityCodeCard()));
//        creditCardMfoodyToUpdate.setUser((newCreditCardMfoody.getUser()));

        log.info("Updating CreditCardMfoody with ID: {}", newCreditCardMfoody.getIdCard());
        return creditCardMfoodyRepository.save(newCreditCardMfoody);
    }

    @Override
    public void deleteCreditCardMfoodyByID(int idCreditCardMfoody) {
        log.info("Deleting CreditCardMfoody with ID: {}", idCreditCardMfoody);
        creditCardMfoodyRepository.deleteById(idCreditCardMfoody);
    }

    @Override
    public void deleteAllCreditCardsMfoodyByIdUser(int idUser) {
        log.info("Deleting All CreditCardsMfoody with idUser: {}", idUser);
        creditCardMfoodyRepository.deleteAllByIdUser(idUser);
    }
}
