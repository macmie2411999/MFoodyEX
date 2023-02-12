package com.macmie.mfoodyex.Controller;

import com.google.gson.Gson;
import com.macmie.mfoodyex.Model.CartMfoody;
import com.macmie.mfoodyex.Model.UserMfoody;
import com.macmie.mfoodyex.POJO.CartMfoodyPOJO;
import com.macmie.mfoodyex.Service.InterfaceService.CartMfoodyInterfaceService;
import com.macmie.mfoodyex.Service.InterfaceService.UserMfoodyInterfaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;

import static com.macmie.mfoodyex.Constant.SecurityConstants.ROLE_ADMIN_SECURITY;
import static com.macmie.mfoodyex.Constant.SecurityConstants.ROLE_USER_SECURITY;
import static com.macmie.mfoodyex.Constant.ViewConstants.*;

/*
 * HttpStatus.NOT_FOUND (404): use when the requested resource cannot be found (null)
 * HttpStatus.NO_CONTENT (204): use when a successful request returns no content (empty)
 * HttpStatus.BAD_REQUEST (400): use when the request is invalid or contains incorrect parameters
 * */

@Slf4j
@Transactional
@RestController // = @ResponseBody + @Controller
@RequestMapping(CART_MFOODY)
public class CartMfoodyController {
    @Autowired
    private CartMfoodyInterfaceService cartMfoodyInterfaceService;

    @Autowired
    private UserMfoodyInterfaceService userMfoodyInterfaceService;

    @Autowired
    private ApplicationCheckAuthorController applicationCheckAuthorController;

