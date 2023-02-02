package com.macmie.mfoodyex.Model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
class DetailProductOrderMfoodyId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "ID_PRODUCT")
    private int idProduct;

    @Column(name = "ID_ORDER")
    private int idOrder;

    public DetailProductOrderMfoodyId() {
    }

    public DetailProductOrderMfoodyId(int idProduct, int idOrder) {
        this.idProduct = idProduct;
        this.idOrder = idOrder;
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
