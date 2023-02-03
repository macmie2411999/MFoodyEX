package com.macmie.mfoodyex.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.macmie.mfoodyex.Model.CartMfoody;
import com.macmie.mfoodyex.Model.CommentMfoody;
import com.macmie.mfoodyex.POJO.CartMfoodyPOJO;
import com.macmie.mfoodyex.POJO.CommentMfoodyPOJO;
import com.macmie.mfoodyex.Service.InterfaceService.CartMfoodyInterfaceService;
import com.macmie.mfoodyex.Service.InterfaceService.UserMfoodyInterfaceService;
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
    private CartMfoodyInterfaceService cartMfoodyInterfaceService;

    @Autowired
    private UserMfoodyInterfaceService userMfoodyInterfaceService;

    @GetMapping(URL_GET_ALL)
    public ResponseEntity<?> getAllCartMfoodys(){
        List<CartMfoody> CartMfoodyList = cartMfoodyInterfaceService.getListCartMfoodys();
        if(CartMfoodyList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(CartMfoodyList, HttpStatus.OK);
    }

    @GetMapping(URL_GET_BY_ID)
    public ResponseEntity<?> getCartMfoodyByID(@PathVariable("ID") int ID){
        CartMfoody cartMfoody = cartMfoodyInterfaceService.getCartMfoodyByID(ID);
        if(cartMfoody == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        System.out.println(cartMfoody.getIdCart());
        return new ResponseEntity<>(cartMfoody, HttpStatus.OK);
    }

    @DeleteMapping(URL_DELETE)
    public ResponseEntity<?> deleteCartMfoodyByID(@PathVariable("ID") int ID){
        cartMfoodyInterfaceService.deleteCartMfoodyByID(ID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(URL_DELETE_BY_ID_USER)
    public ResponseEntity<?> deleteCartMfoodyByIdUser(@PathVariable("ID") int ID){
        cartMfoodyInterfaceService.deleteCartMfoodyByIdUser(ID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(URL_EDIT)
    public ResponseEntity<?> editCartMfoody(@RequestBody String cartPOJOJsonObject, BindingResult errors){
        // Check Error
        if(errors.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to CartMfoodyPOJO object, add new User to Comment
        Gson gson = new Gson();
        CartMfoodyPOJO newCartMfoodyPOJO = gson.fromJson(cartPOJOJsonObject, CartMfoodyPOJO.class);
        CartMfoody newCartMfoody = newCartMfoodyPOJO.renderCartMfoody();

        // Add new User and Product to Comment and log
        newCartMfoody.setUser(userMfoodyInterfaceService.getUserMfoodyByID(newCartMfoodyPOJO.getIdUser()));
        System.out.println("-------- JSon: " + cartPOJOJsonObject);
        System.out.println("-------- Convert from JSon: " + newCartMfoody.getUser().getIdUser());

        // Save to DB
        cartMfoodyInterfaceService.updateCartMfoody(newCartMfoody);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(URL_ADD)
    public ResponseEntity<?> addNewCartMfoody(@RequestBody String cartPOJOJsonObject, BindingResult errors){
        // Check Error
        if(errors.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to CommentPOJO object, add new User to Comment
        Gson gson = new Gson();
        CartMfoodyPOJO newCartMfoodyPOJO = gson.fromJson(cartPOJOJsonObject, CartMfoodyPOJO.class);
        CartMfoody newCartMfoody = newCartMfoodyPOJO.renderCartMfoody();

        // Add new User and Product to Comment and log
        newCartMfoody.setUser(userMfoodyInterfaceService.getUserMfoodyByID(newCartMfoodyPOJO.getIdUser()));
        System.out.println("-------- JSon: " + cartPOJOJsonObject);
        System.out.println("-------- Convert from JSon: " + newCartMfoody.getUser().getIdUser());

        // Save to DB
        cartMfoodyInterfaceService.saveCartMfoody(newCartMfoody);
        return new ResponseEntity<>(newCartMfoody, HttpStatus.CREATED);
    }
}
