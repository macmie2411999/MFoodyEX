package com.macmie.mfoodyex.Controller;

import com.google.gson.Gson;
import com.macmie.mfoodyex.Model.CommentMfoody;
import com.macmie.mfoodyex.Model.ProductMfoody;
import com.macmie.mfoodyex.Model.UserMfoody;
import com.macmie.mfoodyex.POJO.CommentMfoodyPOJO;
import com.macmie.mfoodyex.Service.InterfaceService.CommentMfoodyInterfaceService;
import com.macmie.mfoodyex.Service.InterfaceService.ProductMfoodyInterfaceService;
import com.macmie.mfoodyex.Service.InterfaceService.UserMfoodyInterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.macmie.mfoodyex.Constant.ViewConstant.*;

/*
 * be used when the requested resource cannot be found (null): HttpStatus.NOT_FOUND (404)
 * be used when a successful request returns no content (empty): HttpStatus.NO_CONTENT (204)
 * be used when the request is invalid or contains incorrect parameters: HttpStatus.BAD_REQUEST (400)
 * */

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
            return new ResponseEntity<>("NO_CONTENT List of CommentMfoodys with IdUser: " + ID, HttpStatus.NO_CONTENT);
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
            return new ResponseEntity<>("NO_CONTENT List of CommentMfoodys with IdProduct: " + ID, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(commentMfoodyList, HttpStatus.OK);
    }

    @DeleteMapping(URL_DELETE)
    public ResponseEntity<?> deleteCommentMfoodyByID(@PathVariable("ID") int ID) {
        if (commentMfoodyInterfaceService.getCommentMfoodyByID(ID) == null) {
            return new ResponseEntity<>("NOT_FOUND CommentMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }
        int idProduct = commentMfoodyInterfaceService.getCommentMfoodyByID(ID).getProduct().getIdProduct();
        commentMfoodyInterfaceService.deleteCommentMfoodyByID(ID);

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
            return new ResponseEntity<>("NO_CONTENT List of CommentMfoodys with IdUser: " + ID, HttpStatus.NO_CONTENT);
        }
        Set<ProductMfoody> productMfoodyList = new HashSet<>(); // Use HashSet to ensure that each ProductMfoody object is only added once
        for (CommentMfoody element : commentMfoodyList) {
            productMfoodyList.add(element.getProduct());
        }
        commentMfoodyInterfaceService.deleteAllCommentsMfoodyByIdUser(ID);
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
        commentMfoodyInterfaceService.deleteAllCommentsMfoodyByIdProduct(ID);

        // Update ratingProduct in ProductMfoody (Delete all comments, ratingProduct = 0)
        ProductMfoody newProductMfoody = productMfoodyInterfaceService.getProductMfoodyByID(ID);
        newProductMfoody.setRatingProduct(0);
        productMfoodyInterfaceService.saveProductMfoody(newProductMfoody);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(URL_EDIT) // idUser and idProduct in Json are ignored
    public ResponseEntity<?> editCommentMfoody(@RequestBody String commentPOJOJsonObject, BindingResult errors) {
        // Check Error
        if (errors.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to CommentPOJO object, Check input idComment and update CommentMfoody
        Gson gson = new Gson();
        CommentMfoodyPOJO newCommentMfoodyPOJO = gson.fromJson(commentPOJOJsonObject, CommentMfoodyPOJO.class);
        CommentMfoody newCommentMfoody = commentMfoodyInterfaceService.getCommentMfoodyByID(newCommentMfoodyPOJO.getIdComment());
        if (newCommentMfoody == null) {
            return new ResponseEntity<>("NOT_FOUND CommentMfoody with ID: " + newCommentMfoodyPOJO.getIdComment(), HttpStatus.NOT_FOUND);
        }
        newCommentMfoody.setRatingComment(newCommentMfoodyPOJO.getRatingComment());
        newCommentMfoody.setContentComment(newCommentMfoodyPOJO.getContentComment());
        commentMfoodyInterfaceService.updateCommentMfoody(newCommentMfoody);

        // Update ratingProduct in ProductMfoody
        updateRatingProduct(newCommentMfoodyPOJO.getIdProduct());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(URL_ADD) // idUser and idProduct in Json must be accurate
    public ResponseEntity<?> addNewCommentMfoody(@RequestBody String commentPOJOJsonObject, BindingResult errors) {
        // Check Error
        if (errors.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to CommentPOJO object
        Gson gson = new Gson();
        CommentMfoodyPOJO newCommentMfoodyPOJO = gson.fromJson(commentPOJOJsonObject, CommentMfoodyPOJO.class);
        CommentMfoody newCommentMfoody = newCommentMfoodyPOJO.renderCommentMfoody();

        // Check input idUser and idProduct, attach new UserMfoody and ProductMfoody to CommentMfoody
        UserMfoody attachUserMfoody = userMfoodyInterfaceService.getUserMfoodyByID(newCommentMfoodyPOJO.getIdUser());
        ProductMfoody attachProductMfoody = productMfoodyInterfaceService.getProductMfoodyByID(newCommentMfoodyPOJO.getIdProduct());
        if (attachUserMfoody == null) {
            return new ResponseEntity<>("NOT_FOUND UserMfoody with ID: " + newCommentMfoodyPOJO.getIdUser(), HttpStatus.NOT_FOUND);
        } else if (attachProductMfoody == null) {
            return new ResponseEntity<>("NOT_FOUND ProductMfoody with ID: " + newCommentMfoodyPOJO.getIdProduct(), HttpStatus.NOT_FOUND);
        }
        newCommentMfoody.setUser(attachUserMfoody);
        newCommentMfoody.setProduct(attachProductMfoody);

        // Save Comment to DB (Updated CommentMfoody in DB could have ID differs from user's request)
        commentMfoodyInterfaceService.saveCommentMfoody(newCommentMfoody);

        // Update ratingProduct in ProductMfoody
        updateRatingProduct(newCommentMfoodyPOJO.getIdProduct());

        return new ResponseEntity<>(HttpStatus.CREATED);
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
