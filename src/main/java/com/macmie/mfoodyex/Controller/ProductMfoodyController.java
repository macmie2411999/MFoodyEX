package com.macmie.mfoodyex.Controller;

import com.google.gson.Gson;
import com.macmie.mfoodyex.Model.CartMfoody;
import com.macmie.mfoodyex.Model.DetailProductCartMfoody;
import com.macmie.mfoodyex.Model.ProductMfoody;
import com.macmie.mfoodyex.POJO.ProductMfoodyPOJO;
import com.macmie.mfoodyex.Service.InterfaceService.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.macmie.mfoodyex.Constant.ViewConstant.*;

/*
 * be used when the requested resource cannot be found (null): HttpStatus.NOT_FOUND (404)
 * be used when a successful request returns no content (empty): HttpStatus.NO_CONTENT (204)
 * be used when the request is invalid or contains incorrect parameters: HttpStatus.BAD_REQUEST (400)
 * */

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
            return new ResponseEntity<>("NO_CONTENT List of ProductMfoodys", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productMfoodyList, HttpStatus.OK);
    }

    @GetMapping(URL_GET_BY_ID)
    public ResponseEntity<?> getProductMfoodyByID(@PathVariable("ID") int ID) {
        ProductMfoody productMfoody = productMfoodyInterfaceService.getProductMfoodyByID(ID);
        if (productMfoody == null) {
            return new ResponseEntity<>("NOT_FOUND ProductMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productMfoody, HttpStatus.OK);
    }

    @DeleteMapping(URL_DELETE)
    public ResponseEntity<?> deleteProductMfoodyByID(@PathVariable("ID") int ID) {
        if (productMfoodyInterfaceService.getProductMfoodyByID(ID) == null) {
            return new ResponseEntity<>("NOT_FOUND ProductMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }

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
        ProductMfoody oldProductMfoody = productMfoodyInterfaceService.
                getProductMfoodyByID(newProductMfoodyPOJO.getIdProduct());
        if (oldProductMfoody == null) {
            return new ResponseEntity<>(
                    "NOT_FOUND ProductMfoody with ID: " + newProductMfoodyPOJO.getIdProduct(),
                    HttpStatus.NOT_FOUND);
        }

        // Save to DB
        productMfoodyInterfaceService.updateProductMfoody(newProductMfoody);

        /*
        * 1. Update price in DetailProductCart, CartMfoody if the price changes
        * 2. There is no need to update in OrderMfoody or DetailProductOrder coz price Order will not be change accordingly
        * */
        if (newProductMfoody.getSalePriceProduct() != oldProductMfoody.getSalePriceProduct()
                || newProductMfoody.getFullPriceProduct() != oldProductMfoody.getFullPriceProduct()) {
            List<DetailProductCartMfoody> detailProductCartMfoodyList =
                    detailProductCartMfoodyInterfaceService.findAllDetailProductCartsMfoodyByIdProduct(
                            newProductMfoody.getIdProduct());
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

        // Check duplicate by nameProduct/albumProduct
        ProductMfoody existingNameProduct = productMfoodyInterfaceService.
                getProductMfoodyByNameProduct(newProductMfoody.getNameProduct());
        ProductMfoody existingAlbumProduct = productMfoodyInterfaceService.
                getProductMfoodyByAlbumProduct(newProductMfoody.getAlbumProduct());
        if (existingNameProduct != null || existingAlbumProduct != null) {
            return new ResponseEntity<>(
                    "CONFLICT - A product with the same nameProduct or albumProduct already exists!",
                    HttpStatus.CONFLICT);
        }

        // Save to DB and return (Updated Cart in DB could have ID differs from user's request)
        productMfoodyInterfaceService.saveProductMfoody(newProductMfoody);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
