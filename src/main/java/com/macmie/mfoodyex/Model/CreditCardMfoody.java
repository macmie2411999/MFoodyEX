package com.macmie.mfoodyex.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.NonNull;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/* Handle Jackson – Bidirectional Relationships (Loop)
    @JsonIgnore: ignore Serialization
    @JsonBackReference: the back part of reference; it'll be omitted from serialization (for ManyToOne - Object)
    @JsonManagedReference: the forward part of reference, the one that gets serialized normally (for OneToMany - list)
    @JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id": show full)
    */

@Entity
@Table(name = "`CREDIT_CARD_MFOODY`")
public class CreditCardMfoody {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID_CARD")
    private int idCard;

    @NonNull
    @Column(name = "NAME_USER_CARD")
    private String nameUserCard;

    @NonNull
    @Column(name = "NUMBER_CARD")
    private String numberCard;

    @NonNull
    @Column(name = "EXPIRATION_CARD")
    private String expirationCard;

    @NonNull
    @Column(name = "SECURITY_CODE_CARD")
    private String securityCodeCard;

    // Map to UserMfoody
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "ID_USER")
    private UserMfoody user;

    public CreditCardMfoody() {
    }

    public CreditCardMfoody(int idCard, @NonNull String nameUserCard, @NonNull String numberCard, @NonNull String expirationCard, @NonNull String securityCodeCard, UserMfoody user) {
        this.idCard = idCard;
        this.nameUserCard = nameUserCard;
        this.numberCard = numberCard;
        this.expirationCard = expirationCard;
        this.securityCodeCard = securityCodeCard;
        this.user = user;
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

    public UserMfoody getUser() {
        return user;
    }

    public void setUser(UserMfoody user) {
        this.user = user;
    }
}
