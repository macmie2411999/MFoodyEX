package com.macmie.mfoodyex.POJO;

import com.macmie.mfoodyex.Model.OrderMfoody;

public class OrderMfoodyPOJO {
    private int idOrder;
    private String dateOrder;
    private String dateReceiptOrder;
    private float shippingPriceOrder;
    private String shippingMethodOrder;
    private int quantityAllProductsInOrder;
    private float totalFullPriceOrder;
    private float totalSalePriceOrder;
    private String paymentMethodOrder;
    private String statusOrder;
    private int idUser;

    public OrderMfoody renderOrderMfoody() {
        OrderMfoody newOrderMfoody = new OrderMfoody();
        newOrderMfoody.setIdOrder(this.getIdOrder());
        newOrderMfoody.setDateOrder(this.getDateOrder());
        newOrderMfoody.setDateReceiptOrder(this.getDateReceiptOrder());
        newOrderMfoody.setShippingPriceOrder(this.getShippingPriceOrder());
        newOrderMfoody.setShippingMethodOrder(this.getShippingMethodOrder());
        newOrderMfoody.setQuantityAllProductsInOrder(this.getQuantityAllProductsInOrder());
        newOrderMfoody.setTotalFullPriceOrder(this.getTotalFullPriceOrder());
        newOrderMfoody.setTotalSalePriceOrder(this.getTotalSalePriceOrder());
        newOrderMfoody.setPaymentMethodOrder(this.getPaymentMethodOrder());
        newOrderMfoody.setStatusOrder(this.getStatusOrder());
        return newOrderMfoody;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public String getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(String dateOrder) {
        this.dateOrder = dateOrder;
    }

    public String getDateReceiptOrder() {
        return dateReceiptOrder;
    }

    public void setDateReceiptOrder(String dateReceiptOrder) {
        this.dateReceiptOrder = dateReceiptOrder;
    }

    public float getShippingPriceOrder() {
        return shippingPriceOrder;
    }

    public void setShippingPriceOrder(float shippingPriceOrder) {
        this.shippingPriceOrder = shippingPriceOrder;
    }

    public String getShippingMethodOrder() {
        return shippingMethodOrder;
    }

    public void setShippingMethodOrder(String shippingMethodOrder) {
        this.shippingMethodOrder = shippingMethodOrder;
    }

    public int getQuantityAllProductsInOrder() {
        return quantityAllProductsInOrder;
    }

    public void setQuantityAllProductsInOrder(int quantityAllProductsInOrder) {
        this.quantityAllProductsInOrder = quantityAllProductsInOrder;
    }

    public float getTotalFullPriceOrder() {
        return totalFullPriceOrder;
    }

    public void setTotalFullPriceOrder(float totalFullPriceOrder) {
        this.totalFullPriceOrder = totalFullPriceOrder;
    }

    public float getTotalSalePriceOrder() {
        return totalSalePriceOrder;
    }

    public void setTotalSalePriceOrder(float totalSalePriceOrder) {
        this.totalSalePriceOrder = totalSalePriceOrder;
    }

    public String getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(String statusOrder) {
        this.statusOrder = statusOrder;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getPaymentMethodOrder() {
        return paymentMethodOrder;
    }

    public void setPaymentMethodOrder(String paymentMethodOrder) {
        this.paymentMethodOrder = paymentMethodOrder;
    }
}
