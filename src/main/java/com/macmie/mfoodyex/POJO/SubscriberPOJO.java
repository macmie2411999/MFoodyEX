package com.macmie.mfoodyex.POJO;

import com.macmie.mfoodyex.Util.InputChecker;

public class SubscriberPOJO {
    private int idSubscriber;
    private String emailSubscriber;

    public SubscriberPOJO() {
    }

    public SubscriberPOJO(int idSubscriber, String emailSubscriber) {
        this.idSubscriber = idSubscriber;
        this.emailSubscriber = emailSubscriber;
    }

    public boolean checkSubscriberValidAttributes() {
        if(InputChecker.isValidEmail(this.emailSubscriber)) {
            return true;
        }
        return  false;
    }

    public int getIdSubscriber() {
        return idSubscriber;
    }

    public void setIdSubscriber(int idSubscriber) {
        this.idSubscriber = idSubscriber;
    }

    public String getEmailSubscriber() {
        return emailSubscriber;
    }

    public void setEmailSubscriber(String emailSubscriber) {
        this.emailSubscriber = emailSubscriber;
    }
}
