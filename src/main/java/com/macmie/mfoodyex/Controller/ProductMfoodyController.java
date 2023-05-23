package com.macmie.mfoodyex.Controller;

import com.google.gson.Gson;
import com.macmie.mfoodyex.Model.CartMfoody;
import com.macmie.mfoodyex.Model.CommentMfoody;
import com.macmie.mfoodyex.Model.DetailProductCartMfoody;
import com.macmie.mfoodyex.Model.ProductMfoody;
import com.macmie.mfoodyex.POJO.ProductMfoodyPOJO;
import com.macmie.mfoodyex.Service.InterfaceService.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
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
@RequestMapping(PRODUCT_MFOODY)
public class ProductMfoodyController {
    @Autowired
    private ProductMfoodyInterfaceService productMfoodyInterfaceService;

    @Autowired
    private CommentMfoodyInterfaceService commentMfoodyInterfaceService;

    @Autowired
    private DetailProductCartMfoodyInterfaceService detailProductCartMfoodyInterfaceService;

    @Autowired
    private FavoriteProductMfoodyInterfaceService favoriteProductMfoodyInterfaceService;

    @Autowired
    private DetailProductOrderMfoodyInterfaceService detailProductOrderMfoodyInterfaceService;

    @Autowired
    private OrderMfoodyInterfaceService orderMfoodyInterfaceService;

    @Autowired
    private ApplicationCheckAuthorController applicationCheckAuthorController;

    @Autowired
    private CartMfoodyInterfaceService cartMfoodyInterfaceService;

    @Autowired
    private FavoriteListProductsMfoodyInterfaceService favoriteListProductsMfoodyInterfaceService;

