package com.macmie.mfoodyex.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.macmie.mfoodyex.Model.CreditCardMfoody;
import com.macmie.mfoodyex.Model.UserMfoody;
import com.macmie.mfoodyex.POJO.CreditCardMfoodyPOJO;
import com.macmie.mfoodyex.POJO.UserMfoodyPOJO;
import com.macmie.mfoodyex.Service.InterfaceService.UserMfoodyInterfaceService;
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
        userMfoodyInterfaceService.deleteUserMfoodyByID(ID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(URL_EDIT)
    public ResponseEntity<?> editUserMfoody(@RequestBody String userMfoodyPOJOJsonObject, BindingResult errors){
        // Check Error
        if(errors.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to CreditCardPOJO object, add new User to CreditCard
        Gson gson = new Gson();
        UserMfoodyPOJO newUserMfoodyPOJO = gson.fromJson(userMfoodyPOJOJsonObject, UserMfoodyPOJO.class);
        UserMfoody newUserMfoody = newUserMfoodyPOJO.renderUserMfoody();

        // Check duplicate
        UserMfoody existingEmailUser = userMfoodyInterfaceService.getUserMfoodyByEmail(newUserMfoody.getEmailUser());
        UserMfoody existingPhoneNumberUser = userMfoodyInterfaceService.getUserMfoodyByPhoneNumber(newUserMfoody.getPhoneNumberUser());
        if (existingEmailUser != null || existingPhoneNumberUser != null) {
            return new ResponseEntity<>("A user with the same email or phone number already exists!", HttpStatus.CONFLICT);
        }

        // Save to DB
        userMfoodyInterfaceService.updateUserMfoody(newUserMfoody);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(URL_ADD)
    public ResponseEntity<?> addNewUserMfoody(@RequestBody String userMfoodyJsonObject, BindingResult errors){
        // Check Error
        if(errors.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to UserMfoody object
        Gson gson = new Gson();
        UserMfoody newUserMfoody = gson.fromJson(userMfoodyJsonObject, UserMfoody.class);
        System.out.println("-------- JSon: " + userMfoodyJsonObject);
        System.out.println("-------- Convert from JSon: " + newUserMfoody);

        // Save to DB
        userMfoodyInterfaceService.saveUserMfoody(newUserMfoody);
        return new ResponseEntity<>(newUserMfoody, HttpStatus.CREATED);
    }
}
