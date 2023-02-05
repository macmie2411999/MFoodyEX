package com.macmie.mfoodyex.Controller;

import com.google.gson.Gson;
import com.macmie.mfoodyex.Model.CartMfoody;
import com.macmie.mfoodyex.Model.UserMfoody;
import com.macmie.mfoodyex.POJO.CartMfoodyPOJO;
import com.macmie.mfoodyex.Service.InterfaceService.CartMfoodyInterfaceService;
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
@RequestMapping(CART_MFOODY)
public class CartMfoodyController {
    @Autowired
    private CartMfoodyInterfaceService cartMfoodyInterfaceService;

    @Autowired
    private UserMfoodyInterfaceService userMfoodyInterfaceService;

    @GetMapping(URL_GET_ALL)
    public ResponseEntity<?> getAllCartMfoodys() {
        List<CartMfoody> cartMfoodyList = cartMfoodyInterfaceService.getListCartMfoodys();
        if (cartMfoodyList.isEmpty()) {
            return new ResponseEntity<>("NO_CONTENT List of CartMfoodys", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(cartMfoodyList, HttpStatus.OK);
    }

    @GetMapping(URL_GET_BY_ID)
    public ResponseEntity<?> getCartMfoodyByID(@PathVariable("ID") int ID) {
        CartMfoody cartMfoody = cartMfoodyInterfaceService.getCartMfoodyByID(ID);
        if (cartMfoody == null) {
            return new ResponseEntity<>("NOT_FOUND CartMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cartMfoody, HttpStatus.OK);
    }

    @GetMapping(URL_GET_BY_ID_USER)
    public ResponseEntity<?> getCartMfoodyByIdUser(@PathVariable("ID") int ID) {
        if (userMfoodyInterfaceService.getUserMfoodyByID(ID) == null) {
            return new ResponseEntity<>("NOT_FOUND UserMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }
        CartMfoody cartMfoody = cartMfoodyInterfaceService.getCartMfoodyByIdUser(ID);
        if (cartMfoody == null) {
            return new ResponseEntity<>("NOT_FOUND CartMfoody with idUser: " + ID, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cartMfoody, HttpStatus.OK);
    }

    @DeleteMapping(URL_DELETE)
    public ResponseEntity<?> deleteCartMfoodyByID(@PathVariable("ID") int ID) {
        if (cartMfoodyInterfaceService.getCartMfoodyByID(ID) == null) {
            return new ResponseEntity<>("NOT_FOUND CartMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }
        cartMfoodyInterfaceService.deleteCartMfoodyByID(ID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(URL_DELETE_BY_ID_USER)
    public ResponseEntity<?> deleteCartMfoodyByIdUser(@PathVariable("ID") int ID) {
        if (userMfoodyInterfaceService.getUserMfoodyByID(ID) == null) {
            return new ResponseEntity<>("NOT_FOUND UserMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }
        cartMfoodyInterfaceService.deleteCartMfoodyByIdUser(ID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(URL_EDIT) // Redundant // idUser in Json is ignored
    public ResponseEntity<?> editCartMfoody(@RequestBody String cartMfoodyPOJOJsonObject, BindingResult errors) {
        // Check Error
        if (errors.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to CartMfoodyPOJO object, Check input idCart and update CartMfoody
        Gson gson = new Gson();
        CartMfoodyPOJO newCartMfoodyPOJO = gson.fromJson(cartMfoodyPOJOJsonObject, CartMfoodyPOJO.class);
        CartMfoody newCartMfoody = cartMfoodyInterfaceService.getCartMfoodyByID(newCartMfoodyPOJO.getIdCart());
        if (newCartMfoody == null) {
            return new ResponseEntity<>("NOT_FOUND CardMfoody with ID: " + newCartMfoodyPOJO.getIdCart(), HttpStatus.NOT_FOUND);
        }
        newCartMfoody.setQuantityAllProductsInCart(newCartMfoodyPOJO.getQuantityAllProductsInCart());
        newCartMfoody.setTotalSalePriceCart(newCartMfoodyPOJO.getTotalSalePriceCart());
        newCartMfoody.setTotalFullPriceCart(newCartMfoodyPOJO.getTotalFullPriceCart());

        // Save to DB and return
        cartMfoodyInterfaceService.updateCartMfoody(newCartMfoody);
        return new ResponseEntity<>(newCartMfoody, HttpStatus.OK);
    }

    @PostMapping(URL_ADD) // idUser in Json must be accurate
    public ResponseEntity<?> addNewCartMfoody(@RequestBody String cartMfoodyPOJOJsonObject, BindingResult errors) {
        // Check Error
        if (errors.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to CardMfoodyPOJO object
        Gson gson = new Gson();
        CartMfoodyPOJO newCartMfoodyPOJO = gson.fromJson(cartMfoodyPOJOJsonObject, CartMfoodyPOJO.class);
        CartMfoody newCartMfoody = newCartMfoodyPOJO.renderCartMfoody();

        // Check input idUser and attach UserMfoody to CartMfoody
        UserMfoody attachUserMfoody = userMfoodyInterfaceService.getUserMfoodyByID(newCartMfoodyPOJO.getIdUser());
        if (attachUserMfoody == null) {
            return new ResponseEntity<>("NOT_FOUND UserMfoody with ID: " + newCartMfoodyPOJO.getIdUser(), HttpStatus.NOT_FOUND);
        }
        newCartMfoody.setUser(attachUserMfoody);

        // Save to DB and return (Updated CartMfoody in DB could have ID differs from user's request)
        cartMfoodyInterfaceService.saveCartMfoody(newCartMfoody);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