    @Secured({ ROLE_ADMIN_SECURITY })
    @GetMapping(URL_COUNT_TOTAL)
    public ResponseEntity<?> countTotalNumberOfProductMfoodys(Principal principal) {
        log.info("Count Total Number of ProductMfoodys by " + principal.getName());

        try {
            Long totalNumberOfProductMfoodys = productMfoodyInterfaceService.countTotalNumberOfProductMfoodys();
            return new ResponseEntity<>(totalNumberOfProductMfoodys, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred while counting number of ProductMfoodys");
            log.error("Detail Error: " + e);
            return new ResponseEntity<>("BAD_REQUEST Something Wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    @Secured({ ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY })
    @GetMapping(URL_GET_ALL)
    public ResponseEntity<?> getAllProductMfoodys(Principal principal) {
        log.info("Get List of ProductMfoodys by " + principal.getName());
        List<ProductMfoody> productMfoodyList = productMfoodyInterfaceService.getListProductMfoodys();

        // if (!productMfoodyList.isEmpty()) {
        //     updateRatingListProducts(productMfoodyList);
        // }
        return new ResponseEntity<>(productMfoodyList, HttpStatus.OK);
    }

    @Secured({ ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY })
    @GetMapping(URL_GET_ALL_UPDATE)
    public ResponseEntity<?> getAllProductMfoodysUpdate(Principal principal) {
        log.info("Get List of ProductMfoodys by " + principal.getName());
        List<ProductMfoody> productMfoodyList = productMfoodyInterfaceService.getListProductMfoodys();

        if (!productMfoodyList.isEmpty()) {
            updateRatingListProducts(productMfoodyList);
        }
        return new ResponseEntity<>(productMfoodyList, HttpStatus.OK);
    }

    @Secured({ ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY })
    @GetMapping(URL_GET_BY_ID)
    public ResponseEntity<?> getProductMfoodyByID(@PathVariable("ID") int ID, Principal principal) {
        log.info("Get ProductMfoody with ID: {} by {}", ID, principal.getName());
        ProductMfoody productMfoody = productMfoodyInterfaceService.getProductMfoodyByID(ID);
        if (productMfoody == null) {
            return new ResponseEntity<>("NOT_FOUND ProductMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }
        // updateRatingProduct(ID);
        return new ResponseEntity<>(productMfoody, HttpStatus.OK);
    }

    @Secured({ ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY })
    @GetMapping(URL_GET_BY_ID_UPDATE)
    public ResponseEntity<?> getProductMfoodyByIDUpdate(@PathVariable("ID") int ID, Principal principal) {
        log.info("Get ProductMfoody with ID: {} by {}", ID, principal.getName());
        ProductMfoody productMfoody = productMfoodyInterfaceService.getProductMfoodyByID(ID);
        if (productMfoody == null) {
            return new ResponseEntity<>("NOT_FOUND ProductMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }
        updateRatingProduct(ID);
        return new ResponseEntity<>(productMfoody, HttpStatus.OK);
    }

    @Secured({ ROLE_ADMIN_SECURITY })
    @DeleteMapping(URL_DELETE)
    public ResponseEntity<?> deleteProductMfoodyByID(@PathVariable("ID") int ID, Principal principal) {
        log.info("Delete ProductMfoody with ID: {} by {}", ID, principal.getName());
        if (productMfoodyInterfaceService.getProductMfoodyByID(ID) == null) {
            return new ResponseEntity<>("NOT_FOUND ProductMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }

        try {
            // Delete Detail Product Cart, Detail Product Order, Comment, FavoriteList and
            // Product
            detailProductCartMfoodyInterfaceService.deleteAllDetailProductCartsMfoodyByIdProduct(ID);
            favoriteProductMfoodyInterfaceService.deleteAllFavoriteProductsMfoodyByIdProduct(ID);
            detailProductOrderMfoodyInterfaceService.deleteAllDetailProductOrdersMfoodyByIdProduct(ID);
            commentMfoodyInterfaceService.deleteAllCommentsMfoodyByIdProduct(ID);
            productMfoodyInterfaceService.deleteProductMfoodyByID(ID);
        } catch (Exception e) {
            log.error("An error occurred while deleting ProductMfoody with ID: " + ID);
            log.error("Detail Error: " + e);
            // throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
            // "INTERNAL_SERVER_ERROR Exceptions occur when deleting ProductMfoody");
            return new ResponseEntity<>("BAD_REQUEST Something Wrong!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Secured({ ROLE_ADMIN_SECURITY })
    @PutMapping(URL_EDIT) // idProduct in Json must be accurate
    public ResponseEntity<?> editProductMfoody(@RequestBody String productMfoodyPOJOJsonObject, Principal principal) {
        try {
            // Convert JsonObject to ProductMfoodyPOJO object, Check the input idProduct
            Gson gson = new Gson();
            ProductMfoodyPOJO newProductMfoodyPOJO = gson.fromJson(productMfoodyPOJOJsonObject,
                    ProductMfoodyPOJO.class);
            ProductMfoody newProductMfoody = newProductMfoodyPOJO.renderProductMfoody();
            ProductMfoody oldProductMfoody = productMfoodyInterfaceService
                    .getProductMfoodyByID(newProductMfoodyPOJO.getIdProduct());
            if (oldProductMfoody == null) {
                return new ResponseEntity<>(
                        "NOT_FOUND ProductMfoody with ID: " + newProductMfoodyPOJO.getIdProduct(),
                        HttpStatus.NOT_FOUND);
            }

            // Check duplicate by nameProduct/phoneNumber
            ProductMfoody existingNameProduct = productMfoodyInterfaceService
                    .getProductMfoodyByNameProduct(newProductMfoodyPOJO.getNameProduct());
            ProductMfoody existingAlbumProduct = productMfoodyInterfaceService.getProductMfoodyByAlbumProduct(
                    newProductMfoodyPOJO.getAlbumProduct());
            if (existingNameProduct != null || existingAlbumProduct != null) {
                return new ResponseEntity<>(
                        "CONFLICT - A Product with the same nameProduct or albumProduct already exists!",
                        HttpStatus.CONFLICT);
            }

            // Save to DB (Handle Exception in case the unique attributes in the request
            // already exist)
            try {
                productMfoodyInterfaceService.updateProductMfoody(newProductMfoody);
                log.info("ProductMfoody with ID: {} by {} is edited", newProductMfoody.getIdProduct(),
                        principal.getName());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        "BAD_REQUEST Failed to update ProductMfoody with ID: " + newProductMfoodyPOJO.getIdProduct());
            }

            /*
             * 1. Update price in DetailProductCart, CartMfoody if the price changes
             * 2. There is no need to update in OrderMfoody or DetailProductOrder coz price
             * Order will not be change accordingly
             */
            if (newProductMfoody.getSalePriceProduct() != oldProductMfoody.getSalePriceProduct()
                    || newProductMfoody.getFullPriceProduct() != oldProductMfoody.getFullPriceProduct()) {
                List<DetailProductCartMfoody> detailProductCartMfoodyList = detailProductCartMfoodyInterfaceService
                        .findAllDetailProductCartsMfoodyByIdProduct(
                                newProductMfoody.getIdProduct());
                for (DetailProductCartMfoody element : detailProductCartMfoodyList) {
                    element.setFullPriceDetailProductCart(newProductMfoody.getFullPriceProduct());
                    element.setSalePriceDetailProductCart(newProductMfoody.getSalePriceProduct());
                    detailProductCartMfoodyInterfaceService.updateDetailProductCartMfoody(element);
                    log.info("DetailProductCartMfoody with ID: {} by {} is edited",
                            element.getIdDetailProductCartMFoody(), principal.getName());

                    CartMfoody updateCartMfoody = element.getCart();
                    updateCartMfoody.setTotalSalePriceCart(updateCartMfoody.getTotalSalePriceCart() +
                            newProductMfoody.getSalePriceProduct() - oldProductMfoody.getSalePriceProduct());
                    updateCartMfoody.setTotalFullPriceCart(updateCartMfoody.getTotalFullPriceCart() +
                            newProductMfoody.getFullPriceProduct() - oldProductMfoody.getFullPriceProduct());
                    cartMfoodyInterfaceService.updateCartMfoody(updateCartMfoody);
                    log.info("CartMfoody with ID: {} by {} is edited", updateCartMfoody.getIdCart(),
                            principal.getName());
                }
            }

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred while editing ProductMfoody");
            log.error("Detail Error: " + e);
            return new ResponseEntity<>("BAD_REQUEST Something Wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    @Secured({ ROLE_ADMIN_SECURITY })
    @PostMapping(URL_ADD) // idProduct in Json is ignored
    public ResponseEntity<?> addNewProductMfoody(@RequestBody String productMfoodyPOJOJsonObject, Principal principal) {
        try {
            // Convert JsonObject to ProductMfoody object
            Gson gson = new Gson();
            ProductMfoodyPOJO newProductMfoodyPOJO = gson.fromJson(productMfoodyPOJOJsonObject,
                    ProductMfoodyPOJO.class);
            ProductMfoody newProductMfoody = newProductMfoodyPOJO.renderProductMfoody();

            // Check duplicate by nameProduct/albumProduct
            ProductMfoody existingNameProduct = productMfoodyInterfaceService
                    .getProductMfoodyByNameProduct(newProductMfoody.getNameProduct());
            ProductMfoody existingAlbumProduct = productMfoodyInterfaceService
                    .getProductMfoodyByAlbumProduct(newProductMfoody.getAlbumProduct());
            if (existingNameProduct != null || existingAlbumProduct != null) {
                return new ResponseEntity<>(
                        "CONFLICT - A product with the same nameProduct or albumProduct already exists!",
                        HttpStatus.CONFLICT);
            }

            // Save to DB and return (Updated Cart in DB could have ID differs from user's
            // request)
            productMfoodyInterfaceService.saveProductMfoody(newProductMfoody);
            log.info("A new ProductMfoody is created by " + principal.getName());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("An error occurred while adding ProductMfoody");
            log.error("Detail Error: " + e);
            return new ResponseEntity<>("BAD_REQUEST Something Wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public void updateRatingProduct(int idProduct) {
        ProductMfoody newProductMfoody = productMfoodyInterfaceService.getProductMfoodyByID(idProduct);
        List<CommentMfoody> commentMfoodyList = commentMfoodyInterfaceService
                .getListCommentMfoodysByIdProduct(idProduct);

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
        // log.info("Sum Ratting for idProduct {} is {}", idProduct, sumOfRatings);
        // log.info("Total Comments are {}", commentMfoodyList.size());
        // log.info("Ratting for idProduct {} is {}", idProduct, Math.round(( (double)(sumOfRatings / commentMfoodyList.size()) )*100.00)/100.00);
        newProductMfoody.setRatingProduct((float) (Math.round(( (double)(sumOfRatings / commentMfoodyList.size()) )*100.00)/100.00));
        productMfoodyInterfaceService.saveProductMfoody(newProductMfoody);
    }

    public void updateRatingListProducts(List<ProductMfoody> listProducts) {
        for (ProductMfoody p : listProducts) {
            updateRatingProduct(p.getIdProduct());
        }
    }
}
