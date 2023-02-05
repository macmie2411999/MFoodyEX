package com.macmie.mfoodyex.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

/* Handle Jackson – Bidirectional Relationships (Loop)
    @JsonIgnore: ignore Serialization
    @JsonBackReference: the back part of reference; it'll be omitted from serialization (for ManyToOne - Object)
    @JsonManagedReference: the forward part of reference, the one that gets serialized normally (for OneToMany - list)
    @JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
    */

@Entity
@Table(name = "`PRODUCT_MFOODY`")
@RequiredArgsConstructor
public class ProductMfoody {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID_PRODUCT")
    private int idProduct;

    @NonNull
    @Column(name = "NAME_PRODUCT")
    private String nameProduct;

    @NonNull
    @Column(name = "ALBUM_PRODUCT")
    private String albumProduct;

    @NonNull
    @Column(name = "DESCRIPTION_PRODUCT")
    private String descriptionProduct;

    @NonNull
    @Column(name = "FULL_PRICE_PRODUCT")
    private float fullPriceProduct;

    @NonNull
    @Column(name = "SALE_PRICE_PRODUCT")
    private float salePriceProduct;

    @NonNull
    @Column(name = "WEIGHT_PRODUCT")
    private String weightProduct;

    @NonNull
    @Column(name = "IMPORT_QUANTITY_PRODUCT")
    private int importQuantityProduct;

    @NonNull
    @Column(name = "IMPORT_DATE_PRODUCT")
    private String importDateProduct;

    @NonNull
    @Column(name = "STOREHOUSE_QUANTITY_PRODUCT")
    private int storehouseQuantityProduct;

    @NonNull
    @Column(name = "RATING_PRODUCT")
    private float ratingProduct;

    @NonNull
    @Column(name = "CATEGORY_PRODUCT")
    private String categoryProduct;

    @NonNull
    @Column(name = "BRAND_PRODUCT")
    private String brandProduct;

    // Refer to DETAIL_PRODUCT_CART_MFOODY
    @JsonManagedReference
    @OneToMany(mappedBy = "product")
    private List<DetailProductCartMfoody> listDetailProductCarts;

    // Refer to DETAIL_PRODUCT_ORDER_MFOODY
    @JsonManagedReference
    @OneToMany(mappedBy = "product")
    private List<DetailProductOrderMfoody> listDetailProductOrders;

    // Refer to COMMENT_MFOODY
    @JsonManagedReference
    @OneToMany(mappedBy = "product")
    private List<CommentMfoody> listComments;

    public ProductMfoody() {
    }

    public ProductMfoody(int idProduct, @NonNull String nameProduct, @NonNull String albumProduct, @NonNull String descriptionProduct, @NonNull float fullPriceProduct, @NonNull float salePriceProduct, @NonNull String weightProduct, @NonNull int importQuantityProduct, @NonNull String importDateProduct, @NonNull int storehouseQuantityProduct, @NonNull float ratingProduct, @NonNull String categoryProduct, @NonNull String brandProduct, List<CommentMfoody> listComments, List<DetailProductOrderMfoody> listDetailProductOrders, List<DetailProductCartMfoody> listDetailProductCarts) {
        this.idProduct = idProduct;
        this.nameProduct = nameProduct;
        this.albumProduct = albumProduct;
        this.descriptionProduct = descriptionProduct;
        this.fullPriceProduct = fullPriceProduct;
        this.salePriceProduct = salePriceProduct;
        this.weightProduct = weightProduct;
        this.importQuantityProduct = importQuantityProduct;
        this.importDateProduct = importDateProduct;
        this.storehouseQuantityProduct = storehouseQuantityProduct;
        this.ratingProduct = ratingProduct;
        this.categoryProduct = categoryProduct;
        this.brandProduct = brandProduct;
        this.listDetailProductOrders = listDetailProductOrders;
        this.listDetailProductCarts = listDetailProductCarts;
        this.listComments = listComments;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getAlbumProduct() {
        return albumProduct;
    }

    public void setAlbumProduct(String albumProduct) {
        this.albumProduct = albumProduct;
    }

    public String getDescriptionProduct() {
        return descriptionProduct;
    }

    public void setDescriptionProduct(String descriptionProduct) {
        this.descriptionProduct = descriptionProduct;
    }

    public float getFullPriceProduct() {
        return fullPriceProduct;
    }

    public void setFullPriceProduct(float fullPriceProduct) {
        this.fullPriceProduct = fullPriceProduct;
    }

    public float getSalePriceProduct() {
        return salePriceProduct;
    }

    public void setSalePriceProduct(float salePriceProduct) {
        this.salePriceProduct = salePriceProduct;
    }

    public String getWeightProduct() {
        return weightProduct;
    }

    public void setWeightProduct(String weightProduct) {
        this.weightProduct = weightProduct;
    }

    public int getImportQuantityProduct() {
        return importQuantityProduct;
    }

    public void setImportQuantityProduct(int importQuantityProduct) {
        this.importQuantityProduct = importQuantityProduct;
    }

    public String getImportDateProduct() {
        return importDateProduct;
    }

    public void setImportDateProduct(String importDateProduct) {
        this.importDateProduct = importDateProduct;
    }

    public int getStorehouseQuantityProduct() {
        return storehouseQuantityProduct;
    }

    public void setStorehouseQuantityProduct(int storehouseQuantityProduct) {
        this.storehouseQuantityProduct = storehouseQuantityProduct;
    }

    public float getRatingProduct() {
        return ratingProduct;
    }

    public void setRatingProduct(float ratingProduct) {
        this.ratingProduct = ratingProduct;
    }

    public String getCategoryProduct() {
        return categoryProduct;
    }

    public void setCategoryProduct(String categoryProduct) {
        this.categoryProduct = categoryProduct;
    }

    public String getBrandProduct() {
        return brandProduct;
    }

    public void setBrandProduct(String brandProduct) {
        this.brandProduct = brandProduct;
    }

    public List<CommentMfoody> getListComments() {
        return listComments;
    }

    public void setListComments(List<CommentMfoody> listComments) {
        this.listComments = listComments;
    }

    public List<DetailProductCartMfoody> getListDetailProductCarts() {
        return listDetailProductCarts;
    }

    public void setListDetailProductCarts(List<DetailProductCartMfoody> listDetailProductCarts) {
        this.listDetailProductCarts = listDetailProductCarts;
    }

    public List<DetailProductOrderMfoody> getListDetailProductOrders() {
        return listDetailProductOrders;
    }

    public void setListDetailProductOrders(List<DetailProductOrderMfoody> listDetailProductOrders) {
        this.listDetailProductOrders = listDetailProductOrders;
    }

    @Override
    public String toString() {
        return "ProductMfoody{" +
                "idProduct=" + idProduct +
                ", nameProduct='" + nameProduct + '\'' +
                ", albumProduct='" + albumProduct + '\'' +
                ", descriptionProduct='" + descriptionProduct + '\'' +
                ", fullPriceProduct=" + fullPriceProduct +
                ", salePriceProduct=" + salePriceProduct +
                ", weightProduct='" + weightProduct + '\'' +
                ", importQuantityProduct=" + importQuantityProduct +
                ", importDateProduct='" + importDateProduct + '\'' +
                ", storehouseQuantityProduct=" + storehouseQuantityProduct +
                ", ratingProduct=" + ratingProduct +
                ", categoryProduct='" + categoryProduct + '\'' +
                ", brandProduct='" + brandProduct + '\'' +
                '}';
    }
}
