package com.macmie.mfoodyex.Controller;

import com.google.gson.Gson;
import com.macmie.mfoodyex.Model.CommentMfoody;
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
        System.out.println(commentMfoody.getIdComment());
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

    @PutMapping(URL_EDIT)
    public ResponseEntity<?> editCommentMfoody(@RequestBody String commentPOJOJsonObject, BindingResult errors){
        // Check Error
        if(errors.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to CommentPOJO object
        Gson gson = new Gson();
        CommentMfoodyPOJO newCommentMfoodyPOJO = gson.fromJson(commentPOJOJsonObject, CommentMfoodyPOJO.class);
        CommentMfoody newCommentMfoody = newCommentMfoodyPOJO.renderCommentMfoody();

        // Add new User and Product to Comment and log
        newCommentMfoody.setUser(userMfoodyInterfaceService.getUserMfoodyByID(newCommentMfoodyPOJO.getIdUser()));
        newCommentMfoody.setProduct(productMfoodyInterfaceService.getProductMfoodyByID(newCommentMfoodyPOJO.getIdProduct()));
        System.out.println("-------- JSon: " + commentPOJOJsonObject);
        System.out.println("-------- Convert from JSon: " + newCommentMfoody.getIdComment());

        // Save to DB
        commentMfoodyInterfaceService.updateCommentMfoody(newCommentMfoody);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(URL_ADD)
    public ResponseEntity<?> addNewCommentMfoody(@RequestBody String commentPOJOJsonObject, BindingResult errors){
        // Check Error
        if(errors.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to CommentPOJO object
        Gson gson = new Gson();
        CommentMfoodyPOJO newCommentMfoodyPOJO = gson.fromJson(commentPOJOJsonObject, CommentMfoodyPOJO.class);
        CommentMfoody newCommentMfoody = newCommentMfoodyPOJO.renderCommentMfoody();

        // Add new User and Product to Comment and log
        newCommentMfoody.setUser(userMfoodyInterfaceService.getUserMfoodyByID(newCommentMfoodyPOJO.getIdUser()));
        newCommentMfoody.setProduct(productMfoodyInterfaceService.getProductMfoodyByID(newCommentMfoodyPOJO.getIdProduct()));
        System.out.println("-------- JSon: " + commentPOJOJsonObject);
        System.out.println("-------- Convert from JSon: " + newCommentMfoody.getProduct().getIdProduct());

        // Save Comment to DB
        commentMfoodyInterfaceService.saveCommentMfoody(newCommentMfoody);
        return new ResponseEntity<>(newCommentMfoody, HttpStatus.CREATED);
    }
}
