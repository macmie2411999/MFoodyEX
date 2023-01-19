package com.macmie.mfoodyex.Service.InterfaceService;

import com.macmie.mfoodyex.Model.CartMfoody;

import java.util.List;

public interface CartMfoodyInterfaceService {
    public List<CartMfoody> getListCartMfoodys();

    public CartMfoody getCartMfoodyByID(int ID_CartMfoody);

    public CartMfoody saveCartMfoody(CartMfoody cartMfoody);

    public CartMfoody updateCartMfoody(CartMfoody newCartMfoody);

    public void deleteCartMfoodyByID(int ID_CartMfoody);
}