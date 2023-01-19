package com.macmie.mfoodyex.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.macmie.mfoodyex.Model.CreditCardMfoody;
import com.macmie.mfoodyex.Model.FeedbackMail;
import com.macmie.mfoodyex.Service.InterfaceService.CreditCardMfoodyInterfaceService;
import com.macmie.mfoodyex.Service.InterfaceService.FeedbackMailInterfaceService;
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

    @GetMapping(URL_GET_ALL)
    public ResponseEntity<?> getAllCreditCardMfoodys(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<CreditCardMfoody> creditCardMfoodyList = creditCardMfoodyInterfaceService.getListCreditCardMfoodys();
        if(creditCardMfoodyList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(gson.toJson(creditCardMfoodyList), HttpStatus.OK);
    }

    @GetMapping(URL_GET_BY_ID)
    public ResponseEntity<?> getCreditCardMfoodyByID(@PathVariable("ID") int ID){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        CreditCardMfoody creditCardMfoody = creditCardMfoodyInterfaceService.getCreditCardMfoodyByID(ID);
        if(creditCardMfoody == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(gson.toJson(creditCardMfoody), HttpStatus.OK);
    }

    @DeleteMapping(URL_DELETE)
    public ResponseEntity<?> deleteCreditCardMfoodyByID(@PathVariable("ID") int ID){
        creditCardMfoodyInterfaceService.deleteCreditCardMfoodyByID(ID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Error
    @PutMapping(URL_EDIT)
    public ResponseEntity<?> editFeedbackMail(@RequestBody String creditCardMfoodyJsonObject, BindingResult errors){
        // Check Error
        if(errors.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to CreditCardMfoody object
        Gson gson = new Gson();
        CreditCardMfoody newCreditCardMfoody = gson.fromJson(creditCardMfoodyJsonObject, CreditCardMfoody.class);
        System.out.println("-------- JSon: " + creditCardMfoodyJsonObject);
        System.out.println("-------- Convert from JSon: " + newCreditCardMfoody);

        // Save to DB
        creditCardMfoodyInterfaceService.updateCreditCardMfoody(newCreditCardMfoody);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(URL_ADD)
    public ResponseEntity<?> addNewFeedbackMail(@RequestBody String creditCardMfoodyJsonObject, BindingResult errors){
        // Check Error
        if(errors.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to CreditCardMfoody object
        Gson gson = new Gson();
        CreditCardMfoody newCreditCardMfoody = gson.fromJson(creditCardMfoodyJsonObject, CreditCardMfoody.class);
        System.out.println("-------- JSon: " + creditCardMfoodyJsonObject);
        System.out.println("-------- Convert from JSon: " + newCreditCardMfoody);

        // Save to DB
        creditCardMfoodyInterfaceService.saveCreditCardMfoody(newCreditCardMfoody);
        return new ResponseEntity<>(newCreditCardMfoody, HttpStatus.CREATED);
    }
}
