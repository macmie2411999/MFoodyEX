package com.macmie.mfoodyex.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.macmie.mfoodyex.Model.FeedbackMail;
import com.macmie.mfoodyex.POJO.FeedbackMailPOJO;
import com.macmie.mfoodyex.Service.InterfaceService.FeedbackMailInterfaceService;
import com.macmie.mfoodyex.Util.CheckNullAPIRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.macmie.mfoodyex.Constant.ViewConstant.*;

@RestController // = @ResponseBody + @Controller
@RequestMapping(FEEDBACK_MAIL)
public class FeedbackMailController {
    @Autowired
    private FeedbackMailInterfaceService feedbackMailInterfaceService;

    @GetMapping(URL_GET_ALL)
    public ResponseEntity<?> getAllFeedbackMails(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<FeedbackMail> feedbackMailList = feedbackMailInterfaceService.getListFeedbackMails();
        if(feedbackMailList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(gson.toJson(feedbackMailList), HttpStatus.OK);
    }

    @GetMapping(URL_GET_BY_ID)
    public ResponseEntity<?> getFeedbackMailByID(@PathVariable("ID") int ID){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FeedbackMail feedbackMail = feedbackMailInterfaceService.getFeedbackMailByID(ID);
        if(feedbackMail == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(gson.toJson(feedbackMail), HttpStatus.OK);
    }

    @DeleteMapping(URL_DELETE)
    public ResponseEntity<?> deleteFeedbackMailByID(@PathVariable("ID") int ID){
        feedbackMailInterfaceService.deleteFeedbackMailByID(ID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Error
    @PutMapping(URL_EDIT)
    public ResponseEntity<?> editFeedbackMail(@RequestBody String feedbackMailJsonObject, BindingResult errors){
        // Check Error
        if(errors.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to FeedbackMail object
        Gson gson = new Gson();
        FeedbackMail newFeedbackMail = gson.fromJson(feedbackMailJsonObject, FeedbackMail.class);
        System.out.println("-------- JSon: " + feedbackMailJsonObject);
        System.out.println("-------- Convert from JSon: " + newFeedbackMail);

        // Save to DB
        feedbackMailInterfaceService.updateFeedbackMail(newFeedbackMail);
        return new ResponseEntity<>(newFeedbackMail, HttpStatus.CREATED);
    }

    @PostMapping(URL_ADD)
    public ResponseEntity<?> addNewFeedbackMail(@RequestBody String feedbackMailJsonObject, BindingResult errors){
        // Check Error
        if(errors.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to FeedbackMail object
        Gson gson = new Gson();
        FeedbackMail newFeedbackMail = gson.fromJson(feedbackMailJsonObject, FeedbackMail.class);
        System.out.println("-------- JSon: " + feedbackMailJsonObject);
        System.out.println("-------- Convert from JSon: " + newFeedbackMail);

        // Save to DB
        feedbackMailInterfaceService.saveFeedbackMail(newFeedbackMail);
        return new ResponseEntity<>(newFeedbackMail, HttpStatus.CREATED);
    }
}
