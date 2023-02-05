package com.macmie.mfoodyex.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

import java.io.Serializable;

/* Handle Jackson – Bidirectional Relationships (Loop)
    @JsonIgnore: ignore Serialization
    @JsonBackReference: the back part of reference; it'll be omitted from serialization (for ManyToOne - Object)
    @JsonManagedReference: the forward part of reference, the one that gets serialized normally (for OneToMany - list)
    @JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id": show full)
    */
@Entity
@Table(name= "`DETAIL_PRODUCT_CART_MFOODY`")
public class DetailProductCartMfoody implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId // indicate that a field or property maps to a composite primary key class that is stored as an embedded object
    private DetailProductCartMfoodyId idDetailProductCartMFoody;

    @Column(name="QUANTITY_DETAIL_PRODUCT_CART")
    private int quantityDetailProductCart;

    @Column(name="SALE_PRICE_DETAIL_PRODUCT_CART")
    private float salePriceDetailProductCart;

    @Column(name="FULL_PRICE_DETAIL_PRODUCT_CART")
    private float fullPriceDetailProductCart;

    // Map to CART_MFOODY
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "ID_CART", insertable = false, updatable = false)
    private CartMfoody cart;

    // Map to PRODUCT_MFOODY
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "ID_PRODUCT", insertable = false, updatable = false)
    private ProductMfoody product;

    public DetailProductCartMfoody() {
    }

    public DetailProductCartMfoody(DetailProductCartMfoodyId idDetailProductCartMFoody, int quantityDetailProductCart, float salePriceDetailProductCart, float fullPriceDetailProductCart, CartMfoody cart, ProductMfoody product) {
        this.idDetailProductCartMFoody = idDetailProductCartMFoody;
        this.quantityDetailProductCart = quantityDetailProductCart;
        this.salePriceDetailProductCart = salePriceDetailProductCart;
        this.fullPriceDetailProductCart = fullPriceDetailProductCart;
        this.cart = cart;
        this.product = product;
    }

    public DetailProductCartMfoodyId getIdDetailProductCartMFoody() {
        return idDetailProductCartMFoody;
    }

    public void setIdDetailProductCartMfoody(DetailProductCartMfoodyId idDetailProductCartMFoody) {
        this.idDetailProductCartMFoody = idDetailProductCartMFoody;
    }

    public CartMfoody getCart() {
        return cart;
    }

    public void setCart(CartMfoody cart) {
        this.cart = cart;
    }

    public ProductMfoody getProduct() {
        return product;
    }

    public void setProduct(ProductMfoody product) {
        this.product = product;
    }

    public int getQuantityDetailProductCart() {
        return quantityDetailProductCart;
    }

    public void setQuantityDetailProductCart(int quantityDetailProductCart) {
        this.quantityDetailProductCart = quantityDetailProductCart;
    }

    public float getSalePriceDetailProductCart() {
        return salePriceDetailProductCart;
    }

    public void setSalePriceDetailProductCart(float salePriceDetailProductCart) {
        this.salePriceDetailProductCart = salePriceDetailProductCart;
    }

    public float getFullPriceDetailProductCart() {
        return fullPriceDetailProductCart;
    }

    public void setFullPriceDetailProductCart(float fullPriceDetailProductCart) {
        this.fullPriceDetailProductCart = fullPriceDetailProductCart;
    }
}

