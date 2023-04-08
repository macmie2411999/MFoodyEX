package com.macmie.mfoodyex.Service.ImplementService;

import com.macmie.mfoodyex.Model.ProductMfoody;
import com.macmie.mfoodyex.Repository.ProductMfoodyRepository;
import com.macmie.mfoodyex.Service.InterfaceService.ProductMfoodyInterfaceService;
import com.macmie.mfoodyex.Util.StringUtil;
import com.macmie.mfoodyex.Util.TextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j

/* @Transactional: Handle rollback when exceptions occur
 * @Slf4j: Spring Boot Logging
 * */
public class ProductMfoodyImplementService implements ProductMfoodyInterfaceService {
    @Autowired
    private ProductMfoodyRepository productMfoodyRepository;

    @Autowired
    private StringUtil stringUtil;

    @Autowired
    private TextUtil textUtil;

    @Override
    public List<ProductMfoody> getListProductMfoodys() {
        log.info("Fetching all ProductMfoodys ");
        return productMfoodyRepository.findAll();
    }

    @Override
    public ProductMfoody getProductMfoodyByID(int idProductMfoody) {
        log.info("Fetching ProductMfoody with ID: {}", idProductMfoody);
        return productMfoodyRepository.findById(idProductMfoody).orElse(null);
    }

    @Override
    public ProductMfoody getProductMfoodyByNameProduct(String nameProduct) {
        log.info("Fetching ProductMfoody with nameProduct: {}", nameProduct);
        return productMfoodyRepository.findByNameProduct(nameProduct);
    }

    @Override
    public ProductMfoody getProductMfoodyByAlbumProduct(String albumProduct) {
        log.info("Fetching ProductMfoody with albumProduct: {}", albumProduct);
        return productMfoodyRepository.findByAlbumProduct(albumProduct);
    }

    @Override
    public ProductMfoody getProductMfoodyByIdComment(int idComment) {
        log.info("Fetching ProductMfoody with ID: {}", idComment);
        return productMfoodyRepository.findProductMfoodyByIdComment(idComment);
    }

    @Override
    public ProductMfoody saveProductMfoody(ProductMfoody productMfoody) {
//        feedbackMail.setIdFeedbackMail(feedbackMail.getIdFeedbackMail());
//        ProductMfoody.setNameUserCard(stringUtil.parseName(ProductMfoody.getNameUserCard()));

        log.info("Saving ProductMfoody with ID: {}", productMfoody.getIdProduct());
        return productMfoodyRepository.save(productMfoody);
    }

    @Override
    public ProductMfoody updateProductMfoody(ProductMfoody newProductMfoody) {
//        ProductMfoody productMfoodyToUpdate = productMfoodyRepository.getById(newProductMfoody.getIdProduct());
//        productMfoodyToUpdate.setNameProduct((newProductMfoody.getNameProduct()));
//        productMfoodyToUpdate.setAlbumProduct((newProductMfoody.getAlbumProduct()));
//        productMfoodyToUpdate.setDescriptionProduct((newProductMfoody.getDescriptionProduct()));
//        productMfoodyToUpdate.setFullPriceProduct((newProductMfoody.getFullPriceProduct()));
//        productMfoodyToUpdate.setSalePriceProduct((newProductMfoody.getSalePriceProduct()));
//        productMfoodyToUpdate.setWeightProduct((newProductMfoody.getWeightProduct()));
//        productMfoodyToUpdate.setImportDateProduct((newProductMfoody.getImportDateProduct()));
//        productMfoodyToUpdate.setImportQuantityProduct((newProductMfoody.getImportQuantityProduct()));
//        productMfoodyToUpdate.setStorehouseQuantityProduct((newProductMfoody.getStorehouseQuantityProduct()));
//        productMfoodyToUpdate.setRatingProduct((newProductMfoody.getRatingProduct()));
//        productMfoodyToUpdate.setCategoryProduct((newProductMfoody.getCategoryProduct()));
//        productMfoodyToUpdate.setBrandProduct((newProductMfoody.getBrandProduct()));

        log.info("Updating ProductMfoody with ID: {}", newProductMfoody.getIdProduct());
        return productMfoodyRepository.save(newProductMfoody);
    }

    @Override
    public void deleteProductMfoodyByID(int idProductMfoody) {
        log.info("Deleting ProductMfoody with ID: {}", idProductMfoody);
        productMfoodyRepository.deleteById(idProductMfoody);
    }

    @Override
    public Long countTotalNumberOfProductMfoodys() {
        log.info("Count Total Number of ProductMfoodys");
        return productMfoodyRepository.countTotalNumberOfProductMfoodys();
    }
}
