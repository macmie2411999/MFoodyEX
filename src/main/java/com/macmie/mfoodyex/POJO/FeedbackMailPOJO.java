package com.macmie.mfoodyex.POJO;

public class FeedbackMailPOJO {
    private int IdFeedbackMail;
    private String NameUserFeedbackMail;
    private String EmailUserFeedbackMail;
    private String TitleFeedbackMail;
    private String ContentFeedbackMail;

    public FeedbackMailPOJO() {
    }

    public FeedbackMailPOJO(int idFeedbackMail, String nameUserFeedbackMail, String emailUserFeedbackMail, String titleFeedbackMail, String contentFeedbackMail) {
        IdFeedbackMail = idFeedbackMail;
        NameUserFeedbackMail = nameUserFeedbackMail;
        EmailUserFeedbackMail = emailUserFeedbackMail;
        TitleFeedbackMail = titleFeedbackMail;
        ContentFeedbackMail = contentFeedbackMail;
    }

    public int getIdFeedbackMail() {
        return IdFeedbackMail;
    }

    public void setIdFeedbackMail(int idFeedbackMail) {
        IdFeedbackMail = idFeedbackMail;
    }

    public String getNameUserFeedbackMail() {
        return NameUserFeedbackMail;
    }

    public void setNameUserFeedbackMail(String nameUserFeedbackMail) {
        NameUserFeedbackMail = nameUserFeedbackMail;
    }

    public String getEmailUserFeedbackMail() {
        return EmailUserFeedbackMail;
    }

    public void setEmailUserFeedbackMail(String emailUserFeedbackMail) {
        EmailUserFeedbackMail = emailUserFeedbackMail;
    }

    public String getTitleFeedbackMail() {
        return TitleFeedbackMail;
    }

    public void setTitleFeedbackMail(String titleFeedbackMail) {
        TitleFeedbackMail = titleFeedbackMail;
    }

    public String getContentFeedbackMail() {
        return ContentFeedbackMail;
    }

    public void setContentFeedbackMail(String contentFeedbackMail) {
        ContentFeedbackMail = contentFeedbackMail;
    }
}
