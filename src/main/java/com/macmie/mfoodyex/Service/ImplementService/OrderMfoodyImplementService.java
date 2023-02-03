package com.macmie.mfoodyex.Service.ImplementService;

import com.macmie.mfoodyex.Model.CartMfoody;
import com.macmie.mfoodyex.Model.DetailProductCartMfoody;
import com.macmie.mfoodyex.Model.DetailProductOrderMfoody;
import com.macmie.mfoodyex.Model.OrderMfoody;
import com.macmie.mfoodyex.Repository.DetailProductCartMfoodyRepository;
import com.macmie.mfoodyex.Repository.DetailProductOrderMfoodyRepository;
import com.macmie.mfoodyex.Repository.OrderMfoodyRepository;
import com.macmie.mfoodyex.Service.InterfaceService.OrderMfoodyInterfaceService;
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
public class OrderMfoodyImplementService implements OrderMfoodyInterfaceService {
    @Autowired
    private OrderMfoodyRepository orderMfoodyRepository;

    @Autowired
    private DetailProductOrderMfoodyRepository detailProductOrderMfoodyRepository;

    @Autowired
    private StringUtil stringUtil;

    @Autowired
    private TextUtil textUtil;

    @Override
    public List<OrderMfoody> getListOrderMfoodys() {
        log.info("Fetching all OrderMfoodys: ");
        return orderMfoodyRepository.findAll();
    }

    @Override
    public OrderMfoody getOrderMfoodyByID(int idOrderMfoody) {
        log.info("Fetching OrderMfoody with ID: {}", idOrderMfoody);
        return orderMfoodyRepository.findById(idOrderMfoody).orElse(null);
    }

    @Override
    public OrderMfoody saveOrderMfoody(OrderMfoody orderMfoody) {
//        feedbackMail.setIdFeedbackMail(feedbackMail.getIdFeedbackMail());
//        OrderMfoody.setNameUserCard(stringUtil.parseName(OrderMfoody.getNameUserCard()));

        log.info("Saving OrderMfoody with ID: {}", orderMfoody.getIdOrder());
        return orderMfoodyRepository.save(orderMfoody);
    }

    @Override
    public OrderMfoody updateOrderMfoody(OrderMfoody newOrderMfoody) {
//        OrderMfoody orderMfoodyToUpdate = orderMfoodyRepository.getById(newOrderMfoody.getIdOrder());
//        orderMfoodyToUpdate.setDateOrder((newOrderMfoody.getDateOrder()));
//        orderMfoodyToUpdate.setDateReceiptOrder((newOrderMfoody.getDateReceiptOrder()));
//        orderMfoodyToUpdate.setShippingMethodOrder((newOrderMfoody.getShippingMethodOrder()));
//        orderMfoodyToUpdate.setShippingPriceOrder((newOrderMfoody.getShippingPriceOrder()));
//        orderMfoodyToUpdate.setTotalFullPriceOrder((newOrderMfoody.getTotalFullPriceOrder()));
//        orderMfoodyToUpdate.setTotalSalePriceOrder((newOrderMfoody.getTotalSalePriceOrder()));
//        orderMfoodyToUpdate.setPaymentMethodOrder((newOrderMfoody.getPaymentMethodOrder()));
//        orderMfoodyToUpdate.setStatusOrder((newOrderMfoody.getStatusOrder()));
//        orderMfoodyToUpdate.setUser((newOrderMfoody.getUser()));

        log.info("Updating OrderMfoody with ID: {}", newOrderMfoody.getIdOrder());
        return orderMfoodyRepository.save(newOrderMfoody);
    }

    @Override
    public void deleteOrderMfoodyByID(int idOrderMfoody) {
        log.info("Deleting OrderMfoody with ID: {}", idOrderMfoody);

        // Delete DetailProductOrderMfoody and Order
        List<DetailProductOrderMfoody> detailProductOrderMfoodyList = detailProductOrderMfoodyRepository.findAllByIdOrder(idOrderMfoody);
        detailProductOrderMfoodyRepository.deleteAll(detailProductOrderMfoodyList);
        orderMfoodyRepository.deleteById(idOrderMfoody);
    }

    @Override
    public void deleteOrderMfoodyByIdUser(int idUser) {
        log.info("Deleting OrderMfoody with idUser: {}", idUser);

        // Delete DetailProductOrderMfoody and Order
        OrderMfoody orderMfoody = orderMfoodyRepository.findByIdUser(idUser);
        List<DetailProductOrderMfoody> detailProductOrderMfoodyList = detailProductOrderMfoodyRepository.findAllByIdOrder(orderMfoody.getIdOrder());
        detailProductOrderMfoodyRepository.deleteAll(detailProductOrderMfoodyList);
        orderMfoodyRepository.deleteByIdUser(idUser);
    }
}
