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
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;

import static com.macmie.mfoodyex.Constant.SecurityConstants.ROLE_ADMIN_SECURITY;
import static com.macmie.mfoodyex.Constant.SecurityConstants.ROLE_USER_SECURITY;
import static com.macmie.mfoodyex.Constant.ViewConstants.*;

/*
 * HttpStatus.NOT_FOUND (404): use when the requested resource cannot be found (null)
 * HttpStatus.NO_CONTENT (204): use when a successful request returns no content (empty)
 * HttpStatus.BAD_REQUEST (400): use when the request is invalid or contains incorrect parameters
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

    @Autowired
    private ApplicationCheckAuthorController applicationCheckAuthorController;

    @Secured({ROLE_ADMIN_SECURITY})
    @GetMapping(URL_GET_ALL)
    public ResponseEntity<?> getAllCreditCardMfoodys(Principal principal) {
        log.info("Get List of CreditCardMfoodys by " + principal.getName());
        List<CreditCardMfoody> creditCardMfoodyList = creditCardMfoodyInterfaceService.getListCreditCardMfoodys();
        if (creditCardMfoodyList.isEmpty()) {
            return new ResponseEntity<>("NO_CONTENT List of CreditCards", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(creditCardMfoodyList, HttpStatus.OK);
    }

    @Secured({ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY})
    @GetMapping(URL_GET_BY_ID)
    public ResponseEntity<?> getCreditCardMfoodyByID(@PathVariable("ID") int ID, Principal principal) {
        log.info("Get CreditCardMfoody with ID: {} by {}", ID, principal.getName());
        CreditCardMfoody creditCardMfoody = creditCardMfoodyInterfaceService.getCreditCardMfoodyByID(ID);
        if (creditCardMfoody == null) {
            return new ResponseEntity<>("NOT_FOUND CreditCardMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }

        // Check if the current UserMfoody has role ADMIN or the owner of the CreditCardMfoody
        if(!applicationCheckAuthorController.checkAuthorization(principal, creditCardMfoody.getUser().getIdUser())){
            return  new ResponseEntity<>("FORBIDDEN Authorization failed!", HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(creditCardMfoody, HttpStatus.OK);
    }

    @Secured({ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY})
    @GetMapping(URL_GET_BY_ID_USER)
    public ResponseEntity<?> getAllCreditCardMfoodysByIdUser(@PathVariable("ID") int ID, Principal principal) {
        log.info("Get List CreditCards with idUser: {} by {}", ID, principal.getName());
        if (userMfoodyInterfaceService.getUserMfoodyByID(ID) == null) {
            return new ResponseEntity<>("NOT_FOUND UserMfoody with idUser: " + ID, HttpStatus.NOT_FOUND);
        }

        // Check if the current UserMfoody has role ADMIN or the owner of the CreditCardMfoody
        if(!applicationCheckAuthorController.checkAuthorization(principal, ID)){
            return  new ResponseEntity<>("FORBIDDEN Authorization failed!", HttpStatus.FORBIDDEN);
        }

        List<CreditCardMfoody> creditCardMfoodyList = creditCardMfoodyInterfaceService.getListCreditCardMfoodysByIdUser(ID);
        if (creditCardMfoodyList.isEmpty()) {
            return new ResponseEntity<>("NO_CONTENT List CreditCards of UserMfoody with idUser: " + ID, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(creditCardMfoodyList, HttpStatus.OK);
    }

    @Secured({ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY})
    @DeleteMapping(URL_DELETE)
    public ResponseEntity<?> deleteCreditCardMfoodyByID(@PathVariable("ID") int ID, Principal principal) {
        log.info("Delete CreditCardMfoody with ID: {} by {}", ID, principal.getName());
        CreditCardMfoody creditCardMfoody = creditCardMfoodyInterfaceService.getCreditCardMfoodyByID(ID);
        if (creditCardMfoody == null) {
            return new ResponseEntity<>("NOT_FOUND CreditCardMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }

        // Check if the current UserMfoody has role ADMIN or the owner of the CreditCardMfoody
        if(!applicationCheckAuthorController.checkAuthorization(principal, creditCardMfoody.getUser().getIdUser())){
            return  new ResponseEntity<>("FORBIDDEN Authorization failed!", HttpStatus.FORBIDDEN);
        }

        try {
            creditCardMfoodyInterfaceService.deleteCreditCardMfoodyByID(ID);
        } catch (Exception e) {
            log.error("An error occurred while deleting CreditCardMfoody with ID: " + ID);
            log.error("Detail Error: " + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "INTERNAL_SERVER_ERROR Exceptions occur when deleting CreditCardMfoody");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Secured({ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY})
    @DeleteMapping(URL_DELETE_BY_ID_USER)
    public ResponseEntity<?> deleteAllCreditCardMfoodysByIdUser(@PathVariable("ID") int ID, Principal principal) {
        log.info("Delete List CreditCardMfoodys with idUser: {} by {}", ID, principal.getName());
        if (userMfoodyInterfaceService.getUserMfoodyByID(ID) == null) {
            return new ResponseEntity<>("NOT_FOUND UserMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }

        // Check if the current UserMfoody has role ADMIN or the owner of the CreditCardMfoody
        if(!applicationCheckAuthorController.checkAuthorization(principal, ID)){
            return  new ResponseEntity<>("FORBIDDEN Authorization failed!", HttpStatus.FORBIDDEN);
        }

        try {
            creditCardMfoodyInterfaceService.deleteAllCreditCardsMfoodyByIdUser(ID);
        } catch (Exception e) {
            log.error("An error occurred while deleting List CreditCardMfoodys with idUser: " + ID);
            log.error("Detail Error: " + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "INTERNAL_SERVER_ERROR Exceptions occur when deleting List of CommentMfoodys");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Secured({ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY})
    @PutMapping(URL_EDIT) // idUser in Json is ignored
    public ResponseEntity<?> editCreditCardMfoody(@RequestBody String creditCardPOJOJsonObject, Principal principal) {
        try {
            // Convert JsonObject to CreditCardPOJO object, Check input idCard and attach UserMfoody to CreditCardMfoody
            Gson gson = new Gson();
            CreditCardMfoodyPOJO newCreditCardPOJO = gson.fromJson(creditCardPOJOJsonObject, CreditCardMfoodyPOJO.class);
            CreditCardMfoody newCreditCardMfoody = newCreditCardPOJO.renderCreditCardMfoody();
            UserMfoody attachUserMfoody = userMfoodyInterfaceService.getUserMfoodyByIdCard(newCreditCardPOJO.getIdCard());
            if (attachUserMfoody == null) {
                return new ResponseEntity<>("NOT_FOUND UserMfoody with idCard: " + newCreditCardPOJO.getIdCard(),
                        HttpStatus.NOT_FOUND);
            }

            // Check if the current UserMfoody has role ADMIN or the owner of the CreditCardMfoody
            if(!applicationCheckAuthorController.checkAuthorization(principal, attachUserMfoody.getIdUser())){
                return  new ResponseEntity<>("FORBIDDEN Authorization failed!", HttpStatus.FORBIDDEN);
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
        } catch (Exception e) {
            log.error("An error occurred while editing CreditCardMfoody");
            log.error("Detail Error: " + e);
            return new ResponseEntity<>("BAD_REQUEST Something Wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    @Secured({ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY})
    @PostMapping(URL_ADD) // idUser in Json must be accurate
    public ResponseEntity<?> addNewCreditCardMfoody(@RequestBody String creditCardPOJOJsonObject, Principal principal) {
        try {
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
        } catch (Exception e) {
            log.error("An error occurred while adding CreditCardMfoody");
            log.error("Detail Error: " + e);
            return new ResponseEntity<>("BAD_REQUEST Something Wrong!", HttpStatus.BAD_REQUEST);
        }
    }
}
