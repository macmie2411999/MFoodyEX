package com.macmie.mfoodyex.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

/* Handle Jackson – Bidirectional Relationships (Loop)
    @JsonIgnore: ignore Serialization
    @JsonBackReference: the back part of reference; it'll be omitted from serialization (for ManyToOne - Object)
    @JsonManagedReference: the forward part of reference, the one that gets serialized normally (for OneToMany - list)
    @JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
    */

@Entity
@Table(name = "`USER_MFOODY`")
@RequiredArgsConstructor
public class UserMfoody {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID_USER")
    private int idUser;

    @NonNull
    @Column(name = "EMAIL_USER")
    private String emailUser;

    @NonNull
    @Column(name = "PASSWORD_USER")
    private String passwordUser;

    @NonNull
    @Column(name = "NAME_USER")
    private String nameUser;

    @NonNull
    @Column(name = "PHONE_NUMBER_USER")
    private String phoneNumberUser;

    @NonNull
    @Column(name = "ADDRESS_USER")
    private String addressUser;

    @NonNull
    @Column(name = "ROLE_USER")
    private String roleUser;

    // Refer to CREDIT_CARD_MFOODY
    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<CreditCardMfoody> listCreditCards;

//    // Refer to ORDER_MFOODY
//    @OneToMany(mappedBy = "User")
//    private List<OrderMfoody> listOrders;

    // Refer to CART_MFOODY
    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<CartMfoody> listCarts;

    // Refer to COMMENT_MFOODY
    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<CommentMfoody> listComments;

    public UserMfoody() {
    }

    public UserMfoody(int idUser, @NonNull String emailUser, @NonNull String passwordUser, @NonNull String nameUser, @NonNull String phoneNumberUser, @NonNull String addressUser, @NonNull String roleUser, List<CreditCardMfoody> listCreditCards, List<CommentMfoody> listComments, List<CartMfoody> listCarts) {
        this.idUser = idUser;
        this.emailUser = emailUser;
        this.passwordUser = passwordUser;
        this.nameUser = nameUser;
        this.phoneNumberUser = phoneNumberUser;
        this.addressUser = addressUser;
        this.roleUser = roleUser;
        this.listComments = listComments;
        this.listCreditCards = listCreditCards;
        this.listCarts = listCarts;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getPhoneNumberUser() {
        return phoneNumberUser;
    }

    public void setPhoneNumberUser(String phoneNumberUser) {
        this.phoneNumberUser = phoneNumberUser;
    }

    public String getAddressUser() {
        return addressUser;
    }

    public void setAddressUser(String addressUser) {
        this.addressUser = addressUser;
    }

    public String getRoleUser() {
        return roleUser;
    }

    public void setRoleUser(String roleUser) {
        this.roleUser = roleUser;
    }

    public List<CreditCardMfoody> getListCreditCards() {
        return listCreditCards;
    }

    public void setListCreditCards(List<CreditCardMfoody> listCreditCards) {
        this.listCreditCards = listCreditCards;
    }
}
