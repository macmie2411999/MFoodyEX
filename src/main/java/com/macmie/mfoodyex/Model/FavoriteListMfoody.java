package com.macmie.mfoodyex.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "`FAVORITE_LIST_MFOODY`")
// @RequiredArgsConstructor
public class FavoriteListMfoody {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID_FAVORITE_LIST")
    private int idFavoriteList;

    // Map to UserMfoody
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "ID_USER")
    private UserMfoody user;

    // Map to ProductMfoody
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "ID_PRODUCT")
    private ProductMfoody product;

    public FavoriteListMfoody() {
    }

    public FavoriteListMfoody(int idFavoriteList, UserMfoody user, ProductMfoody product) {
        this.idFavoriteList = idFavoriteList;
        this.user = user;
        this.product = product;
    }

    public int getIdFavoriteList() {
        return idFavoriteList;
    }

    public void setIdFavoriteList(int idFavoriteList) {
        this.idFavoriteList = idFavoriteList;
    }

    public UserMfoody getUser() {
        return user;
    }

    public void setUser(UserMfoody user) {
        this.user = user;
    }

    public ProductMfoody getProduct() {
        return product;
    }

    public void setProduct(ProductMfoody product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "FavoriteListMfoody{" +
                "idFavoriteList=" + idFavoriteList +
                ", user=" + user +
                ", product=" + product +
                '}';
    }
}
