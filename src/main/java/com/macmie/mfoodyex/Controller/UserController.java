package com.macmie.mfoodyex.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.macmie.mfoodyex.Model.FeedbackMail;
import com.macmie.mfoodyex.Model.UserMfoody;
import com.macmie.mfoodyex.Service.InterfaceService.FeedbackMailInterfaceService;
import com.macmie.mfoodyex.Service.InterfaceService.UserMfoodyInterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.macmie.mfoodyex.Constant.ViewConstant.*;

@RestController // = @ResponseBody + @Controller
@RequestMapping(USER)
public class UserController {
    @Autowired
    private UserMfoodyInterfaceService userMfoodyInterfaceService;

    @GetMapping(URL_GET_ALL)
    public ResponseEntity<?> getAllUserMfoodys(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<UserMfoody> userMfoodyList = userMfoodyInterfaceService.getListUserMfoodys();
        if(userMfoodyList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(gson.toJson(userMfoodyList), HttpStatus.OK);
    }

    @GetMapping(URL_GET_BY_ID)
    public ResponseEntity<?> getUserMfoodyByID(@PathVariable("ID") int ID){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        UserMfoody userMfoody = userMfoodyInterfaceService.getUserMfoodyByID(ID);
        if(userMfoody == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(gson.toJson(userMfoody), HttpStatus.OK);
    }

    @DeleteMapping(URL_DELETE)
    public ResponseEntity<?> deleteUserMfoodyByID(@PathVariable("ID") int ID){
        userMfoodyInterfaceService.deleteUserMfoodyByID(ID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Error
    @PutMapping(URL_EDIT)
    public ResponseEntity<?> editUserMfoody(@RequestBody String userMfoodyJsonObject, BindingResult errors){
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
        userMfoodyInterfaceService.updateUserMfoody(newUserMfoody);
        return new ResponseEntity<>(newUserMfoody, HttpStatus.CREATED);
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
