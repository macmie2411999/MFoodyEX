package com.macmie.mfoodyex.Model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name= "`PRODUCT_MFOODY`")
@Data
@RequiredArgsConstructor
public class ProductMfoody {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID_PRODUCT")
    private int IdProduct;

    @NonNull
    @Column(name = "NAME_PRODUCT")
    private String NameProduct;

    @NonNull
    @Column(name = "ALBUM_PRODUCT")
    private String AlbumProduct;

    @NonNull
    @Column(name = "DESCRIPTION_PRODUCT")
    private String DescriptionProduct;

    @NonNull
    @Column(name = "FULL_PRICE_PRODUCT")
    private String FullPriceProduct;

    @NonNull
    @Column(name = "SALE_PRICE_PRODUCT")
    private String SalePriceProduct;

    @NonNull
    @Column(name = "WEIGHT_PRODUCT")
    private String WeightProduct;

    @NonNull
    @Column(name = "IMPORT_QUANTITY_PRODUCT")
    private String ImportQuantityProduct;

    @NonNull
    @Column(name = "IMPORT_DATE_PRODUCT")
    private String ImportDateProduct;

    @NonNull
    @Column(name = "STOREHOUSE_QUANTITY_PRODUCT")
    private String StorehouseQuantityProduct;

    @NonNull
    @Column(name = "RATING_PRODUCT")
    private String RatingProduct;

    @NonNull
    @Column(name = "CATEGORY_PRODUCT")
    private String CategoryProduct;

    @NonNull
    @Column(name = "BRAND_PRODUCT")
    private String BrandProduct;

    public ProductMfoody() {
    }

    public ProductMfoody(int idProduct, @NonNull String nameProduct, @NonNull String albumProduct, @NonNull String descriptionProduct, @NonNull String fullPriceProduct, @NonNull String salePriceProduct, @NonNull String weightProduct, @NonNull String importQuantityProduct, @NonNull String importDateProduct, @NonNull String storehouseQuantityProduct, @NonNull String ratingProduct, @NonNull String categoryProduct, @NonNull String brandProduct) {
        IdProduct = idProduct;
        NameProduct = nameProduct;
        AlbumProduct = albumProduct;
        DescriptionProduct = descriptionProduct;
        FullPriceProduct = fullPriceProduct;
        SalePriceProduct = salePriceProduct;
        WeightProduct = weightProduct;
        ImportQuantityProduct = importQuantityProduct;
        ImportDateProduct = importDateProduct;
        StorehouseQuantityProduct = storehouseQuantityProduct;
        RatingProduct = ratingProduct;
        CategoryProduct = categoryProduct;
        BrandProduct = brandProduct;
    }

    public int getIdProduct() {
        return IdProduct;
    }

    public void setIdProduct(int idProduct) {
        IdProduct = idProduct;
    }

    public String getNameProduct() {
        return NameProduct;
    }

    public void setNameProduct(String nameProduct) {
        NameProduct = nameProduct;
    }

    public String getAlbumProduct() {
        return AlbumProduct;
    }

    public void setAlbumProduct(String albumProduct) {
        AlbumProduct = albumProduct;
    }

    public String getDescriptionProduct() {
        return DescriptionProduct;
    }

    public void setDescriptionProduct(String descriptionProduct) {
        DescriptionProduct = descriptionProduct;
    }

    public String getFullPriceProduct() {
        return FullPriceProduct;
    }

    public void setFullPriceProduct(String fullPriceProduct) {
        FullPriceProduct = fullPriceProduct;
    }

    public String getSalePriceProduct() {
        return SalePriceProduct;
    }

    public void setSalePriceProduct(String salePriceProduct) {
        SalePriceProduct = salePriceProduct;
    }

    public String getWeightProduct() {
        return WeightProduct;
    }

    public void setWeightProduct(String weightProduct) {
        WeightProduct = weightProduct;
    }

    public String getImportQuantityProduct() {
        return ImportQuantityProduct;
    }

    public void setImportQuantityProduct(String importQuantityProduct) {
        ImportQuantityProduct = importQuantityProduct;
    }

    public String getImportDateProduct() {
        return ImportDateProduct;
    }

    public void setImportDateProduct(String importDateProduct) {
        ImportDateProduct = importDateProduct;
    }

    public String getStorehouseQuantityProduct() {
        return StorehouseQuantityProduct;
    }

    public void setStorehouseQuantityProduct(String storehouseQuantityProduct) {
        StorehouseQuantityProduct = storehouseQuantityProduct;
    }

    public String getRatingProduct() {
        return RatingProduct;
    }

    public void setRatingProduct(String ratingProduct) {
        RatingProduct = ratingProduct;
    }

    public String getCategoryProduct() {
        return CategoryProduct;
    }

    public void setCategoryProduct(String categoryProduct) {
        CategoryProduct = categoryProduct;
    }

    public String getBrandProduct() {
        return BrandProduct;
    }

    public void setBrandProduct(String brandProduct) {
        BrandProduct = brandProduct;
    }
}
