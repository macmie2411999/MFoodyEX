package com.macmie.mfoodyex.Model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

/* Handle Jackson – Bidirectional Relationships (Loop)
    @JsonIgnore: ignore Serialization
    @JsonBackReference: the back part of reference; it'll be omitted from serialization (for ManyToOne - Object)
    @JsonManagedReference: the forward part of reference, the one that gets serialized normally (for OneToMany - list)
    @JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
    */

@Entity
@Table(name= "`CART_MFOODY`")
@Data
@RequiredArgsConstructor
public class CartMfoody {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID_CART")
    private int idCart;

    @NonNull
    @Column(name = "QUANTITY_ALL_PRODUCTS_IN_CART")
    private String quantityAllProductsInCart;

    @NonNull
    @Column(name = "SALE_PRICE_CART")
    private String salePriceCart;

    @NonNull
    @Column(name = "FULL_PRICE_CART")
    private String fullPriceCart;

    @NonNull
    @Column(name = "ID_USER")
    private int idUser;

    // Map to User
//    @ManyToOne
//    @JoinColumn(name = "ID_USER")
//    private UserMfoody user;

    public CartMfoody() {
    }

    public CartMfoody(int idUser, @NonNull String quantityAllProductsInCart, @NonNull String salePriceCart, @NonNull String fullPriceCart) {
        this.idUser = idUser;
        this.quantityAllProductsInCart = quantityAllProductsInCart;
        this.salePriceCart = salePriceCart;
        this.fullPriceCart = fullPriceCart;
    }

    public int getIdCart() {
        return idCart;
    }

    public void setIdCart(int idCart) {
        this.idCart = idCart;
    }

    public String getQuantityAllProductsInCart() {
        return quantityAllProductsInCart;
    }

    public void setQuantityAllProductsInCart(String quantityAllProductsInCart) {
        this.quantityAllProductsInCart = quantityAllProductsInCart;
    }

    public String getSalePriceCart() {
        return salePriceCart;
    }

    public void setSalePriceCart(String salePriceCart) {
        this.salePriceCart = salePriceCart;
    }

    public String getFullPriceCart() {
        return fullPriceCart;
    }

    public void setFullPriceCart(String fullPriceCart) {
        this.fullPriceCart = fullPriceCart;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
