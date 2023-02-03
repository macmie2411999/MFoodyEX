package com.macmie.mfoodyex.Service.InterfaceService;

import com.macmie.mfoodyex.Model.DetailProductCartMfoody;

import java.util.List;

public interface DetailProductCartMfoodyInterfaceService {
    public List<DetailProductCartMfoody> getListDetailProductCartMfoodys();

    public DetailProductCartMfoody getDetailProductCartMfoodyByID(int idDetailProductCartMfoody);

    public DetailProductCartMfoody saveDetailProductCartMfoody(DetailProductCartMfoody DetailProductCartMfoody);

    public DetailProductCartMfoody updateDetailProductCartMfoody(DetailProductCartMfoody newDetailProductCartMfoody);

    public void deleteDetailProductCartMfoodyByID(int idDetailProductCartMfoody);

    public void deleteAllDetailProductCartsMfoodyByIdCart(int idCart);

    public void deleteAllDetailProductCartsMfoodyByIdProduct(int idProduct);
}
