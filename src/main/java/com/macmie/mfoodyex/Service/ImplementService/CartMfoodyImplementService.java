package com.macmie.mfoodyex.Service.ImplementService;

import com.macmie.mfoodyex.Model.CartMfoody;
import com.macmie.mfoodyex.Repository.CartMfoodyRepository;
import com.macmie.mfoodyex.Service.InterfaceService.CartMfoodyInterfaceService;
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
public class CartMfoodyImplementService implements CartMfoodyInterfaceService {
    @Autowired
    private CartMfoodyRepository cartMfoodyRepository;

    @Autowired
    private StringUtil stringUtil;

    @Autowired
    private TextUtil textUtil;

    @Override
    public List<CartMfoody> getListCartMfoodys() {
        log.info("Fetching all CartMfoodys: ");
        return cartMfoodyRepository.findAll();
    }

    @Override
    public CartMfoody getCartMfoodyByID(int idCartMfoody) {
        log.info("Fetching CartMfoody with ID: {}", idCartMfoody);
        return cartMfoodyRepository.findById(idCartMfoody).orElse(null);
    }

    @Override
    public CartMfoody saveCartMfoody(CartMfoody cartMfoody) {
//        feedbackMail.setIdFeedbackMail(feedbackMail.getIdFeedbackMail());
//        CartMfoody.setNameUserCard(stringUtil.parseName(CartMfoody.getNameUserCard()));

        log.info("Saving CartMfoody with ID: {}", cartMfoody.getIdCart());
        return cartMfoodyRepository.save(cartMfoody);
    }

    @Override
    public CartMfoody updateCartMfoody(CartMfoody newCartMfoody) {
//        CartMfoody cartMfoodyToUpdate = cartMfoodyRepository.getById(newCartMfoody.getIdCart());
//        cartMfoodyToUpdate.setQuantityAllProductsInCart((newCartMfoody.getQuantityAllProductsInCart()));
//        cartMfoodyToUpdate.setSalePriceCart((newCartMfoody.getSalePriceCart()));
//        cartMfoodyToUpdate.setFullPriceCart((newCartMfoody.getFullPriceCart()));
//        cartMfoodyToUpdate.setUser((newCartMfoody.getUser()));

        log.info("Updating CartMfoody with ID: {}", newCartMfoody.getIdCart());
        return cartMfoodyRepository.save(newCartMfoody);
    }

    @Override
    public void deleteCartMfoodyByID(int idCartMfoody) {
        log.info("Deleting CartMfoody with ID: {}", idCartMfoody);
        cartMfoodyRepository.deleteById(idCartMfoody);
    }
}
