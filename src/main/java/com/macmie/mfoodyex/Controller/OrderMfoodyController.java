package com.macmie.mfoodyex.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.macmie.mfoodyex.Model.CreditCardMfoody;
import com.macmie.mfoodyex.Model.OrderMfoody;
import com.macmie.mfoodyex.POJO.CreditCardMfoodyPOJO;
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

@RestController // = @ResponseBody + @Controller
@RequestMapping(ORDER_MFOODY)
public class OrderMfoodyController {
    @Autowired
    private OrderMfoodyInterfaceService orderMfoodyInterfaceService;

    @Autowired
    private UserMfoodyInterfaceService userMfoodyInterfaceService;

    @GetMapping(URL_GET_ALL)
    public ResponseEntity<?> getAllOrderMfoodys(){
        List<OrderMfoody> orderMfoodyList = orderMfoodyInterfaceService.getListOrderMfoodys();
        if(orderMfoodyList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(orderMfoodyList, HttpStatus.OK);
    }

    @GetMapping(URL_GET_BY_ID)
    public ResponseEntity<?> getOrderMfoodyByID(@PathVariable("ID") int ID){
        OrderMfoody OrderMfoody = orderMfoodyInterfaceService.getOrderMfoodyByID(ID);
        if(OrderMfoody == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(OrderMfoody, HttpStatus.OK);
    }

    @DeleteMapping(URL_DELETE)
    public ResponseEntity<?> deleteOrderMfoodyByID(@PathVariable("ID") int ID){
        orderMfoodyInterfaceService.deleteOrderMfoodyByID(ID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(URL_EDIT)
    public ResponseEntity<?> editOrderMfoody(@RequestBody String orderPOJOJsonObject, BindingResult errors){
        // Check Error
        if(errors.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to OrderPOJO object, add new User to OrderMfoody
        Gson gson = new Gson();
        OrderMfoodyPOJO newOrderPOJO = gson.fromJson(orderPOJOJsonObject, OrderMfoodyPOJO.class);
        OrderMfoody newOrderMfoody = newOrderPOJO.renderOrderMfoody();

        // Add new User to CreditCard and log
        newOrderMfoody.setUser(userMfoodyInterfaceService.getUserMfoodyByID(newOrderPOJO.getIdUser()));
        System.out.println("-------- JSon: " + orderPOJOJsonObject);
        System.out.println("-------- Convert from JSon: " + newOrderMfoody.getUser().getIdUser());

        // Save to DB
        orderMfoodyInterfaceService.updateOrderMfoody(newOrderMfoody);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(URL_ADD)
    public ResponseEntity<?> addNewOrderMfoody(@RequestBody String orderPOJOJsonObject, BindingResult errors){
        // Check Error
        if(errors.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to OrderPOJO object, add new User to OrderMfoody
        Gson gson = new Gson();
        OrderMfoodyPOJO newOrderPOJO = gson.fromJson(orderPOJOJsonObject, OrderMfoodyPOJO.class);
        OrderMfoody newOrderMfoody = newOrderPOJO.renderOrderMfoody();

        // Add new User to CreditCard and log
        newOrderMfoody.setUser(userMfoodyInterfaceService.getUserMfoodyByID(newOrderPOJO.getIdUser()));
        System.out.println("-------- JSon: " + orderPOJOJsonObject);
        System.out.println("-------- Convert from JSon: " + newOrderMfoody.getUser().getIdUser());

        // Save to DB
        orderMfoodyInterfaceService.saveOrderMfoody(newOrderMfoody);
        return new ResponseEntity<>(newOrderMfoody, HttpStatus.CREATED);
    }
}
