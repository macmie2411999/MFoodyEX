package com.macmie.mfoodyex.Service.InterfaceService;

import com.macmie.mfoodyex.Model.DetailProductOrderMfoody;
import com.macmie.mfoodyex.Model.DetailProductOrderMfoody;

import java.util.List;

public interface DetailProductOrderMfoodyInterfaceService {
    public List<DetailProductOrderMfoody> getListDetailProductOrderMfoodys();

    public List<DetailProductOrderMfoody> getListDetailProductOrderMfoodysByIdOrder(int idOrder);

    public List<DetailProductOrderMfoody> getListDetailProductOrderMfoodysByIdProduct(int idProduct);

    public DetailProductOrderMfoody getDetailProductOrderMfoodyByIOrderAndIdProduct(int idOrder, int idProduct);

    public DetailProductOrderMfoody saveDetailProductOrderMfoody(DetailProductOrderMfoody detailProductOrderMfoody);

    public DetailProductOrderMfoody saveDetailProductOrderMfoodyByQuery(int idOrder, int idProduct, int quantityDetailProductOrder, float salePriceDetailProductOrder, float fullPriceDetailProductOrder);

    public DetailProductOrderMfoody updateDetailProductOrderMfoody(DetailProductOrderMfoody newDetailProductOrderMfoody);

    public void deleteDetailProductOrderMfoodyByIdDetailProductOrderMfoody(int idOrder, int idProduct);

    public void deleteAllDetailProductOrdersMfoodyByIdOrder(int idOrder);

    public void deleteAllDetailProductOrdersMfoodyByIdProduct(int idProduct);

    public List<DetailProductOrderMfoody> findAllDetailProductOrdersMfoodyByIdOrder(int idOrder);

    public List<DetailProductOrderMfoody> findAllDetailProductOrdersMfoodyByIdProduct(int idProduct);
}
