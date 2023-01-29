package com.macmie.mfoodyex.Controller;

import com.google.gson.Gson;
import com.macmie.mfoodyex.Model.CreditCardMfoody;
import com.macmie.mfoodyex.POJO.CreditCardMfoodyPOJO;
import com.macmie.mfoodyex.Service.InterfaceService.CreditCardMfoodyInterfaceService;
import com.macmie.mfoodyex.Service.InterfaceService.UserMfoodyInterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.macmie.mfoodyex.Constant.ViewConstant.*;

@RestController // = @ResponseBody + @Controller
@RequestMapping(CREDIT_CARD_MFOODY)
public class CreditCardMfoodyController {
    @Autowired
    private CreditCardMfoodyInterfaceService creditCardMfoodyInterfaceService;

    @Autowired
    private UserMfoodyInterfaceService userMfoodyInterfaceService;

    @GetMapping(URL_GET_ALL)
    public ResponseEntity<List<CreditCardMfoody>> getAllCreditCardMfoodys(){
        List<CreditCardMfoody> creditCardMfoodyList = creditCardMfoodyInterfaceService.getListCreditCardMfoodys();
        if(creditCardMfoodyList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(creditCardMfoodyList, HttpStatus.OK);
    }

    @GetMapping(URL_GET_BY_ID)
    public ResponseEntity<CreditCardMfoody> getCreditCardMfoodyByID(@PathVariable("ID") int ID){
        CreditCardMfoody creditCardMfoody = creditCardMfoodyInterfaceService.getCreditCardMfoodyByID(ID);
        if(creditCardMfoody == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        System.out.println(creditCardMfoody.getNameUserCard());
        return new ResponseEntity<>(creditCardMfoody, HttpStatus.OK);
    }

    @DeleteMapping(URL_DELETE)
    public ResponseEntity<?> deleteCreditCardMfoodyByID(@PathVariable("ID") int ID){
        creditCardMfoodyInterfaceService.deleteCreditCardMfoodyByID(ID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Error
    @PutMapping(URL_EDIT)
    public ResponseEntity<?> editCreditCardMfoody(@RequestBody String creditCardMPOJOJsonObject, BindingResult errors){
        // Check Error
        if(errors.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to CreditCardPOJO object, add new User to CreditCard
        Gson gson = new Gson();
        CreditCardMfoodyPOJO newCreditCardPOJO = gson.fromJson(creditCardMPOJOJsonObject, CreditCardMfoodyPOJO.class);
        CreditCardMfoody newCreditCardMfoody = newCreditCardPOJO.renderCreditCardMfoody();

        // Check duplicate
        CreditCardMfoody existingCard = creditCardMfoodyInterfaceService.getCreditCardMfoodyByNumberCard(newCreditCardMfoody.getNumberCard());
        if (existingCard != null) {
            return new ResponseEntity<>("A card with the same number already exists!", HttpStatus.CONFLICT);
        }

        // Add new User to CreditCard and log
        newCreditCardMfoody.setUser(userMfoodyInterfaceService.getUserMfoodyByID(newCreditCardPOJO.getIdUser()));
        System.out.println("-------- JSon: " + creditCardMPOJOJsonObject);
        System.out.println("-------- Convert from JSon: " + newCreditCardMfoody.getIdCard());

        // Save to DB
        creditCardMfoodyInterfaceService.updateCreditCardMfoody(newCreditCardMfoody);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(URL_ADD)
    public ResponseEntity<?> addNewCreditCardMfoody(@RequestBody String creditCardMPOJOJsonObject, BindingResult errors){
        // Check Error
        if(errors.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to CreditCardPOJO object, add new User to CreditCard
        Gson gson = new Gson();
        CreditCardMfoodyPOJO newCreditCardPOJO = gson.fromJson(creditCardMPOJOJsonObject, CreditCardMfoodyPOJO.class);
        CreditCardMfoody newCreditCardMfoody = newCreditCardPOJO.renderCreditCardMfoody();

        // Check duplicate
        CreditCardMfoody existingCard = creditCardMfoodyInterfaceService.getCreditCardMfoodyByNumberCard(newCreditCardMfoody.getNumberCard());
        if (existingCard != null) {
            return new ResponseEntity<>("A card with the same number already exists!", HttpStatus.CONFLICT);
        }

        // Add new User to CreditCard and log
        newCreditCardMfoody.setUser(userMfoodyInterfaceService.getUserMfoodyByID(newCreditCardPOJO.getIdUser()));
        System.out.println("-------- JSon: " + creditCardMPOJOJsonObject);
        System.out.println("-------- Convert from JSon: " + newCreditCardMfoody.getUser().getIdUser());

        // Save CreditCard to DB
        creditCardMfoodyInterfaceService.saveCreditCardMfoody(newCreditCardMfoody);
        return new ResponseEntity<>(newCreditCardMfoody, HttpStatus.CREATED);
    }
}
