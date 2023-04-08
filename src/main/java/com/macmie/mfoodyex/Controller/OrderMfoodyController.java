package com.macmie.mfoodyex.Controller;

import com.google.gson.Gson;
import com.macmie.mfoodyex.Model.OrderMfoody;
import com.macmie.mfoodyex.Model.UserMfoody;
import com.macmie.mfoodyex.POJO.OrderMfoodyPOJO;
import com.macmie.mfoodyex.Service.InterfaceService.DetailProductOrderMfoodyInterfaceService;
import com.macmie.mfoodyex.Service.InterfaceService.OrderMfoodyInterfaceService;
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
@RequestMapping(ORDER_MFOODY)
public class OrderMfoodyController {
    @Autowired
    private OrderMfoodyInterfaceService orderMfoodyInterfaceService;

    @Autowired
    private UserMfoodyInterfaceService userMfoodyInterfaceService;

    @Autowired
    private DetailProductOrderMfoodyInterfaceService detailProductOrderMfoodyInterfaceService;

    @Autowired
    private ApplicationCheckAuthorController applicationCheckAuthorController;

    @Secured({ROLE_ADMIN_SECURITY})
    @GetMapping(URL_COUNT_TOTAL)
    public ResponseEntity<?> countTotalNumberOfOrderMfoodys(Principal principal) {
        log.info("Count Total Number of OrderMfoodys by " + principal.getName());

        try {
            Long totalNumberOfOrderMfoodys = orderMfoodyInterfaceService.countTotalNumberOfOrderMfoodys();
            return new ResponseEntity<>(totalNumberOfOrderMfoodys, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred while counting number of OrderMfoodys");
            log.error("Detail Error: " + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "INTERNAL_SERVER_ERROR Exceptions occur when counting OrderMfoodys");
        }
    }

    @Secured({ROLE_ADMIN_SECURITY})
    @GetMapping(URL_GET_ALL)
    public ResponseEntity<?> getAllOrderMfoodys(Principal principal) {
        log.info("Get List of OrderMfoodys by " + principal.getName());
        List<OrderMfoody> orderMfoodyList = orderMfoodyInterfaceService.getListOrderMfoodys();
        if (orderMfoodyList.isEmpty()) {
            return new ResponseEntity<>("NO_CONTENT List of OrderMfoodys", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(orderMfoodyList, HttpStatus.OK);
    }

    @Secured({ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY})
    @GetMapping(URL_GET_BY_ID)
    public ResponseEntity<?> getOrderMfoodyByID(@PathVariable("ID") int ID, Principal principal) {
        log.info("Get OrderMfoody with ID: {} by {}", ID, principal.getName());
        OrderMfoody orderMfoody = orderMfoodyInterfaceService.getOrderMfoodyByID(ID);
        if (orderMfoody == null) {
            return new ResponseEntity<>("NOT_FOUND OrderMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }

        // Check if the current UserMfoody has role ADMIN or the owner of the OrderMfoody
        if(!applicationCheckAuthorController.checkAuthorization(principal, orderMfoody.getUser().getIdUser())){
            return  new ResponseEntity<>("FORBIDDEN Authorization failed!", HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(orderMfoody, HttpStatus.OK);
    }

    @Secured({ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY})
    @GetMapping(URL_GET_BY_ID_USER)
    public ResponseEntity<?> getAllOrderMfoodysByIdUser(@PathVariable("ID") int ID, Principal principal) {
        log.info("Get List of OrderMfoodys with idUser: {} by {}", ID, principal.getName());
        if (userMfoodyInterfaceService.getUserMfoodyByID(ID) == null) {
            return new ResponseEntity<>("NOT_FOUND UserMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }

        // Check if the current UserMfoody has role ADMIN or the owner of the OrderMfoody
        if(!applicationCheckAuthorController.checkAuthorization(principal, ID)){
            return  new ResponseEntity<>("FORBIDDEN Authorization failed!", HttpStatus.FORBIDDEN);
        }

        List<OrderMfoody> orderMfoodyList = orderMfoodyInterfaceService.getListOrderMfoodysByIdUser(ID);
        if (orderMfoodyList.isEmpty()) {
            return new ResponseEntity<>("NO_CONTENT List of OrderMfoodys with idUser: " + ID,
                    HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(orderMfoodyList, HttpStatus.OK);
    }

    @Secured({ROLE_ADMIN_SECURITY}) // User with role USER can't delete Order by themselves (Amin will do)
    @DeleteMapping(URL_DELETE)
    public ResponseEntity<?> deleteOrderMfoodyByID(@PathVariable("ID") int ID, Principal principal) {
        log.info("Delete OrderMfoody with ID: {} by {}", ID, principal.getName());
        OrderMfoody orderMfoody = orderMfoodyInterfaceService.getOrderMfoodyByID(ID);
        if (orderMfoody == null) {
            return new ResponseEntity<>("NOT_FOUND OrderMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }

        // Check if the current UserMfoody has role ADMIN or the owner of the OrderMfoody
        // if(!applicationCheckAuthorController.checkAuthorization(principal, orderMfoody.getUser().getIdUser())){
        //    return  new ResponseEntity<>("FORBIDDEN Authorization failed!", HttpStatus.FORBIDDEN);
        // }

        try {
            orderMfoodyInterfaceService.deleteOrderMfoodyByID(ID);
        } catch (Exception e) {
            log.error("An error occurred while deleting OrderMfoody with ID: " + ID);
            log.error("Detail Error: " + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "INTERNAL_SERVER_ERROR Exceptions occur when deleting OrderMfoody");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Secured({ROLE_ADMIN_SECURITY}) // User with role USER can't delete Order by themselves (Amin will do)
    @DeleteMapping(URL_DELETE_BY_ID_USER)
    public ResponseEntity<?> deleteAllOrderMfoodysByIdUser(@PathVariable("ID") int ID, Principal principal) {
        log.info("Delete List of OrderMfoodys with idUser: {} by {}", ID, principal.getName());
        if (userMfoodyInterfaceService.getUserMfoodyByID(ID) == null) {
            return new ResponseEntity<>("NOT_FOUND UserMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }

        // Check if the current UserMfoody has role ADMIN or the owner of the OrderMfoody
        // if(!applicationCheckAuthorController.checkAuthorization(principal, ID)){
        //    return  new ResponseEntity<>("FORBIDDEN Authorization failed!", HttpStatus.FORBIDDEN);
        // }

        try {
            orderMfoodyInterfaceService.deleteAllOrderMfoodysByIdUser(ID);
        } catch (Exception e) {
            log.error("An error occurred while deleting List of OrderMfoodys with idUser: " + ID);
            log.error("Detail Error: " + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "INTERNAL_SERVER_ERROR Exceptions occur when deleting List of OrderMfoodys");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
     * 1. idOrder in Json must be accurate, idUser/quantityAllProductsInOrder/totalSalPrice/totalFullPrice are ignored
     * 2. This API is required coz its attributes like dateOder/shippingPrice... still can be updated
     *    (excepts quantityAllProductsInOrder/totalSalPrice/totalFullPrice)
     * 3. The attributes quantityAllProductsInOrder/totalSalPrice/totalFullPrice will get default values 0 and will be
     *    updated later with APIs of DetailProductOrderMfoody coz the oneToMany table is always created first
     * */
    @Secured({ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY})
    @PutMapping(URL_EDIT)
    public ResponseEntity<?> editOrderMfoody(@RequestBody String orderPOJOJsonObject, Principal principal) {
        try {
            // Convert JsonObject to OrderPOJO object, Check input idOrder and set default values for newOrderMfoody
            Gson gson = new Gson();
            OrderMfoodyPOJO newOrderPOJO = gson.fromJson(orderPOJOJsonObject, OrderMfoodyPOJO.class);
            OrderMfoody oldOrderMfoody = orderMfoodyInterfaceService.getOrderMfoodyByID(newOrderPOJO.getIdOrder());
            if (oldOrderMfoody == null) {
                return new ResponseEntity<>("NOT_FOUND OrderMfoody with ID: " + newOrderPOJO.getIdOrder(),
                        HttpStatus.NOT_FOUND);
            }

            // Check UserMfoody and set it to OrderMfoody
            UserMfoody attachUserMfoody = userMfoodyInterfaceService.getUserMfoodyByIdOrder(newOrderPOJO.getIdOrder());
            if (attachUserMfoody == null) {
                return new ResponseEntity<>("NOT_FOUND UserMfoody with idOrder: " + newOrderPOJO.getIdOrder(),
                        HttpStatus.NOT_FOUND);
            }

            // Check if the current UserMfoody has role ADMIN or the owner of the OrderMfoody
            if(!applicationCheckAuthorController.checkAuthorization(principal, attachUserMfoody.getIdUser())){
                return  new ResponseEntity<>("FORBIDDEN Authorization failed!", HttpStatus.FORBIDDEN);
            }

            OrderMfoody newOrderMfoody = newOrderPOJO.renderOrderMfoody();
            newOrderMfoody.setQuantityAllProductsInOrder(oldOrderMfoody.getQuantityAllProductsInOrder());
            newOrderMfoody.setTotalFullPriceOrder(oldOrderMfoody.getTotalFullPriceOrder());
            newOrderMfoody.setTotalSalePriceOrder(oldOrderMfoody.getTotalSalePriceOrder());
            newOrderMfoody.setUser(attachUserMfoody);

            // Save to DB and return
            orderMfoodyInterfaceService.updateOrderMfoody(newOrderMfoody);
            log.info("OrderMfoody with ID: {} by {} is edited", newOrderMfoody.getIdOrder(), principal.getName());
            return new ResponseEntity<>(newOrderMfoody, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred while editing OrderMfoody");
            log.error("Detail Error: " + e);
            return new ResponseEntity<>("BAD_REQUEST Something Wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    /*
     * 1. quantityAllProductsInOrder/totalSalPrice/totalFullPrice are ignored
     * 2. Need to create a new OrderMfoody coz it has it own attributes like dateOder/shippingPrice...
     * 3. Every OrderMfoody is created with quantityAllProductsInOrder/totalSalPrice/totalFullPrice = 0 and will be
     *    updated later with APIs of DetailProductOrderMfoody coz the oneToMany table is always created first
     * 4. Get idUser from Principal (Token)
     *    (The threat is any UserMfoodys can create CreditCardMfoody using different idUser)
     * */
    @Secured({ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY})
    @PostMapping(URL_ADD)
    public ResponseEntity<?> addNewOrderMfoody(@RequestBody String orderPOJOJsonObject, Principal principal) {
        try {
            // Convert JsonObject to OrderPOJO object and Add new UserMfoody to OrderMfoody
            Gson gson = new Gson();
            OrderMfoodyPOJO newOrderPOJO = gson.fromJson(orderPOJOJsonObject, OrderMfoodyPOJO.class);
            OrderMfoody newOrderMfoody = newOrderPOJO.renderOrderMfoody();
            newOrderMfoody.setQuantityAllProductsInOrder(0);
            newOrderMfoody.setTotalFullPriceOrder(0);
            newOrderMfoody.setTotalSalePriceOrder(0);

            // Check input idUser and attach UserMfoody to OrderMfoody
            // UserMfoody attachUserMfoody = userMfoodyInterfaceService.getUserMfoodyByID(newOrderPOJO.getIdUser());
            UserMfoody attachUserMfoody = userMfoodyInterfaceService.getUserMfoodyByID(
                    userMfoodyInterfaceService.getUserMfoodyByEmail(principal.getName()).getIdUser());

            if (attachUserMfoody == null) {
                return new ResponseEntity<>("NOT_FOUND UserMfoody with idOrder: " + newOrderPOJO.getIdOrder(),
                        HttpStatus.NOT_FOUND);
            }
            newOrderMfoody.setUser(attachUserMfoody);

            // Save to DB and return (Updated Cart in DB could have ID differs from user's request)
            orderMfoodyInterfaceService.saveOrderMfoody(newOrderMfoody);
            log.info("A new OrderMfoody is created by " + principal.getName());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("An error occurred while adding OrderMfoody");
            log.error("Detail Error: " + e);
            return new ResponseEntity<>("BAD_REQUEST Something Wrong!", HttpStatus.BAD_REQUEST);
        }
    }
}
