package com.macmie.mfoodyex.Controller;

import com.google.gson.Gson;
import com.macmie.mfoodyex.Model.CartMfoody;
import com.macmie.mfoodyex.Model.DetailProductCartMfoody;
import com.macmie.mfoodyex.Model.ProductMfoody;
import com.macmie.mfoodyex.Model.UserMfoody;
import com.macmie.mfoodyex.POJO.ProductMfoodyPOJO;
import com.macmie.mfoodyex.Service.InterfaceService.*;
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

    @Autowired
    private OrderMfoodyInterfaceService orderMfoodyInterfaceService;

    @Autowired
    private CartMfoodyInterfaceService cartMfoodyInterfaceService;

    @GetMapping(URL_GET_ALL)
    public ResponseEntity<?> getAllProductMfoodys() {
        List<ProductMfoody> productMfoodyList = productMfoodyInterfaceService.getListProductMfoodys();
        if (productMfoodyList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productMfoodyList, HttpStatus.OK);
    }

    @GetMapping(URL_GET_BY_ID)
    public ResponseEntity<?> getProductMfoodyByID(@PathVariable("ID") int ID) {
        ProductMfoody productMfoody = productMfoodyInterfaceService.getProductMfoodyByID(ID);
        if (productMfoody == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productMfoody, HttpStatus.OK);
    }

    @DeleteMapping(URL_DELETE)
    public ResponseEntity<?> deleteProductMfoodyByID(@PathVariable("ID") int ID) {
        // Delete Detail Product Cart, Detail Product Order, Comment and Product
        detailProductCartMfoodyInterfaceService.deleteAllDetailProductCartsMfoodyByIdProduct(ID);
        detailProductOrderMfoodyInterfaceService.deleteAllDetailProductOrdersMfoodyByIdProduct(ID);
        commentMfoodyInterfaceService.deleteAllCommentsMfoodyByIdProduct(ID);
        productMfoodyInterfaceService.deleteProductMfoodyByID(ID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(URL_EDIT)
    public ResponseEntity<?> editProductMfoody(@RequestBody String productMfoodyPOJOJsonObject, BindingResult errors) {
        // Check Error
        if (errors.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to ProductMfoodyPOJO object, Check the input idProduct
        Gson gson = new Gson();
        ProductMfoodyPOJO newProductMfoodyPOJO = gson.fromJson(productMfoodyPOJOJsonObject, ProductMfoodyPOJO.class);
        ProductMfoody newProductMfoody = newProductMfoodyPOJO.renderProductMfoody();
        ProductMfoody oldProductMfoody = productMfoodyInterfaceService.getProductMfoodyByID(newProductMfoodyPOJO.getIdProduct());
        if(oldProductMfoody == null){
            return new ResponseEntity<>("Can't find any ProductMfoody with ID: " + newProductMfoodyPOJO.getIdProduct(), HttpStatus.NOT_FOUND);
        }

        // Save to DB
        productMfoodyInterfaceService.updateProductMfoody(newProductMfoody);

        // Update price in Detail Product Cart, Cart if the price changes (Do not update in Order or Detail Product Order coz price Order will not be change)
        if (newProductMfoody.getSalePriceProduct() != oldProductMfoody.getSalePriceProduct()
                || newProductMfoody.getFullPriceProduct() != oldProductMfoody.getFullPriceProduct()) {
            List<DetailProductCartMfoody> detailProductCartMfoodyList = detailProductCartMfoodyInterfaceService.findAllDetailProductCartsMfoodyByIdProduct(newProductMfoody.getIdProduct());
            for (DetailProductCartMfoody element : detailProductCartMfoodyList) {
                element.setFullPriceDetailProductCart(newProductMfoody.getFullPriceProduct());
                element.setSalePriceDetailProductCart(newProductMfoody.getSalePriceProduct());
                detailProductCartMfoodyInterfaceService.updateDetailProductCartMfoody(element);

                CartMfoody updateCartMfoody = element.getCart();
                updateCartMfoody.setTotalSalePriceCart(updateCartMfoody.getTotalSalePriceCart() +
                        newProductMfoody.getSalePriceProduct() - oldProductMfoody.getSalePriceProduct());
                updateCartMfoody.setTotalFullPriceCart(updateCartMfoody.getTotalFullPriceCart() +
                        newProductMfoody.getFullPriceProduct() - oldProductMfoody.getFullPriceProduct());
                cartMfoodyInterfaceService.updateCartMfoody(updateCartMfoody);
            }
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(URL_ADD) // idProduct is ignored
    public ResponseEntity<?> addNewProductMfoody(@RequestBody String productMfoodyPOJOJsonObject, BindingResult errors) {
        // Check Error
        if (errors.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to ProductMfoody object
        Gson gson = new Gson();
        ProductMfoodyPOJO newProductMfoodyPOJO = gson.fromJson(productMfoodyPOJOJsonObject, ProductMfoodyPOJO.class);
        ProductMfoody newProductMfoody = newProductMfoodyPOJO.renderProductMfoody();

        // Check duplicate
        ProductMfoody existingNameProduct = productMfoodyInterfaceService.getProductMfoodyByNameProduct(newProductMfoody.getNameProduct());
        ProductMfoody existingAlbumProduct = productMfoodyInterfaceService.getProductMfoodyByAlbumProduct(newProductMfoody.getAlbumProduct());
        if (existingNameProduct != null || existingAlbumProduct != null) {
            return new ResponseEntity<>("A product with the same name or album already exists!", HttpStatus.CONFLICT);
        }

        // Save to DB and return (Updated Cart in DB could have ID differs from user's request)
        productMfoodyInterfaceService.saveProductMfoody(newProductMfoody);
        return new ResponseEntity<>(newProductMfoody, HttpStatus.CREATED);
    }
}
