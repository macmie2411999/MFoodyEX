package com.macmie.mfoodyex.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

/* Handle Jackson – Bidirectional Relationships (Loop)
    @JsonIgnore: ignore Serialization
    @JsonBackReference: the back part of reference; it'll be omitted from serialization (for ManyToOne - Object)
    @JsonManagedReference: the forward part of reference, the one that gets serialized normally (for OneToMany - list)
    @JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
    */

@Entity
@Table(name= "`ORDER_MFOODY`")
@RequiredArgsConstructor
public class OrderMfoody {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID_ORDER")
    private int idOrder;

    @NonNull
    @Column(name = "DATE_ORDER")
    private String dateOrder;

    @NonNull
    @Column(name = "DATE_RECEIPT_ORDER")
    private String dateReceiptOrder;

    @NonNull
    @Column(name = "SHIPPING_PRICE_ORDER")
    private float shippingPriceOrder;

    @NonNull
    @Column(name = "SHIPPING_METHOD_ORDER")
    private String shippingMethodOrder;

    @NonNull
    @Column(name = "QUANTITY_ALL_PRODUCTS_IN_ORDER")
    private int quantityAllProductsInOrder;

    @NonNull
    @Column(name = "TOTAL_FULL_PRICE_ORDER")
    private float totalFullPriceOrder;

    @NonNull
    @Column(name = "TOTAL_SALE_PRICE_ORDER")
    private float totalSalePriceOrder;

    @NonNull
    @Column(name = "PAYMENT_METHOD_ORDER")
    private String paymentMethodOrder;

    @NonNull
    @Column(name = "STATUS_ORDER")
    private String statusOrder;

    // Map to User
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "ID_USER")
    private UserMfoody user;

    // Refer to DETAIL_PRODUCT_ORDER_MFOODY
    @JsonManagedReference
    @OneToMany(mappedBy = "order")
    private List<DetailProductOrderMfoody> listDetailProductOrders;

    public OrderMfoody() {
    }

    public OrderMfoody(int idOrder, @NonNull String dateOrder, @NonNull String dateReceiptOrder, @NonNull float shippingPriceOrder, @NonNull String shippingMethodOrder, @NonNull int quantityAllProductsInOrder , @NonNull float totalFullPriceOrder, @NonNull float totalSalePriceOrder, @NonNull String paymentMethodOrder, @NonNull String statusOrder, UserMfoody user, List<DetailProductOrderMfoody> listDetailProductOrders) {
        this.idOrder = idOrder;
        this.dateOrder = dateOrder;
        this.dateReceiptOrder = dateReceiptOrder;
        this.shippingPriceOrder = shippingPriceOrder;
        this.shippingMethodOrder = shippingMethodOrder;
        this.quantityAllProductsInOrder = quantityAllProductsInOrder;
        this.totalFullPriceOrder = totalFullPriceOrder;
        this.totalSalePriceOrder = totalSalePriceOrder;
        this.paymentMethodOrder = paymentMethodOrder;
        this.statusOrder = statusOrder;
        this.user = user;
        this.listDetailProductOrders = listDetailProductOrders;
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

    public String getPaymentMethodOrder() {
        return paymentMethodOrder;
    }

    public void setPaymentMethodOrder(String paymentMethodOrder) {
        this.paymentMethodOrder = paymentMethodOrder;
    }

    public String getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(String statusOrder) {
        this.statusOrder = statusOrder;
    }

    public UserMfoody getUser() {
        return user;
    }

    public void setUser(UserMfoody user) {
        this.user = user;
    }

    public List<DetailProductOrderMfoody> getListDetailProductOrders() {
        return listDetailProductOrders;
    }

    public void setListDetailProductOrders(List<DetailProductOrderMfoody> listDetailProductOrders) {
        this.listDetailProductOrders = listDetailProductOrders;
    }
}
