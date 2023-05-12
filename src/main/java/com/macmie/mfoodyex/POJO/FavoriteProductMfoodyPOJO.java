package com.macmie.mfoodyex.POJO;

import com.macmie.mfoodyex.Model.FavoriteProductMfoody;

public class FavoriteProductMfoodyPOJO {
    private int idFavoriteListProducts;
    private int idProduct;

    public FavoriteProductMfoodyPOJO() {
    }

    public FavoriteProductMfoodyPOJO(int idFavoriteListProducts, int idProduct) {
        this.idFavoriteListProducts = idFavoriteListProducts;
        this.idProduct = idProduct;
    }

    public boolean checkFavoriteProductMfoodyValidAttributes() {
        return true;
    }

    // ?
    public FavoriteProductMfoody renderFavoriteProductMfoody() {
        FavoriteProductMfoody newFavoriteProductMfoody = new FavoriteProductMfoody();
        // newFavoriteProductMfoody.getIdDetailProductCartMfoody().setIdFavoriteListProducts(this.getIdFavoriteListProducts());
        // newFavoriteProductMfoody.getIdDetailProductCartMfoody().setIdProduct(this.getIdProduct());
        return newFavoriteProductMfoody;
    }

    public int getIdFavoriteListProducts() {
        return idFavoriteListProducts;
    }

    public void setIdFavoriteListProducts(int idFavoriteListProducts) {
        this.idFavoriteListProducts = idFavoriteListProducts;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }
}
