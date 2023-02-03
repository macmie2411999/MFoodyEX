package com.macmie.mfoodyex.POJO;

import com.macmie.mfoodyex.Model.DetailProductCartMfoody;

public class DetailProductCartMfoodyPOJO {
    private int idProduct;
    private int idCart;
    private int quantityDetailProductCart;
    private float salePriceDetailProductCart;
    private float fullPriceDetailProductCart;

    public DetailProductCartMfoodyPOJO() {
    }

    public DetailProductCartMfoodyPOJO(int idProduct, int idCart, int quantityDetailProductCart, float salePriceDetailProductCart, float fullPriceDetailProductCart) {
        this.idProduct = idProduct;
        this.idCart = idCart;
        this.quantityDetailProductCart = quantityDetailProductCart;
        this.salePriceDetailProductCart = salePriceDetailProductCart;
        this.fullPriceDetailProductCart = fullPriceDetailProductCart;
    }

    public DetailProductCartMfoody renderDetailProductCartMfoody() {
        DetailProductCartMfoody newDetailProductCartMfoody = new DetailProductCartMfoody();
        newDetailProductCartMfoody.setQuantityDetailProductCart(this.getQuantityDetailProductCart());
        newDetailProductCartMfoody.setSalePriceDetailProductCart(this.getSalePriceDetailProductCart());
        newDetailProductCartMfoody.setFullPriceDetailProductCart(this.getFullPriceDetailProductCart());
        return newDetailProductCartMfoody;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getIdCart() {
        return idCart;
    }

    public void setIdCart(int idCart) {
        this.idCart = idCart;
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
