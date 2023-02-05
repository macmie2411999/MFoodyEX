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

import java.util.List;

import static com.macmie.mfoodyex.Constant.ViewConstant.*;

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
    public ResponseEntity<List<CommentMfoody>> getAllCommentMfoodys(){
        List<CommentMfoody> commentMfoodyList = commentMfoodyInterfaceService.getListCommentMfoodys();
        if(commentMfoodyList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(commentMfoodyList, HttpStatus.OK);
    }

    @GetMapping(URL_GET_BY_ID)
    public ResponseEntity<CommentMfoody> getCommentMfoodyByID(@PathVariable("ID") int ID){
        CommentMfoody commentMfoody = commentMfoodyInterfaceService.getCommentMfoodyByID(ID);
        if(commentMfoody == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(commentMfoody, HttpStatus.OK);
    }

    @DeleteMapping(URL_DELETE)
    public ResponseEntity<?> deleteCommentMfoodyByID(@PathVariable("ID") int ID){
        commentMfoodyInterfaceService.deleteCommentMfoodyByID(ID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(URL_DELETE_BY_ID_USER)
    public ResponseEntity<?> deleteCommentMfoodyByIdUser(@PathVariable("ID") int ID){
        commentMfoodyInterfaceService.deleteAllCommentsMfoodyByIdUser(ID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(URL_DELETE_BY_ID_PRODUCT)
    public ResponseEntity<?> deleteCommentMfoodyByIdProduct(@PathVariable("ID") int ID){
        commentMfoodyInterfaceService.deleteAllCommentsMfoodyByIdProduct(ID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(URL_EDIT) // idUser and idProduct in Json are ignored
    public ResponseEntity<?> editCommentMfoody(@RequestBody String commentPOJOJsonObject, BindingResult errors){
        // Check Error
        if(errors.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to CommentPOJO object, Check input idComment and update CommentMfoody
        Gson gson = new Gson();
        CommentMfoodyPOJO newCommentMfoodyPOJO = gson.fromJson(commentPOJOJsonObject, CommentMfoodyPOJO.class);
        CommentMfoody newCommentMfoody = commentMfoodyInterfaceService.getCommentMfoodyByID(newCommentMfoodyPOJO.getIdComment());
        if (newCommentMfoody == null) {
            return new ResponseEntity<>("Can't find any CommentMfoody with ID: " + newCommentMfoodyPOJO.getIdComment(), HttpStatus.NOT_FOUND);
        }
        newCommentMfoody.setRatingComment(newCommentMfoodyPOJO.getRatingComment());
        newCommentMfoody.setContentComment(newCommentMfoodyPOJO.getContentComment());
        commentMfoodyInterfaceService.updateCommentMfoody(newCommentMfoody);

        // Update ratingProduct in ProductMfoody
        updateRatingProduct(newCommentMfoodyPOJO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(URL_ADD) // idUser and idProduct in Json must be accurate
    public ResponseEntity<?> addNewCommentMfoody(@RequestBody String commentPOJOJsonObject, BindingResult errors){
        // Check Error
        if(errors.hasErrors()){
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
            return new ResponseEntity<>("Can't find any UserMfoody with ID: " + newCommentMfoodyPOJO.getIdUser(), HttpStatus.NOT_FOUND);
        } else if (attachProductMfoody == null) {
            return new ResponseEntity<>("Can't find any ProductMfoody with ID: " + newCommentMfoodyPOJO.getIdProduct(), HttpStatus.NOT_FOUND);
        }
        newCommentMfoody.setUser(attachUserMfoody);
        newCommentMfoody.setProduct(attachProductMfoody);

        // Save Comment to DB (Updated CommentMfoody in DB could have ID differs from user's request)
        commentMfoodyInterfaceService.saveCommentMfoody(newCommentMfoody);

        // Update ratingProduct in ProductMfoody
        updateRatingProduct(newCommentMfoodyPOJO);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public void updateRatingProduct(CommentMfoodyPOJO newCommentMfoodyPOJO){
        ProductMfoody newProductMfoody = productMfoodyInterfaceService.getProductMfoodyByID(newCommentMfoodyPOJO.getIdProduct());
        List<CommentMfoody> commentMfoodyList = commentMfoodyInterfaceService.getListCommentMfoodysByIdProduct(newCommentMfoodyPOJO.getIdProduct());
        float sumOfRatings = 0;
        for (CommentMfoody c : commentMfoodyList) {
            sumOfRatings += c.getRatingComment();
        }
        newProductMfoody.setRatingProduct((sumOfRatings / commentMfoodyList.size()));
        productMfoodyInterfaceService.saveProductMfoody(newProductMfoody);
    }
}
