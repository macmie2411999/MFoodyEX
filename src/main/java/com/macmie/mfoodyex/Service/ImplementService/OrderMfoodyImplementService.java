package com.macmie.mfoodyex.Service.ImplementService;

import com.macmie.mfoodyex.Model.OrderMfoody;
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
    private StringUtil stringUtil;

    @Autowired
    private TextUtil textUtil;

    @Override
    public List<OrderMfoody> getListOrderMfoodys() {
        log.info("Fetching all OrderMfoodys: ");
        return orderMfoodyRepository.findAll();
    }

    @Override
    public OrderMfoody getOrderMfoodyByID(int ID_OrderMfoody) {
        log.info("Fetching OrderMfoody with ID: {}", ID_OrderMfoody);
        return orderMfoodyRepository.findById(ID_OrderMfoody).orElse(null);
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
        OrderMfoody orderMfoodyToUpdate = orderMfoodyRepository.getById(newOrderMfoody.getIdOrder());
        orderMfoodyToUpdate.setDateOrder((newOrderMfoody.getDateOrder()));
        orderMfoodyToUpdate.setDateReceiptOrder((newOrderMfoody.getDateReceiptOrder()));
        orderMfoodyToUpdate.setShippingMethodOrder((newOrderMfoody.getShippingMethodOrder()));
        orderMfoodyToUpdate.setShippingPriceOrder((newOrderMfoody.getShippingPriceOrder()));
        orderMfoodyToUpdate.setTotalFullPriceOrder((newOrderMfoody.getTotalFullPriceOrder()));
        orderMfoodyToUpdate.setTotalSalePriceOrder((newOrderMfoody.getTotalSalePriceOrder()));
        orderMfoodyToUpdate.setPaymentMethodOrder((newOrderMfoody.getPaymentMethodOrder()));
        orderMfoodyToUpdate.setStatusOrder((newOrderMfoody.getStatusOrder()));
        orderMfoodyToUpdate.setIdUser((newOrderMfoody.getIdUser()));

        log.info("Updating OrderMfoody with ID: {}", orderMfoodyToUpdate.getIdOrder());
        return orderMfoodyRepository.save(orderMfoodyToUpdate);
    }

    @Override
    public void deleteOrderMfoodyByID(int ID_OrderMfoody) {
        log.info("Deleting OrderMfoody with ID: {}", ID_OrderMfoody);
        orderMfoodyRepository.deleteById(ID_OrderMfoody);
    }
}
