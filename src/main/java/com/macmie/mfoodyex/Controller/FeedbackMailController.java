package com.macmie.mfoodyex.Controller;

import com.google.gson.Gson;
import com.macmie.mfoodyex.Model.FeedbackMail;
import com.macmie.mfoodyex.Service.InterfaceService.FeedbackMailInterfaceService;
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
import static com.macmie.mfoodyex.Constant.ViewConstants.*;

/*
 * HttpStatus.NOT_FOUND (404): use when the requested resource cannot be found (null)
 * HttpStatus.NO_CONTENT (204): use when a successful request returns no content (empty)
 * HttpStatus.BAD_REQUEST (400): use when the request is invalid or contains incorrect parameters
 * */

@Slf4j
@Transactional
@RestController // = @ResponseBody + @Controller
@RequestMapping(FEEDBACK_MAIL)
public class FeedbackMailController {
    @Autowired
    private FeedbackMailInterfaceService feedbackMailInterfaceService;

    @Autowired
    private ApplicationCheckAuthorController applicationCheckAuthorController;

    @Secured({ROLE_ADMIN_SECURITY})
    @GetMapping(URL_GET_ALL)
    public ResponseEntity<?> getAllFeedbackMails(Principal principal) {
        log.info("Get List of FeedbackMails by " + principal.getName());
        List<FeedbackMail> feedbackMailList = feedbackMailInterfaceService.getListFeedbackMails();
        if (feedbackMailList.isEmpty()) {
            return new ResponseEntity<>("NO_CONTENT List of FeedbackMails", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(feedbackMailList, HttpStatus.OK);
    }

    @Secured({ROLE_ADMIN_SECURITY})
    @GetMapping(URL_GET_BY_ID)
    public ResponseEntity<?> getFeedbackMailByID(@PathVariable("ID") int ID, Principal principal) {
        log.info("Get FeedbackMail with ID: {} by {}", ID, principal.getName());
        FeedbackMail feedbackMail = feedbackMailInterfaceService.getFeedbackMailByID(ID);
        if (feedbackMail == null) {
            return new ResponseEntity<>("NOT_FOUND FeedbackMail with ID: " + ID, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(feedbackMail, HttpStatus.OK);
    }

    @Secured({ROLE_ADMIN_SECURITY})
    @DeleteMapping(URL_DELETE)
    public ResponseEntity<?> deleteFeedbackMailByID(@PathVariable("ID") int ID, Principal principal) {
        log.info("Delete FeedbackMail with ID: {} by {}", ID, principal.getName());
        if (feedbackMailInterfaceService.getFeedbackMailByID(ID) == null) {
            return new ResponseEntity<>("NOT_FOUND FeedbackMail with ID: " + ID, HttpStatus.NOT_FOUND);
        }

        try {
            feedbackMailInterfaceService.deleteFeedbackMailByID(ID);
        } catch (Exception e) {
            log.error("An error occurred while deleting FeedbackMail with ID: " + ID);
            log.error("Detail Error: " + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "INTERNAL_SERVER_ERROR Exceptions occur when deleting FeedbackMail");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Secured({ROLE_ADMIN_SECURITY})
    @PutMapping(URL_EDIT) // idFeedbackMail in Json must be accurate
    public ResponseEntity<?> editFeedbackMail(@RequestBody String feedbackMailJsonObject, Principal principal) {
        try {
            // Convert JsonObject to FeedbackMail object, Save to DB and return
            FeedbackMail newFeedbackMail = this.convertJsonToFeedbackMail(feedbackMailJsonObject);

            // Check idFeedbackMail
            if (feedbackMailInterfaceService.getFeedbackMailByID(newFeedbackMail.getIdFeedbackMail()) == null) {
                return new ResponseEntity<>(
                        "NOT_FOUND FeedbackMail with ID: " + newFeedbackMail.getIdFeedbackMail(),
                        HttpStatus.NOT_FOUND);
            }

            feedbackMailInterfaceService.updateFeedbackMail(newFeedbackMail);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred while editing FeedbackMail");
            log.error("Detail Error: " + e);
            return new ResponseEntity<>("BAD_REQUEST Something Wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(URL_ADD) // idFeedbackMail in Json is ignored
    public ResponseEntity<?> addNewFeedbackMail(@RequestBody String feedbackMailJsonObject, Principal principal) {
        try {
            // Convert JsonObject to FeedbackMail object (Updated Cart in DB could have ID differs from user's request)
            FeedbackMail newFeedbackMail = this.convertJsonToFeedbackMail(feedbackMailJsonObject);

            // Check duplicate by emailUser and contentFeedbackMail
            if (feedbackMailInterfaceService.getFeedbackMailByEmailUserAndContentFeedbackMail(
                    newFeedbackMail.getEmailUserFeedbackMail(),
                    newFeedbackMail.getContentFeedbackMail()) != null) {
                return new ResponseEntity<>(
                        "CONFLICT - A FeedbackMail with the same emailUser and content already exists!",
                        HttpStatus.CONFLICT);
            }

            feedbackMailInterfaceService.saveFeedbackMail(newFeedbackMail);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("An error occurred while adding FeedbackMail");
            log.error("Detail Error: " + e);
            return new ResponseEntity<>("BAD_REQUEST Something Wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public FeedbackMail convertJsonToFeedbackMail(String feedbackMailJsonObject) {
        Gson gson = new Gson();
        return gson.fromJson(feedbackMailJsonObject, FeedbackMail.class);
    }
}
