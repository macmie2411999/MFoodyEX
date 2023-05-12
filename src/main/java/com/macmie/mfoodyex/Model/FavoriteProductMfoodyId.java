package com.macmie.mfoodyex.Model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class FavoriteProductMfoodyId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "ID_FAVORITE_LIST_PRODUCTS")
    private int idFavoriteListProducts;

    @Column(name = "ID_PRODUCT")
    private int idProduct;

    public FavoriteProductMfoodyId() {
    }

    public FavoriteProductMfoodyId(int idFavoriteListProducts, int idProduct) {
        this.idFavoriteListProducts = idFavoriteListProducts;
        this.idProduct = idProduct;
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
