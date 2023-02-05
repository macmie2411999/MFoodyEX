package com.macmie.mfoodyex.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.macmie.mfoodyex.Model.CartMfoody;
import com.macmie.mfoodyex.Model.CreditCardMfoody;
import com.macmie.mfoodyex.Model.ProductMfoody;
import com.macmie.mfoodyex.Model.UserMfoody;
import com.macmie.mfoodyex.POJO.CartMfoodyPOJO;
import com.macmie.mfoodyex.POJO.CreditCardMfoodyPOJO;
import com.macmie.mfoodyex.POJO.UserMfoodyPOJO;
import com.macmie.mfoodyex.Service.InterfaceService.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.macmie.mfoodyex.Constant.ViewConstant.*;

@RestController // = @ResponseBody + @Controller
@RequestMapping(USER_MFOODY)
public class UserMfoodyController {
    @Autowired
    private UserMfoodyInterfaceService userMfoodyInterfaceService;

    @Autowired
    private CreditCardMfoodyInterfaceService creditCardMfoodyInterfaceService;

    @Autowired
    private CommentMfoodyInterfaceService commentMfoodyInterfaceService;

    @Autowired
    private CartMfoodyInterfaceService cartMfoodyInterfaceService;

    @Autowired
    private DetailProductCartMfoodyInterfaceService detailProductCartMfoodyInterfaceService;

    @GetMapping(URL_GET_ALL)
    public ResponseEntity<List<UserMfoody>> getAllUserMfoodys(){
        List<UserMfoody> userMfoodyList = userMfoodyInterfaceService.getListUserMfoodys();
        if(userMfoodyList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(userMfoodyList, HttpStatus.OK);
    }

    @GetMapping(URL_GET_BY_ID)
    public ResponseEntity<?> getUserMfoodyByID(@PathVariable("ID") int ID){
        UserMfoody userMfoody = userMfoodyInterfaceService.getUserMfoodyByID(ID);
        if(userMfoody == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userMfoody, HttpStatus.OK);
    }

    @DeleteMapping(URL_DELETE)
    public ResponseEntity<?> deleteUserMfoodyByID(@PathVariable("ID") int ID){
        // Delete Cart (Detail Product Cart), Comment, Credit Card, and User
        creditCardMfoodyInterfaceService.deleteAllCreditCardsMfoodyByIdUser(ID);
        commentMfoodyInterfaceService.deleteAllCommentsMfoodyByIdUser(ID);
        cartMfoodyInterfaceService.deleteCartMfoodyByIdUser(ID);
        userMfoodyInterfaceService.deleteUserMfoodyByID(ID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(URL_EDIT)
    public ResponseEntity<?> editUserMfoody(@RequestBody String userMfoodyPOJOJsonObject, BindingResult errors){
        // Check Error
        if(errors.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to UserMfoodyPOJO object, Check the input idUser
        Gson gson = new Gson();
        UserMfoodyPOJO newUserMfoodyPOJO = gson.fromJson(userMfoodyPOJOJsonObject, UserMfoodyPOJO.class);
        UserMfoody newUserMfoody = newUserMfoodyPOJO.renderUserMfoody();
        UserMfoody oldUserMfoody = userMfoodyInterfaceService.getUserMfoodyByID(newUserMfoodyPOJO.getIdUser());
        if(oldUserMfoody == null){
            return new ResponseEntity<>("Can't find any ProductMfoody with ID: " + newUserMfoodyPOJO.getIdUser(), HttpStatus.NOT_FOUND);
        }

        // Save to DB
        userMfoodyInterfaceService.updateUserMfoody(newUserMfoody);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(URL_ADD)
    public ResponseEntity<?> addNewUserMfoody(@RequestBody String userMfoodyPOJOJsonObject, BindingResult errors){
        // Check Error
        if(errors.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to UserMfoody object
        Gson gson = new Gson();
        UserMfoodyPOJO newUserMfoodyPOJO = gson.fromJson(userMfoodyPOJOJsonObject, UserMfoodyPOJO.class);
        UserMfoody newUserMfoody = newUserMfoodyPOJO.renderUserMfoody();

        // Check duplicate
        UserMfoody existingEmailUser = userMfoodyInterfaceService.getUserMfoodyByEmail(newUserMfoodyPOJO.getEmailUser());
        UserMfoody existingPhoneNumberUser = userMfoodyInterfaceService.getUserMfoodyByPhoneNumber(newUserMfoodyPOJO.getPhoneNumberUser());
        if (existingEmailUser != null || existingPhoneNumberUser != null) {
            return new ResponseEntity<>("A user with the same email or phone number already exists!", HttpStatus.CONFLICT);
        }

        // Save the UserMfoody to DB (Updated Cart in DB could have ID differs from user's request)
        userMfoodyInterfaceService.saveUserMfoody(newUserMfoody);

        // Create and save a new CartMfoody (CartMfoody is automatically created when having a new UserMfoody, 1-to-1 relationship)
        CartMfoodyPOJO newCartMfoodyPOJO = new CartMfoodyPOJO(0,0,0,0, newUserMfoodyPOJO.getIdUser());
        CartMfoody newCartMfoody = newCartMfoodyPOJO.renderCartMfoody();
        newCartMfoody.setUser(userMfoodyInterfaceService.getUserMfoodyByEmail(newUserMfoodyPOJO.getEmailUser()));
        cartMfoodyInterfaceService.saveCartMfoody(newCartMfoody);

        return new ResponseEntity<>(newUserMfoody, HttpStatus.CREATED);
    }
}
