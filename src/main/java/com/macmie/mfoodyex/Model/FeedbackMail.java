package com.macmie.mfoodyex.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity(name= "FEEDBACK_MAIL")
@Data
@AllArgsConstructor
@NoArgsConstructor
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
}
