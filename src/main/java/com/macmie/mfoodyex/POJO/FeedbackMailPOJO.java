package com.macmie.mfoodyex.POJO;

import com.macmie.mfoodyex.Util.InputChecker;

public class FeedbackMailPOJO {
    private int idFeedbackMail;
    private String nameUserFeedbackMail;
    private String emailUserFeedbackMail;
    private String titleFeedbackMail;
    private String contentFeedbackMail;

    public FeedbackMailPOJO() {
    }

    public FeedbackMailPOJO(int idFeedbackMail, String nameUserFeedbackMail, String emailUserFeedbackMail, String titleFeedbackMail, String contentFeedbackMail) {
        this.idFeedbackMail = idFeedbackMail;
        this.nameUserFeedbackMail = nameUserFeedbackMail;
        this.emailUserFeedbackMail = emailUserFeedbackMail;
        this.titleFeedbackMail = titleFeedbackMail;
        this.contentFeedbackMail = contentFeedbackMail;
    }

    public boolean checkFeedbackMailValidAttributes() {
        if(InputChecker.isStringValid(this.nameUserFeedbackMail) && InputChecker.isValidEmail(this.emailUserFeedbackMail)
                && InputChecker.isStringValid(this.titleFeedbackMail) && InputChecker.isStringValid(this.contentFeedbackMail)) {
            return true;
        }
        return  false;
    }

    public int getIdFeedbackMail() {
        return idFeedbackMail;
    }

    public void setIdFeedbackMail(int idFeedbackMail) {
        this.idFeedbackMail = idFeedbackMail;
    }

    public String getNameUserFeedbackMail() {
        return nameUserFeedbackMail;
    }

    public void setNameUserFeedbackMail(String nameUserFeedbackMail) {
        this.nameUserFeedbackMail = nameUserFeedbackMail;
    }

    public String getEmailUserFeedbackMail() {
        return emailUserFeedbackMail;
    }

    public void setEmailUserFeedbackMail(String emailUserFeedbackMail) {
        this.emailUserFeedbackMail = emailUserFeedbackMail;
    }

    public String getTitleFeedbackMail() {
        return titleFeedbackMail;
    }

    public void setTitleFeedbackMail(String titleFeedbackMail) {
        this.titleFeedbackMail = titleFeedbackMail;
    }

    public String getContentFeedbackMail() {
        return contentFeedbackMail;
    }

    public void setContentFeedbackMail(String contentFeedbackMail) {
        this.contentFeedbackMail = contentFeedbackMail;
    }
}
