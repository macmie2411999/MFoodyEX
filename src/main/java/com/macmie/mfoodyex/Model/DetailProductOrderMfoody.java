package com.macmie.mfoodyex.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;

/* Handle Jackson – Bidirectional Relationships (Loop)
    @JsonIgnore: ignore Serialization
    @JsonBackReference: the back part of reference; it'll be omitted from serialization (for ManyToOne - Object)
    @JsonManagedReference: the forward part of reference, the one that gets serialized normally (for OneToMany - list)
    @JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id": show full)
    */

@Entity
@Table(name = "`DETAIL_PRODUCT_ORDER_MFOODY`")
public class DetailProductOrderMfoody implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private DetailProductOrderMfoodyId idDetailProductOrderMfoody;

    @Column(name = "QUANTITY_DETAIL_PRODUCT_ORDER")
    private int quantityDetailProductOrder;

    @Column(name = "SALE_PRICE_DETAIL_PRODUCT_ORDER")
    private float salePriceDetailProductOrder;

    @Column(name = "FULL_PRICE_DETAIL_PRODUCT_ORDER")
    private float fullPriceDetailProductOrder;

    // Map to ORDER_MFOODY
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "ID_ORDER", insertable = false, updatable = false)
    private OrderMfoody order;

    // Map to PRODUCT_MFOODY
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "ID_PRODUCT", insertable = false, updatable = false)
    private ProductMfoody product;

    public DetailProductOrderMfoody() {
    }

    public DetailProductOrderMfoody(DetailProductOrderMfoodyId idDetailProductOrderMfoody, int quantityDetailProductOrder, float salePriceDetailProductOrder, float fullPriceDetailProductOrder, OrderMfoody order, ProductMfoody product) {
        this.idDetailProductOrderMfoody = idDetailProductOrderMfoody;
        this.quantityDetailProductOrder = quantityDetailProductOrder;
        this.salePriceDetailProductOrder = salePriceDetailProductOrder;
        this.fullPriceDetailProductOrder = fullPriceDetailProductOrder;
        this.order = order;
        this.product = product;
    }

    public DetailProductOrderMfoodyId getIdDetailProductOrderMfoody() {
        return idDetailProductOrderMfoody;
    }

    public void setIdDetailProductOrderMfoody(DetailProductOrderMfoodyId idDetailProductOrderMfoody) {
        this.idDetailProductOrderMfoody = idDetailProductOrderMfoody;
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

    public OrderMfoody getOrder() {
        return order;
    }

    public void setOrder(OrderMfoody order) {
        this.order = order;
    }

    public ProductMfoody getProduct() {
        return product;
    }

    public void setProduct(ProductMfoody product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "DetailProductOrderMfoody{" +
                "idDetailProductOrderMfoody=" + idDetailProductOrderMfoody +
                ", quantityDetailProductOrder=" + quantityDetailProductOrder +
                ", salePriceDetailProductOrder=" + salePriceDetailProductOrder +
                ", fullPriceDetailProductOrder=" + fullPriceDetailProductOrder +
                ", order=" + order.getIdOrder() +
                ", product=" + product.getIdProduct() +
                '}';
    }
}

