package com.macmie.mfoodyex.Service.InterfaceService;

import com.macmie.mfoodyex.Model.FavoriteProductMfoody;
import com.macmie.mfoodyex.Model.FavoriteProductMfoodyId;

import java.util.List;

public interface FavoriteProductMfoodyInterfaceService {
    public List<FavoriteProductMfoody> getListFavoriteProductMfoodys();

    public List<FavoriteProductMfoody> getListFavoriteProductMfoodysByIdFavoriteListProducts(int idFavoriteListProducts);

    public List<FavoriteProductMfoody> getListFavoriteProductMfoodysByIdProduct(int idProduct);

    public FavoriteProductMfoody getFavoriteProductMfoodyByIFavoriteListProductsAndIdProduct(int idFavoriteListProducts, int idProduct);

    public FavoriteProductMfoody saveFavoriteProductMfoody(FavoriteProductMfoody FavoriteProductMfoody);

    public FavoriteProductMfoody updateFavoriteProductMfoody(FavoriteProductMfoody newFavoriteProductMfoody);

    public void deleteFavoriteProductMfoodyByIdFavoriteProductMfoody(int idFavoriteListProducts, int idProduct);

    public void deleteAllFavoriteProductsMfoodyByIdFavoriteListProducts(int idFavoriteListProducts);

    public void deleteAllFavoriteProductsMfoodyByIdProduct(int idProduct);

    public List<FavoriteProductMfoody> findAllFavoriteProductsMfoodyByIdFavoriteListProducts(int idFavoriteListProducts);

    public List<FavoriteProductMfoody> findAllFavoriteProductsMfoodyByIdProduct(int idProduct);
}
