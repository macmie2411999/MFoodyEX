package com.macmie.mfoodyex.POJO;

import com.macmie.mfoodyex.Model.FavoriteListProductsMfoody;
import org.springframework.beans.factory.annotation.Autowired;
import com.macmie.mfoodyex.Util.InputChecker;

public class FavoriteListProductsMfoodyPOJO {
    @Autowired
    private InputChecker inputChecker;

    private int idFavoriteListProducts;
    private int idUser;

    public FavoriteListProductsMfoodyPOJO() {
    }

    public FavoriteListProductsMfoodyPOJO(int idFavoriteListProducts, int idUser) {
        this.idFavoriteListProducts = idFavoriteListProducts;
        this.idUser = idUser;
    }

    public FavoriteListProductsMfoody renderFavoriteListProductsMfoody() {
        FavoriteListProductsMfoody newFavoriteListProductsMfoody = new FavoriteListProductsMfoody();
        newFavoriteListProductsMfoody.setIdFavoriteListProducts(this.getIdFavoriteListProducts());
        return newFavoriteListProductsMfoody;
    }

    public boolean checkFavoriteListProductsMfoodyValidAttributes() {
        return true;
    }

    public int getIdFavoriteListProducts() {
        return idFavoriteListProducts;
    }

    public void setIdFavoriteListProducts(int idFavoriteListProducts) {
        this.idFavoriteListProducts = idFavoriteListProducts;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
