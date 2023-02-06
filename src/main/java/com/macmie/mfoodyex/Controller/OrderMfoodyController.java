package com.macmie.mfoodyex.Controller;

import com.google.gson.Gson;
import com.macmie.mfoodyex.Model.CommentMfoody;
import com.macmie.mfoodyex.Model.OrderMfoody;
import com.macmie.mfoodyex.Model.UserMfoody;
import com.macmie.mfoodyex.POJO.OrderMfoodyPOJO;
import com.macmie.mfoodyex.Service.InterfaceService.OrderMfoodyInterfaceService;
import com.macmie.mfoodyex.Service.InterfaceService.UserMfoodyInterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.macmie.mfoodyex.Constant.ViewConstant.*;

/*
 * be used when the requested resource cannot be found (null): HttpStatus.NOT_FOUND (404)
 * be used when a successful request returns no content (empty): HttpStatus.NO_CONTENT (204)
 * be used when the request is invalid or contains incorrect parameters: HttpStatus.BAD_REQUEST (400)
 * */

@RestController // = @ResponseBody + @Controller
@RequestMapping(ORDER_MFOODY)
public class OrderMfoodyController {
    @Autowired
    private OrderMfoodyInterfaceService orderMfoodyInterfaceService;

    @Autowired
    private UserMfoodyInterfaceService userMfoodyInterfaceService;

    @GetMapping(URL_GET_ALL)
    public ResponseEntity<?> getAllOrderMfoodys() {
        List<OrderMfoody> orderMfoodyList = orderMfoodyInterfaceService.getListOrderMfoodys();
        if (orderMfoodyList.isEmpty()) {
            return new ResponseEntity<>("NO_CONTENT List of OrderMfoodys", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(orderMfoodyList, HttpStatus.OK);
    }

    @GetMapping(URL_GET_BY_ID)
    public ResponseEntity<?> getOrderMfoodyByID(@PathVariable("ID") int ID) {
        OrderMfoody OrderMfoody = orderMfoodyInterfaceService.getOrderMfoodyByID(ID);
        if (OrderMfoody == null) {
            return new ResponseEntity<>("NOT_FOUND OrderMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(OrderMfoody, HttpStatus.OK);
    }

    @GetMapping(URL_GET_BY_ID_USER)
    public ResponseEntity<?> getOrderMfoodyByIdUser(@PathVariable("ID") int ID) {
        if (userMfoodyInterfaceService.getUserMfoodyByID(ID) == null) {
            return new ResponseEntity<>("NOT_FOUND UserMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }
        List<OrderMfoody> orderMfoodyList = orderMfoodyInterfaceService.getListOrderMfoodysByIdUser(ID);
        if (orderMfoodyList.isEmpty()) {
            return new ResponseEntity<>("NO_CONTENT List of OrderMfoodys with idUser: " + ID,
                    HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(orderMfoodyList, HttpStatus.OK);
    }

    @DeleteMapping(URL_DELETE)
    public ResponseEntity<?> deleteOrderMfoodyByID(@PathVariable("ID") int ID) {
        if (orderMfoodyInterfaceService.getOrderMfoodyByID(ID) == null) {
            return new ResponseEntity<>("NOT_FOUND OrderMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }
        orderMfoodyInterfaceService.deleteOrderMfoodyByID(ID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(URL_DELETE_BY_ID_USER)
    public ResponseEntity<?> deleteOrderMfoodyByIdUser(@PathVariable("ID") int ID) {
        if (userMfoodyInterfaceService.getUserMfoodyByID(ID) == null) {
            return new ResponseEntity<>("NOT_FOUND UserMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }
        orderMfoodyInterfaceService.deleteAllOrderMfoodysByIdUser(ID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
     * 1. idOrder in Json must be accurate, idUser/quantityAllProductsInOrder/totalSalPrice/totalFullPrice are ignored
     * 2. This API is required coz its attributes like dateOder/shippingPrice... still can be updated
     *    (excepts quantityAllProductsInOrder/totalSalPrice/totalFullPrice)
     * 3. The attributes quantityAllProductsInOrder/totalSalPrice/totalFullPrice will get default values 0 and will be
     *    updated later with APIs of DetailProductOrderMfoody coz the oneToMany table is always created first
     * */
    @PutMapping(URL_EDIT)
    public ResponseEntity<?> editOrderMfoody(@RequestBody String orderPOJOJsonObject, BindingResult errors) {
        // Check Error
        if (errors.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to OrderPOJO object, Check input idOrder and set default values for newOrderMfoody
        Gson gson = new Gson();
        OrderMfoodyPOJO newOrderPOJO = gson.fromJson(orderPOJOJsonObject, OrderMfoodyPOJO.class);
        if (orderMfoodyInterfaceService.getOrderMfoodyByID(newOrderPOJO.getIdOrder()) == null) {
            return new ResponseEntity<>("NOT_FOUND OrderMfoody with ID: " + newOrderPOJO.getIdOrder(),
                    HttpStatus.NOT_FOUND);
        }
        OrderMfoody newOrderMfoody = newOrderPOJO.renderOrderMfoody();
        newOrderMfoody.setQuantityAllProductsInOrder(0);
        newOrderMfoody.setTotalFullPriceOrder(0);
        newOrderMfoody.setTotalSalePriceOrder(0);

        // Check UserMfoody and set it to OrderMfoody
        UserMfoody attachUserMfoody = userMfoodyInterfaceService.getUserMfoodyByIdOrder(newOrderPOJO.getIdOrder());
        if (attachUserMfoody == null) {
            return new ResponseEntity<>("NOT_FOUND UserMfoody with idOrder: " + newOrderPOJO.getIdOrder(),
                    HttpStatus.NOT_FOUND);
        }
        newOrderMfoody.setUser(attachUserMfoody);

        // Save to DB and return
        orderMfoodyInterfaceService.updateOrderMfoody(newOrderMfoody);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
     * 1. idUser in Json must be accurate, quantityAllProductsInOrder/totalSalPrice/totalFullPrice are ignored
     * 2. Need to create a new OrderMfoody coz it has it own attributes like dateOder/shippingPrice...
     * 3. Every OrderMfoody is created with quantityAllProductsInOrder/totalSalPrice/totalFullPrice = 0 and will be
     *    updated later with APIs of DetailProductOrderMfoody coz the oneToMany table is always created first
     * */
    @PostMapping(URL_ADD)
    public ResponseEntity<?> addNewOrderMfoody(@RequestBody String orderPOJOJsonObject, BindingResult errors) {
        // Check Error
        if (errors.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to OrderPOJO object and Add new UserMfoody to OrderMfoody
        Gson gson = new Gson();
        OrderMfoodyPOJO newOrderPOJO = gson.fromJson(orderPOJOJsonObject, OrderMfoodyPOJO.class);
        OrderMfoody newOrderMfoody = newOrderPOJO.renderOrderMfoody();

        // Check input idUser and attach UserMfoody to OrderMfoody
        UserMfoody attachUserMfoody = userMfoodyInterfaceService.getUserMfoodyByID(newOrderPOJO.getIdUser());
        if (attachUserMfoody == null) {
            return new ResponseEntity<>("NOT_FOUND UserMfoody with idOrder: " + newOrderPOJO.getIdOrder(),
                    HttpStatus.NOT_FOUND);
        }
        newOrderMfoody.setUser(attachUserMfoody);

        // Save to DB and return (Updated Cart in DB could have ID differs from user's request)
        orderMfoodyInterfaceService.saveOrderMfoody(newOrderMfoody);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
