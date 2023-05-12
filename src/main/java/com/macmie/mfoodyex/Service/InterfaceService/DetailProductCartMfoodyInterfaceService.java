package com.macmie.mfoodyex.Service.InterfaceService;

import com.macmie.mfoodyex.Model.DetailProductCartMfoody;
import com.macmie.mfoodyex.Model.DetailProductCartMfoodyId;

import java.util.List;

public interface DetailProductCartMfoodyInterfaceService {
    public List<DetailProductCartMfoody> getListDetailProductCartMfoodys();

    public List<DetailProductCartMfoody> getListDetailProductCartMfoodysByIdCart(int idCart);

    public List<DetailProductCartMfoody> getListDetailProductCartMfoodysByIdProduct(int idProduct);

    public DetailProductCartMfoody getDetailProductCartMfoodyByICartAndIdProduct(int idCart, int idProduct);

    public DetailProductCartMfoody saveDetailProductCartMfoody(DetailProductCartMfoody detailProductCartMfoody);

    public DetailProductCartMfoody updateDetailProductCartMfoody(DetailProductCartMfoody newDetailProductCartMfoody);

    public void deleteDetailProductCartMfoodyByIdDetailProductCartMfoody(int idCart, int idProduct);

    public void deleteAllDetailProductCartsMfoodyByIdCart(int idCart);

    public void deleteAllDetailProductCartsMfoodyByIdProduct(int idProduct);

    public List<DetailProductCartMfoody> findAllDetailProductCartsMfoodyByIdCart(int idCart);

    public List<DetailProductCartMfoody> findAllDetailProductCartsMfoodyByIdProduct(int idProduct);
}
