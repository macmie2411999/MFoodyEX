package com.macmie.mfoodyex.Controller;

import com.google.gson.Gson;
import com.macmie.mfoodyex.Model.CartMfoody;
import com.macmie.mfoodyex.Model.DetailProductCartMfoody;
import com.macmie.mfoodyex.Model.DetailProductCartMfoodyId;
import com.macmie.mfoodyex.Model.ProductMfoody;
import com.macmie.mfoodyex.POJO.DetailProductCartMfoodyPOJO;
import com.macmie.mfoodyex.Service.InterfaceService.CartMfoodyInterfaceService;
import com.macmie.mfoodyex.Service.InterfaceService.DetailProductCartMfoodyInterfaceService;
import com.macmie.mfoodyex.Service.InterfaceService.ProductMfoodyInterfaceService;
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
@RequestMapping(DETAIL_PRODUCT_CART_MFOODY)
public class DetailProductCartMfoodyController {
    @Autowired
    private DetailProductCartMfoodyInterfaceService detailProductCartMfoodyInterfaceService;

    @Autowired
    private CartMfoodyInterfaceService cartMfoodyInterfaceService;

    @Autowired
    private ProductMfoodyInterfaceService productMfoodyInterfaceService;

    @GetMapping(URL_GET_ALL)
    public ResponseEntity<?> getAllDetailProductCartMfoodys() {
        List<DetailProductCartMfoody> detailProductCartMfoodyList = detailProductCartMfoodyInterfaceService.getListDetailProductCartMfoodys();
        if (detailProductCartMfoodyList.isEmpty()) {
            return new ResponseEntity<>("NO_CONTENT List of DetailProductCartMfoodys", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(detailProductCartMfoodyList, HttpStatus.OK);
    }

    @GetMapping(URL_GET_BY_ID_CART)
    public ResponseEntity<?> getAllDetailProductCartMfoodysByIdCart(@PathVariable("ID") int ID) {
        List<DetailProductCartMfoody> detailProductCartMfoodyList = detailProductCartMfoodyInterfaceService.getListDetailProductCartMfoodysByIdCart(ID);
        if (detailProductCartMfoodyList.isEmpty()) {
            return new ResponseEntity<>("NO_CONTENT List of DetailProductCartMfoodys with idCart: " + ID, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(detailProductCartMfoodyList, HttpStatus.OK);
    }

    @GetMapping(URL_GET_BY_ID_PRODUCT)
    public ResponseEntity<?> getAllDetailProductCartMfoodysByIdProduct(@PathVariable("ID") int ID) {
        List<DetailProductCartMfoody> detailProductCartMfoodyList = detailProductCartMfoodyInterfaceService.getListDetailProductCartMfoodysByIdProduct(ID);
        if (detailProductCartMfoodyList.isEmpty()) {
            return new ResponseEntity<>("NO_CONTENT List of DetailProductCartMfoodys with idProduct: " + ID, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(detailProductCartMfoodyList, HttpStatus.OK);
    }

    @GetMapping(URL_GET_BY_ID_CART_AND_ID_PRODUCT)
    public ResponseEntity<?> getDetailProductCartMfoodyByID(@PathVariable("IdCart") int idCart, @PathVariable("IdProduct") int idProduct) {
        DetailProductCartMfoody detailProductCartMfoody = detailProductCartMfoodyInterfaceService.getDetailProductCartMfoodyByICartAndIdProduct(idCart, idProduct);
        if (detailProductCartMfoody == null) {
            return new ResponseEntity<>("NOT_FOUND DetailProductCartMfoody with idCart: " + idCart + ", idProduct: " + idProduct, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(detailProductCartMfoody, HttpStatus.OK);
    }

    @DeleteMapping(URL_DELETE_BY_ID_CART_AND_ID_PRODUCT)
    public ResponseEntity<?> deleteDetailProductCartMfoodyByID(@PathVariable("IdCart") int idCart, @PathVariable("IdProduct") int idProduct) {
        DetailProductCartMfoody detailProductCartMfoody = detailProductCartMfoodyInterfaceService.getDetailProductCartMfoodyByICartAndIdProduct(idCart, idProduct);
        if (detailProductCartMfoody == null) {
            return new ResponseEntity<>("NOT_FOUND DetailProductCartMfoody with idCart: " + idCart + ", idProduct: " + idProduct, HttpStatus.NOT_FOUND);
        }
        detailProductCartMfoodyInterfaceService.deleteDetailProductCartMfoodyByIdDetailProductCartMfoody(idCart, idProduct);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(URL_DELETE_BY_ID_CART)
    public ResponseEntity<?> deleteDetailProductCartMfoodyByIdCart(@PathVariable("ID") int ID) {
        if (cartMfoodyInterfaceService.getCartMfoodyByID(ID) == null) {
            return new ResponseEntity<>("NOT_FOUND CartMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }
        detailProductCartMfoodyInterfaceService.deleteAllDetailProductCartsMfoodyByIdCart(ID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(URL_DELETE_BY_ID_PRODUCT)
    public ResponseEntity<?> deleteDetailProductCartMfoodyByIdProduct(@PathVariable("ID") int ID) {
        if (productMfoodyInterfaceService.getProductMfoodyByID(ID) == null) {
            return new ResponseEntity<>("NOT_FOUND ProductMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }
        detailProductCartMfoodyInterfaceService.deleteAllDetailProductCartsMfoodyByIdProduct(ID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    * 1. idCart and idProduct in Json must be accurate, salePrice/fullPrice in Json are ignored
    * 2. salePrice/fullPrice of DetailProductCartMfoody are fetched from ProductMfoody
    * */
    @PutMapping(URL_EDIT)
    public ResponseEntity<?> editDetailProductCartMfoody(@RequestBody String detailProductCartPOJOJsonObject, BindingResult errors) {
        // Check Error
        if (errors.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to DetailProductCartPOJO object
        Gson gson = new Gson();
        DetailProductCartMfoodyPOJO newDetailProductCartMfoodyPOJO = gson.fromJson(detailProductCartPOJOJsonObject, DetailProductCartMfoodyPOJO.class);
        DetailProductCartMfoody newDetailProductCartMfoody = newDetailProductCartMfoodyPOJO.renderDetailProductCartMfoody();

        // Check valid idCart and idProduct
        if (productMfoodyInterfaceService.getProductMfoodyByID(newDetailProductCartMfoodyPOJO.getIdProduct()) == null) {
            return new ResponseEntity<>("NOT_FOUND ProductMfoody with ID: " + newDetailProductCartMfoodyPOJO.getIdProduct(), HttpStatus.NOT_FOUND);
        }
        if (cartMfoodyInterfaceService.getCartMfoodyByID(newDetailProductCartMfoodyPOJO.getIdCart()) == null) {
            return new ResponseEntity<>("NOT_FOUND CartMfoody with ID: " + newDetailProductCartMfoodyPOJO.getIdCart(), HttpStatus.NOT_FOUND);
        }
        if (detailProductCartMfoodyInterfaceService.getDetailProductCartMfoodyByICartAndIdProduct(
                newDetailProductCartMfoodyPOJO.getIdCart(),
                newDetailProductCartMfoodyPOJO.getIdProduct()) == null) {
            return new ResponseEntity<>(
                    "NOT_FOUND DetailProductCartMfoody with idCart: " + newDetailProductCartMfoodyPOJO.getIdCart()
                            + " and idProduct: " + newDetailProductCartMfoodyPOJO.getIdProduct(), HttpStatus.NOT_FOUND);
        }

        // Update IdDetailProductCartMfoody, ProductMfoody, and CartMfoody; re-assign values for salePrice/fullPrice from ProductMfoody
        newDetailProductCartMfoody.setIdDetailProductCartMfoody(
                new DetailProductCartMfoodyId(newDetailProductCartMfoodyPOJO.getIdCart(),
                        newDetailProductCartMfoodyPOJO.getIdProduct()));
        ProductMfoody productMfoody = productMfoodyInterfaceService.getProductMfoodyByID(newDetailProductCartMfoodyPOJO.getIdProduct());
        newDetailProductCartMfoody.setProduct(productMfoody);
        newDetailProductCartMfoody.setSalePriceDetailProductCart(productMfoody.getSalePriceProduct());
        newDetailProductCartMfoody.setFullPriceDetailProductCart(productMfoody.getFullPriceProduct());
        processCartMfoody(newDetailProductCartMfoodyPOJO, newDetailProductCartMfoody);

        // Save to DB and return
        detailProductCartMfoodyInterfaceService.updateDetailProductCartMfoody(newDetailProductCartMfoody);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
     * 1. idCart and idProduct in Json must be accurate, salePrice/fullPrice in Json are ignored
     * 2. salePrice/fullPrice of DetailProductCartMfoody are fetched from ProductMfoody
     * */
    @PostMapping(URL_ADD)
    public ResponseEntity<?> addNewDetailProductCartMfoody(@RequestBody String detailProductCartPOJOJsonObject, BindingResult errors) {
        // Check Error
        if (errors.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to DetailProductCartPOJO object and Add new CartMfoody and ProductMfoody to DetailProductCart
        Gson gson = new Gson();
        DetailProductCartMfoodyPOJO newDetailProductCartMfoodyPOJO = gson.fromJson(detailProductCartPOJOJsonObject, DetailProductCartMfoodyPOJO.class);
        DetailProductCartMfoody newDetailProductCartMfoody = newDetailProductCartMfoodyPOJO.renderDetailProductCartMfoody();

        // Check valid idCart and idProduct
        if (productMfoodyInterfaceService.getProductMfoodyByID(newDetailProductCartMfoodyPOJO.getIdProduct()) == null) {
            return new ResponseEntity<>("NOT_FOUND ProductMfoody with ID: " + newDetailProductCartMfoodyPOJO.getIdProduct(), HttpStatus.NOT_FOUND);
        }
        if (cartMfoodyInterfaceService.getCartMfoodyByID(newDetailProductCartMfoodyPOJO.getIdCart()) == null) {
            return new ResponseEntity<>("NOT_FOUND CartMfoody with ID: " + newDetailProductCartMfoodyPOJO.getIdCart(), HttpStatus.NOT_FOUND);
        }

        // Add IdDetailProductCartMfoody, ProductMfoody, and CartMfoody; re-assign values for salePrice/fullPrice from ProductMfoody
        newDetailProductCartMfoody.setIdDetailProductCartMfoody(
                new DetailProductCartMfoodyId(newDetailProductCartMfoodyPOJO.getIdCart(),
                        newDetailProductCartMfoodyPOJO.getIdProduct()));
        ProductMfoody productMfoody = productMfoodyInterfaceService.getProductMfoodyByID(newDetailProductCartMfoodyPOJO.getIdProduct());
        newDetailProductCartMfoody.setProduct(productMfoody);
        newDetailProductCartMfoody.setSalePriceDetailProductCart(productMfoody.getSalePriceProduct());
        newDetailProductCartMfoody.setFullPriceDetailProductCart(productMfoody.getFullPriceProduct());
        processCartMfoody(newDetailProductCartMfoodyPOJO, newDetailProductCartMfoody);

        // Save to DB and return (Updated Cart in DB could have ID differs from user's request)
        detailProductCartMfoodyInterfaceService.saveDetailProductCartMfoody(newDetailProductCartMfoody);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public void processCartMfoody(DetailProductCartMfoodyPOJO newDetailProductCartMfoodyPOJO, DetailProductCartMfoody newDetailProductCartMfoody) {
        DetailProductCartMfoody oldDetailProductCartMfoody = detailProductCartMfoodyInterfaceService.getDetailProductCartMfoodyByICartAndIdProduct(
                newDetailProductCartMfoodyPOJO.getIdCart(), newDetailProductCartMfoodyPOJO.getIdProduct());
        CartMfoody newCartMfoody = cartMfoodyInterfaceService.getCartMfoodyByID(newDetailProductCartMfoodyPOJO.getIdCart());
        newCartMfoody.setQuantityAllProductsInCart(newCartMfoody.getQuantityAllProductsInCart()
                + newDetailProductCartMfoody.getQuantityDetailProductCart() - oldDetailProductCartMfoody.getQuantityDetailProductCart());
        newCartMfoody.setTotalSalePriceCart(newCartMfoody.getTotalSalePriceCart()
                + newDetailProductCartMfoody.getSalePriceDetailProductCart() - oldDetailProductCartMfoody.getSalePriceDetailProductCart());
        newCartMfoody.setTotalFullPriceCart(newCartMfoody.getTotalFullPriceCart()
                + newDetailProductCartMfoody.getFullPriceDetailProductCart() - oldDetailProductCartMfoody.getFullPriceDetailProductCart());
        cartMfoodyInterfaceService.updateCartMfoody(newCartMfoody);
        newDetailProductCartMfoody.setCart(newCartMfoody);
    }
}
