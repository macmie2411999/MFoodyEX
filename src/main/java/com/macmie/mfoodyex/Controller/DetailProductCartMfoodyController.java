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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;

import static com.macmie.mfoodyex.Constant.ViewConstants.*;

/*
 * HttpStatus.NOT_FOUND (404): use when the requested resource cannot be found (null)
 * HttpStatus.NO_CONTENT (204): use when a successful request returns no content (empty)
 * HttpStatus.BAD_REQUEST (400): use when the request is invalid or contains incorrect parameters
 * */

@Slf4j
@Transactional
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
        List<DetailProductCartMfoody> detailProductCartMfoodyList =
                detailProductCartMfoodyInterfaceService.getListDetailProductCartMfoodys();
        if (detailProductCartMfoodyList.isEmpty()) {
            return new ResponseEntity<>("NO_CONTENT List of DetailProductCartMfoodys", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(detailProductCartMfoodyList, HttpStatus.OK);
    }

    @GetMapping(URL_GET_BY_ID_CART)
    public ResponseEntity<?> getAllDetailProductCartMfoodysByIdCart(@PathVariable("ID") int ID) {
        List<DetailProductCartMfoody> detailProductCartMfoodyList =
                detailProductCartMfoodyInterfaceService.getListDetailProductCartMfoodysByIdCart(ID);
        if (detailProductCartMfoodyList.isEmpty()) {
            return new ResponseEntity<>("NO_CONTENT List of DetailProductCartMfoodys with idCart: " + ID,
                    HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(detailProductCartMfoodyList, HttpStatus.OK);
    }

    @GetMapping(URL_GET_BY_ID_PRODUCT)
    public ResponseEntity<?> getAllDetailProductCartMfoodysByIdProduct(@PathVariable("ID") int ID) {
        List<DetailProductCartMfoody> detailProductCartMfoodyList =
                detailProductCartMfoodyInterfaceService.getListDetailProductCartMfoodysByIdProduct(ID);
        if (detailProductCartMfoodyList.isEmpty()) {
            return new ResponseEntity<>(
                    "NO_CONTENT List of DetailProductCartMfoodys with idProduct: " + ID, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(detailProductCartMfoodyList, HttpStatus.OK);
    }

    @GetMapping(URL_GET_BY_ID_CART_AND_ID_PRODUCT)
    public ResponseEntity<?> getDetailProductCartMfoodyByID(@PathVariable("IdCart") int idCart,
                                                            @PathVariable("IdProduct") int idProduct) {
        DetailProductCartMfoody detailProductCartMfoody =
                detailProductCartMfoodyInterfaceService.getDetailProductCartMfoodyByICartAndIdProduct(idCart, idProduct);
        if (detailProductCartMfoody == null) {
            return new ResponseEntity<>(
                    "NOT_FOUND DetailProductCartMfoody with idCart: " + idCart + ", idProduct: " + idProduct,
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(detailProductCartMfoody, HttpStatus.OK);
    }

    @DeleteMapping(URL_DELETE_BY_ID_CART_AND_ID_PRODUCT)
    public ResponseEntity<?> deleteDetailProductCartMfoodyByID(@PathVariable("IdCart") int idCart,
                                                               @PathVariable("IdProduct") int idProduct) {
        DetailProductCartMfoody detailProductCartMfoody =
                detailProductCartMfoodyInterfaceService.getDetailProductCartMfoodyByICartAndIdProduct(idCart, idProduct);
        if (detailProductCartMfoody == null) {
            return new ResponseEntity<>(
                    "NOT_FOUND DetailProductCartMfoody with idCart: " + idCart + ", idProduct: " + idProduct,
                    HttpStatus.NOT_FOUND);
        }

        try {
            detailProductCartMfoodyInterfaceService.deleteDetailProductCartMfoodyByIdDetailProductCartMfoody(idCart, idProduct);
        } catch (Exception e) {
            log.error("An error occurred while deleting DetailProductCarts with idCart: {} and idProduct: {}", idCart, idProduct);
            log.error("Detail Error: " + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "INTERNAL_SERVER_ERROR Exceptions occur when deleting DetailProductCarts");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(URL_DELETE_BY_ID_CART)
    public ResponseEntity<?> deleteDetailProductCartMfoodyByIdCart(@PathVariable("ID") int ID) {
        if (cartMfoodyInterfaceService.getCartMfoodyByID(ID) == null) {
            return new ResponseEntity<>("NOT_FOUND CartMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }

        try {
            detailProductCartMfoodyInterfaceService.deleteAllDetailProductCartsMfoodyByIdCart(ID);
        } catch (Exception e) {
            log.error("An error occurred while deleting DetailProductCarts with idCart: " + ID);
            log.error("Detail Error: " + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "INTERNAL_SERVER_ERROR Exceptions occur when deleting DetailProductCarts");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(URL_DELETE_BY_ID_PRODUCT)
    public ResponseEntity<?> deleteDetailProductCartMfoodyByIdProduct(@PathVariable("ID") int ID) {
        if (productMfoodyInterfaceService.getProductMfoodyByID(ID) == null) {
            return new ResponseEntity<>("NOT_FOUND ProductMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }

        try {
            detailProductCartMfoodyInterfaceService.deleteAllDetailProductCartsMfoodyByIdProduct(ID);
        } catch (Exception e) {
            log.error("An error occurred while deleting DetailProductCarts with idProduct: " + ID + "; " + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "INTERNAL_SERVER_ERROR Exceptions occur when deleting DetailProductCarts");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
     * 1. idCart and idProduct in Json must be accurate, salePrice/fullPrice in Json are ignored
     * 2. salePrice/fullPrice of DetailProductCartMfoody are fetched from ProductMfoody
     * */
    @PutMapping(URL_EDIT)
    public ResponseEntity<?> editDetailProductCartMfoody(@RequestBody String detailProductCartPOJOJsonObject) {
        try {
            // Convert JsonObject to DetailProductCartPOJO object
            Gson gson = new Gson();
            DetailProductCartMfoodyPOJO newDetailProductCartMfoodyPOJO = gson.fromJson(detailProductCartPOJOJsonObject,
                    DetailProductCartMfoodyPOJO.class);
            DetailProductCartMfoody newDetailProductCartMfoody = detailProductCartMfoodyInterfaceService.
                    getDetailProductCartMfoodyByICartAndIdProduct(
                            newDetailProductCartMfoodyPOJO.getIdCart(),
                            newDetailProductCartMfoodyPOJO.getIdProduct());

            // Check valid idCart and idProduct
            if (productMfoodyInterfaceService.getProductMfoodyByID(newDetailProductCartMfoodyPOJO.getIdProduct()) == null) {
                return new ResponseEntity<>(
                        "NOT_FOUND ProductMfoody with ID: " + newDetailProductCartMfoodyPOJO.getIdProduct(),
                        HttpStatus.NOT_FOUND);
            }
            if (cartMfoodyInterfaceService.getCartMfoodyByID(newDetailProductCartMfoodyPOJO.getIdCart()) == null) {
                return new ResponseEntity<>(
                        "NOT_FOUND CartMfoody with ID: " + newDetailProductCartMfoodyPOJO.getIdCart(),
                        HttpStatus.NOT_FOUND);
            }
            if (newDetailProductCartMfoody == null) {
                return new ResponseEntity<>(
                        "NOT_FOUND DetailProductCartMfoody with idCart: " + newDetailProductCartMfoodyPOJO.getIdCart()
                                + " and idProduct: " + newDetailProductCartMfoodyPOJO.getIdProduct(), HttpStatus.NOT_FOUND);
            }

            // Update IdDetailProductCartMfoody, ProductMfoody, and CartMfoody; re-assign values for salePrice/fullPrice from ProductMfoody
            newDetailProductCartMfoody.setQuantityDetailProductCart(newDetailProductCartMfoodyPOJO.getQuantityDetailProductCart());
            ProductMfoody productMfoody = productMfoodyInterfaceService.getProductMfoodyByID(
                    newDetailProductCartMfoodyPOJO.getIdProduct());
            newDetailProductCartMfoody.setProduct(productMfoody);
            newDetailProductCartMfoody.setSalePriceDetailProductCart(productMfoody.getSalePriceProduct());
            newDetailProductCartMfoody.setFullPriceDetailProductCart(productMfoody.getFullPriceProduct());

            // Save to DB and Update associated CartMfoody
            detailProductCartMfoodyInterfaceService.updateDetailProductCartMfoody(newDetailProductCartMfoody);
            processCartMfoody(newDetailProductCartMfoodyPOJO.getIdCart());

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred while editing DetailProductCartMfoody");
            log.error("Detail Error: " + e);
            return new ResponseEntity<>("BAD_REQUEST Something Wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    /*
     * 1. idCart and idProduct in Json must be accurate, salePrice/fullPrice in Json are ignored
     * 2. salePrice/fullPrice of DetailProductCartMfoody are fetched from ProductMfoody
     * */
    @PostMapping(URL_ADD)
    public ResponseEntity<?> addNewDetailProductCartMfoody(@RequestBody String detailProductCartPOJOJsonObject) {
        try {
            // Convert JsonObject to DetailProductCartPOJO object and Add new CartMfoody and ProductMfoody to DetailProductCart
            Gson gson = new Gson();
            DetailProductCartMfoodyPOJO newDetailProductCartMfoodyPOJO = gson.fromJson(detailProductCartPOJOJsonObject, DetailProductCartMfoodyPOJO.class);
            DetailProductCartMfoody newDetailProductCartMfoody = newDetailProductCartMfoodyPOJO.renderDetailProductCartMfoody();

            // Check valid idCart and idProduct
            DetailProductCartMfoody checkNewDetailProductCartMfoody = detailProductCartMfoodyInterfaceService.
                    getDetailProductCartMfoodyByICartAndIdProduct(
                            newDetailProductCartMfoodyPOJO.getIdCart(),
                            newDetailProductCartMfoodyPOJO.getIdProduct());
            ProductMfoody productMfoody = productMfoodyInterfaceService.
                    getProductMfoodyByID(newDetailProductCartMfoodyPOJO.getIdProduct());
            if (productMfoodyInterfaceService.getProductMfoodyByID(newDetailProductCartMfoodyPOJO.getIdProduct()) == null) {
                return new ResponseEntity<>("NOT_FOUND ProductMfoody with ID: " + newDetailProductCartMfoodyPOJO.getIdProduct(), HttpStatus.NOT_FOUND);
            }
            if (cartMfoodyInterfaceService.getCartMfoodyByID(newDetailProductCartMfoodyPOJO.getIdCart()) == null) {
                return new ResponseEntity<>("NOT_FOUND CartMfoody with ID: " + newDetailProductCartMfoodyPOJO.getIdCart(), HttpStatus.NOT_FOUND);
            }

            // If the DetailProductCartMfoody is already in the DB, update it
            if (checkNewDetailProductCartMfoody != null) {
                checkNewDetailProductCartMfoody.setQuantityDetailProductCart(
                        checkNewDetailProductCartMfoody.getQuantityDetailProductCart() + newDetailProductCartMfoodyPOJO.getQuantityDetailProductCart());
                detailProductCartMfoodyInterfaceService.saveDetailProductCartMfoody(checkNewDetailProductCartMfoody);
                processCartMfoody(newDetailProductCartMfoodyPOJO.getIdCart());
                return new ResponseEntity<>("OK DetailProductCartMfoody updated", HttpStatus.OK);
            }

            // If the DetailProductCartMfoody is completely new, add new
            newDetailProductCartMfoody.setIdDetailProductCartMfoody(new
                    DetailProductCartMfoodyId(newDetailProductCartMfoodyPOJO.getIdCart(),
                    newDetailProductCartMfoodyPOJO.getIdProduct()));
            newDetailProductCartMfoody.setProduct(productMfoody);
            newDetailProductCartMfoody.setSalePriceDetailProductCart(productMfoody.getSalePriceProduct());
            newDetailProductCartMfoody.setFullPriceDetailProductCart(productMfoody.getFullPriceProduct());
            newDetailProductCartMfoody.setCart(cartMfoodyInterfaceService.
                    getCartMfoodyByID(newDetailProductCartMfoodyPOJO.getIdCart()));

            // Save to DB and Update associated CartMfoody
            detailProductCartMfoodyInterfaceService.saveDetailProductCartMfoody(newDetailProductCartMfoody);
            processCartMfoody(newDetailProductCartMfoodyPOJO.getIdCart());

            System.out.println("++++++++++ newDetailProductCartMfoody to save: " + newDetailProductCartMfoody.toString());
            System.out.println("++++++++++ Cart of newDetailProductCartMfoody: " + newDetailProductCartMfoody.getCart().toString());
            System.out.println("++++++++++ Product of newDetailProductCartMfoody: " + newDetailProductCartMfoody.getProduct().toString());

            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("An error occurred while adding DetailProductCartMfoody");
            log.error("Detail Error: " + e);
            return new ResponseEntity<>("BAD_REQUEST Something Wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    // Update associated CartMfoody
    public void processCartMfoody(int idCartMfoody) {
        CartMfoody cartMfoody = cartMfoodyInterfaceService.getCartMfoodyByID(idCartMfoody);
        int quantityAllProductInCart = 0;
        float totalSalePriceCart = 0;
        float totalFullPriceCart = 0;

        List<DetailProductCartMfoody> detailProductCartMfoodyList = detailProductCartMfoodyInterfaceService.
                getListDetailProductCartMfoodysByIdCart(idCartMfoody);
        if (!detailProductCartMfoodyList.isEmpty()) {
            for (DetailProductCartMfoody element : detailProductCartMfoodyList) {
                quantityAllProductInCart += element.getQuantityDetailProductCart();
                totalSalePriceCart += element.getQuantityDetailProductCart() * element.getSalePriceDetailProductCart();
                totalFullPriceCart += element.getQuantityDetailProductCart() * element.getFullPriceDetailProductCart();
            }
        }

        cartMfoody.setQuantityAllProductsInCart(quantityAllProductInCart);
        cartMfoody.setTotalSalePriceCart(totalSalePriceCart);
        cartMfoody.setTotalFullPriceCart(totalFullPriceCart);

        cartMfoodyInterfaceService.updateCartMfoody(cartMfoody);
    }
}
