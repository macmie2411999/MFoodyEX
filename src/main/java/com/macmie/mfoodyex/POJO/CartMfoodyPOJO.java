package com.macmie.mfoodyex.POJO;

import com.macmie.mfoodyex.Model.CartMfoody;
import com.macmie.mfoodyex.Model.CommentMfoody;

public class CartMfoodyPOJO {
    private int idCart;
    private int quantityAllProductsInCart;
    private int salePriceCart;
    private int fullPriceCart;
    private int idUser;

    public CartMfoody renderCartMfoody() {
        CartMfoody newCartMfoody = new CartMfoody();
        newCartMfoody.setIdCart(this.getIdCart());
        newCartMfoody.setQuantityAllProductsInCart(this.getQuantityAllProductsInCart());
        newCartMfoody.setSalePriceCart(this.getSalePriceCart());
        newCartMfoody.setFullPriceCart(this.getFullPriceCart());
        return newCartMfoody;
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

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