    @Secured({ROLE_ADMIN_SECURITY})
    @GetMapping(URL_GET_ALL)
    public ResponseEntity<?> getAllCartMfoodys(Principal principal) {
        log.info("Get List of CartMfoodys by " + principal.getName());
        List<CartMfoody> cartMfoodyList = cartMfoodyInterfaceService.getListCartMfoodys();
        if (cartMfoodyList.isEmpty()) {
            return new ResponseEntity<>("NO_CONTENT List of CartMfoodys", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(cartMfoodyList, HttpStatus.OK);
    }

    @Secured({ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY})
    @GetMapping(URL_GET_BY_ID)
    public ResponseEntity<?> getCartMfoodyByID(@PathVariable("ID") int ID, Principal principal) {
        log.info("Get CartMfoody with ID: {} by {}", ID, principal.getName());
        CartMfoody cartMfoody = cartMfoodyInterfaceService.getCartMfoodyByID(ID);
        if (cartMfoody == null) {
            return new ResponseEntity<>("NOT_FOUND CartMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }

        // Check if the current UserMfoody has role ADMIN or the owner of the CartMfoody
        if(!applicationCheckAuthorController.checkAuthorization(principal, cartMfoody.getUser().getIdUser())){
            return new ResponseEntity<>("FORBIDDEN Authorization failed!", HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(cartMfoody, HttpStatus.OK);
    }

    @Secured({ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY})
    @GetMapping(URL_GET_BY_ID_USER)
    public ResponseEntity<?> getCartMfoodyByIdUser(@PathVariable("ID") int ID, Principal principal) {
        log.info("Get CartMfoody with idUser: {} by {}", ID, principal.getName());
        if (userMfoodyInterfaceService.getUserMfoodyByID(ID) == null) {
            return new ResponseEntity<>("NOT_FOUND UserMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }

        // Check if the current UserMfoody has role ADMIN or the owner of the CartMfoody
        if(!applicationCheckAuthorController.checkAuthorization(principal, ID)){
            return  new ResponseEntity<>("FORBIDDEN Authorization failed!", HttpStatus.FORBIDDEN);
        }

        CartMfoody cartMfoody = cartMfoodyInterfaceService.getCartMfoodyByIdUser(ID);
        if (cartMfoody == null) {
            return new ResponseEntity<>("NOT_FOUND CartMfoody with idUser: " + ID, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cartMfoody, HttpStatus.OK);
    }

    @Secured({ROLE_ADMIN_SECURITY})
    @DeleteMapping(URL_DELETE)
    public ResponseEntity<?> deleteCartMfoodyByID(@PathVariable("ID") int ID, Principal principal) {
        log.info("Delete CartMfoody with ID: {} by {}", ID, principal.getName());
        CartMfoody cartMfoody = cartMfoodyInterfaceService.getCartMfoodyByID(ID);
        if (cartMfoody == null) {
            return new ResponseEntity<>("NOT_FOUND CartMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }

        // Check if the current UserMfoody has role ADMIN or the owner of the CartMfoody
        // if(!applicationCheckAuthorController.checkAuthorization(principal, cartMfoody.getUser().getIdUser())){
        //     return  new ResponseEntity<>("FORBIDDEN Authorization failed!", HttpStatus.FORBIDDEN);
        // }

        try {
            cartMfoodyInterfaceService.deleteCartMfoodyByID(ID);
        } catch (Exception e) {
            log.error("An error occurred while deleting CartMfoody with ID: " + ID);
            log.error("Detail Error: " + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "INTERNAL_SERVER_ERROR Exceptions occur when deleting CartMfoody");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Secured({ROLE_ADMIN_SECURITY})
    @DeleteMapping(URL_DELETE_BY_ID_USER)
    public ResponseEntity<?> deleteCartMfoodyByIdUser(@PathVariable("ID") int ID, Principal principal) {
        log.info("Delete CartMfoody with idUser: {} by {}", ID, principal.getName());
        if (userMfoodyInterfaceService.getUserMfoodyByID(ID) == null) {
            return new ResponseEntity<>("NOT_FOUND UserMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }

        // Check if the current UserMfoody has role ADMIN or the owner of the CartMfoody
        // if(!applicationCheckAuthorController.checkAuthorization(principal, ID)){
        //    return  new ResponseEntity<>("FORBIDDEN Authorization failed!", HttpStatus.FORBIDDEN);
        // }

        try {
            cartMfoodyInterfaceService.deleteCartMfoodyByIdUser(ID);
        } catch (Exception e) {
            log.error("An error occurred while deleting CartMfoody with idUser: " + ID);
            log.error("Detail Error: " + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "INTERNAL_SERVER_ERROR Exceptions occur when deleting CartMfoody");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
     * 1. idCart in Json must be accurate, idUser/quantityAllProductsInCart/totalSalPrice/totalFullPrice are ignored
     * 2. This API is quite redundant coz quantityAllProductsInCart/totalSalPrice/totalFullPrice (all of its attributes)
     *    will be updated later with APIs of DetailProductCartMfoody (coz the oneToMany table is always created first)
     * */
    @Secured({ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY})
    @PutMapping(URL_EDIT)
    public ResponseEntity<?> editCartMfoody(@RequestBody String cartMfoodyPOJOJsonObject, Principal principal) {
        try {
            // Convert JsonObject to CartMfoodyPOJO object, Check input idCart and update CartMfoody
            Gson gson = new Gson();
            CartMfoodyPOJO newCartMfoodyPOJO = gson.fromJson(cartMfoodyPOJOJsonObject, CartMfoodyPOJO.class);
            if (!newCartMfoodyPOJO.checkCartMfoodyValidAttributes()) {
                return new ResponseEntity<>("BAD_REQUEST Invalid Input" + newCartMfoodyPOJO.getIdCart(),
                        HttpStatus.BAD_REQUEST);
            }

            CartMfoody newCartMfoody = cartMfoodyInterfaceService.getCartMfoodyByID(newCartMfoodyPOJO.getIdCart());
            if (newCartMfoody == null) {
                return new ResponseEntity<>("NOT_FOUND CartMfoody with ID: " + newCartMfoodyPOJO.getIdCart(),
                        HttpStatus.NOT_FOUND);
            }

            // Check if the current UserMfoody has role ADMIN or the owner of the CartMfoody
            if(!applicationCheckAuthorController.checkAuthorization(principal, newCartMfoody.getUser().getIdUser())){
                return new ResponseEntity<>("FORBIDDEN Authorization failed!", HttpStatus.FORBIDDEN);
            }

            // newCartMfoody.setQuantityAllProductsInCart(0);
            // newCartMfoody.setTotalSalePriceCart(0);
            // newCartMfoody.setTotalFullPriceCart(0);

            // Save to DB and return
            cartMfoodyInterfaceService.updateCartMfoody(newCartMfoody);
            log.info("CartMfoody with ID: {} by {} is edited", newCartMfoody.getIdCart(), principal.getName());
            return new ResponseEntity<>(newCartMfoody, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred while editing CartMfoody");
            log.error("Detail Error: " + e);
            return new ResponseEntity<>("BAD_REQUEST Something Wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    /*
     * 1. idUser in Json must be accurate, quantityAllProductsInCart/totalSalPrice/totalFullPrice are ignored
     * 2. There is no need to create a new CartMfoody coz every UserMfoody will have one automatically right after
     *    their account is created
     * 3. Every CartMfoody is created with quantityAllProductsInCart/totalSalPrice/totalFullPrice = 0 and will be
     *    updated later with APIs of DetailProductCartMfoody coz the oneToMany table is always created first
     * */
    @Secured({ROLE_ADMIN_SECURITY})
    @PostMapping(URL_ADD)
    public ResponseEntity<?> addNewCartMfoody(@RequestBody String cartMfoodyPOJOJsonObject, Principal principal) {
        try {
            // Convert JsonObject to CardMfoodyPOJO object and set default value
            Gson gson = new Gson();
            CartMfoodyPOJO newCartMfoodyPOJO = gson.fromJson(cartMfoodyPOJOJsonObject, CartMfoodyPOJO.class);

            CartMfoody newCartMfoody = newCartMfoodyPOJO.renderCartMfoody();
            newCartMfoody.setQuantityAllProductsInCart(0);
            newCartMfoody.setTotalFullPriceCart(0);
            newCartMfoody.setTotalSalePriceCart(0);

            // Check input idUser and attach UserMfoody to CartMfoody
            UserMfoody attachUserMfoody = userMfoodyInterfaceService.getUserMfoodyByID(newCartMfoodyPOJO.getIdUser());
            if (attachUserMfoody == null) {
                return new ResponseEntity<>("NOT_FOUND UserMfoody with ID: " + newCartMfoodyPOJO.getIdUser(),
                        HttpStatus.NOT_FOUND);
            }

            // Check duplicate by idUser (UserMfoody and CartMfoody have one-to-one relationship)
            if (cartMfoodyInterfaceService.getCartMfoodyByIdUser(newCartMfoodyPOJO.getIdUser()) != null) {
                return new ResponseEntity<>("CONFLICT - A CartMfoody with the same idUser already exists!",
                        HttpStatus.CONFLICT);
            }

            newCartMfoody.setUser(attachUserMfoody);

            // Save to DB and return (Updated CartMfoody in DB could have ID differs from user's request)
            cartMfoodyInterfaceService.saveCartMfoody(newCartMfoody);
            log.info("A new CartMfoody is created by " + principal.getName());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("An error occurred while adding CartMfoody");
            log.error("Detail Error: " + e);
            return new ResponseEntity<>("BAD_REQUEST Something Wrong!", HttpStatus.BAD_REQUEST);
        }
    }
}
