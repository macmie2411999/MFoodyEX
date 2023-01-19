package com.macmie.mfoodyex.Model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name= "`CART_MFOODY`")
@Data
@RequiredArgsConstructor
public class CartMfoody {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID_CART")
    private int IdCart;

    @NonNull
    @Column(name = "QUANTITY_ALL_PRODUCTS_IN_CART")
    private String QuantityAllProductsInCart;

    @NonNull
    @Column(name = "SALE_PRICE_CART")
    private String SalePriceCart;

    @NonNull
    @Column(name = "FULL_PRICE_CART")
    private String FullPriceCart;

    @NonNull
    @Column(name = "ID_USER")
    private int IdUser;

    // Map to User
//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "ID_USER", referencedColumnName = "ID_USER", insertable = false, updatable = false)
//    private UserMfoody User;

    public CartMfoody() {
    }

    public CartMfoody(int idUser, @NonNull String quantityAllProductsInCart, @NonNull String salePriceCart, @NonNull String fullPriceCart) {
        IdUser = idUser;
        QuantityAllProductsInCart = quantityAllProductsInCart;
        SalePriceCart = salePriceCart;
        FullPriceCart = fullPriceCart;
    }

    public int getIdUser() {
        return IdUser;
    }

    public void setIdUser(int idUser) {
        IdUser = idUser;
    }

    public String getQuantityAllProductsInCart() {
        return QuantityAllProductsInCart;
    }

    public void setQuantityAllProductsInCart(String quantityAllProductsInCart) {
        QuantityAllProductsInCart = quantityAllProductsInCart;
    }

    public String getSalePriceCart() {
        return SalePriceCart;
    }

    public void setSalePriceCart(String salePriceCart) {
        SalePriceCart = salePriceCart;
    }

    public String getFullPriceCart() {
        return FullPriceCart;
    }

    public void setFullPriceCart(String fullPriceCart) {
        FullPriceCart = fullPriceCart;
    }
}
