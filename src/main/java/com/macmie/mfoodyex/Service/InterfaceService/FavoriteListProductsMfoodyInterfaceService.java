package com.macmie.mfoodyex.Service.InterfaceService;

import com.macmie.mfoodyex.Model.FavoriteListProductsMfoody;

import java.util.List;

public interface FavoriteListProductsMfoodyInterfaceService {
    public List<FavoriteListProductsMfoody> getListFavoriteListProductsMfoodys();

    public FavoriteListProductsMfoody getFavoriteListProductsMfoodyByID(int idFavoriteListProductsMfoody);

    public FavoriteListProductsMfoody getFavoriteListProductsMfoodyByIdUser(int idUser);

    public FavoriteListProductsMfoody saveFavoriteListProductsMfoody(FavoriteListProductsMfoody FavoriteListProductsMfoody);

    public FavoriteListProductsMfoody updateFavoriteListProductsMfoody(FavoriteListProductsMfoody newFavoriteListProductsMfoody);

    public void deleteFavoriteListProductsMfoodyByID(int idFavoriteListProductsMfoody);

    public void deleteFavoriteListProductsMfoodyByIdUser(int idUser);
}
