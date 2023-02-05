package com.macmie.mfoodyex.Model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class DetailProductCartMfoodyId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "ID_CART")
    private int idCart;

    @Column(name = "ID_PRODUCT")
    private int idProduct;

    public DetailProductCartMfoodyId() {
    }

    public DetailProductCartMfoodyId(int idCart, int idProduct) {
        this.idCart = idCart;
        this.idProduct = idProduct;
    }

    public int getIdCart() {
        return idCart;
    }

    public void setIdCart(int idCart) {
        this.idCart = idCart;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }
}
