package com.macmie.mfoodyex.Service.InterfaceService;

import com.macmie.mfoodyex.Model.OrderMfoody;

import java.util.List;

public interface OrderMfoodyInterfaceService {
    public List<OrderMfoody> getListOrderMfoodys();

    public OrderMfoody getOrderMfoodyByID(int ID_OrderMfoody);

    public OrderMfoody saveOrderMfoody(OrderMfoody orderMfoody);

    public OrderMfoody updateOrderMfoody(OrderMfoody newOrderMfoody);

    public void deleteOrderMfoodyByID(int ID_OrderMfoody);
}
