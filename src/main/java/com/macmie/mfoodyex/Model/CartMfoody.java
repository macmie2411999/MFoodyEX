package com.macmie.mfoodyex.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

import java.util.List;

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
    private float salePriceCart;

    @NonNull
    @Column(name = "FULL_PRICE_CART")
    private float fullPriceCart;

    // Map to User
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "ID_USER")
    private UserMfoody user;

    // Refer to DETAIL_PRODUCT_CART_MFOODY
    @JsonManagedReference
    @OneToMany(mappedBy = "cart")
    private List<DetailProductCartMfoody> listDetailProductCarts;

    public CartMfoody() {
    }

    public CartMfoody( int idCart, @NonNull int quantityAllProductsInCart, @NonNull float salePriceCart, @NonNull float fullPriceCart, UserMfoody user, List<DetailProductCartMfoody> listDetailProductCarts) {
        this.idCart = idCart;
        this.quantityAllProductsInCart = quantityAllProductsInCart;
        this.salePriceCart = salePriceCart;
        this.fullPriceCart = fullPriceCart;
        this.user = user;
        this.listDetailProductCarts = listDetailProductCarts;
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

    public float getSalePriceCart() {
        return salePriceCart;
    }

    public void setSalePriceCart(float salePriceCart) {
        this.salePriceCart = salePriceCart;
    }

    public float getFullPriceCart() {
        return fullPriceCart;
    }

    public void setFullPriceCart(float fullPriceCart) {
        this.fullPriceCart = fullPriceCart;
    }

    public UserMfoody getUser() {
        return user;
    }

    public void setUser(UserMfoody user) {
        this.user = user;
    }

    public List<DetailProductCartMfoody> getListDetailProductCarts() {
        return listDetailProductCarts;
    }

    public void setListDetailProductCarts(List<DetailProductCartMfoody> listDetailProductCarts) {
        this.listDetailProductCarts = listDetailProductCarts;
    }
}
