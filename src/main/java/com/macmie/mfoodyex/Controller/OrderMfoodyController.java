package com.macmie.mfoodyex.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.macmie.mfoodyex.Model.OrderMfoody;
import com.macmie.mfoodyex.Service.InterfaceService.OrderMfoodyInterfaceService;
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

    @GetMapping(URL_GET_ALL)
    public ResponseEntity<?> getAllOrderMfoodys(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<OrderMfoody> orderMfoodyList = orderMfoodyInterfaceService.getListOrderMfoodys();
        if(orderMfoodyList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(gson.toJson(orderMfoodyList), HttpStatus.OK);
    }

    @GetMapping(URL_GET_BY_ID)
    public ResponseEntity<?> getOrderMfoodyByID(@PathVariable("ID") int ID){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        OrderMfoody OrderMfoody = orderMfoodyInterfaceService.getOrderMfoodyByID(ID);
        if(OrderMfoody == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(gson.toJson(OrderMfoody), HttpStatus.OK);
    }

    @DeleteMapping(URL_DELETE)
    public ResponseEntity<?> deleteOrderMfoodyByID(@PathVariable("ID") int ID){
        orderMfoodyInterfaceService.deleteOrderMfoodyByID(ID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(URL_EDIT)
    public ResponseEntity<?> editOrderMfoody(@RequestBody String orderMfoodyJsonObject, BindingResult errors){
        // Check Error
        if(errors.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to OrderMfoody object
        Gson gson = new Gson();
        OrderMfoody newOrderMfoody = gson.fromJson(orderMfoodyJsonObject, OrderMfoody.class);
        System.out.println("-------- JSon: " + orderMfoodyJsonObject);
        System.out.println("-------- Convert from JSon: " + newOrderMfoody);

        // Save to DB
        orderMfoodyInterfaceService.updateOrderMfoody(newOrderMfoody);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(URL_ADD)
    public ResponseEntity<?> addNewOrderMfoody(@RequestBody String orderMfoodyJsonObject, BindingResult errors){
        // Check Error
        if(errors.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to OrderMfoody object
        Gson gson = new Gson();
        OrderMfoody newOrderMfoody = gson.fromJson(orderMfoodyJsonObject, OrderMfoody.class);
        System.out.println("-------- JSon: " + orderMfoodyJsonObject);
        System.out.println("-------- Convert from JSon: " + newOrderMfoody);

        // Save to DB
        orderMfoodyInterfaceService.saveOrderMfoody(newOrderMfoody);
        return new ResponseEntity<>(newOrderMfoody, HttpStatus.CREATED);
    }
}
