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
@Table(name = "`FAVORITE_PRODUCT_MFOODY`")
public class FavoriteProductMfoody implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    // indicate that a field or property maps to a composite primary key class that is stored as an embedded object
    private FavoriteProductMfoodyId idFavoriteProductMFoody;

    // Map to FavoriteListProducts_MFOODY
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "ID_FAVORITE_LIST_PRODUCTS", insertable = false, updatable = false)
    private FavoriteListProductsMfoody favoriteListProducts;

    // Map to PRODUCT_MFOODY
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "ID_PRODUCT", insertable = false, updatable = false)
    private ProductMfoody product;

    public FavoriteProductMfoody() {
    }

    public FavoriteProductMfoody(FavoriteProductMfoodyId idFavoriteProductMFoody, FavoriteListProductsMfoody favoriteListProducts, ProductMfoody product) {
        this.idFavoriteProductMFoody = idFavoriteProductMFoody;
        this.favoriteListProducts = favoriteListProducts;
        this.product = product;
    }

    public FavoriteProductMfoodyId getIdFavoriteProductMFoody() {
        return idFavoriteProductMFoody;
    }

    public void setIdFavoriteProductMfoody(FavoriteProductMfoodyId idFavoriteProductMFoody) {
        this.idFavoriteProductMFoody = idFavoriteProductMFoody;
    }

    public FavoriteListProductsMfoody getFavoriteListProducts() {
        return favoriteListProducts;
    }

    public void setFavoriteListProducts(FavoriteListProductsMfoody favoriteListProducts) {
        this.favoriteListProducts = favoriteListProducts;
    }

    public ProductMfoody getProduct() {
        return product;
    }

    public void setProduct(ProductMfoody product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "FavoriteProductMfoody{" +
                "idFavoriteProductMFoody=" + idFavoriteProductMFoody +
                ", favoriteListProducts=" + favoriteListProducts.getIdFavoriteListProducts() +
                ", product=" + product.getIdProduct() +
                '}';
    }
}

