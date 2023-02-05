package com.macmie.mfoodyex.Controller;

import com.google.gson.Gson;
import com.macmie.mfoodyex.Model.CartMfoody;
import com.macmie.mfoodyex.Model.DetailProductCartMfoody;
import com.macmie.mfoodyex.Model.DetailProductCartMfoodyId;
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
public class DetailProductCartMfoodyController {
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

    @GetMapping(URL_GET_BY_ID_CART_AND_ID_PRODUCT)
    public ResponseEntity<DetailProductCartMfoody> getDetailProductCartMfoodyByID(@PathVariable("IdCart") int idCart, @PathVariable("IdProduct") int idProduct){
        DetailProductCartMfoody detailProductCartMfoody = detailProductCartMfoodyInterfaceService.getDetailProductCartMfoodyByICartAndIdProduct(idCart, idProduct);
        if(detailProductCartMfoody == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
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

    @PutMapping(URL_EDIT) // idCart and idProduct in Json must be accurate
    public ResponseEntity<?> editDetailProductCartMfoody(@RequestBody String detailProductCartPOJOJsonObject, BindingResult errors){
        // Check Error
        if(errors.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to DetailProductCartPOJO object
        Gson gson = new Gson();
        DetailProductCartMfoodyPOJO newDetailProductCartMfoodyPOJO = gson.fromJson(detailProductCartPOJOJsonObject, DetailProductCartMfoodyPOJO.class);
        DetailProductCartMfoody newDetailProductCartMfoody = newDetailProductCartMfoodyPOJO.renderDetailProductCartMfoody();

        // Update IdDetailProductCartMfoody, ProductMfoody, and CartMfoody
        newDetailProductCartMfoody.setIdDetailProductCartMfoody(new DetailProductCartMfoodyId(newDetailProductCartMfoodyPOJO.getIdCart(), newDetailProductCartMfoodyPOJO.getIdProduct()));
        newDetailProductCartMfoody.setProduct(productMfoodyInterfaceService.getProductMfoodyByID(newDetailProductCartMfoodyPOJO.getIdProduct()));
        processCartMfoody(newDetailProductCartMfoodyPOJO, newDetailProductCartMfoody);

        // Save to DB and return
        detailProductCartMfoodyInterfaceService.updateDetailProductCartMfoody(newDetailProductCartMfoody);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(URL_ADD) // idCart and idProduct in Json must be accurate
    public ResponseEntity<?> addNewDetailProductCartMfoody(@RequestBody String detailProductCartPOJOJsonObject, BindingResult errors){
        // Check Error
        if(errors.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to DetailProductCartPOJO object and Add new CartMfoody and ProductMfoody to DetailProductCart
        Gson gson = new Gson();
        DetailProductCartMfoodyPOJO newDetailProductCartMfoodyPOJO = gson.fromJson(detailProductCartPOJOJsonObject, DetailProductCartMfoodyPOJO.class);
        DetailProductCartMfoody newDetailProductCartMfoody = newDetailProductCartMfoodyPOJO.renderDetailProductCartMfoody();

        // Update IdDetailProductCartMfoody, ProductMfoody, and CartMfoody
        newDetailProductCartMfoody.setIdDetailProductCartMfoody(new DetailProductCartMfoodyId(newDetailProductCartMfoodyPOJO.getIdCart(), newDetailProductCartMfoodyPOJO.getIdProduct()));
        newDetailProductCartMfoody.setProduct(productMfoodyInterfaceService.getProductMfoodyByID(newDetailProductCartMfoodyPOJO.getIdProduct()));
        processCartMfoody(newDetailProductCartMfoodyPOJO, newDetailProductCartMfoody);

        // Save to DB and return (Updated Cart in DB could have ID differs from user's request)
        detailProductCartMfoodyInterfaceService.saveDetailProductCartMfoody(newDetailProductCartMfoody);
        return new ResponseEntity<>(newDetailProductCartMfoody, HttpStatus.CREATED);
    }

    public void processCartMfoody(DetailProductCartMfoodyPOJO newDetailProductCartMfoodyPOJO, DetailProductCartMfoody newDetailProductCartMfoody){
        DetailProductCartMfoody oldDetailProductCartMfoody = detailProductCartMfoodyInterfaceService.getDetailProductCartMfoodyByICartAndIdProduct(
                newDetailProductCartMfoodyPOJO.getIdCart(), newDetailProductCartMfoodyPOJO.getIdProduct());
        CartMfoody newCartMfoody = cartMfoodyInterfaceService.getCartMfoodyByID(newDetailProductCartMfoodyPOJO.getIdCart());
        newCartMfoody.setQuantityAllProductsInCart(newCartMfoody.getQuantityAllProductsInCart()
                + newDetailProductCartMfoody.getQuantityDetailProductCart() - oldDetailProductCartMfoody.getQuantityDetailProductCart());
        newCartMfoody.setTotalSalePriceCart(newCartMfoody.getTotalSalePriceCart()
                + newDetailProductCartMfoody.getSalePriceDetailProductCart() - oldDetailProductCartMfoody.getSalePriceDetailProductCart());
        newCartMfoody.setTotalFullPriceCart(newCartMfoody.getTotalFullPriceCart()
                + newDetailProductCartMfoody.getFullPriceDetailProductCart() - oldDetailProductCartMfoody.getFullPriceDetailProductCart());
        cartMfoodyInterfaceService.updateCartMfoody(newCartMfoody);
        newDetailProductCartMfoody.setCart(newCartMfoody);
    }
}
