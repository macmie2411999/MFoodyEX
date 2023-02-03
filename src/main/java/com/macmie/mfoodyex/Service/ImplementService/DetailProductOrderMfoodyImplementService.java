package com.macmie.mfoodyex.Service.ImplementService;

import com.macmie.mfoodyex.Model.DetailProductOrderMfoody;
import com.macmie.mfoodyex.Repository.DetailProductOrderMfoodyRepository;
import com.macmie.mfoodyex.Service.InterfaceService.DetailProductOrderMfoodyInterfaceService;
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
public class DetailProductOrderMfoodyImplementService implements DetailProductOrderMfoodyInterfaceService {
    @Autowired
    private DetailProductOrderMfoodyRepository detailProductOrderMfoodyRepository;

    @Autowired
    private StringUtil stringUtil;

    @Autowired
    private TextUtil textUtil;

    @Override
    public List<DetailProductOrderMfoody> getListDetailProductOrderMfoodys() {
        log.info("Fetching all DetailProductOrderMfoodys: ");
        return detailProductOrderMfoodyRepository.findAll();
    }

    @Override
    public DetailProductOrderMfoody getDetailProductOrderMfoodyByID(int idDetailProductOrderMfoody) {
        log.info("Fetching DetailProductOrderMfoody with ID: {}", idDetailProductOrderMfoody);
        return detailProductOrderMfoodyRepository.findById(idDetailProductOrderMfoody).orElse(null);
    }

    @Override
    public DetailProductOrderMfoody saveDetailProductOrderMfoody(DetailProductOrderMfoody DetailProductOrderMfoody) {
//        feedbackMail.setIdFeedbackMail(feedbackMail.getIdFeedbackMail());
//        DetailProductOrderMfoody.setNameUserCard(stringUtil.parseName(DetailProductOrderMfoody.getNameUserCard()));

        log.info("Saving DetailProductOrderMfoody with ID: {}", DetailProductOrderMfoody.getIdDetailProductOrderMfoody());
        return detailProductOrderMfoodyRepository.save(DetailProductOrderMfoody);
    }

    @Override
    public DetailProductOrderMfoody updateDetailProductOrderMfoody(DetailProductOrderMfoody newDetailProductOrderMfoody) {
//        DetailProductOrderMfoody DetailProductOrderMfoodyToUpdate = detailProductOrderMfoodyRepository.getById(newDetailProductOrderMfoody.getIdDetailProductOrder());
//        DetailProductOrderMfoodyToUpdate.setRatingDetailProductOrder((newDetailProductOrderMfoody.getRatingDetailProductOrder()));
//        DetailProductOrderMfoodyToUpdate.setContentDetailProductOrder((newDetailProductOrderMfoody.getContentDetailProductOrder()));
//        DetailProductOrderMfoodyToUpdate.setUser((newDetailProductOrderMfoody.getUser()));
//        DetailProductOrderMfoodyToUpdate.setProduct((newDetailProductOrderMfoody.getProduct()));

        log.info("Updating DetailProductOrderMfoody with ID: {}", newDetailProductOrderMfoody.getIdDetailProductOrderMfoody());
        return detailProductOrderMfoodyRepository.save(newDetailProductOrderMfoody);
    }

    @Override
    public void deleteDetailProductOrderMfoodyByID(int idDetailProductOrderMfoody) {
        log.info("Deleting DetailProductOrderMfoody with ID: {}", idDetailProductOrderMfoody);
        detailProductOrderMfoodyRepository.deleteById(idDetailProductOrderMfoody);
    }

    @Override
    public void deleteAllDetailProductOrdersMfoodyByIdOrder(int idOrder) {
        log.info("Deleting All DetailProductOrderMfoodys with idOrder: {}", idOrder);
        List<DetailProductOrderMfoody> detailProductOrderMfoodyList = detailProductOrderMfoodyRepository.findAllByIdOrder(idOrder);
        detailProductOrderMfoodyRepository.deleteAll(detailProductOrderMfoodyList);
    }

    @Override
    public void deleteAllDetailProductOrdersMfoodyByIdProduct(int idProduct) {
        log.info("Deleting All DetailProductOrderMfoodys with idProduct: {}", idProduct);
        List<DetailProductOrderMfoody> detailProductOrderMfoodyList = detailProductOrderMfoodyRepository.findAllByIdProduct(idProduct);
        detailProductOrderMfoodyRepository.deleteAll(detailProductOrderMfoodyList);
    }
}
