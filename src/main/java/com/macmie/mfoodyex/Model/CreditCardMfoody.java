package com.macmie.mfoodyex.Model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name= "`CREDIT_CARD_MFOODY`")
@Data
@RequiredArgsConstructor
public class CreditCardMfoody {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID_CARD")
    private int IdCard;

    @NonNull
    @Column(name = "NAME_USER_CARD")
    private String NameUserCard;

    @NonNull
    @Column(name = "NUMBER_CARD")
    private String NumberCard;

    @NonNull
    @Column(name = "EXPIRATION_CARD")
    private String ExpirationCard;

    @NonNull
    @Column(name = "SECURITY_CODE_CARD")
    private String SecurityCodeCard;

    // Foreign Key to User
    @NonNull
    @Column(name = "ID_USER")
    private String IdUser;

    // Map to User
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ID_USER", referencedColumnName = "ID_USER", insertable = false, updatable = false)
    private UserMfoody User;

    public CreditCardMfoody() {
    }

    public CreditCardMfoody(int idCard, @NonNull String nameUserCard, @NonNull String numberCard, @NonNull String expirationCard, @NonNull String securityCodeCard, @NonNull String idUser) {
        IdCard = idCard;
        NameUserCard = nameUserCard;
        NumberCard = numberCard;
        ExpirationCard = expirationCard;
        SecurityCodeCard = securityCodeCard;
        IdUser = idUser;
    }

    public int getIdCard() {
        return IdCard;
    }

    public void setIdCard(int idCard) {
        IdCard = idCard;
    }

    public String getNameUserCard() {
        return NameUserCard;
    }

    public void setNameUserCard(String nameUserCard) {
        NameUserCard = nameUserCard;
    }

    public String getNumberCard() {
        return NumberCard;
    }

    public void setNumberCard(String numberCard) {
        NumberCard = numberCard;
    }

    public String getExpirationCard() {
        return ExpirationCard;
    }

    public void setExpirationCard(String expirationCard) {
        ExpirationCard = expirationCard;
    }

    public String getSecurityCodeCard() {
        return SecurityCodeCard;
    }

    public void setSecurityCodeCard(String securityCodeCard) {
        SecurityCodeCard = securityCodeCard;
    }

    public String getIdUser() {
        return IdUser;
    }

    public void setIdUser(String idUser) {
        IdUser = idUser;
    }
}
