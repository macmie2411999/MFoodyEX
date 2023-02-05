package com.macmie.mfoodyex.POJO;

import com.macmie.mfoodyex.Model.CommentMfoody;
import com.macmie.mfoodyex.Model.CreditCardMfoody;
import com.macmie.mfoodyex.Model.UserMfoody;

import java.util.List;

public class UserMfoodyPOJO {
    private int idUser;
    private String emailUser;
    private String passwordUser;
    private String nameUser;
    private String phoneNumberUser;
    private String addressUser;
    private String roleUser;
    private List<CreditCardMfoody> listCreditCards;
    private List<CommentMfoody> listComments;

    public UserMfoodyPOJO() {
    }

    public UserMfoodyPOJO(int idUser, String emailUser, String passwordUser, String nameUser, String phoneNumberUser, String addressUser, String roleUser) {
        this.idUser = idUser;
        this.emailUser = emailUser;
        this.passwordUser = passwordUser;
        this.nameUser = nameUser;
        this.phoneNumberUser = phoneNumberUser;
        this.addressUser = addressUser;
        this.roleUser = roleUser;
    }

    public UserMfoody renderUserMfoody() {
        UserMfoody newUserMfoody = new UserMfoody();
        newUserMfoody.setIdUser(this.getIdUser());
        newUserMfoody.setEmailUser(this.getEmailUser());
        newUserMfoody.setPasswordUser(this.getPasswordUser());
        newUserMfoody.setNameUser(this.getNameUser());
        newUserMfoody.setPhoneNumberUser(this.getPhoneNumberUser());
        newUserMfoody.setAddressUser(this.getAddressUser());
        newUserMfoody.setRoleUser(this.getRoleUser());
        return newUserMfoody;
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

    public List<CommentMfoody> getListComments() {
        return listComments;
    }

    public void setListComments(List<CommentMfoody> listComments) {
        this.listComments = listComments;
    }
}
