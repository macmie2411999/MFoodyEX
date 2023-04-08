package com.macmie.mfoodyex.Controller;

import com.google.gson.Gson;
import com.macmie.mfoodyex.Model.CommentMfoody;
import com.macmie.mfoodyex.Model.ProductMfoody;
import com.macmie.mfoodyex.Model.UserMfoody;
import com.macmie.mfoodyex.POJO.CommentMfoodyPOJO;
import com.macmie.mfoodyex.Service.InterfaceService.CommentMfoodyInterfaceService;
import com.macmie.mfoodyex.Service.InterfaceService.ProductMfoodyInterfaceService;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
@RequestMapping(COMMENT_MFOODY)
public class CommentMfoodyController {
    @Autowired
    private CommentMfoodyInterfaceService commentMfoodyInterfaceService;

    @Autowired
    private UserMfoodyInterfaceService userMfoodyInterfaceService;

    @Autowired
    private ProductMfoodyInterfaceService productMfoodyInterfaceService;

    @Autowired
    private ApplicationCheckAuthorController applicationCheckAuthorController;

    @Secured({ROLE_ADMIN_SECURITY})
    @GetMapping(URL_COUNT_TOTAL)
    public ResponseEntity<?> countTotalNumberOfCommentMfoodys(Principal principal) {
        log.info("Count Total Number of CommentMfoodys by " + principal.getName());

        try {
            Long totalNumberOfCommentMfoodys = commentMfoodyInterfaceService.countTotalNumberOfCommentMfoodys();
            return new ResponseEntity<>(totalNumberOfCommentMfoodys, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred while counting number of CommentMfoodys");
            log.error("Detail Error: " + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "INTERNAL_SERVER_ERROR Exceptions occur when counting CommentMfoodys");
        }
    }

    @Secured({ROLE_ADMIN_SECURITY})
    @GetMapping(URL_GET_ALL)
    public ResponseEntity<?> getAllCommentMfoodys(Principal principal) {
        log.info("Get List of CommentMfoodys by " + principal.getName());
        List<CommentMfoody> commentMfoodyList = commentMfoodyInterfaceService.getListCommentMfoodys();
        if (commentMfoodyList.isEmpty()) {
            return new ResponseEntity<>("NO_CONTENT List of CommentMfoodys", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(commentMfoodyList, HttpStatus.OK);
    }

    @Secured({ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY})
    @GetMapping(URL_GET_BY_ID)
    public ResponseEntity<?> getCommentMfoodyByID(@PathVariable("ID") int ID, Principal principal) {
        log.info("Get CommentMfoody with ID: {} by {}", ID, principal.getName());
        CommentMfoody commentMfoody = commentMfoodyInterfaceService.getCommentMfoodyByID(ID);

        if (commentMfoody == null) {
            return new ResponseEntity<>("NOT_FOUND CommentMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }

        // Check if the current UserMfoody has role ADMIN or the owner of the CommentMfoody
        if(!applicationCheckAuthorController.checkAuthorization(principal, commentMfoody.getUser().getIdUser())){
            return  new ResponseEntity<>("FORBIDDEN Authorization failed!", HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(commentMfoody, HttpStatus.OK);
    }

    @Secured({ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY})
    @GetMapping(URL_GET_BY_ID_USER)
    public ResponseEntity<?> getAllCommentMfoodysByIdUser(@PathVariable("ID") int ID, Principal principal) {
        log.info("Get List of CommentMfoodys with idUser: {} by {}", ID, principal.getName());
        if (userMfoodyInterfaceService.getUserMfoodyByID(ID) == null) {
            return new ResponseEntity<>("NOT_FOUND UserMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }

        // Check if the current UserMfoody has role ADMIN or the owner of the CommentMfoody
        if(!applicationCheckAuthorController.checkAuthorization(principal, ID)){
            return new ResponseEntity<>("FORBIDDEN Authorization failed!", HttpStatus.FORBIDDEN);
        }

        List<CommentMfoody> commentMfoodyList = commentMfoodyInterfaceService.getListCommentMfoodysByIdUser(ID);
        if (commentMfoodyList.isEmpty()) {
            return new ResponseEntity<>("NO_CONTENT List of CommentMfoodys with idUser: " + ID,
                    HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(commentMfoodyList, HttpStatus.OK);
    }

    @Secured({ROLE_ADMIN_SECURITY})
    @GetMapping(URL_GET_BY_ID_PRODUCT)
    public ResponseEntity<?> getAllCommentMfoodysByIdProduct(@PathVariable("ID") int ID, Principal principal) {
        log.info("Get List of CommentMfoodys with idProduct: {} by {}", ID, principal.getName());
        if (productMfoodyInterfaceService.getProductMfoodyByID(ID) == null) {
            return new ResponseEntity<>("NOT_FOUND ProductMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }
        List<CommentMfoody> commentMfoodyList = commentMfoodyInterfaceService.getListCommentMfoodysByIdProduct(ID);
        if (commentMfoodyList.isEmpty()) {
            return new ResponseEntity<>("NO_CONTENT List of CommentMfoodys with idProduct: " + ID,
                    HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(commentMfoodyList, HttpStatus.OK);
    }

    @Secured({ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY})
    @DeleteMapping(URL_DELETE)
    public ResponseEntity<?> deleteCommentMfoodyByID(@PathVariable("ID") int ID, Principal principal) {
        log.info("Delete CommentMfoody with ID: {} by {}", ID, principal.getName());

        CommentMfoody commentMfoody = commentMfoodyInterfaceService.getCommentMfoodyByID(ID);
        if (commentMfoody == null) {
            return new ResponseEntity<>("NOT_FOUND CommentMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }

        // Check if the current UserMfoody has role ADMIN or the owner of the CommentMfoody
        if(!applicationCheckAuthorController.checkAuthorization(principal, commentMfoody.getUser().getIdUser())){
            return  new ResponseEntity<>("FORBIDDEN Authorization failed!", HttpStatus.FORBIDDEN);
        }

        // Save idProduct for updating associated attributes
        int idProduct = commentMfoody.getProduct().getIdProduct();

        try {
            commentMfoodyInterfaceService.deleteCommentMfoodyByID(ID);
        } catch (Exception e) {
            log.error("An error occurred while deleting CommentMfoody with ID: " + ID);
            log.error("Detail Error: " + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "INTERNAL_SERVER_ERROR Exceptions occur when deleting CommentMfoody");
        }

        // Update ratingProduct in ProductMfoody
        updateRatingProduct(idProduct);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Secured({ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY})
    @DeleteMapping(URL_DELETE_BY_ID_USER)
    public ResponseEntity<?> deleteAllCommentMfoodysByIdUser(@PathVariable("ID") int ID, Principal principal) {
        log.info("Delete List of CommentMfoodys with idUser: {} by {}", ID, principal.getName());
        if (userMfoodyInterfaceService.getUserMfoodyByID(ID) == null) {
            return new ResponseEntity<>("NOT_FOUND UserMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }

        // Check if the current UserMfoody has role ADMIN or the owner of the CommentMfoody
        if(!applicationCheckAuthorController.checkAuthorization(principal, ID)){
            return new ResponseEntity<>("FORBIDDEN Authorization failed!", HttpStatus.FORBIDDEN);
        }

        // Update ratingProduct in ProductMfoody
        List<CommentMfoody> commentMfoodyList = commentMfoodyInterfaceService.getListCommentMfoodysByIdUser(ID);
        if (commentMfoodyList.isEmpty()) {
            return new ResponseEntity<>("NO_CONTENT List of CommentMfoodys with idUser: " + ID,
                    HttpStatus.NO_CONTENT);
        }

        // Use HashSet to ensure that each ProductMfoody object is only added once
        Set<ProductMfoody> productMfoodyList = new HashSet<>();
        for (CommentMfoody element : commentMfoodyList) {
            productMfoodyList.add(element.getProduct());
        }

        try {
            commentMfoodyInterfaceService.deleteAllCommentsMfoodyByIdUser(ID);
        } catch (Exception e) {
            log.error("An error occurred while deleting List of CommentMfoodys with idUser: " + ID);
            log.error("Detail Error: " + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "INTERNAL_SERVER_ERROR Exceptions occur when deleting List of CommentMfoodys");
        }

        for (ProductMfoody element : productMfoodyList) {
            updateRatingProduct(element.getIdProduct());
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Secured({ROLE_ADMIN_SECURITY})
    @DeleteMapping(URL_DELETE_BY_ID_PRODUCT)
    public ResponseEntity<?> deleteAllCommentMfoodysByIdProduct(@PathVariable("ID") int ID, Principal principal) {
        log.info("Delete List of CommentMfoodys with idProduct: {} by {}", ID, principal.getName());
        if (productMfoodyInterfaceService.getProductMfoodyByID(ID) == null) {
            return new ResponseEntity<>("NOT_FOUND ProductMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }

        try {
            commentMfoodyInterfaceService.deleteAllCommentsMfoodyByIdProduct(ID);
        } catch (Exception e) {
            log.error("An error occurred while deleting List of CommentMfoodys with idProduct: " + ID);
            log.error("Detail Error: " + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "INTERNAL_SERVER_ERROR Exceptions occur when deleting List of CommentMfoodys");
        }

        // Update ratingProduct in ProductMfoody (Delete all comments, ratingProduct = 0)
        ProductMfoody newProductMfoody = productMfoodyInterfaceService.getProductMfoodyByID(ID);
        newProductMfoody.setRatingProduct(0);
        productMfoodyInterfaceService.saveProductMfoody(newProductMfoody);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    * 1. idUser and idProduct in Json are ignored
    * 2. idComment must be accurate
    * */
    @Secured({ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY})
    @PutMapping(URL_EDIT)
    public ResponseEntity<?> editCommentMfoody(@RequestBody String commentPOJOJsonObject, Principal principal) {
        try {
            // Convert JsonObject to CommentPOJO object, Check input idComment and update CommentMfoody
            Gson gson = new Gson();
            CommentMfoodyPOJO newCommentMfoodyPOJO = gson.fromJson(commentPOJOJsonObject, CommentMfoodyPOJO.class);
            CommentMfoody newCommentMfoody = commentMfoodyInterfaceService.
                    getCommentMfoodyByID(newCommentMfoodyPOJO.getIdComment());
            if (newCommentMfoody == null) {
                return new ResponseEntity<>("NOT_FOUND CommentMfoody with ID: " + newCommentMfoodyPOJO.getIdComment(),
                        HttpStatus.NOT_FOUND);
            }

            // Check if the current UserMfoody has role ADMIN or the owner of the CommentMfoody
            if(!applicationCheckAuthorController.checkAuthorization(principal, newCommentMfoody.getUser().getIdUser())){
                return new ResponseEntity<>("FORBIDDEN Authorization failed!", HttpStatus.FORBIDDEN);
            }

            newCommentMfoody.setRatingComment(newCommentMfoodyPOJO.getRatingComment());
            newCommentMfoody.setContentComment(newCommentMfoodyPOJO.getContentComment());
            commentMfoodyInterfaceService.updateCommentMfoody(newCommentMfoody);

            // Update ratingProduct in ProductMfoody
            updateRatingProduct(newCommentMfoodyPOJO.getIdProduct());
            log.info("CommentMfoody with ID: {} by {} is edited", newCommentMfoody.getIdComment(), principal.getName());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred while editing CommentMfoody");
            log.error("Detail Error: " + e);
            return new ResponseEntity<>("BAD_REQUEST Something Wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    /*
    * 1. The threat is any UserMfoodys can create CommentMfoody using different idUser
    * 2. Get idUser from Principal (Token) and idProduct in Json must be accurate
    * */
    @Secured({ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY})
    @PostMapping(URL_ADD)
    public ResponseEntity<?> addNewCommentMfoody(@RequestBody String commentPOJOJsonObject, Principal principal) {
        try {
            // Convert JsonObject to CommentPOJO object
            Gson gson = new Gson();
            CommentMfoodyPOJO newCommentMfoodyPOJO = gson.fromJson(commentPOJOJsonObject, CommentMfoodyPOJO.class);
            CommentMfoody newCommentMfoody = newCommentMfoodyPOJO.renderCommentMfoody();

            // Check duplicate by contentComment
            if (commentMfoodyInterfaceService.
                    getCommentMfoodyByContentComment(newCommentMfoodyPOJO.getContentComment()) != null) {
                return new ResponseEntity<>("CONFLICT - A CommentMfoody with the same contentComment already exists!",
                        HttpStatus.CONFLICT);
            }

            // Check input idUser and idProduct, attach new UserMfoody and ProductMfoody to CommentMfoody
            // UserMfoody attachUserMfoody = userMfoodyInterfaceService.getUserMfoodyByID(
            //        newCommentMfoodyPOJO.getIdUser());
            UserMfoody attachUserMfoody = userMfoodyInterfaceService.getUserMfoodyByID(
                            userMfoodyInterfaceService.getUserMfoodyByEmail(principal.getName()).getIdUser());
            ProductMfoody attachProductMfoody = productMfoodyInterfaceService.
                    getProductMfoodyByID(newCommentMfoodyPOJO.getIdProduct());
            if (attachUserMfoody == null) {
                return new ResponseEntity<>("NOT_FOUND UserMfoody with ID: " + newCommentMfoodyPOJO.getIdUser(),
                        HttpStatus.NOT_FOUND);
            } else if (attachProductMfoody == null) {
                return new ResponseEntity<>("NOT_FOUND ProductMfoody with ID: " + newCommentMfoodyPOJO.getIdProduct(),
                        HttpStatus.NOT_FOUND);
            }
            newCommentMfoody.setUser(attachUserMfoody);
            newCommentMfoody.setProduct(attachProductMfoody);

            // Save Comment to DB (Updated CommentMfoody in DB could have ID differs from user's request)
            commentMfoodyInterfaceService.saveCommentMfoody(newCommentMfoody);

            // Update ratingProduct in ProductMfoody
            updateRatingProduct(newCommentMfoodyPOJO.getIdProduct());
            log.info("A new CommentMfoody is created by " + principal.getName());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("An error occurred while adding CommentMfoody");
            log.error("Detail Error: " + e);
            return new ResponseEntity<>("BAD_REQUEST Something Wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public void updateRatingProduct(int idProduct) {
        ProductMfoody newProductMfoody = productMfoodyInterfaceService.getProductMfoodyByID(idProduct);
        List<CommentMfoody> commentMfoodyList = commentMfoodyInterfaceService.getListCommentMfoodysByIdProduct(idProduct);

        // If there is no comments, the ProductMfoody gets ratingProduct = 0
        if (commentMfoodyList.isEmpty()) {
            newProductMfoody.setRatingProduct(0);
            productMfoodyInterfaceService.saveProductMfoody(newProductMfoody);
            return;
        }

        // Otherwise, calculate the average
        float sumOfRatings = 0;
        for (CommentMfoody c : commentMfoodyList) {
            sumOfRatings += c.getRatingComment();
        }
        newProductMfoody.setRatingProduct((sumOfRatings / commentMfoodyList.size()));
        productMfoodyInterfaceService.saveProductMfoody(newProductMfoody);
    }
}
