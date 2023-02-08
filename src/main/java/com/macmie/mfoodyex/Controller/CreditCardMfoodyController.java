package com.macmie.mfoodyex.Controller;

import com.google.gson.Gson;
import com.macmie.mfoodyex.Model.CreditCardMfoody;
import com.macmie.mfoodyex.Model.UserMfoody;
import com.macmie.mfoodyex.POJO.CreditCardMfoodyPOJO;
import com.macmie.mfoodyex.Service.InterfaceService.CreditCardMfoodyInterfaceService;
import com.macmie.mfoodyex.Service.InterfaceService.UserMfoodyInterfaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;

import static com.macmie.mfoodyex.Constant.ViewConstant.*;

/*
 * be used when the requested resource cannot be found (null): HttpStatus.NOT_FOUND (404)
 * be used when a successful request returns no content (empty): HttpStatus.NO_CONTENT (204)
 * be used when the request is invalid or contains incorrect parameters: HttpStatus.BAD_REQUEST (400)
 * */

@Slf4j
@Transactional
@RestController // = @ResponseBody + @Controller
@RequestMapping(CREDIT_CARD_MFOODY)
public class CreditCardMfoodyController {
    @Autowired
    private CreditCardMfoodyInterfaceService creditCardMfoodyInterfaceService;

    @Autowired
    private UserMfoodyInterfaceService userMfoodyInterfaceService;

    @GetMapping(URL_GET_ALL)
    public ResponseEntity<?> getAllCreditCardMfoodys() {
        List<CreditCardMfoody> creditCardMfoodyList = creditCardMfoodyInterfaceService.getListCreditCardMfoodys();
        if (creditCardMfoodyList.isEmpty()) {
            return new ResponseEntity<>("NO_CONTENT List of CreditCards", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(creditCardMfoodyList, HttpStatus.OK);
    }

    @GetMapping(URL_GET_BY_ID)
    public ResponseEntity<?> getCreditCardMfoodyByID(@PathVariable("ID") int ID) {
        CreditCardMfoody creditCardMfoody = creditCardMfoodyInterfaceService.getCreditCardMfoodyByID(ID);
        if (creditCardMfoody == null) {
            return new ResponseEntity<>("NOT_FOUND CreditCardMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(creditCardMfoody, HttpStatus.OK);
    }

    @GetMapping(URL_GET_BY_ID_USER)
    public ResponseEntity<?> getCreditCardMfoodyByIdUser(@PathVariable("ID") int ID) {
        if (userMfoodyInterfaceService.getUserMfoodyByID(ID) == null) {
            return new ResponseEntity<>("NOT_FOUND UserMfoody with idUser: " + ID, HttpStatus.NOT_FOUND);
        }
        List<CreditCardMfoody> creditCardMfoodyList = creditCardMfoodyInterfaceService.getListCreditCardMfoodysByIdUser(ID);
        if (creditCardMfoodyList.isEmpty()) {
            return new ResponseEntity<>("NO_CONTENT CreditCard of UserMfoody with ID: " + ID, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(creditCardMfoodyList, HttpStatus.OK);
    }

    @DeleteMapping(URL_DELETE)
    public ResponseEntity<?> deleteCreditCardMfoodyByID(@PathVariable("ID") int ID) {
        if (creditCardMfoodyInterfaceService.getCreditCardMfoodyByID(ID) == null) {
            return new ResponseEntity<>("NOT_FOUND CreditCardMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }

        try {
            creditCardMfoodyInterfaceService.deleteCreditCardMfoodyByID(ID);
        } catch (Exception e) {
            log.error("An error occurred while deleting CreditCardMfoody with ID: " + ID);
            log.error("Detail Error: " + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR Exceptions occur when deleting CreditCardMfoody");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(URL_DELETE_BY_ID_USER)
    public ResponseEntity<?> deleteCreditCardMfoodyByIdUser(@PathVariable("ID") int ID) {
        if (userMfoodyInterfaceService.getUserMfoodyByID(ID) == null) {
            return new ResponseEntity<>("NOT_FOUND UserMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }

        try {
            creditCardMfoodyInterfaceService.deleteAllCreditCardsMfoodyByIdUser(ID);
        } catch (Exception e) {
            log.error("An error occurred while deleting CreditCardMfoody with idUser: " + ID);
            log.error("Detail Error: " + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR Exceptions occur when deleting CreditCardMfoody");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(URL_EDIT) // idUser is ignored
    public ResponseEntity<?> editCreditCardMfoody(@RequestBody String creditCardPOJOJsonObject, BindingResult errors) {
        // Check Error
        if (errors.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to CreditCardPOJO object, Check input idCard and attach UserMfoody to CreditCardMfoody
        Gson gson = new Gson();
        CreditCardMfoodyPOJO newCreditCardPOJO = gson.fromJson(creditCardPOJOJsonObject, CreditCardMfoodyPOJO.class);
        CreditCardMfoody newCreditCardMfoody = newCreditCardPOJO.renderCreditCardMfoody();
        UserMfoody attachUserMfoody = userMfoodyInterfaceService.getUserMfoodyByIdCard(newCreditCardPOJO.getIdCard());
        if (attachUserMfoody == null) {
            return new ResponseEntity<>("NOT_FOUND UserMfoody with idCard: " + newCreditCardPOJO.getIdCard(),
                    HttpStatus.NOT_FOUND);
        }
        newCreditCardMfoody.setUser(attachUserMfoody);

        // Save to DB (Handle Exception in case the unique attributes in the request already exist)
        try {
            creditCardMfoodyInterfaceService.updateCreditCardMfoody(newCreditCardMfoody);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "BAD_REQUEST Failed to update CreditCardMfoody with ID: " + newCreditCardPOJO.getIdCard());
        }

        return new ResponseEntity<>(newCreditCardMfoody, HttpStatus.OK);
    }

    @PostMapping(URL_ADD) // idUser must be accurate
    public ResponseEntity<?> addNewCreditCardMfoody(@RequestBody String creditCardPOJOJsonObject, BindingResult errors) {
        // Check Error
        if (errors.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to CreditCardPOJO object
        Gson gson = new Gson();
        CreditCardMfoodyPOJO newCreditCardPOJO = gson.fromJson(creditCardPOJOJsonObject, CreditCardMfoodyPOJO.class);
        CreditCardMfoody newCreditCardMfoody = newCreditCardPOJO.renderCreditCardMfoody();

        // Check duplicate by numberCard
        CreditCardMfoody existingCard = creditCardMfoodyInterfaceService.
                getCreditCardMfoodyByNumberCard(newCreditCardPOJO.getNumberCard());
        if (existingCard != null) {
            return new ResponseEntity<>("CONFLICT - A CreditCardMfoody with the same numberCard already exists!",
                    HttpStatus.CONFLICT);
        }

        // Check input idUser and attach UserMfoody to CreditCardMfoody
        UserMfoody attachUserMfoody = userMfoodyInterfaceService.getUserMfoodyByID(newCreditCardPOJO.getIdUser());
        if (attachUserMfoody == null) {
            return new ResponseEntity<>("NOT_FOUND UserMfoody with ID: " + newCreditCardPOJO.getIdUser(),
                    HttpStatus.NOT_FOUND);
        }
        newCreditCardMfoody.setUser(attachUserMfoody);

        // Save CreditCard to DB and return (Updated Cart in DB could have ID differs from user's request)
        creditCardMfoodyInterfaceService.saveCreditCardMfoody(newCreditCardMfoody);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
