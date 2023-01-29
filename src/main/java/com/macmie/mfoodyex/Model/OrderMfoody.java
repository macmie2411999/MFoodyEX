package com.macmie.mfoodyex.Model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

/* Handle Jackson – Bidirectional Relationships (Loop)
    @JsonIgnore: ignore Serialization
    @JsonBackReference: the back part of reference; it'll be omitted from serialization (for ManyToOne - Object)
    @JsonManagedReference: the forward part of reference, the one that gets serialized normally (for OneToMany - list)
    @JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
    */

@Entity
@Table(name= "`USER_MFOODY`")
@Data
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
    private String shippingPriceOrder;

    @NonNull
    @Column(name = "SHIPPING_METHOD_ORDER")
    private String shippingMethodOrder;

    @NonNull
    @Column(name = "TOTAL_FULL_PRICE_ORDER")
    private String totalFullPriceOrder;

    @NonNull
    @Column(name = "TOTAL_SALE_PRICE_ORDER")
    private String totalSalePriceOrder;

    @NonNull
    @Column(name = "PAYMENT_METHOD_ORDER")
    private String paymentMethodOrder;

    @NonNull
    @Column(name = "STATUS_ORDER")
    private String statusOrder;

    @NonNull
    @Column(name = "ID_USER")
    private String idUser;

    // Map to User
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ID_USER", referencedColumnName = "ID_USER", insertable = false, updatable = false)
    private UserMfoody User;

    public OrderMfoody() {
    }

    public OrderMfoody(int idOrder, @NonNull String dateOrder, @NonNull String dateReceiptOrder, @NonNull String shippingPriceOrder, @NonNull String shippingMethodOrder, @NonNull String totalFullPriceOrder, @NonNull String totalSalePriceOrder, @NonNull String paymentMethodOrder, @NonNull String statusOrder, @NonNull String idUser, UserMfoody user) {
        this.idOrder = idOrder;
        this.dateOrder = dateOrder;
        this.dateReceiptOrder = dateReceiptOrder;
        this.shippingPriceOrder = shippingPriceOrder;
        this.shippingMethodOrder = shippingMethodOrder;
        this.totalFullPriceOrder = totalFullPriceOrder;
        this.totalSalePriceOrder = totalSalePriceOrder;
        this.paymentMethodOrder = paymentMethodOrder;
        this.statusOrder = statusOrder;
        this.idUser = idUser;
        User = user;
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

    public String getShippingPriceOrder() {
        return shippingPriceOrder;
    }

    public void setShippingPriceOrder(String shippingPriceOrder) {
        this.shippingPriceOrder = shippingPriceOrder;
    }

    public String getShippingMethodOrder() {
        return shippingMethodOrder;
    }

    public void setShippingMethodOrder(String shippingMethodOrder) {
        this.shippingMethodOrder = shippingMethodOrder;
    }

    public String getTotalFullPriceOrder() {
        return totalFullPriceOrder;
    }

    public void setTotalFullPriceOrder(String totalFullPriceOrder) {
        this.totalFullPriceOrder = totalFullPriceOrder;
    }

    public String getTotalSalePriceOrder() {
        return totalSalePriceOrder;
    }

    public void setTotalSalePriceOrder(String totalSalePriceOrder) {
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

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public UserMfoody getUser() {
        return User;
    }

    public void setUser(UserMfoody user) {
        User = user;
    }
}
