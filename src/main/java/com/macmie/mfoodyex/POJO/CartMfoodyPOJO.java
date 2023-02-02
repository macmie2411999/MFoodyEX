package com.macmie.mfoodyex.POJO;

import com.macmie.mfoodyex.Model.CartMfoody;
import com.macmie.mfoodyex.Model.CommentMfoody;

public class CartMfoodyPOJO {
    private int idCart;
    private int quantityAllProductsInCart;
    private float salePriceCart;
    private float fullPriceCart;
    private int idUser;

    public CartMfoody renderCartMfoody() {
        CartMfoody newCartMfoody = new CartMfoody();
        newCartMfoody.setIdCart(this.getIdCart());
        newCartMfoody.setQuantityAllProductsInCart(this.getQuantityAllProductsInCart());
        newCartMfoody.setSalePriceCart(this.getSalePriceCart());
        newCartMfoody.setFullPriceCart(this.getFullPriceCart());
        return newCartMfoody;
    }

    public CartMfoodyPOJO(int idCart, int quantityAllProductsInCart, float salePriceCart, float fullPriceCart, int idUser) {
        this.idCart = idCart;
        this.quantityAllProductsInCart = quantityAllProductsInCart;
        this.salePriceCart = salePriceCart;
        this.fullPriceCart = fullPriceCart;
        this.idUser = idUser;
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

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
