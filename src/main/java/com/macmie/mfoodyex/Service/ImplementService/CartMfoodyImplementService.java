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
    public CartMfoody getCartMfoodyByID(int ID_CartMfoody) {
        log.info("Fetching CartMfoody with ID: {}", ID_CartMfoody);
        return cartMfoodyRepository.findById(ID_CartMfoody).orElse(null);
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
        CartMfoody cartMfoodyToUpdate = cartMfoodyRepository.getById(newCartMfoody.getIdCart());
        cartMfoodyToUpdate.setQuantityAllProductsInCart((newCartMfoody.getQuantityAllProductsInCart()));
        cartMfoodyToUpdate.setSalePriceCart((newCartMfoody.getSalePriceCart()));
        cartMfoodyToUpdate.setFullPriceCart((newCartMfoody.getFullPriceCart()));
        cartMfoodyToUpdate.setIdUser((newCartMfoody.getIdUser()));

        log.info("Updating CartMfoody with ID: {}", cartMfoodyToUpdate.getIdCart());
        return cartMfoodyRepository.save(cartMfoodyToUpdate);
    }

    // ID_CART = ID_USER
    @Override
    public void deleteCartMfoodyByID(int ID_CartMfoody) {
        log.info("Deleting CartMfoody with ID: {}", ID_CartMfoody);
        cartMfoodyRepository.deleteById(ID_CartMfoody);
    }
}
