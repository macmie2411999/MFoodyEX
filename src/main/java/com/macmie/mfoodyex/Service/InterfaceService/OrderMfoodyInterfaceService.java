package com.macmie.mfoodyex.Service.InterfaceService;

import com.macmie.mfoodyex.Model.OrderMfoody;

import java.util.List;

public interface OrderMfoodyInterfaceService {
    public List<OrderMfoody> getListOrderMfoodys();

    public OrderMfoody getOrderMfoodyByID(int idOrderMfoody);

    public OrderMfoody saveOrderMfoody(OrderMfoody orderMfoody);

    public OrderMfoody updateOrderMfoody(OrderMfoody newOrderMfoody);

    public void deleteOrderMfoodyByID(int idOrderMfoody);

    public void deleteOrderMfoodyByIdUser(int idUser);
}
