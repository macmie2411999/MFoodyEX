package com.macmie.mfoodyex.Model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name= "`USER_MFOODY`")
@Data
@RequiredArgsConstructor
public class OrderMfoody {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID_ORDER")
    private int IdOrder;

    @NonNull
    @Column(name = "DATE_ORDER")
    private String DateOrder;

    @NonNull
    @Column(name = "DATE_RECEIPT_ORDER")
    private String DateReceiptOrder;

    @NonNull
    @Column(name = "SHIPPING_PRICE_ORDER")
    private String ShippingPriceOrder;

    @NonNull
    @Column(name = "SHIPPING_METHOD_ORDER")
    private String ShippingMethodOrder;

    @NonNull
    @Column(name = "TOTAL_FULL_PRICE_ORDER")
    private String TotalFullPriceOrder;

    @NonNull
    @Column(name = "TOTAL_SALE_PRICE_ORDER")
    private String TotalSalePriceOrder;

    @NonNull
    @Column(name = "PAYMENT_METHOD_ORDER")
    private String PaymentMethodOrder;

    @NonNull
    @Column(name = "STATUS_ORDER")
    private String StatusOrder;

    @NonNull
    @Column(name = "ID_USER")
    private String IdUser;

    // Map to User
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ID_USER", referencedColumnName = "ID_USER", insertable = false, updatable = false)
    private UserMfoody User;

    public OrderMfoody() {
    }

    public OrderMfoody(int idOrder, @NonNull String dateOrder, @NonNull String dateReceiptOrder, @NonNull String shippingPriceOrder, @NonNull String shippingMethodOrder, @NonNull String totalFullPriceOrder, @NonNull String totalSalePriceOrder, @NonNull String paymentMethodOrder, @NonNull String statusOrder, @NonNull String idUser, UserMfoody user) {
        IdOrder = idOrder;
        DateOrder = dateOrder;
        DateReceiptOrder = dateReceiptOrder;
        ShippingPriceOrder = shippingPriceOrder;
        ShippingMethodOrder = shippingMethodOrder;
        TotalFullPriceOrder = totalFullPriceOrder;
        TotalSalePriceOrder = totalSalePriceOrder;
        PaymentMethodOrder = paymentMethodOrder;
        StatusOrder = statusOrder;
        IdUser = idUser;
        User = user;
    }

    public int getIdOrder() {
        return IdOrder;
    }

    public void setIdOrder(int idOrder) {
        IdOrder = idOrder;
    }

    public String getDateOrder() {
        return DateOrder;
    }

    public void setDateOrder(String dateOrder) {
        DateOrder = dateOrder;
    }

    public String getDateReceiptOrder() {
        return DateReceiptOrder;
    }

    public void setDateReceiptOrder(String dateReceiptOrder) {
        DateReceiptOrder = dateReceiptOrder;
    }

    public String getShippingMethodOrder() {
        return ShippingMethodOrder;
    }

    public void setShippingMethodOrder(String shippingMethodOrder) {
        ShippingMethodOrder = shippingMethodOrder;
    }

    public String getTotalFullPriceOrder() {
        return TotalFullPriceOrder;
    }

    public void setTotalFullPriceOrder(String totalFullPriceOrder) {
        TotalFullPriceOrder = totalFullPriceOrder;
    }

    public String getTotalSalePriceOrder() {
        return TotalSalePriceOrder;
    }

    public void setTotalSalePriceOrder(String totalSalePriceOrder) {
        TotalSalePriceOrder = totalSalePriceOrder;
    }

    public String getPaymentMethodOrder() {
        return PaymentMethodOrder;
    }

    public void setPaymentMethodOrder(String paymentMethodOrder) {
        PaymentMethodOrder = paymentMethodOrder;
    }

    public String getStatusOrder() {
        return StatusOrder;
    }

    public void setStatusOrder(String statusOrder) {
        StatusOrder = statusOrder;
    }

    public String getIdUser() {
        return IdUser;
    }

    public void setIdUser(String idUser) {
        IdUser = idUser;
    }

    public String getShippingPriceOrder() {
        return ShippingPriceOrder;
    }

    public void setShippingPriceOrder(String shippingPriceOrder) {
        ShippingPriceOrder = shippingPriceOrder;
    }

    public UserMfoody getUser() {
        return User;
    }

    public void setUser(UserMfoody user) {
        User = user;
    }
}
