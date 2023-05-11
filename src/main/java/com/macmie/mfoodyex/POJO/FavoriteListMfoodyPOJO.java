package com.macmie.mfoodyex.POJO;

import com.macmie.mfoodyex.Model.FavoriteListMfoody;
import com.macmie.mfoodyex.Util.InputChecker;

public class FavoriteListMfoodyPOJO {
    private int idFavoriteList;
    private int idUser;
    private int idProduct;

    public FavoriteListMfoody renderFavoriteListMfoody() {
        FavoriteListMfoody newFavoriteListMfoody = new FavoriteListMfoody();
        newFavoriteListMfoody.setIdFavoriteList(this.getIdFavoriteList());
        return newFavoriteListMfoody;
    }

    // public boolean checkFavoriteListMfoodyValidAttributes() {
    //     if(InputChecker.isIntegerValid(this.idUser) && InputChecker.isIntegerValid(this.idProduct)) {
    //         return true;
    //     }
    //     return  false;
    // }

    public int getIdFavoriteList() {
        return idFavoriteList;
    }

    public void setIdFavoriteList(int idFavoriteList) {
        this.idFavoriteList = idFavoriteList;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }
}
