package com.macmie.mfoodyex.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private int quantityAllProductsInCart;

    @NonNull
    @Column(name = "SALE_PRICE_CART")
    private int salePriceCart;

    @NonNull
    @Column(name = "FULL_PRICE_CART")
    private int fullPriceCart;

    // Map to User
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "ID_USER")
    private UserMfoody user;

    public CartMfoody() {
    }

    public CartMfoody( @NonNull int quantityAllProductsInCart, @NonNull int salePriceCart, @NonNull int fullPriceCart, UserMfoody user) {
        this.quantityAllProductsInCart = quantityAllProductsInCart;
        this.salePriceCart = salePriceCart;
        this.fullPriceCart = fullPriceCart;
        this.user = user;
    }

    public int getIdCart() {
        return idCart;
    }

    public void setIdCart(int idCart) {
        this.idCart = idCart;
    }

    public int getQuantityAllProductsInCart() {
        return quantityAllProductsInCart;
    }

    public void setQuantityAllProductsInCart(int quantityAllProductsInCart) {
        this.quantityAllProductsInCart = quantityAllProductsInCart;
    }

    public int getSalePriceCart() {
        return salePriceCart;
    }

    public void setSalePriceCart(int salePriceCart) {
        this.salePriceCart = salePriceCart;
    }

    public int getFullPriceCart() {
        return fullPriceCart;
    }

    public void setFullPriceCart(int fullPriceCart) {
        this.fullPriceCart = fullPriceCart;
    }

    public UserMfoody getUser() {
        return user;
    }

    public void setUser(UserMfoody user) {
        this.user = user;
    }
}
