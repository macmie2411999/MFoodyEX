package com.macmie.mfoodyex.Service.InterfaceService;

import com.macmie.mfoodyex.Model.DetailProductOrderMfoody;

import java.util.List;

public interface DetailProductOrderMfoodyInterfaceService {
    public List<DetailProductOrderMfoody> getListDetailProductOrderMfoodys();

    public DetailProductOrderMfoody getDetailProductOrderMfoodyByIOrderAndIdProduct(int idOrder, int idProduct);

    public DetailProductOrderMfoody saveDetailProductOrderMfoody(DetailProductOrderMfoody DetailProductOrderMfoody);

    public DetailProductOrderMfoody updateDetailProductOrderMfoody(DetailProductOrderMfoody newDetailProductOrderMfoody);

    public void deleteDetailProductOrderMfoodyByID(int idDetailProductOrderMfoody);

    public void deleteAllDetailProductOrdersMfoodyByIdOrder(int idOrder);

    public void deleteAllDetailProductOrdersMfoodyByIdProduct(int idProduct);

    public List<DetailProductOrderMfoody> findAllDetailProductOrdersMfoodyByIdOrder(int idOrder);

    public List<DetailProductOrderMfoody> findAllDetailProductOrdersMfoodyByIdProduct(int idProduct);
}
