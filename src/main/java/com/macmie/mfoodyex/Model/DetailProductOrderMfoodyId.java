package com.macmie.mfoodyex.Model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class DetailProductOrderMfoodyId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "ID_ORDER")
    private int idOrder;

    @Column(name = "ID_PRODUCT")
    private int idProduct;

    public DetailProductOrderMfoodyId() {
    }

    public DetailProductOrderMfoodyId(int idOrder, int idProduct) {
        this.idOrder = idOrder;
        this.idProduct = idProduct;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }
}
