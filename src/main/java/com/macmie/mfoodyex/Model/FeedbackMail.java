package com.macmie.mfoodyex.Model;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/* Handle Jackson – Bidirectional Relationships (Loop)
    @JsonIgnore: ignore Serialization
    @JsonBackReference: the back part of reference; it'll be omitted from serialization (for ManyToOne - Object)
    @JsonManagedReference: the forward part of reference, the one that gets serialized normally (for OneToMany - list)
    @JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
    */

@Entity
@Table(name = "`FEEDBACK_MAIL`")
@RequiredArgsConstructor
public class FeedbackMail {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID_FEEDBACK_MAIL")
    private int idFeedbackMail;

    @NonNull
    @Column(name = "NAME_USER_FEEDBACK_MAIL")
    private String nameUserFeedbackMail;

    @NonNull
    @Column(name = "EMAIL_USER_FEEDBACK_MAIL")
    private String emailUserFeedbackMail;

    @NonNull
    @Column(name = "TITLE_FEEDBACK_MAIL")
    private String titleFeedbackMail;

    @NonNull
    @Column(name = "CONTENT_FEEDBACK_MAIL")
    private String contentFeedbackMail;

    public FeedbackMail() {
    }

    public FeedbackMail(int idFeedbackMail, @NonNull String nameUserFeedbackMail, @NonNull String emailUserFeedbackMail, @NonNull String titleFeedbackMail, @NonNull String contentFeedbackMail) {
        this.idFeedbackMail = idFeedbackMail;
        this.nameUserFeedbackMail = nameUserFeedbackMail;
        this.emailUserFeedbackMail = emailUserFeedbackMail;
        this.titleFeedbackMail = titleFeedbackMail;
        this.contentFeedbackMail = contentFeedbackMail;
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
