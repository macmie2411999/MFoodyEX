package com.macmie.mfoodyex.Service.InterfaceService;

import com.macmie.mfoodyex.Model.FavoriteListMfoody;

import java.util.List;

public interface FavoriteListMfoodyInterfaceService {
    public List<FavoriteListMfoody> getListFavoriteListMfoodys();

    public List<FavoriteListMfoody> getListFavoriteListMfoodysByIdProduct(int idProduct);

    public List<FavoriteListMfoody> getListFavoriteListMfoodysByIdUser(int idUser);

    public FavoriteListMfoody getFavoriteListMfoodyByID(int idFavoriteListMfoody);

    public FavoriteListMfoody saveFavoriteListMfoody(FavoriteListMfoody favoriteListMfoody);

    public FavoriteListMfoody updateFavoriteListMfoody(FavoriteListMfoody newFavoriteListMfoody);

    public void deleteFavoriteListMfoodyByID(int idFavoriteListMfoody);

    public void deleteAllFavoriteListMfoodysByIdUser(int idUser);

    public void deleteAllFavoriteListMfoodysByIdProduct(int idProduct);

    public Long countTotalNumberOfFavoriteListMfoodys();
}
