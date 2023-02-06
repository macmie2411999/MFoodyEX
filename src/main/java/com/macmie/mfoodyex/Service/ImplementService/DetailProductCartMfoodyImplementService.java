package com.macmie.mfoodyex.Service.ImplementService;

import com.macmie.mfoodyex.Model.DetailProductCartMfoody;
import com.macmie.mfoodyex.Repository.DetailProductCartMfoodyRepository;
import com.macmie.mfoodyex.Service.InterfaceService.DetailProductCartMfoodyInterfaceService;
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
public class DetailProductCartMfoodyImplementService implements DetailProductCartMfoodyInterfaceService {
    @Autowired
    private DetailProductCartMfoodyRepository detailProductCartMfoodyRepository;

    @Autowired
    private StringUtil stringUtil;

    @Autowired
    private TextUtil textUtil;

    @Override
    public List<DetailProductCartMfoody> getListDetailProductCartMfoodys() {
        log.info("Fetching all DetailProductCartMfoodys: ");
        return detailProductCartMfoodyRepository.findAll();
    }

    @Override
    public List<DetailProductCartMfoody> getListDetailProductCartMfoodysByIdCart(int idCart) {
        log.info("Fetching all DetailProductCartMfoodys with idCart: {}", idCart);
        return detailProductCartMfoodyRepository.findAllByIdCart(idCart);
    }

    @Override
    public List<DetailProductCartMfoody> getListDetailProductCartMfoodysByIdProduct(int idProduct) {
        log.info("Fetching all DetailProductCartMfoodys with idProduct: {}", idProduct);
        return detailProductCartMfoodyRepository.findAllByIdProduct(idProduct);
    }

    @Override
    public DetailProductCartMfoody getDetailProductCartMfoodyByICartAndIdProduct(int idCart, int idProduct) {
        log.info("Fetching DetailProductCartMfoody with idCart {} and idProduct: {}", idCart, idProduct);
        return detailProductCartMfoodyRepository.findByIdCartAndIdProduct(idCart, idProduct);
    }

    @Override
    public DetailProductCartMfoody saveDetailProductCartMfoody(DetailProductCartMfoody DetailProductCartMfoody) {
//        feedbackMail.setIdFeedbackMail(feedbackMail.getIdFeedbackMail());
//        DetailProductCartMfoody.setNameUserCard(stringUtil.parseName(DetailProductCartMfoody.getNameUserCard()));

        log.info("Saving DetailProductCartMfoody with ID: {}", DetailProductCartMfoody.getIdDetailProductCartMFoody());
        return detailProductCartMfoodyRepository.save(DetailProductCartMfoody);
    }

    @Override
    public DetailProductCartMfoody updateDetailProductCartMfoody(DetailProductCartMfoody newDetailProductCartMfoody) {
//        DetailProductCartMfoody DetailProductCartMfoodyToUpdate = detailProductCartMfoodyRepository.getById(newDetailProductCartMfoody.getIdDetailProductCart());
//        DetailProductCartMfoodyToUpdate.setRatingDetailProductCart((newDetailProductCartMfoody.getRatingDetailProductCart()));
//        DetailProductCartMfoodyToUpdate.setContentDetailProductCart((newDetailProductCartMfoody.getContentDetailProductCart()));
//        DetailProductCartMfoodyToUpdate.setUser((newDetailProductCartMfoody.getUser()));
//        DetailProductCartMfoodyToUpdate.setProduct((newDetailProductCartMfoody.getProduct()));

        log.info("Updating DetailProductCartMfoody with ID: {}", newDetailProductCartMfoody.getIdDetailProductCartMFoody());
        return detailProductCartMfoodyRepository.save(newDetailProductCartMfoody);
    }

    @Override
    public void deleteDetailProductCartMfoodyByIdDetailProductCartMfoody(int idCart, int idProduct) {
        log.info("Deleting DetailProductCartMfoody with idCart: {} and idProduct: {}", idCart, idProduct);
        detailProductCartMfoodyRepository.deleteDetailProductCartMfoodyByIdProductAndIdCart(idCart, idProduct);
    }

    @Override
    public void deleteAllDetailProductCartsMfoodyByIdCart(int idCart) {
        log.info("Deleting All DetailProductCartMfoodys with idCart: {}", idCart);
        List<DetailProductCartMfoody> detailProductCartMfoodyList = detailProductCartMfoodyRepository.findAllByIdCart(idCart);
        detailProductCartMfoodyRepository.deleteAll(detailProductCartMfoodyList);
    }

    @Override
    public void deleteAllDetailProductCartsMfoodyByIdProduct(int idProduct) {
        log.info("Deleting All DetailProductCartMfoodys with idProduct: {}", idProduct);
        List<DetailProductCartMfoody> detailProductCartMfoodyList = detailProductCartMfoodyRepository.findAllByIdProduct(idProduct);
        detailProductCartMfoodyRepository.deleteAll(detailProductCartMfoodyList);
    }

    @Override
    public List<DetailProductCartMfoody> findAllDetailProductCartsMfoodyByIdCart(int idCart) {
        log.info("Fetching All DetailProductCartMfoodys with idCart: {}", idCart);
        return detailProductCartMfoodyRepository.findAllByIdCart(idCart);
    }

    @Override
    public List<DetailProductCartMfoody> findAllDetailProductCartsMfoodyByIdProduct(int idProduct) {
        log.info("Fetching All DetailProductCartMfoodys with idProduct: {}", idProduct);
        return detailProductCartMfoodyRepository.findAllByIdProduct(idProduct);
    }
}
