package com.macmie.mfoodyex.Service.ImplementService;

import com.macmie.mfoodyex.Model.FavoriteProductMfoody;
import com.macmie.mfoodyex.Repository.FavoriteProductMfoodyRepository;
import com.macmie.mfoodyex.Service.InterfaceService.FavoriteProductMfoodyInterfaceService;
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
public class FavoriteProductMfoodyImplementService implements FavoriteProductMfoodyInterfaceService {
    @Autowired
    private FavoriteProductMfoodyRepository favoriteProductMfoodyRepository;

    @Autowired
    private StringUtil stringUtil;

    @Autowired
    private TextUtil textUtil;

    @Override
    public List<FavoriteProductMfoody> getListFavoriteProductMfoodys() {
        log.info("Fetching all FavoriteProductMfoodys ");
        return favoriteProductMfoodyRepository.findAll();
    }

    @Override
    public List<FavoriteProductMfoody> getListFavoriteProductMfoodysByIdFavoriteListProducts(int idFavoriteListProducts) {
        log.info("Fetching all FavoriteProductMfoodys with idFavoriteListProducts: {}", idFavoriteListProducts);
        return favoriteProductMfoodyRepository.findAllByIdFavoriteListProducts(idFavoriteListProducts);
    }

    @Override
    public List<FavoriteProductMfoody> getListFavoriteProductMfoodysByIdProduct(int idProduct) {
        log.info("Fetching all FavoriteProductMfoodys with idProduct: {}", idProduct);
        return favoriteProductMfoodyRepository.findAllByIdProduct(idProduct);
    }

    @Override
    public FavoriteProductMfoody getFavoriteProductMfoodyByIFavoriteListProductsAndIdProduct(int idFavoriteListProducts, int idProduct) {
        log.info("Fetching FavoriteProductMfoody with idFavoriteListProducts {} and idProduct: {}", idFavoriteListProducts, idProduct);
        return favoriteProductMfoodyRepository.findByIdFavoriteListProductsAndIdProduct(idFavoriteListProducts, idProduct);
    }

    @Override
    public FavoriteProductMfoody saveFavoriteProductMfoody(FavoriteProductMfoody favoriteProductMfoody) {
//        feedbackMail.setIdFeedbackMail(feedbackMail.getIdFeedbackMail());
//        FavoriteProductMfoody.setNameUserCard(stringUtil.parseName(FavoriteProductMfoody.getNameUserCard()));

        log.info("Saving FavoriteProductMfoody with ID: {}", favoriteProductMfoody.getIdFavoriteProductMFoody());
        return favoriteProductMfoodyRepository.save(favoriteProductMfoody);
    }

    @Override
    public FavoriteProductMfoody updateFavoriteProductMfoody(FavoriteProductMfoody newFavoriteProductMfoody) {
//        FavoriteProductMfoody FavoriteProductMfoodyToUpdate = FavoriteProductMfoodyRepository.getById(newFavoriteProductMfoody.getIdFavoriteProduct());
//        FavoriteProductMfoodyToUpdate.setRatingFavoriteProduct((newFavoriteProductMfoody.getRatingFavoriteProduct()));
//        FavoriteProductMfoodyToUpdate.setContentFavoriteProduct((newFavoriteProductMfoody.getContentFavoriteProduct()));
//        FavoriteProductMfoodyToUpdate.setUser((newFavoriteProductMfoody.getUser()));
//        FavoriteProductMfoodyToUpdate.setProduct((newFavoriteProductMfoody.getProduct()));

        log.info("Updating FavoriteProductMfoody with ID: {}", newFavoriteProductMfoody.getIdFavoriteProductMFoody());
        return favoriteProductMfoodyRepository.save(newFavoriteProductMfoody);
    }

    @Override
    public void deleteFavoriteProductMfoodyByIdFavoriteProductMfoody(int idFavoriteListProducts, int idProduct) {
        log.info("Deleting FavoriteProductMfoody with idFavoriteListProducts: {} and idProduct: {}", idFavoriteListProducts, idProduct);
        favoriteProductMfoodyRepository.deleteFavoriteProductMfoodyByIdProductAndIdFavoriteListProducts(idFavoriteListProducts, idProduct);
    }

    @Override
    public void deleteAllFavoriteProductsMfoodyByIdFavoriteListProducts(int idFavoriteListProducts) {
        log.info("Deleting All FavoriteProductMfoodys with idFavoriteListProducts: {}", idFavoriteListProducts);
        List<FavoriteProductMfoody> favoriteProductMfoodyList = favoriteProductMfoodyRepository.findAllByIdFavoriteListProducts(idFavoriteListProducts);
        favoriteProductMfoodyRepository.deleteAll(favoriteProductMfoodyList);
    }

    @Override
    public void deleteAllFavoriteProductsMfoodyByIdProduct(int idProduct) {
        log.info("Deleting All FavoriteProductMfoodys with idProduct: {}", idProduct);
        List<FavoriteProductMfoody> favoriteProductMfoodyList = favoriteProductMfoodyRepository.findAllByIdProduct(idProduct);
        favoriteProductMfoodyRepository.deleteAll(favoriteProductMfoodyList);
    }

    @Override
    public List<FavoriteProductMfoody> findAllFavoriteProductsMfoodyByIdFavoriteListProducts(int idFavoriteListProducts) {
        log.info("Fetching All FavoriteProductMfoodys with idFavoriteListProducts: {}", idFavoriteListProducts);
        return favoriteProductMfoodyRepository.findAllByIdFavoriteListProducts(idFavoriteListProducts);
    }

    @Override
    public List<FavoriteProductMfoody> findAllFavoriteProductsMfoodyByIdProduct(int idProduct) {
        log.info("Fetching All FavoriteProductMfoodys with idProduct: {}", idProduct);
        return favoriteProductMfoodyRepository.findAllByIdProduct(idProduct);
    }
}
