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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @GetMapping(URL_GET_ALL)
    public ResponseEntity<?> getAllCommentMfoodys() {
        List<CommentMfoody> commentMfoodyList = commentMfoodyInterfaceService.getListCommentMfoodys();
        if (commentMfoodyList.isEmpty()) {
            return new ResponseEntity<>("NO_CONTENT List of CartMfoodys", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(commentMfoodyList, HttpStatus.OK);
    }

    @GetMapping(URL_GET_BY_ID)
    public ResponseEntity<?> getCommentMfoodyByID(@PathVariable("ID") int ID) {
        CommentMfoody commentMfoody = commentMfoodyInterfaceService.getCommentMfoodyByID(ID);
        if (commentMfoody == null) {
            return new ResponseEntity<>("NOT_FOUND CommentMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(commentMfoody, HttpStatus.OK);
    }

    @GetMapping(URL_GET_BY_ID_USER)
    public ResponseEntity<?> getCommentMfoodyByIdUser(@PathVariable("ID") int ID) {
        if (userMfoodyInterfaceService.getUserMfoodyByID(ID) == null) {
            return new ResponseEntity<>("NOT_FOUND UserMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }
        List<CommentMfoody> commentMfoodyList = commentMfoodyInterfaceService.getListCommentMfoodysByIdUser(ID);
        if (commentMfoodyList.isEmpty()) {
            return new ResponseEntity<>("NO_CONTENT List of CommentMfoodys with idUser: " + ID,
                    HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(commentMfoodyList, HttpStatus.OK);
    }

    @GetMapping(URL_GET_BY_ID_PRODUCT)
    public ResponseEntity<?> getCommentMfoodyByIdProduct(@PathVariable("ID") int ID) {
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

    @DeleteMapping(URL_DELETE)
    public ResponseEntity<?> deleteCommentMfoodyByID(@PathVariable("ID") int ID) {
        if (commentMfoodyInterfaceService.getCommentMfoodyByID(ID) == null) {
            return new ResponseEntity<>("NOT_FOUND CommentMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }
        int idProduct = commentMfoodyInterfaceService.getCommentMfoodyByID(ID).getProduct().getIdProduct();


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

    @DeleteMapping(URL_DELETE_BY_ID_USER)
    public ResponseEntity<?> deleteCommentMfoodyByIdUser(@PathVariable("ID") int ID) {
        if (userMfoodyInterfaceService.getUserMfoodyByID(ID) == null) {
            return new ResponseEntity<>("NOT_FOUND UserMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
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
            log.error("An error occurred while deleting CommentMfoody with idUser: " + ID);
            log.error("Detail Error: " + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "INTERNAL_SERVER_ERROR Exceptions occur when deleting CommentMfoody");
        }

        for (ProductMfoody element : productMfoodyList) {
            updateRatingProduct(element.getIdProduct());
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(URL_DELETE_BY_ID_PRODUCT)
    public ResponseEntity<?> deleteCommentMfoodyByIdProduct(@PathVariable("ID") int ID) {
        if (productMfoodyInterfaceService.getProductMfoodyByID(ID) == null) {
            return new ResponseEntity<>("NOT_FOUND ProductMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }

        try {
            commentMfoodyInterfaceService.deleteAllCommentsMfoodyByIdProduct(ID);
        } catch (Exception e) {
            log.error("An error occurred while deleting CommentMfoody with idProduct: " + ID);
            log.error("Detail Error: " + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "INTERNAL_SERVER_ERROR Exceptions occur when deleting CommentMfoody");
        }

        // Update ratingProduct in ProductMfoody (Delete all comments, ratingProduct = 0)
        ProductMfoody newProductMfoody = productMfoodyInterfaceService.getProductMfoodyByID(ID);
        newProductMfoody.setRatingProduct(0);
        productMfoodyInterfaceService.saveProductMfoody(newProductMfoody);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(URL_EDIT) // idUser and idProduct in Json are ignored
    public ResponseEntity<?> editCommentMfoody(@RequestBody String commentPOJOJsonObject) {
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
            newCommentMfoody.setRatingComment(newCommentMfoodyPOJO.getRatingComment());
            newCommentMfoody.setContentComment(newCommentMfoodyPOJO.getContentComment());
            commentMfoodyInterfaceService.updateCommentMfoody(newCommentMfoody);

            // Update ratingProduct in ProductMfoody
            updateRatingProduct(newCommentMfoodyPOJO.getIdProduct());

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred while editing CommentMfoody");
            log.error("Detail Error: " + e);
            return new ResponseEntity<>("BAD_REQUEST Something Wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(URL_ADD) // idUser and idProduct in Json must be accurate
    public ResponseEntity<?> addNewCommentMfoody(@RequestBody String commentPOJOJsonObject) {
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
            UserMfoody attachUserMfoody = userMfoodyInterfaceService.getUserMfoodyByID(newCommentMfoodyPOJO.getIdUser());
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
