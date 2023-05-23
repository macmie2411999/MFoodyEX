package com.macmie.mfoodyex.POJO;

import com.macmie.mfoodyex.Model.CartMfoody;
import com.macmie.mfoodyex.Util.InputChecker;
import com.macmie.mfoodyex.Util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class CartMfoodyPOJO {
    @Autowired
    private InputChecker inputChecker;

    private int idCart;
    private int quantityAllProductsInCart;
    private float totalSalePriceCart;
    private float totalFullPriceCart;
    private int idUser;

    public CartMfoody renderCartMfoody() {
        CartMfoody newCartMfoody = new CartMfoody();
        newCartMfoody.setIdCart(this.getIdCart());
        newCartMfoody.setQuantityAllProductsInCart(this.getQuantityAllProductsInCart());
        newCartMfoody.setTotalSalePriceCart(this.getTotalSalePriceCart());
        newCartMfoody.setTotalFullPriceCart(this.getTotalFullPriceCart());
        return newCartMfoody;
    }

    public CartMfoody renderCartMfoodyWithoutIdCart() {
        CartMfoody newCartMfoody = new CartMfoody();
        newCartMfoody.setQuantityAllProductsInCart(this.getQuantityAllProductsInCart());
        newCartMfoody.setTotalSalePriceCart(this.getTotalSalePriceCart());
        newCartMfoody.setTotalFullPriceCart(this.getTotalFullPriceCart());
        return newCartMfoody;
    }

    public boolean checkCartMfoodyValidAttributes() {
            return true;
    }

    public CartMfoodyPOJO(int idCart, int quantityAllProductsInCart, float totalSalePriceCart, float totalFullPriceCart, int idUser) {
        this.idCart = idCart;
        this.quantityAllProductsInCart = quantityAllProductsInCart;
        this.totalSalePriceCart = totalSalePriceCart;
        this.totalFullPriceCart = totalFullPriceCart;
        this.idUser = idUser;
    }

    public int getIdCart() {
        return idCart;
    }

    public void setIdCart(int idCart) {
        this.idCart = idCart;
    }

    public int getQuantityAllProductsInCart() {
        return quantityAllProductsInCart;
    }

    public void setQuantityAllProductsInCart(int quantityAllProductsInCart) {
        this.quantityAllProductsInCart = quantityAllProductsInCart;
    }

    public float getTotalSalePriceCart() {
        return totalSalePriceCart;
    }

    public void setTotalSalePriceCart(float totalSalePriceCart) {
        this.totalSalePriceCart = totalSalePriceCart;
    }

    public float getTotalFullPriceCart() {
        return totalFullPriceCart;
    }

    public void setTotalFullPriceCart(float totalFullPriceCart) {
        this.totalFullPriceCart = totalFullPriceCart;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
