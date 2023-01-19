package com.macmie.mfoodyex.Service.InterfaceService;

import com.macmie.mfoodyex.Model.ProductMfoody;

import java.util.List;

public interface ProductMfoodyInterfaceService {
    public List<ProductMfoody> getListProductMfoodys();

    public ProductMfoody getProductMfoodyByID(int ID_ProductMfoody);

    public ProductMfoody saveProductMfoody(ProductMfoody productMfoody);

    public ProductMfoody updateProductMfoody(ProductMfoody newProductMfoody);

    public void deleteProductMfoodyByID(int ID_ProductMfoody);
}
