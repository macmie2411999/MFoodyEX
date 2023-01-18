package com.macmie.mfoodyex.Model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name= "`USER_MFOODY`")
@Data
@RequiredArgsConstructor
public class UserMfoody {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID_USER")
    private int IdUser;

    @NonNull
    @Column(name = "EMAIL_USER")
    private String EmailUser;

    @NonNull
    @Column(name = "PASSWORD_USER")
    private String PasswordUser;

    @NonNull
    @Column(name = "NAME_USER")
    private String NameUser;

    @NonNull
    @Column(name = "PHONE_NUMBER_USER")
    private String PhoneNumberUser;

    @NonNull
    @Column(name = "ADDRESS_USER")
    private String AddressUser;

    @NonNull
    @Column(name = "ROLE_USER")
    private String RoleUser;

    // Refer to CREDIT_CARD_MFOODY
    @OneToMany(mappedBy = "User")
    private List<CreditCardMfoody> ListCreditCards;

//    // Refer to ORDER_MFOODY
//    @OneToMany(mappedBy = "User")
//    private List<OrderMfoody> ListOrders;

//    // Refer to CART_MFOODY
//    @OneToMany(mappedBy = "User")
//    private List<CartMfoody> ListCarts;
//
//    // Refer to COMMENT_MFOODY
//    @OneToMany(mappedBy = "User")
//    private List<CommentMfoody> ListComments;

    public UserMfoody() {
    }

    public UserMfoody(int idUser, @NonNull String emailUser, @NonNull String passwordUser, @NonNull String nameUser, @NonNull String phoneNumberUser, @NonNull String addressUser, @NonNull String roleUser) {
        IdUser = idUser;
        EmailUser = emailUser;
        PasswordUser = passwordUser;
        NameUser = nameUser;
        PhoneNumberUser = phoneNumberUser;
        AddressUser = addressUser;
        RoleUser = roleUser;
    }

    public int getIdUser() {
        return IdUser;
    }

    public void setIdUser(int idUser) {
        IdUser = idUser;
    }

    public String getEmailUser() {
        return EmailUser;
    }

    public void setEmailUser(String emailUser) {
        EmailUser = emailUser;
    }

    public String getPasswordUser() {
        return PasswordUser;
    }

    public void setPasswordUser(String passwordUser) {
        PasswordUser = passwordUser;
    }

    public String getNameUser() {
        return NameUser;
    }

    public void setNameUser(String nameUser) {
        NameUser = nameUser;
    }

    public String getPhoneNumberUser() {
        return PhoneNumberUser;
    }

    public void setPhoneNumberUser(String phoneNumberUser) {
        PhoneNumberUser = phoneNumberUser;
    }

    public String getAddressUser() {
        return AddressUser;
    }

    public void setAddressUser(String addressUser) {
        AddressUser = addressUser;
    }

    public String getRoleUser() {
        return RoleUser;
    }

    public void setRoleUser(String roleUser) {
        RoleUser = roleUser;
    }
}
