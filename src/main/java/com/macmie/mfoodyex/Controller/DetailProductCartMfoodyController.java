package com.macmie.mfoodyex.Controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.macmie.mfoodyex.Model.CartMfoody;
import com.macmie.mfoodyex.Model.DetailProductCartMfoody;
import com.macmie.mfoodyex.Model.DetailProductCartMfoodyId;
import com.macmie.mfoodyex.Model.ProductMfoody;
import com.macmie.mfoodyex.Model.UserMfoody;
import com.macmie.mfoodyex.POJO.DetailProductCartMfoodyPOJO;
import com.macmie.mfoodyex.Service.InterfaceService.CartMfoodyInterfaceService;
import com.macmie.mfoodyex.Service.InterfaceService.DetailProductCartMfoodyInterfaceService;
import com.macmie.mfoodyex.Service.InterfaceService.ProductMfoodyInterfaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.security.Principal;
import java.util.List;

import static com.macmie.mfoodyex.Constant.SecurityConstants.ROLE_ADMIN_SECURITY;
import static com.macmie.mfoodyex.Constant.SecurityConstants.ROLE_USER_SECURITY;
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

    @Autowired
    private ApplicationCheckAuthorController applicationCheckAuthorController;

    @Secured({ ROLE_ADMIN_SECURITY })
    @GetMapping(URL_GET_ALL)
    public ResponseEntity<?> getAllDetailProductCartMfoodys(Principal principal) {
        log.info("Get List of DetailProductCartMfoodys by " + principal.getName());
        List<DetailProductCartMfoody> detailProductCartMfoodyList = detailProductCartMfoodyInterfaceService
                .getListDetailProductCartMfoodys();
        // if (detailProductCartMfoodyList.isEmpty()) {
        // return new ResponseEntity<>("NO_CONTENT List of DetailProductCartMfoodys",
        // HttpStatus.NO_CONTENT);
        // }
        return new ResponseEntity<>(detailProductCartMfoodyList, HttpStatus.OK);
    }

    @Secured({ ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY })
    @GetMapping(URL_GET_BY_ID_CART)
    public ResponseEntity<?> getAllDetailProductCartMfoodysByIdCart(@PathVariable("ID") int ID, Principal principal) {
        log.info("Get List of DetailProductCartMfoodys with idCart: {} by {}", ID, principal.getName());

        CartMfoody cartMfoody = cartMfoodyInterfaceService.getCartMfoodyByID(ID);
        if (cartMfoody == null) {
            return new ResponseEntity<>("NOT_FOUND CartMfoody with idCart: " + ID,
                    HttpStatus.NOT_FOUND);
        }

        List<DetailProductCartMfoody> detailProductCartMfoodyList = detailProductCartMfoodyInterfaceService
                .getListDetailProductCartMfoodysByIdCart(ID);

        if (detailProductCartMfoodyList == null) {
            return new ResponseEntity<>("NOT_FOUND List of DetailProductCartMfoodys with idCart: " + ID,
                    HttpStatus.NOT_FOUND);
        }

        // Check if the current UserMfoody has role ADMIN or the owner of
        // DetailProductCartMfoody
        if (!applicationCheckAuthorController.checkAuthorization(principal,
                cartMfoody.getUser().getIdUser())) {
            return new ResponseEntity<>("FORBIDDEN Authorization failed!", HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(detailProductCartMfoodyList, HttpStatus.OK);
    }

    @Secured({ ROLE_ADMIN_SECURITY })
    @GetMapping(URL_GET_BY_ID_PRODUCT)
    public ResponseEntity<?> getAllDetailProductCartMfoodysByIdProduct(@PathVariable("ID") int ID,
            Principal principal) {
        log.info("Get List of DetailProductCartMfoodys with idProduct: {} by {}", ID, principal.getName());

        ProductMfoody productMfoody = productMfoodyInterfaceService.getProductMfoodyByID(ID);
        if (productMfoody == null) {
            return new ResponseEntity<>("NOT_FOUND ProductMfoody with idProduct: " + ID,
                    HttpStatus.NOT_FOUND);
        }

        List<DetailProductCartMfoody> detailProductCartMfoodyList = detailProductCartMfoodyInterfaceService
                .getListDetailProductCartMfoodysByIdProduct(ID);
        if (detailProductCartMfoodyList == null) {
            return new ResponseEntity<>(
                    "NOT_FOUND List of DetailProductCartMfoodys with idProduct: " + ID, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(detailProductCartMfoodyList, HttpStatus.OK);
    }

    @Secured({ ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY })
    @GetMapping(URL_GET_BY_ID_CART_AND_ID_PRODUCT)
    public ResponseEntity<?> getDetailProductCartMfoodyByID(@PathVariable("IdCart") int idCart,
            @PathVariable("IdProduct") int idProduct,
            Principal principal) {
        log.info("Get DetailProductCartMfoody with idCart: {} and idProduct: {} by {}",
                idCart, idProduct, principal.getName());

        // Check valid idCart
        CartMfoody currentCartMfoody = cartMfoodyInterfaceService.getCartMfoodyByID(idCart);
        if (currentCartMfoody == null) {
            return new ResponseEntity<>("NOT_FOUND CartMfoody with ID: " + idCart,
                    HttpStatus.NOT_FOUND);
        }

        UserMfoody currentUserMfoody = currentCartMfoody.getUser();
        if (currentUserMfoody == null) {
            return new ResponseEntity<>("NOT_FOUND UserMfoody with idCart: " + idCart,
                    HttpStatus.NOT_FOUND);
        }

        // Check if the current UserMfoody has role ADMIN or the owner of
        // DetailProductCartMfoody
        if (!applicationCheckAuthorController.checkAuthorization(principal,
                currentUserMfoody.getIdUser())) {
            return new ResponseEntity<>("FORBIDDEN Authorization failed!", HttpStatus.FORBIDDEN);
        }

        DetailProductCartMfoody detailProductCartMfoody = detailProductCartMfoodyInterfaceService
                .getDetailProductCartMfoodyByICartAndIdProduct(idCart, idProduct);
        if (detailProductCartMfoody == null) {
            return new ResponseEntity<>(
                    "NOT_FOUND DetailProductCartMfoody with idCart: " + idCart + ", idProduct: " + idProduct,
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(detailProductCartMfoody, HttpStatus.OK);
    }

    @Secured({ ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY })
    @DeleteMapping(URL_DELETE_BY_ID_CART_AND_ID_PRODUCT)
    public ResponseEntity<?> deleteDetailProductCartMfoodyByIDs(@PathVariable("IdCart") int idCart,
            @PathVariable("IdProduct") int idProduct,
            Principal principal) {
        log.info("Delete DetailProductCartMfoody with idCart: {} and idProduct: {} by {}",
                idCart, idProduct, principal.getName());
        DetailProductCartMfoody detailProductCartMfoody = detailProductCartMfoodyInterfaceService
                .getDetailProductCartMfoodyByICartAndIdProduct(idCart, idProduct);
        if (detailProductCartMfoody == null) {
            return new ResponseEntity<>(
                    "NOT_FOUND DetailProductCartMfoody with idCart: " + idCart + ", idProduct: " + idProduct,
                    HttpStatus.NOT_FOUND);
        }

        // Check if the current UserMfoody has role ADMIN or the owner of
        // DetailProductCartMfoody
        CartMfoody cartMfoody = cartMfoodyInterfaceService.getCartMfoodyByID(idCart);
        if (cartMfoody != null) {
            if (!applicationCheckAuthorController.checkAuthorization(principal, cartMfoody.getUser().getIdUser())) {
                return new ResponseEntity<>("FORBIDDEN Authorization failed!", HttpStatus.FORBIDDEN);
            }
        }

        try {
            detailProductCartMfoodyInterfaceService.deleteDetailProductCartMfoodyByIdDetailProductCartMfoody(idCart,
                    idProduct);
        } catch (Exception e) {
            log.error("An error occurred while deleting DetailProductCart with idCart: {} and idProduct: {}", idCart,
                    idProduct);
            log.error("Detail Error: " + e);
            // throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
            // "INTERNAL_SERVER_ERROR Exceptions occur when deleting DetailProductCart");
            return new ResponseEntity<>("BAD_REQUEST Something Wrong!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Secured({ ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY })
    @DeleteMapping(URL_DELETE_BY_ID_CART)
    public ResponseEntity<?> deleteAllDetailProductCartMfoodysByIdCart(@PathVariable("ID") int ID,
            Principal principal) {
        log.info("Delete List of DetailProductCartMfoodys with idCart: {} by {}", ID, principal.getName());
        CartMfoody cartMfoody = cartMfoodyInterfaceService.getCartMfoodyByID(ID);
        if (cartMfoody == null) {
            return new ResponseEntity<>("NOT_FOUND CartMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }

        // Check if the current UserMfoody has role ADMIN or the owner of
        // DetailProductCartMfoody
        if (!applicationCheckAuthorController.checkAuthorization(principal, cartMfoody.getUser().getIdUser())) {
            return new ResponseEntity<>("FORBIDDEN Authorization failed!", HttpStatus.FORBIDDEN);
        }

        try {
            detailProductCartMfoodyInterfaceService.deleteAllDetailProductCartsMfoodyByIdCart(ID);
        } catch (Exception e) {
            log.error("An error occurred while deleting List of DetailProductCartMfoodys with idCart: " + ID);
            log.error("Detail Error: " + e);
            // throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
            // "INTERNAL_SERVER_ERROR Exceptions occur when deleting List of
            // DetailProductCartMfoodys");
            return new ResponseEntity<>("BAD_REQUEST Something Wrong!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Secured({ ROLE_ADMIN_SECURITY })
    @DeleteMapping(URL_DELETE_BY_ID_PRODUCT)
    public ResponseEntity<?> deleteAllDetailProductCartMfoodysByIdProduct(@PathVariable("ID") int ID,
            Principal principal) {
        log.info("Delete List of DetailProductCartMfoodys with idProduct: {} by {}", ID, principal.getName());
        if (productMfoodyInterfaceService.getProductMfoodyByID(ID) == null) {
            return new ResponseEntity<>("NOT_FOUND ProductMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }

        try {
            detailProductCartMfoodyInterfaceService.deleteAllDetailProductCartsMfoodyByIdProduct(ID);
        } catch (Exception e) {
            log.error("An error occurred while deleting List of DetailProductCartMfoodys with idProduct: " + ID);
            log.error("Detail Error: " + e);
            // throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
            // "INTERNAL_SERVER_ERROR Exceptions occur when deleting List of
            // DetailProductCartMfoodys");
            return new ResponseEntity<>("BAD_REQUEST Something Wrong!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
     * 1. idCart and idProduct in Json must be accurate, salePrice/fullPrice in Json
     * are ignored
     * 2. salePrice/fullPrice of DetailProductCartMfoody are fetched from
     * ProductMfoody
     */
    @Secured({ ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY })
    @PutMapping(URL_EDIT)
    public ResponseEntity<?> editDetailProductCartMfoody(@RequestBody String detailProductCartPOJOJsonObject,
            Principal principal) {
        log.info("Edit DetailProductCartMfoodys by {}", principal.getName());
        try {
            // Convert JsonObject to DetailProductCartPOJO object
            Gson gson = new Gson();
            DetailProductCartMfoodyPOJO newDetailProductCartMfoodyPOJO = gson.fromJson(detailProductCartPOJOJsonObject,
                    DetailProductCartMfoodyPOJO.class);
            DetailProductCartMfoody newDetailProductCartMfoody = detailProductCartMfoodyInterfaceService
                    .getDetailProductCartMfoodyByICartAndIdProduct(
                            newDetailProductCartMfoodyPOJO.getIdCart(),
                            newDetailProductCartMfoodyPOJO.getIdProduct());
            ProductMfoody newProductMfoody = productMfoodyInterfaceService.getProductMfoodyByID(
                    newDetailProductCartMfoodyPOJO.getIdProduct());
            CartMfoody newCartMfoody = cartMfoodyInterfaceService.getCartMfoodyByID(
                    newDetailProductCartMfoodyPOJO.getIdCart());

            // Check valid idCart and idProduct
            if (newProductMfoody == null) {
                return new ResponseEntity<>(
                        "NOT_FOUND ProductMfoody with ID: " + newDetailProductCartMfoodyPOJO.getIdProduct(),
                        HttpStatus.NOT_FOUND);
            }
            if (newCartMfoody == null) {
                return new ResponseEntity<>(
                        "NOT_FOUND CartMfoody with ID: " + newDetailProductCartMfoodyPOJO.getIdCart(),
                        HttpStatus.NOT_FOUND);
            }
            if (newDetailProductCartMfoody == null) {
                return new ResponseEntity<>(
                        "NOT_FOUND DetailProductCartMfoody with idCart: "
                                + newDetailProductCartMfoodyPOJO.getIdCart()
                                + " and idProduct: " + newDetailProductCartMfoodyPOJO.getIdProduct(),
                        HttpStatus.NOT_FOUND);
            }

            // Check if the current UserMfoody has role ADMIN or the owner of
            // DetailProductCartMfoody
            if (!applicationCheckAuthorController.checkAuthorization(principal, newCartMfoody.getUser().getIdUser())) {
                return new ResponseEntity<>("FORBIDDEN Authorization failed!", HttpStatus.FORBIDDEN);
            }

            // Update IdDetailProductCartMfoody, ProductMfoody, and CartMfoody
            newDetailProductCartMfoody.setQuantityDetailProductCart(
                    newDetailProductCartMfoodyPOJO.getQuantityDetailProductCart());
            newDetailProductCartMfoody.setProduct(newProductMfoody);
            newDetailProductCartMfoody.setSalePriceDetailProductCart(newProductMfoody.getSalePriceProduct());
            newDetailProductCartMfoody.setFullPriceDetailProductCart(newProductMfoody.getFullPriceProduct());

            // Save to DB and Update associated CartMfoody
            detailProductCartMfoodyInterfaceService.updateDetailProductCartMfoody(newDetailProductCartMfoody);
            log.info("DetailProductCartMfoody with ID: {} by {} is edited",
                    newDetailProductCartMfoody.getIdDetailProductCartMFoody(), principal.getName());
            processCartMfoody(newDetailProductCartMfoodyPOJO.getIdCart());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred while editing DetailProductCartMfoody");
            log.error("Detail Error: " + e);
            return new ResponseEntity<>("BAD_REQUEST Something Wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    /*
     * 1. idCart and idProduct in Json must be accurate, salePrice/fullPrice in Json
     * are ignored
     * 2. salePrice/fullPrice of DetailProductCartMfoody are fetched from
     * ProductMfoody
     * 3. The threat is any UserMfoodys can create DetailProductCartMfoody using
     * different idCart and idProduct
     * (There is no double check)
     */
    @Secured({ ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY })
    @PostMapping(URL_ADD)
    public ResponseEntity<?> addNewDetailProductCartMfoody(@RequestBody String detailProductCartPOJOJsonObject,
            Principal principal) {
        log.info("Add DetailProductCartMfoodys by {}", principal.getName());
        try {
            // Convert JsonObject to DetailProductCartPOJO object and Add new CartMfoody and
            // ProductMfoody to DetailProductCart
            Gson gson = new Gson();
            DetailProductCartMfoodyPOJO newDetailProductCartMfoodyPOJO = gson.fromJson(detailProductCartPOJOJsonObject,
                    DetailProductCartMfoodyPOJO.class);
            DetailProductCartMfoody newDetailProductCartMfoody = newDetailProductCartMfoodyPOJO
                    .renderDetailProductCartMfoody();

            // Check valid idCart and idProduct
            DetailProductCartMfoody checkNewDetailProductCartMfoody = detailProductCartMfoodyInterfaceService
                    .getDetailProductCartMfoodyByICartAndIdProduct(
                            newDetailProductCartMfoodyPOJO.getIdCart(),
                            newDetailProductCartMfoodyPOJO.getIdProduct());
            ProductMfoody productMfoody = productMfoodyInterfaceService
                    .getProductMfoodyByID(newDetailProductCartMfoodyPOJO.getIdProduct());
            if (productMfoodyInterfaceService
                    .getProductMfoodyByID(newDetailProductCartMfoodyPOJO.getIdProduct()) == null) {
                return new ResponseEntity<>("NOT_FOUND ProductMfoody with ID: "
                        + newDetailProductCartMfoodyPOJO.getIdProduct(), HttpStatus.NOT_FOUND);
            }
            if (cartMfoodyInterfaceService.getCartMfoodyByID(newDetailProductCartMfoodyPOJO.getIdCart()) == null) {
                return new ResponseEntity<>("NOT_FOUND CartMfoody with ID: "
                        + newDetailProductCartMfoodyPOJO.getIdCart(), HttpStatus.NOT_FOUND);
            }

            // If the DetailProductCartMfoody is already in the DB, update it
            if (checkNewDetailProductCartMfoody != null) {
                checkNewDetailProductCartMfoody.setQuantityDetailProductCart(
                        checkNewDetailProductCartMfoody.getQuantityDetailProductCart()
                                + newDetailProductCartMfoodyPOJO.getQuantityDetailProductCart());
                detailProductCartMfoodyInterfaceService.saveDetailProductCartMfoody(checkNewDetailProductCartMfoody);
                processCartMfoody(newDetailProductCartMfoodyPOJO.getIdCart());
                return new ResponseEntity<>("OK DetailProductCartMfoody updated", HttpStatus.OK);
            }

            // If the DetailProductCartMfoody is completely new, add new
            newDetailProductCartMfoody.setIdDetailProductCartMfoody(
                    new DetailProductCartMfoodyId(newDetailProductCartMfoodyPOJO.getIdCart(),
                            newDetailProductCartMfoodyPOJO.getIdProduct()));
            newDetailProductCartMfoody.setProduct(productMfoody);
            newDetailProductCartMfoody.setSalePriceDetailProductCart(productMfoody.getSalePriceProduct());
            newDetailProductCartMfoody.setFullPriceDetailProductCart(productMfoody.getFullPriceProduct());
            newDetailProductCartMfoody
                    .setCart(cartMfoodyInterfaceService.getCartMfoodyByID(newDetailProductCartMfoodyPOJO.getIdCart()));

            // Save to DB and Update associated CartMfoody
            detailProductCartMfoodyInterfaceService.saveDetailProductCartMfoody(newDetailProductCartMfoody);
            log.info("A new DetailProductCartMfoody is created by " + principal.getName());
            processCartMfoody(newDetailProductCartMfoodyPOJO.getIdCart());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("An error occurred while adding DetailProductCartMfoody");
            log.error("Detail Error: " + e);
            return new ResponseEntity<>("BAD_REQUEST Something Wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    @Secured({ ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY })
    @PostMapping(URL_UPDATE_PRODUCT_INFORMATION_IN_CART)
    public ResponseEntity<?> saveListDetailProductCartMfoodyToCart(@RequestBody String cartJson, Principal principal) {
        log.info("Update List of DetailProductCartMfoodys by {}", principal.getName());
        try {
            // Parse the JSON string into a list of DetailProductCartMfoodyPOJO objects
            Gson gson = new Gson();
            Type listType = new TypeToken<List<DetailProductCartMfoodyPOJO>>() {
            }.getType();
            List<DetailProductCartMfoodyPOJO> productList = gson.fromJson(cartJson, listType);

            for (DetailProductCartMfoodyPOJO product : productList) {
                // Check if the product is already in the cart
                DetailProductCartMfoody existingProductCart = detailProductCartMfoodyInterfaceService
                        .getDetailProductCartMfoodyByICartAndIdProduct(product.getIdCart(), product.getIdProduct());

                if (existingProductCart == null) {
                    // If the product is not in the cart, add a new product
                    ResponseEntity<?> addResult = addNewDetailProductCartMfoody(gson.toJson(product), principal);
                    if (!addResult.getStatusCode().equals(HttpStatus.CREATED)) {
                        return addResult;
                    }
                } else {
                    // If the product is in the cart, update the product information
                    ResponseEntity<?> updateResult = editDetailProductCartMfoody(gson.toJson(product), principal);
                    if (!updateResult.getStatusCode().equals(HttpStatus.OK)) {
                        return updateResult;
                    }
                }
            }

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred while saving the cart");
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

        List<DetailProductCartMfoody> detailProductCartMfoodyList = detailProductCartMfoodyInterfaceService
                .getListDetailProductCartMfoodysByIdCart(idCartMfoody);
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
        log.info("CartMfoody with ID: {} is edited! ", cartMfoody.getIdCart());
    }
}
