package com.macmie.mfoodyex.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.macmie.mfoodyex.Model.CartMfoody;
import com.macmie.mfoodyex.Service.InterfaceService.CartMfoodyInterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.macmie.mfoodyex.Constant.ViewConstant.*;

@RestController // = @ResponseBody + @Controller
@RequestMapping(CART_MFOODY)
public class CartFoodyController {
    @Autowired
    private CartMfoodyInterfaceService CartMfoodyInterfaceService;

    @GetMapping(URL_GET_ALL)
    public ResponseEntity<?> getAllCartMfoodys(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<CartMfoody> CartMfoodyList = CartMfoodyInterfaceService.getListCartMfoodys();
        if(CartMfoodyList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(gson.toJson(CartMfoodyList), HttpStatus.OK);
    }

    @GetMapping(URL_GET_BY_ID)
    public ResponseEntity<?> getCartMfoodyByID(@PathVariable("ID") int ID){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        CartMfoody CartMfoody = CartMfoodyInterfaceService.getCartMfoodyByID(ID);
        if(CartMfoody == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(gson.toJson(CartMfoody), HttpStatus.OK);
    }

    @DeleteMapping(URL_DELETE)
    public ResponseEntity<?> deleteCartMfoodyByID(@PathVariable("ID") int ID){
        CartMfoodyInterfaceService.deleteCartMfoodyByID(ID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(URL_EDIT)
    public ResponseEntity<?> editCartMfoody(@RequestBody String CartMfoodyJsonObject, BindingResult errors){
        // Check Error
        if(errors.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to CartMfoody object
        Gson gson = new Gson();
        CartMfoody newCartMfoody = gson.fromJson(CartMfoodyJsonObject, CartMfoody.class);
        System.out.println("-------- JSon: " + CartMfoodyJsonObject);
        System.out.println("-------- Convert from JSon: " + newCartMfoody);

        // Save to DB
        CartMfoodyInterfaceService.updateCartMfoody(newCartMfoody);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(URL_ADD)
    public ResponseEntity<?> addNewCartMfoody(@RequestBody String CartMfoodyJsonObject, BindingResult errors){
        // Check Error
        if(errors.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to CartMfoody object
        Gson gson = new Gson();
        CartMfoody newCartMfoody = gson.fromJson(CartMfoodyJsonObject, CartMfoody.class);
        System.out.println("-------- JSon: " + CartMfoodyJsonObject);
        System.out.println("-------- Convert from JSon: " + newCartMfoody);

        // Save to DB
        CartMfoodyInterfaceService.saveCartMfoody(newCartMfoody);
        return new ResponseEntity<>(newCartMfoody, HttpStatus.CREATED);
    }
}
