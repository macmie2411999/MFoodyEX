package com.macmie.mfoodyex.POJO;

import com.macmie.mfoodyex.Model.ProductMfoody;

public class ProductMfoodyPOJO {
    private int idProduct;
    private String nameProduct;
    private String albumProduct;
    private String descriptionProduct;
    private float fullPriceProduct;
    private float salePriceProduct;
    private String weightProduct;
    private int importQuantityProduct;
    private String importDateProduct;
    private int storehouseQuantityProduct;
    private float ratingProduct;
    private String categoryProduct;
    private String brandProduct;

    public ProductMfoodyPOJO() {
    }

    // Redundant
    public ProductMfoodyPOJO(int idProduct, String nameProduct, String albumProduct, String descriptionProduct, float fullPriceProduct, float salePriceProduct, String weightProduct, int importQuantityProduct, String importDateProduct, int storehouseQuantityProduct, float ratingProduct, String categoryProduct, String brandProduct) {
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
    }

    public ProductMfoody renderProductMfoody() {
        ProductMfoody newProductMfoody = new ProductMfoody();
        newProductMfoody.setIdProduct(this.getIdProduct());
        newProductMfoody.setNameProduct(this.getNameProduct());
        newProductMfoody.setAlbumProduct(this.getAlbumProduct());
        newProductMfoody.setDescriptionProduct(this.getDescriptionProduct());
        newProductMfoody.setFullPriceProduct(this.getFullPriceProduct());
        newProductMfoody.setSalePriceProduct(this.getSalePriceProduct());
        newProductMfoody.setWeightProduct(this.getWeightProduct());
        newProductMfoody.setImportQuantityProduct(this.getImportQuantityProduct());
        newProductMfoody.setImportDateProduct(this.getImportDateProduct());
        newProductMfoody.setStorehouseQuantityProduct(this.getStorehouseQuantityProduct());
        newProductMfoody.setRatingProduct(this.getRatingProduct());
        newProductMfoody.setCategoryProduct(this.getCategoryProduct());
        newProductMfoody.setBrandProduct(this.getBrandProduct());
        return newProductMfoody;
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
}
