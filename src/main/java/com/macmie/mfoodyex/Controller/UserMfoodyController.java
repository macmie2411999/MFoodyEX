package com.macmie.mfoodyex.Controller;

import com.google.gson.Gson;
import com.macmie.mfoodyex.Model.CartMfoody;
import com.macmie.mfoodyex.Model.UserMfoody;
import com.macmie.mfoodyex.POJO.CartMfoodyPOJO;
import com.macmie.mfoodyex.POJO.UserMfoodyPOJO;
import com.macmie.mfoodyex.Service.InterfaceService.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;

import static com.macmie.mfoodyex.Constant.ViewConstants.*;

/*
 * be used when the requested resource cannot be found (null): HttpStatus.NOT_FOUND (404)
 * be used when a successful request returns no content (empty): HttpStatus.NO_CONTENT (204)
 * be used when the request is invalid or contains incorrect parameters: HttpStatus.BAD_REQUEST (400)
 * */

@Slf4j
@Transactional
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
    private OrderMfoodyInterfaceService orderMfoodyInterfaceService;

    @Autowired
    private DetailProductCartMfoodyInterfaceService detailProductCartMfoodyInterfaceService;

    @Autowired
    private DetailProductOrderMfoodyInterfaceService detailProductOrderMfoodyInterfaceService;

    @GetMapping(URL_GET_ALL)
    public ResponseEntity<?> getAllUserMfoodys() {
        List<UserMfoody> userMfoodyList = userMfoodyInterfaceService.getListUserMfoodys();
        if (userMfoodyList.isEmpty()) {
            return new ResponseEntity<>("NO_CONTENT List of UserMfoodys", HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(userMfoodyList, HttpStatus.OK);
    }

    @GetMapping(URL_GET_BY_ID)
    public ResponseEntity<?> getUserMfoodyByID(@PathVariable("ID") int ID) {
        UserMfoody userMfoody = userMfoodyInterfaceService.getUserMfoodyByID(ID);
        if (userMfoody == null) {
            return new ResponseEntity<>("NOT_FOUND UserMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userMfoody, HttpStatus.OK);
    }

    @DeleteMapping(URL_DELETE)
    public ResponseEntity<?> deleteUserMfoodyByID(@PathVariable("ID") int ID) {
        if (userMfoodyInterfaceService.getUserMfoodyByID(ID) == null) {
            return new ResponseEntity<>("NOT_FOUND UserMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }

        try {
            // Delete Cart (DetailProductCart), DeleteOrder (DetailProductOrder), Comment, Credit Card, and User
            creditCardMfoodyInterfaceService.deleteAllCreditCardsMfoodyByIdUser(ID);
            commentMfoodyInterfaceService.deleteAllCommentsMfoodyByIdUser(ID);
            cartMfoodyInterfaceService.deleteCartMfoodyByIdUser(ID);
            orderMfoodyInterfaceService.deleteAllOrderMfoodysByIdUser(ID);
            userMfoodyInterfaceService.deleteUserMfoodyByID(ID);
        } catch (Exception e) {
            log.error("An error occurred while deleting UserMfoody with ID: " + ID);
            log.error("Detail Error: " + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "INTERNAL_SERVER_ERROR Exceptions occur when deleting UserMfoody");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(URL_EDIT)
    public ResponseEntity<?> editUserMfoody(@RequestBody String userMfoodyPOJOJsonObject) {
        try {
            // Convert JsonObject to UserMfoodyPOJO object, Check the input idUser
            Gson gson = new Gson();
            UserMfoodyPOJO newUserMfoodyPOJO = gson.fromJson(userMfoodyPOJOJsonObject, UserMfoodyPOJO.class);
            UserMfoody newUserMfoody = newUserMfoodyPOJO.renderUserMfoody();
            UserMfoody oldUserMfoody = userMfoodyInterfaceService.getUserMfoodyByID(newUserMfoodyPOJO.getIdUser());
            if (oldUserMfoody == null) {
                return new ResponseEntity<>(
                        "NOT_FOUND UserMfoody with ID: " + newUserMfoodyPOJO.getIdUser(), HttpStatus.NOT_FOUND);
            }

            // Save to DB (Handle Exception in case the unique attributes in the request already exist)
            try {
                userMfoodyInterfaceService.updateUserMfoody(newUserMfoody);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        "BAD_REQUEST Failed to update UserMfoody with ID: " + newUserMfoodyPOJO.getIdUser());
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred while editing UserMfoody");
            log.error("Detail Error: " + e);
            return new ResponseEntity<>("BAD_REQUEST Something Wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(URL_ADD) // idUser is ignored
    public ResponseEntity<?> addNewUserMfoody(@RequestBody String userMfoodyPOJOJsonObject) {
        try {
            // Convert JsonObject to UserMfoody object
            Gson gson = new Gson();
            UserMfoodyPOJO newUserMfoodyPOJO = gson.fromJson(userMfoodyPOJOJsonObject, UserMfoodyPOJO.class);

            UserMfoody newUserMfoody = newUserMfoodyPOJO.renderUserMfoody();

            // Check duplicate by emailUser/phoneNumber
            UserMfoody existingEmailUser = userMfoodyInterfaceService.getUserMfoodyByEmail(newUserMfoodyPOJO.getEmailUser());
            UserMfoody existingPhoneNumberUser = userMfoodyInterfaceService.getUserMfoodyByPhoneNumber(
                    newUserMfoodyPOJO.getPhoneNumberUser());
            if (existingEmailUser != null || existingPhoneNumberUser != null) {
                return new ResponseEntity<>(
                        "CONFLICT - A user with the same emailUser or phoneNumber already exists!",
                        HttpStatus.CONFLICT);
            }

            // Save the UserMfoody to DB (Updated Cart in DB could have ID differs from user's request)
            userMfoodyInterfaceService.saveUserMfoody(newUserMfoody);

            // Create and save a new CartMfoody (CartMfoody is automatically created when having a new UserMfoody, 1-to-1 relationship)
            CartMfoodyPOJO newCartMfoodyPOJO = new
                    CartMfoodyPOJO(0, 0, 0, 0,
                    newUserMfoodyPOJO.getIdUser());
            CartMfoody newCartMfoody = newCartMfoodyPOJO.renderCartMfoody();
            newCartMfoody.setUser(userMfoodyInterfaceService.getUserMfoodyByEmail(newUserMfoodyPOJO.getEmailUser()));
            cartMfoodyInterfaceService.saveCartMfoody(newCartMfoody);

            // Save to DB and return (New UserMfoody in DB could have ID differs from user's request)
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("An error occurred while adding UserMfoody");
            log.error("Detail Error: " + e);
            return new ResponseEntity<>("BAD_REQUEST Something Wrong!", HttpStatus.BAD_REQUEST);
        }
    }
}
