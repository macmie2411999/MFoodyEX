package com.macmie.mfoodyex.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "`FAVORITE_LIST_PRODUCTS_MFOODY`")
@RequiredArgsConstructor
public class FavoriteListProductsMfoody {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID_FAVORITE_LIST_PRODUCTS")
    private int idFavoriteListProducts;

    // Map to User
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "ID_USER")
    private UserMfoody user;

    // Refer to DETAIL_PRODUCT_FAVORITE_LIST_PRODUCTS_MFOODY
    @JsonManagedReference
    @OneToMany(mappedBy = "favoriteListProducts")
    private List<FavoriteProductMfoody> favoriteListProducts;

    // public FavoriteListProductsMfoody() {
    // }

    public FavoriteListProductsMfoody(int idFavoriteListProducts, UserMfoody user, List<FavoriteProductMfoody> favoriteListProducts) {
        this.idFavoriteListProducts = idFavoriteListProducts;
        this.user = user;
        this.favoriteListProducts = favoriteListProducts;
    }

    public int getIdFavoriteListProducts() {
        return idFavoriteListProducts;
    }

    public void setIdFavoriteListProducts(int idFavoriteListProducts) {
        this.idFavoriteListProducts = idFavoriteListProducts;
    }

    public UserMfoody getUser() {
        return user;
    }

    public void setUser(UserMfoody user) {
        this.user = user;
    }

    public List<FavoriteProductMfoody> getFavoriteListProducts() {
        return favoriteListProducts;
    }

    public void setfavoriteListProducts(List<FavoriteProductMfoody> favoriteListProducts) {
        this.favoriteListProducts = favoriteListProducts;
    }

    @Override
    public String toString() {
        return "FavoriteListProductsMfoody{" +
                "idFavoriteListProducts=" + idFavoriteListProducts +
                ", user=" + user.getIdUser() +
                ", favoriteListProducts=" + favoriteListProducts +
                '}';
    }
}
