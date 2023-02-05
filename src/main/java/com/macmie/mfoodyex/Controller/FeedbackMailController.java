package com.macmie.mfoodyex.Controller;

import com.google.gson.Gson;
import com.macmie.mfoodyex.Model.FeedbackMail;
import com.macmie.mfoodyex.Service.InterfaceService.FeedbackMailInterfaceService;
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
@RequestMapping(FEEDBACK_MAIL)
public class FeedbackMailController {
    @Autowired
    private FeedbackMailInterfaceService feedbackMailInterfaceService;

    @GetMapping(URL_GET_ALL)
    public ResponseEntity<?> getAllFeedbackMails() {
        List<FeedbackMail> feedbackMailList = feedbackMailInterfaceService.getListFeedbackMails();
        if (feedbackMailList.isEmpty()) {
            return new ResponseEntity<>("NO_CONTENT List of FeedbackMails", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(feedbackMailList, HttpStatus.OK);
    }

    @GetMapping(URL_GET_BY_ID)
    public ResponseEntity<?> getFeedbackMailByID(@PathVariable("ID") int ID) {
        FeedbackMail feedbackMail = feedbackMailInterfaceService.getFeedbackMailByID(ID);
        if (feedbackMail == null) {
            return new ResponseEntity<>("NOT_FOUND FeedbackMail with ID: " + ID, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(feedbackMail, HttpStatus.OK);
    }

    @DeleteMapping(URL_DELETE)
    public ResponseEntity<?> deleteFeedbackMailByID(@PathVariable("ID") int ID) {
        if (feedbackMailInterfaceService.getFeedbackMailByID(ID) == null) {
            return new ResponseEntity<>("NOT_FOUND FeedbackMail with ID: " + ID, HttpStatus.NOT_FOUND);
        }
        feedbackMailInterfaceService.deleteFeedbackMailByID(ID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(URL_EDIT)
    public ResponseEntity<?> editFeedbackMail(@RequestBody String feedbackMailJsonObject, BindingResult errors) {
        // Check Error
        if (errors.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to FeedbackMail object, Save to DB and return
        FeedbackMail newFeedbackMail = this.convertJsonToFeedbackMail(feedbackMailJsonObject);
        feedbackMailInterfaceService.updateFeedbackMail(newFeedbackMail);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(URL_ADD)
    public ResponseEntity<?> addNewFeedbackMail(@RequestBody String feedbackMailJsonObject, BindingResult errors) {
        // Check Error
        if (errors.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to FeedbackMail object, Save to DB and return (Updated Cart in DB could have ID differs from user's request)
        FeedbackMail newFeedbackMail = this.convertJsonToFeedbackMail(feedbackMailJsonObject);
        feedbackMailInterfaceService.saveFeedbackMail(newFeedbackMail);
        return new ResponseEntity<>(newFeedbackMail, HttpStatus.CREATED);
    }

    public FeedbackMail convertJsonToFeedbackMail(String feedbackMailJsonObject) {
        Gson gson = new Gson();
        return gson.fromJson(feedbackMailJsonObject, FeedbackMail.class);
    }
}
