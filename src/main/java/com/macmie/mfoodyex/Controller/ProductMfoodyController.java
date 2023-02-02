package com.macmie.mfoodyex.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.macmie.mfoodyex.Model.ProductMfoody;
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
        productMfoodyInterfaceService.deleteProductMfoodyByID(ID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(URL_EDIT)
    public ResponseEntity<?> editProductMfoody(@RequestBody String productMfoodyJsonObject, BindingResult errors){
        // Check Error
        if(errors.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to ProductMfoody object
        Gson gson = new Gson();
        ProductMfoody newProductMfoody = gson.fromJson(productMfoodyJsonObject, ProductMfoody.class);
        System.out.println("-------- JSon: " + productMfoodyJsonObject);
        System.out.println("-------- Convert from JSon: " + newProductMfoody);

        // Save to DB
        productMfoodyInterfaceService.updateProductMfoody(newProductMfoody);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(URL_ADD)
    public ResponseEntity<?> addNewProductMfoody(@RequestBody String productMfoodyJsonObject, BindingResult errors){
        // Check Error
        if(errors.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to ProductMfoody object
        Gson gson = new Gson();
        ProductMfoody newProductMfoody = gson.fromJson(productMfoodyJsonObject, ProductMfoody.class);
        System.out.println("-------- JSon: " + productMfoodyJsonObject);
        System.out.println("-------- Convert from JSon: " + newProductMfoody);

        // Save to DB
        productMfoodyInterfaceService.saveProductMfoody(newProductMfoody);
        return new ResponseEntity<>(newProductMfoody, HttpStatus.CREATED);
    }
}
