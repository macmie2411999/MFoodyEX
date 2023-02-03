package com.macmie.mfoodyex.Controller;

import com.google.gson.Gson;
import com.macmie.mfoodyex.Model.DetailProductOrderMfoody;
import com.macmie.mfoodyex.POJO.DetailProductOrderMfoodyPOJO;
import com.macmie.mfoodyex.Service.InterfaceService.OrderMfoodyInterfaceService;
import com.macmie.mfoodyex.Service.InterfaceService.DetailProductOrderMfoodyInterfaceService;
import com.macmie.mfoodyex.Service.InterfaceService.ProductMfoodyInterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.macmie.mfoodyex.Constant.ViewConstant.*;

@RestController // = @ResponseBody + @Controller
@RequestMapping(DETAIL_PRODUCT_ORDER_MFOODY)
public class DetailProductOrderMFoodyController {
    @Autowired
    private DetailProductOrderMfoodyInterfaceService detailProductOrderMfoodyInterfaceService;

    @Autowired
    private OrderMfoodyInterfaceService orderMfoodyInterfaceService;

    @Autowired
    private ProductMfoodyInterfaceService productMfoodyInterfaceService;

    @GetMapping(URL_GET_ALL)
    public ResponseEntity<List<DetailProductOrderMfoody>> getAllDetailProductOrderMfoodys(){
        List<DetailProductOrderMfoody> detailProductOrderMfoodyList = detailProductOrderMfoodyInterfaceService.getListDetailProductOrderMfoodys();
        if(detailProductOrderMfoodyList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(detailProductOrderMfoodyList, HttpStatus.OK);
    }

    @GetMapping(URL_GET_BY_ID)
    public ResponseEntity<DetailProductOrderMfoody> getDetailProductOrderMfoodyByID(@PathVariable("ID") int ID){
        DetailProductOrderMfoody detailProductOrderMfoody = detailProductOrderMfoodyInterfaceService.getDetailProductOrderMfoodyByID(ID);
        if(detailProductOrderMfoody == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        System.out.println(detailProductOrderMfoody.getIdDetailProductOrderMfoody());
        return new ResponseEntity<>(detailProductOrderMfoody, HttpStatus.OK);
    }

    @DeleteMapping(URL_DELETE)
    public ResponseEntity<?> deleteDetailProductOrderMfoodyByID(@PathVariable("ID") int ID){
        detailProductOrderMfoodyInterfaceService.deleteDetailProductOrderMfoodyByID(ID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(URL_DELETE_BY_ID_ORDER)
    public ResponseEntity<?> deleteDetailProductCartMfoodyByIdCart(@PathVariable("ID") int ID){
        detailProductOrderMfoodyInterfaceService.deleteAllDetailProductOrdersMfoodyByIdOrder(ID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(URL_DELETE_BY_ID_PRODUCT)
    public ResponseEntity<?> deleteDetailProductCartMfoodyByIdProduct(@PathVariable("ID") int ID){
        detailProductOrderMfoodyInterfaceService.deleteAllDetailProductOrdersMfoodyByIdProduct(ID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(URL_EDIT)
    public ResponseEntity<?> editDetailProductOrderMfoody(@RequestBody String detailProductOrderPOJOJsonObject, BindingResult errors){
        // Check Error
        if(errors.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to DetailProductOrderPOJO object
        Gson gson = new Gson();
        DetailProductOrderMfoodyPOJO newDetailProductOrderMfoodyPOJO = gson.fromJson(detailProductOrderPOJOJsonObject, DetailProductOrderMfoodyPOJO.class);
        DetailProductOrderMfoody newDetailProductOrderMfoody = newDetailProductOrderMfoodyPOJO.renderDetailProductOrderMfoody();

        // Add new Order and Product to DetailProductOrder and log
        newDetailProductOrderMfoody.setOrder(orderMfoodyInterfaceService.getOrderMfoodyByID(newDetailProductOrderMfoodyPOJO.getIdOrder()));
        newDetailProductOrderMfoody.setProduct(productMfoodyInterfaceService.getProductMfoodyByID(newDetailProductOrderMfoodyPOJO.getIdProduct()));
        System.out.println("-------- JSon: " + detailProductOrderPOJOJsonObject);
        System.out.println("-------- Convert from JSon: " + newDetailProductOrderMfoody.toString());

        // Save to DB
        detailProductOrderMfoodyInterfaceService.updateDetailProductOrderMfoody(newDetailProductOrderMfoody);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(URL_ADD)
    public ResponseEntity<?> addNewDetailProductOrderMfoody(@RequestBody String detailProductOrderPOJOJsonObject, BindingResult errors){
        // Check Error
        if(errors.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to DetailProductOrderPOJO object
        Gson gson = new Gson();
        DetailProductOrderMfoodyPOJO newDetailProductOrderMfoodyPOJO = gson.fromJson(detailProductOrderPOJOJsonObject, DetailProductOrderMfoodyPOJO.class);
        DetailProductOrderMfoody newDetailProductOrderMfoody = newDetailProductOrderMfoodyPOJO.renderDetailProductOrderMfoody();

        // Add new Order and Product to DetailProductOrder and log
        newDetailProductOrderMfoody.setOrder(orderMfoodyInterfaceService.getOrderMfoodyByID(newDetailProductOrderMfoodyPOJO.getIdOrder()));
        newDetailProductOrderMfoody.setProduct(productMfoodyInterfaceService.getProductMfoodyByID(newDetailProductOrderMfoodyPOJO.getIdProduct()));
        System.out.println("-------- JSon: " + detailProductOrderPOJOJsonObject);
        System.out.println("-------- Convert from JSon: " + newDetailProductOrderMfoody.toString());

        // Save DetailProductOrder to DB
        detailProductOrderMfoodyInterfaceService.saveDetailProductOrderMfoody(newDetailProductOrderMfoody);
        return new ResponseEntity<>(newDetailProductOrderMfoody, HttpStatus.CREATED);
    }
}
