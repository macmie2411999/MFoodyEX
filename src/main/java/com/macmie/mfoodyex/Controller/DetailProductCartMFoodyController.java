package com.macmie.mfoodyex.Controller;

import com.google.gson.Gson;
import com.macmie.mfoodyex.Model.DetailProductCartMfoody;
import com.macmie.mfoodyex.POJO.DetailProductCartMfoodyPOJO;
import com.macmie.mfoodyex.Service.InterfaceService.*;
import com.macmie.mfoodyex.Service.InterfaceService.DetailProductCartMfoodyInterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.macmie.mfoodyex.Constant.ViewConstant.*;

@RestController // = @ResponseBody + @Controller
@RequestMapping(DETAIL_PRODUCT_CART_MFOODY)
public class DetailProductCartMFoodyController {
    @Autowired
    private DetailProductCartMfoodyInterfaceService detailProductCartMfoodyInterfaceService;

    @Autowired
    private CartMfoodyInterfaceService cartMfoodyInterfaceService;

    @Autowired
    private ProductMfoodyInterfaceService productMfoodyInterfaceService;

    @GetMapping(URL_GET_ALL)
    public ResponseEntity<List<DetailProductCartMfoody>> getAllDetailProductCartMfoodys(){
        List<DetailProductCartMfoody> detailProductCartMfoodyList = detailProductCartMfoodyInterfaceService.getListDetailProductCartMfoodys();
        if(detailProductCartMfoodyList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(detailProductCartMfoodyList, HttpStatus.OK);
    }

    @GetMapping(URL_GET_BY_ID)
    public ResponseEntity<DetailProductCartMfoody> getDetailProductCartMfoodyByID(@PathVariable("ID") int ID){
        DetailProductCartMfoody detailProductCartMfoody = detailProductCartMfoodyInterfaceService.getDetailProductCartMfoodyByID(ID);
        if(detailProductCartMfoody == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        System.out.println(detailProductCartMfoody.getIdDetailProductCartMFoody());
        return new ResponseEntity<>(detailProductCartMfoody, HttpStatus.OK);
    }

    @DeleteMapping(URL_DELETE)
    public ResponseEntity<?> deleteDetailProductCartMfoodyByID(@PathVariable("ID") int ID){
        detailProductCartMfoodyInterfaceService.deleteDetailProductCartMfoodyByID(ID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(URL_DELETE_BY_ID_CART)
    public ResponseEntity<?> deleteDetailProductCartMfoodyByIdCart(@PathVariable("ID") int ID){
        detailProductCartMfoodyInterfaceService.deleteAllDetailProductCartsMfoodyByIdCart(ID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(URL_DELETE_BY_ID_PRODUCT)
    public ResponseEntity<?> deleteDetailProductCartMfoodyByIdProduct(@PathVariable("ID") int ID){
        detailProductCartMfoodyInterfaceService.deleteAllDetailProductCartsMfoodyByIdProduct(ID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(URL_EDIT)
    public ResponseEntity<?> editDetailProductCartMfoody(@RequestBody String detailProductCartPOJOJsonObject, BindingResult errors){
        // Check Error
        if(errors.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to DetailProductCartPOJO object
        Gson gson = new Gson();
        DetailProductCartMfoodyPOJO newDetailProductCartMfoodyPOJO = gson.fromJson(detailProductCartPOJOJsonObject, DetailProductCartMfoodyPOJO.class);
        DetailProductCartMfoody newDetailProductCartMfoody = newDetailProductCartMfoodyPOJO.renderDetailProductCartMfoody();

        // Add new Cart and Product to DetailProductCart and log
        newDetailProductCartMfoody.setCart(cartMfoodyInterfaceService.getCartMfoodyByID(newDetailProductCartMfoodyPOJO.getIdCart()));
        newDetailProductCartMfoody.setProduct(productMfoodyInterfaceService.getProductMfoodyByID(newDetailProductCartMfoodyPOJO.getIdProduct()));
        System.out.println("-------- JSon: " + detailProductCartPOJOJsonObject);
        System.out.println("-------- Convert from JSon: " + newDetailProductCartMfoody.toString());

        // Save to DB
        detailProductCartMfoodyInterfaceService.updateDetailProductCartMfoody(newDetailProductCartMfoody);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(URL_ADD)
    public ResponseEntity<?> addNewDetailProductCartMfoody(@RequestBody String detailProductCartPOJOJsonObject, BindingResult errors){
        // Check Error
        if(errors.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to DetailProductCartPOJO object
        Gson gson = new Gson();
        DetailProductCartMfoodyPOJO newDetailProductCartMfoodyPOJO = gson.fromJson(detailProductCartPOJOJsonObject, DetailProductCartMfoodyPOJO.class);
        DetailProductCartMfoody newDetailProductCartMfoody = newDetailProductCartMfoodyPOJO.renderDetailProductCartMfoody();

        // Add new Cart and Product to DetailProductCart and log
        newDetailProductCartMfoody.setCart(cartMfoodyInterfaceService.getCartMfoodyByID(newDetailProductCartMfoodyPOJO.getIdCart()));
        newDetailProductCartMfoody.setProduct(productMfoodyInterfaceService.getProductMfoodyByID(newDetailProductCartMfoodyPOJO.getIdProduct()));
        System.out.println("-------- JSon: " + detailProductCartPOJOJsonObject);
        System.out.println("-------- Convert from JSon: " + newDetailProductCartMfoody.toString());

        // Save DetailProductCart to DB
        detailProductCartMfoodyInterfaceService.saveDetailProductCartMfoody(newDetailProductCartMfoody);
        return new ResponseEntity<>(newDetailProductCartMfoody, HttpStatus.CREATED);
    }
}
