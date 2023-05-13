package com.macmie.mfoodyex.Controller;

import com.google.gson.Gson;
import com.macmie.mfoodyex.Model.Subscriber;
import com.macmie.mfoodyex.Service.InterfaceService.SubscriberInterfaceService;
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
@RequestMapping(SUBSCRIBER)
public class SubscriberController {
    @Autowired
    private SubscriberInterfaceService subscriberInterfaceService;

    @Autowired
    private ApplicationCheckAuthorController applicationCheckAuthorController;

    @Secured({ ROLE_ADMIN_SECURITY })
    @GetMapping(URL_COUNT_TOTAL)
    public ResponseEntity<?> countTotalNumberOfSubscribers(Principal principal) {
        log.info("Count Total Number of Subscribers by " + principal.getName());

        try {
            Long totalNumberOfSubscribers = subscriberInterfaceService.countTotalNumberOfSubscribers();
            return new ResponseEntity<>(totalNumberOfSubscribers, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred while counting number of Subscribers");
            log.error("Detail Error: " + e);
            // throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
            // "INTERNAL_SERVER_ERROR Exceptions occur when counting Subscribers");
            return new ResponseEntity<>("BAD_REQUEST Something Wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    @Secured({ ROLE_ADMIN_SECURITY })
    @GetMapping(URL_GET_ALL)
    public ResponseEntity<?> getAllSubscribers(Principal principal) {
        log.info("Get List of Subscribers by " + principal.getName());
        List<Subscriber> SubscriberList = subscriberInterfaceService.getListSubscribers();
        if (SubscriberList.isEmpty()) {
            return new ResponseEntity<>("NO_CONTENT List of Subscribers", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(SubscriberList, HttpStatus.OK);
    }

    @Secured({ ROLE_ADMIN_SECURITY })
    @GetMapping(URL_GET_BY_ID)
    public ResponseEntity<?> getSubscriberByID(@PathVariable("ID") int ID, Principal principal) {
        log.info("Get Subscriber with ID: {} by {}", ID, principal.getName());
        Subscriber Subscriber = subscriberInterfaceService.getSubscriberByID(ID);
        if (Subscriber == null) {
            return new ResponseEntity<>("NOT_FOUND Subscriber with ID: " + ID, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(Subscriber, HttpStatus.OK);
    }

    @Secured({ ROLE_ADMIN_SECURITY })
    @DeleteMapping(URL_DELETE)
    public ResponseEntity<?> deleteSubscriberByID(@PathVariable("ID") int ID, Principal principal) {
        log.info("Delete Subscriber with ID: {} by {}", ID, principal.getName());
        if (subscriberInterfaceService.getSubscriberByID(ID) == null) {
            return new ResponseEntity<>("NOT_FOUND Subscriber with ID: " + ID, HttpStatus.NOT_FOUND);
        }

        try {
            subscriberInterfaceService.deleteSubscriberByID(ID);
        } catch (Exception e) {
            log.error("An error occurred while deleting Subscriber with ID: " + ID);
            log.error("Detail Error: " + e);
            // throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
            // "INTERNAL_SERVER_ERROR Exceptions occur when deleting Subscriber");
            return new ResponseEntity<>("BAD_REQUEST Something Wrong!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Secured({ ROLE_ADMIN_SECURITY })
    @PutMapping(URL_EDIT) // idSubscriber in Json must be accurate
    public ResponseEntity<?> editSubscriber(@RequestBody String SubscriberJsonObject, Principal principal) {
        try {
            // Convert JsonObject to Subscriber object, Save to DB and return
            Subscriber newSubscriber = this.convertJsonToSubscriber(SubscriberJsonObject);

            // Check idSubscriber
            if (subscriberInterfaceService.getSubscriberByID(newSubscriber.getIdSubscriber()) == null) {
                return new ResponseEntity<>(
                        "NOT_FOUND Subscriber with ID: " + newSubscriber.getIdSubscriber(),
                        HttpStatus.NOT_FOUND);
            }

            subscriberInterfaceService.updateSubscriber(newSubscriber);
            log.info("Subscriber with ID: {} by {} is edited", newSubscriber.getIdSubscriber(), principal.getName());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred while editing Subscriber");
            log.error("Detail Error: " + e);
            return new ResponseEntity<>("BAD_REQUEST Something Wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(URL_ADD) // idSubscriber in Json is ignored
    public ResponseEntity<?> addNewSubscriber(@RequestBody String SubscriberJsonObject) {
        try {
            // Convert JsonObject to Subscriber object (Updated Cart in DB could have ID
            // differs from user's request)
            Subscriber newSubscriber = this.convertJsonToSubscriber(SubscriberJsonObject);

            // Check duplicate by emailUser and contentSubscriber
            if (subscriberInterfaceService.getSubscriberByEmail(newSubscriber.getEmailSubscriber()) != null) {
                return new ResponseEntity<>(
                        "CONFLICT - A Subscriber with the same email already exists!",
                        HttpStatus.CONFLICT);
            }

            subscriberInterfaceService.saveSubscriber(newSubscriber);
            log.info("A new Subscriber is created!");
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("An error occurred while adding Subscriber");
            log.error("Detail Error: " + e);
            return new ResponseEntity<>("BAD_REQUEST Something Wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public Subscriber convertJsonToSubscriber(String SubscriberJsonObject) {
        Gson gson = new Gson();
        return gson.fromJson(SubscriberJsonObject, Subscriber.class);
    }
}
