package com.macmie.mfoodyex.Service.InterfaceService;

import com.macmie.mfoodyex.Model.CreditCardMfoody;
import com.macmie.mfoodyex.Model.FeedbackMail;

import java.util.List;

public interface CreditCardMfoodyInterfaceService {
    public List<CreditCardMfoody> getListCreditCardMfoodys();

    public CreditCardMfoody getCreditCardMfoodyByID(int idCreditCardMfoody);

    public CreditCardMfoody getCreditCardMfoodyByNumberCard(String CardNumber);

    public CreditCardMfoody saveCreditCardMfoody(CreditCardMfoody creditCardMfoody);

    public CreditCardMfoody updateCreditCardMfoody(CreditCardMfoody newCreditCardMfoody);

    public void deleteCreditCardMfoodyByID(int idCreditCardMfoody);

    public void deleteAllCreditCardsMfoodyByIdUser(int idUser);
}
