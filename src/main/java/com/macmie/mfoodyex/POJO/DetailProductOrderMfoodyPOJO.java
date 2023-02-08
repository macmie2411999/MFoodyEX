package com.macmie.mfoodyex.POJO;

import com.macmie.mfoodyex.Model.DetailProductOrderMfoody;

public class DetailProductOrderMfoodyPOJO {
    private int idProduct;
    private int idOrder;
    private int quantityDetailProductOrder;
    private float salePriceDetailProductOrder;
    private float fullPriceDetailProductOrder;

    public DetailProductOrderMfoodyPOJO() {
    }

    public DetailProductOrderMfoodyPOJO(int idProduct, int idOrder, int quantityDetailProductOrder, float salePriceDetailProductOrder, float fullPriceDetailProductOrder) {
        this.idProduct = idProduct;
        this.idOrder = idOrder;
        this.quantityDetailProductOrder = quantityDetailProductOrder;
        this.salePriceDetailProductOrder = salePriceDetailProductOrder;
        this.fullPriceDetailProductOrder = fullPriceDetailProductOrder;
    }

    public DetailProductOrderMfoody renderDetailProductOrderMfoody() {
        DetailProductOrderMfoody newDetailProductOrderMfoody = new DetailProductOrderMfoody();
        newDetailProductOrderMfoody.setQuantityDetailProductOrder(this.getQuantityDetailProductOrder());
        newDetailProductOrderMfoody.setSalePriceDetailProductOrder(this.getSalePriceDetailProductOrder());
        newDetailProductOrderMfoody.setFullPriceDetailProductOrder(this.getFullPriceDetailProductOrder());
        return newDetailProductOrderMfoody;
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

    public int getQuantityDetailProductOrder() {
        return quantityDetailProductOrder;
    }

    public void setQuantityDetailProductOrder(int quantityDetailProductOrder) {
        this.quantityDetailProductOrder = quantityDetailProductOrder;
    }

    public float getSalePriceDetailProductOrder() {
        return salePriceDetailProductOrder;
    }

    public void setSalePriceDetailProductOrder(float salePriceDetailProductOrder) {
        this.salePriceDetailProductOrder = salePriceDetailProductOrder;
    }

    public float getFullPriceDetailProductOrder() {
        return fullPriceDetailProductOrder;
    }

    public void setFullPriceDetailProductOrder(float fullPriceDetailProductOrder) {
        this.fullPriceDetailProductOrder = fullPriceDetailProductOrder;
    }

    @Override
    public String toString() {
        return "DetailProductOrderMfoodyPOJO{" +
                "idProduct=" + idProduct +
                ", idOrder=" + idOrder +
                ", quantityDetailProductOrder=" + quantityDetailProductOrder +
                ", salePriceDetailProductOrder=" + salePriceDetailProductOrder +
                ", fullPriceDetailProductOrder=" + fullPriceDetailProductOrder +
                '}';
    }
}
