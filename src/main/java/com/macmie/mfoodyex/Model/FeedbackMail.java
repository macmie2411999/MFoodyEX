package com.macmie.mfoodyex.Model;
import lombok.*;

//import jakarta.persistence.*;
//import static jakarta.persistence.GenerationType.IDENTITY;

import javax.persistence.*;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name= "`FEEDBACK_MAIL`")
@Data
@RequiredArgsConstructor
public class FeedbackMail {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID_FEEDBACK_MAIL")
    private int IdFeedbackMail;

    @NonNull
    @Column(name = "NAME_USER_FEEDBACK_MAIL")
    private String NameUserFeedbackMail;

    @NonNull
    @Column(name = "EMAIL_USER_FEEDBACK_MAIL")
    private String EmailUserFeedbackMail;

    @NonNull
    @Column(name = "TITLE_FEEDBACK_MAIL")
    private String TitleFeedbackMail;

    @NonNull
    @Column(name = "CONTENT_FEEDBACK_MAIL")
    private String ContentFeedbackMail;

    public FeedbackMail() {
    }

    public FeedbackMail(int idFeedbackMail, @NonNull String nameUserFeedbackMail, @NonNull String emailUserFeedbackMail, @NonNull String titleFeedbackMail, @NonNull String contentFeedbackMail) {
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
