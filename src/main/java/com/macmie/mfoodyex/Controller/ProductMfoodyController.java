package com.macmie.mfoodyex.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.macmie.mfoodyex.Model.CartMfoody;
import com.macmie.mfoodyex.Model.ProductMfoody;
import com.macmie.mfoodyex.Model.UserMfoody;
import com.macmie.mfoodyex.POJO.CartMfoodyPOJO;
import com.macmie.mfoodyex.POJO.ProductMfoodyPOJO;
import com.macmie.mfoodyex.POJO.UserMfoodyPOJO;
import com.macmie.mfoodyex.Service.InterfaceService.CommentMfoodyInterfaceService;
import com.macmie.mfoodyex.Service.InterfaceService.DetailProductCartMfoodyInterfaceService;
import com.macmie.mfoodyex.Service.InterfaceService.DetailProductOrderMfoodyInterfaceService;
import com.macmie.mfoodyex.Service.InterfaceService.ProductMfoodyInterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.macmie.mfoodyex.Constant.ViewConstant.*;

@RestController // = @ResponseBody + @Controller
@RequestMapping(PRODUCT_MFOODY)
public class ProductMfoodyController {
    @Autowired
    private ProductMfoodyInterfaceService productMfoodyInterfaceService;

    @Autowired
    private CommentMfoodyInterfaceService commentMfoodyInterfaceService;

    @Autowired
    private DetailProductCartMfoodyInterfaceService detailProductCartMfoodyInterfaceService;

    @Autowired
    private DetailProductOrderMfoodyInterfaceService detailProductOrderMfoodyInterfaceService;

    @GetMapping(URL_GET_ALL)
    public ResponseEntity<?> getAllProductMfoodys(){
        List<ProductMfoody> productMfoodyList = productMfoodyInterfaceService.getListProductMfoodys();
        if(productMfoodyList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productMfoodyList, HttpStatus.OK);
    }

    @GetMapping(URL_GET_BY_ID)
    public ResponseEntity<?> getProductMfoodyByID(@PathVariable("ID") int ID){
        ProductMfoody productMfoody = productMfoodyInterfaceService.getProductMfoodyByID(ID);
        if(productMfoody == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productMfoody, HttpStatus.OK);
    }

    @DeleteMapping(URL_DELETE)
    public ResponseEntity<?> deleteProductMfoodyByID(@PathVariable("ID") int ID){
        // Delete Detail Product Cart, Detail Product Order, Comment and Product
        detailProductCartMfoodyInterfaceService.deleteAllDetailProductCartsMfoodyByIdProduct(ID);
        detailProductOrderMfoodyInterfaceService.deleteAllDetailProductOrdersMfoodyByIdProduct(ID);
        commentMfoodyInterfaceService.deleteAllCommentsMfoodyByIdProduct(ID);
        productMfoodyInterfaceService.deleteProductMfoodyByID(ID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(URL_EDIT)
    public ResponseEntity<?> editProductMfoody(@RequestBody String productMfoodyPOJOJsonObject, BindingResult errors){
        // Check Error
        if(errors.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to ProductMfoodyPOJO object
        Gson gson = new Gson();
        ProductMfoodyPOJO newProductMfoodyPOJO = gson.fromJson(productMfoodyPOJOJsonObject, ProductMfoodyPOJO.class);
        ProductMfoody newProductMfoody = newProductMfoodyPOJO.renderProductMfoody();
        System.out.println("-------- JSon: " + productMfoodyPOJOJsonObject);
        System.out.println("-------- Convert from JSon: " + newProductMfoody.toString());

        // Save to DB
        productMfoodyInterfaceService.updateProductMfoody(newProductMfoody);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(URL_ADD)
    public ResponseEntity<?> addNewProductMfoody(@RequestBody String productMfoodyPOJOJsonObject, BindingResult errors){
        // Check Error
        if(errors.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to ProductMfoody object
        Gson gson = new Gson();
        ProductMfoodyPOJO newProductMfoodyPOJO = gson.fromJson(productMfoodyPOJOJsonObject, ProductMfoodyPOJO.class);
        ProductMfoody newProductMfoody = newProductMfoodyPOJO.renderProductMfoody();
        System.out.println("-------- JSon: " + productMfoodyPOJOJsonObject);
        System.out.println("-------- Convert from JSon: " + newProductMfoody.toString());

        // Check duplicate
        ProductMfoody existingNameProduct = productMfoodyInterfaceService.getProductMfoodyByNameProduct(newProductMfoody.getNameProduct());
        ProductMfoody existingAlbumProduct = productMfoodyInterfaceService.getProductMfoodyByAlbumProduct(newProductMfoody.getAlbumProduct());
        if (existingNameProduct != null || existingAlbumProduct != null) {
            return new ResponseEntity<>("A product with the same name or album already exists!", HttpStatus.CONFLICT);
        }

        // Save the Product to DB
        productMfoodyInterfaceService.saveProductMfoody(newProductMfoody);

        return new ResponseEntity<>(newProductMfoody, HttpStatus.CREATED);
    }
}
