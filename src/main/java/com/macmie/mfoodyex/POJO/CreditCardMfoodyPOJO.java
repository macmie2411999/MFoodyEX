package com.macmie.mfoodyex.POJO;

import com.macmie.mfoodyex.Model.CreditCardMfoody;
import com.macmie.mfoodyex.Util.InputChecker;

public class CreditCardMfoodyPOJO {
    private int idCard;
    private String nameUserCard;
    private String numberCard;
    private String expirationCard;
    private String securityCodeCard;
    private int idUser;

    public CreditCardMfoody renderCreditCardMfoody() {
        CreditCardMfoody newCreditCardMfoody = new CreditCardMfoody();
        newCreditCardMfoody.setIdCard(this.getIdCard());
        newCreditCardMfoody.setNameUserCard(this.getNameUserCard());
        newCreditCardMfoody.setNumberCard(this.getNumberCard());
        newCreditCardMfoody.setExpirationCard(this.getExpirationCard());
        newCreditCardMfoody.setSecurityCodeCard(this.getSecurityCodeCard());
        return newCreditCardMfoody;
    }

    public CreditCardMfoody renderCreditCardMfoodyWithoutIdCard() {
        CreditCardMfoody newCreditCardMfoody = new CreditCardMfoody();
        newCreditCardMfoody.setNameUserCard(this.getNameUserCard());
        newCreditCardMfoody.setNumberCard(this.getNumberCard());
        newCreditCardMfoody.setExpirationCard(this.getExpirationCard());
        newCreditCardMfoody.setSecurityCodeCard(this.getSecurityCodeCard());
        return newCreditCardMfoody;
    }

    public boolean checkCreditCardMfoodyValidAttributes() {
        if(InputChecker.isStringValid(this.nameUserCard) && InputChecker.isStringInt(this.numberCard)
        && InputChecker.isValidDateFormat(this.expirationCard) && InputChecker.isStringInt(this.securityCodeCard)) {
            return true;
        }
        return  false;
    }

    public int getIdCard() {
        return idCard;
    }

    public void setIdCard(int idCard) {
        this.idCard = idCard;
    }

    public String getNameUserCard() {
        return nameUserCard;
    }

    public void setNameUserCard(String nameUserCard) {
        this.nameUserCard = nameUserCard;
    }

    public String getNumberCard() {
        return numberCard;
    }

    public void setNumberCard(String numberCard) {
        this.numberCard = numberCard;
    }

    public String getExpirationCard() {
        return expirationCard;
    }

    public void setExpirationCard(String expirationCard) {
        this.expirationCard = expirationCard;
    }

    public String getSecurityCodeCard() {
        return securityCodeCard;
    }

    public void setSecurityCodeCard(String securityCodeCard) {
        this.securityCodeCard = securityCodeCard;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
