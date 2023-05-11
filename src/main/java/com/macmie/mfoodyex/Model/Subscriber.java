package com.macmie.mfoodyex.Model;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "`SUBSCRIBER_MFOODY`")
@RequiredArgsConstructor
public class Subscriber {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID_SUBSCRIBER")
    private int idSubscriber;

    @NonNull
    @Column(name = "EMAIL_SUBSCRIBER")
    private String emailSubscriber;

    public Subscriber() {
    }

    public Subscriber(int idSubscriber, @NonNull String emailSubscriber) {
        this.idSubscriber = idSubscriber;
        this.emailSubscriber = emailSubscriber;
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
