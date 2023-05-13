package com.macmie.mfoodyex.Controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.macmie.mfoodyex.Model.FavoriteListProductsMfoody;
import com.macmie.mfoodyex.Model.FavoriteProductMfoody;
import com.macmie.mfoodyex.Model.FavoriteProductMfoodyId;
import com.macmie.mfoodyex.Model.ProductMfoody;
import com.macmie.mfoodyex.Model.UserMfoody;
import com.macmie.mfoodyex.POJO.FavoriteProductMfoodyPOJO;
import com.macmie.mfoodyex.Service.InterfaceService.FavoriteListProductsMfoodyInterfaceService;
import com.macmie.mfoodyex.Service.InterfaceService.FavoriteProductMfoodyInterfaceService;
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
@RequestMapping(FAVORITE_PRODUCT_MFOODY)
public class FavoriteProductMfoodyController {

        @Autowired
        private FavoriteProductMfoodyInterfaceService favoriteProductMfoodyInterfaceService;

        @Autowired
        private FavoriteListProductsMfoodyInterfaceService favoriteListProductsMfoodyInterfaceService;

        @Autowired
        private ProductMfoodyInterfaceService productMfoodyInterfaceService;

        @Autowired
        private ApplicationCheckAuthorController applicationCheckAuthorController;

        @Secured({ ROLE_ADMIN_SECURITY })
        @GetMapping(URL_GET_ALL)
        public ResponseEntity<?> getAllFavoriteProductMfoodys(Principal principal) {
                log.info("Get List of FavoriteProductMfoodys by " + principal.getName());
                List<FavoriteProductMfoody> favoriteProductMfoodyList = favoriteProductMfoodyInterfaceService
                                .getListFavoriteProductMfoodys();
                // if (FavoriteProductMfoodyList.isEmpty()) {
                // return new ResponseEntity<>("NOT_FOUND List of FavoriteProductMfoodys",
                // HttpStatus.NOT_FOUND);
                // }
                return new ResponseEntity<>(favoriteProductMfoodyList, HttpStatus.OK);
        }

        @Secured({ ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY })
        @GetMapping(URL_GET_BY_ID_FAVORITE_LIST_PRODUCTS)
        public ResponseEntity<?> getAllFavoriteProductMfoodysByIdFavoriteListProducts(@PathVariable("ID") int ID,
                        Principal principal) {
                log.info("Get List of FavoriteProductMfoodys with idFavoriteListProducts: {} by {}", ID,
                                principal.getName());

                FavoriteListProductsMfoody favoriteListProductsMfoody = favoriteListProductsMfoodyInterfaceService
                                .getFavoriteListProductsMfoodyByID(ID);
                if (favoriteListProductsMfoody == null) {
                        return new ResponseEntity<>(
                                        "NOT_FOUND FavoriteListProductsMfoodywith ID: " + ID,
                                        HttpStatus.NOT_FOUND);
                }

                List<FavoriteProductMfoody> favoriteProductMfoodyList = favoriteProductMfoodyInterfaceService
                                .getListFavoriteProductMfoodysByIdFavoriteListProducts(ID);
                if (favoriteProductMfoodyList == null) {
                        return new ResponseEntity<>(
                                        "NOT_FOUND List of FavoriteProductMfoodys with idFavoriteListProducts: " + ID,
                                        HttpStatus.NOT_FOUND);
                }

                // Check if the current UserMfoody has role ADMIN or the owner of
                // FavoriteProductMfoody
                if (!applicationCheckAuthorController.checkAuthorization(principal,
                                favoriteListProductsMfoody.getUser().getIdUser())) {
                        return new ResponseEntity<>("FORBIDDEN Authorization failed!", HttpStatus.FORBIDDEN);
                }

                return new ResponseEntity<>(favoriteProductMfoodyList, HttpStatus.OK);
        }

        @Secured({ ROLE_ADMIN_SECURITY })
        @GetMapping(URL_GET_BY_ID_PRODUCT)
        public ResponseEntity<?> getAllFavoriteProductMfoodysByIdProduct(@PathVariable("ID") int ID,
                        Principal principal) {
                log.info("Get List of FavoriteProductMfoodys with idProduct: {} by {}", ID, principal.getName());
                List<FavoriteProductMfoody> favoriteProductMfoodyList = favoriteProductMfoodyInterfaceService
                                .getListFavoriteProductMfoodysByIdProduct(ID);
                if (favoriteProductMfoodyList.isEmpty()) {
                        return new ResponseEntity<>(
                                        "NOT_FOUND List of FavoriteProductMfoodys with idProduct: " + ID,
                                        HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<>(favoriteProductMfoodyList, HttpStatus.OK);
        }

        @Secured({ ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY })
        @GetMapping(URL_GET_BY_ID_FAVORITE_LIST_PRODUCTS_AND_ID_PRODUCT)
        public ResponseEntity<?> getFavoriteProductMfoodyByID(
                        @PathVariable("IdFavoriteListProducts") int idFavoriteListProducts,
                        @PathVariable("IdProduct") int idProduct,
                        Principal principal) {
                log.info("Get FavoriteProductMfoody with idFavoriteListProducts: {} and idProduct: {} by {}",
                                idFavoriteListProducts, idProduct, principal.getName());

                // Check valid idFavoriteListProducts
                FavoriteListProductsMfoody currentFavoriteListProductsMfoody = favoriteListProductsMfoodyInterfaceService
                                .getFavoriteListProductsMfoodyByID(idFavoriteListProducts);
                if (currentFavoriteListProductsMfoody == null) {
                        return new ResponseEntity<>(
                                        "NOT_FOUND FavoriteListProductsMfoody with ID: " + idFavoriteListProducts,
                                        HttpStatus.NOT_FOUND);
                }

                UserMfoody currentUserMfoody = currentFavoriteListProductsMfoody.getUser();
                if (currentUserMfoody == null) {
                        return new ResponseEntity<>(
                                        "NOT_FOUND UserMfoody with idFavoriteListProducts: " + idFavoriteListProducts,
                                        HttpStatus.NOT_FOUND);
                }

                // Check if the current UserMfoody has role ADMIN or the owner of
                // FavoriteProductMfoody
                if (!applicationCheckAuthorController.checkAuthorization(principal,
                                currentUserMfoody.getIdUser())) {
                        return new ResponseEntity<>("FORBIDDEN Authorization failed!", HttpStatus.FORBIDDEN);
                }

                FavoriteProductMfoody favoriteProductMfoody = favoriteProductMfoodyInterfaceService
                                .getFavoriteProductMfoodyByIFavoriteListProductsAndIdProduct(idFavoriteListProducts,
                                                idProduct);
                if (favoriteProductMfoody == null) {
                        return new ResponseEntity<>(
                                        "NOT_FOUND FavoriteProductMfoody with idFavoriteListProducts: "
                                                        + idFavoriteListProducts
                                                        + ", idProduct: " + idProduct,
                                        HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<>(favoriteProductMfoody, HttpStatus.OK);
        }

        @Secured({ ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY })
        @DeleteMapping(URL_DELETE_BY_ID_FAVORITE_LIST_PRODUCTS_AND_ID_PRODUCT)
        public ResponseEntity<?> deleteFavoriteProductMfoodyByID(
                        @PathVariable("IdFavoriteListProducts") int idFavoriteListProducts,
                        @PathVariable("IdProduct") int idProduct,
                        Principal principal) {
                log.info("Delete FavoriteProductMfoody with idFavoriteListProducts: {} and idProduct: {} by {}",
                                idFavoriteListProducts, idProduct, principal.getName());
                FavoriteProductMfoody favoriteProductMfoody = favoriteProductMfoodyInterfaceService
                                .getFavoriteProductMfoodyByIFavoriteListProductsAndIdProduct(idFavoriteListProducts,
                                                idProduct);
                if (favoriteProductMfoody == null) {
                        return new ResponseEntity<>(
                                        "NOT_FOUND FavoriteProductMfoody with idFavoriteListProducts: "
                                                        + idFavoriteListProducts
                                                        + ", idProduct: " + idProduct,
                                        HttpStatus.NOT_FOUND);
                }

                // Check if the current UserMfoody has role ADMIN or the owner of
                // FavoriteProductMfoody
                FavoriteListProductsMfoody favoriteListProductsMfoody = favoriteListProductsMfoodyInterfaceService
                                .getFavoriteListProductsMfoodyByID(idFavoriteListProducts);
                if (favoriteListProductsMfoody != null) {
                        if (!applicationCheckAuthorController.checkAuthorization(principal,
                                        favoriteListProductsMfoody.getUser().getIdUser())) {
                                return new ResponseEntity<>("FORBIDDEN Authorization failed!", HttpStatus.FORBIDDEN);
                        }
                }

                try {
                        favoriteProductMfoodyInterfaceService
                                        .deleteFavoriteProductMfoodyByIdFavoriteProductMfoody(idFavoriteListProducts,
                                                        idProduct);
                } catch (Exception e) {
                        log.error(
                                        "An error occurred while deleting FavoriteProduct with idFavoriteListProducts: {} and idProduct: {}",
                                        idFavoriteListProducts, idProduct);
                        log.error("Detail Error: " + e);
                        // throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        // "INTERNAL_SERVER_ERROR Exceptions occur when deleting FavoriteProduct");
                        return new ResponseEntity<>("BAD_REQUEST Something Wrong!", HttpStatus.BAD_REQUEST);
                }

                return new ResponseEntity<>(HttpStatus.OK);
        }

        @Secured({ ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY })
        @DeleteMapping(URL_DELETE_BY_ID_FAVORITE_LIST_PRODUCTS)
        public ResponseEntity<?> deleteAllFavoriteProductMfoodysByIdFavoriteListProducts(@PathVariable("ID") int ID,
                        Principal principal) {
                log.info("Delete List of FavoriteProductMfoodys with idFavoriteListProducts: {} by {}", ID,
                                principal.getName());
                FavoriteListProductsMfoody favoriteListProductsMfoody = favoriteListProductsMfoodyInterfaceService
                                .getFavoriteListProductsMfoodyByID(ID);
                if (favoriteListProductsMfoody == null) {
                        return new ResponseEntity<>("NOT_FOUND FavoriteListProductsMfoody with ID: " + ID,
                                        HttpStatus.NOT_FOUND);
                }

                // Check if the current UserMfoody has role ADMIN or the owner of
                // FavoriteProductMfoody
                if (!applicationCheckAuthorController.checkAuthorization(principal,
                                favoriteListProductsMfoody.getUser().getIdUser())) {
                        return new ResponseEntity<>("FORBIDDEN Authorization failed!", HttpStatus.FORBIDDEN);
                }

                try {
                        favoriteProductMfoodyInterfaceService
                                        .deleteAllFavoriteProductsMfoodyByIdFavoriteListProducts(ID);
                } catch (Exception e) {
                        log.error("An error occurred while deleting List of FavoriteProductMfoodys with idFavoriteListProducts: "
                                        + ID);
                        log.error("Detail Error: " + e);
                        // throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        // "INTERNAL_SERVER_ERROR Exceptions occur when deleting List of
                        // FavoriteProductMfoodys");
                        return new ResponseEntity<>("BAD_REQUEST Something Wrong!", HttpStatus.BAD_REQUEST);
                }

                return new ResponseEntity<>(HttpStatus.OK);
        }

        @Secured({ ROLE_ADMIN_SECURITY })
        @DeleteMapping(URL_DELETE_BY_ID_PRODUCT)
        public ResponseEntity<?> deleteAllFavoriteProductMfoodysByIdProduct(@PathVariable("ID") int ID,
                        Principal principal) {
                log.info("Delete List of FavoriteProductMfoodys with idProduct: {} by {}", ID, principal.getName());
                if (productMfoodyInterfaceService.getProductMfoodyByID(ID) == null) {
                        return new ResponseEntity<>("NOT_FOUND ProductMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
                }

                try {
                        favoriteProductMfoodyInterfaceService.deleteAllFavoriteProductsMfoodyByIdProduct(ID);
                } catch (Exception e) {
                        log.error("An error occurred while deleting List of FavoriteProductMfoodys with idProduct: "
                                        + ID);
                        log.error("Detail Error: " + e);
                        // throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        // "INTERNAL_SERVER_ERROR Exceptions occur when deleting List of
                        // FavoriteProductMfoodys");
                        return new ResponseEntity<>("BAD_REQUEST Something Wrong!", HttpStatus.BAD_REQUEST);
                }

                return new ResponseEntity<>(HttpStatus.OK);
        }

        /*
         * 1. idFavoriteListProducts and idProduct in Json must be accurate,
         * salePrice/fullPrice in Json are ignored
         * 2. salePrice/fullPrice of FavoriteProductMfoody are fetched from
         * ProductMfoody
         */
        @Secured({ ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY })
        @PutMapping(URL_EDIT)
        public ResponseEntity<?> editFavoriteProductMfoody(@RequestBody String FavoriteProductPOJOJsonObject,
                        Principal principal) {
                log.info("Edit FavoriteProductMfoodys by {}", principal.getName());
                try {
                        // Convert JsonObject to FavoriteProductPOJO object
                        Gson gson = new Gson();
                        FavoriteProductMfoodyPOJO newFavoriteProductMfoodyPOJO = gson.fromJson(
                                        FavoriteProductPOJOJsonObject,
                                        FavoriteProductMfoodyPOJO.class);
                        FavoriteProductMfoody newFavoriteProductMfoody = favoriteProductMfoodyInterfaceService
                                        .getFavoriteProductMfoodyByIFavoriteListProductsAndIdProduct(
                                                        newFavoriteProductMfoodyPOJO.getIdFavoriteListProducts(),
                                                        newFavoriteProductMfoodyPOJO.getIdProduct());
                        ProductMfoody newProductMfoody = productMfoodyInterfaceService.getProductMfoodyByID(
                                        newFavoriteProductMfoodyPOJO.getIdProduct());
                        FavoriteListProductsMfoody newFavoriteListProductsMfoody = favoriteListProductsMfoodyInterfaceService
                                        .getFavoriteListProductsMfoodyByID(
                                                        newFavoriteProductMfoodyPOJO.getIdFavoriteListProducts());

                        // Check valid idFavoriteListProducts and idProduct
                        if (newProductMfoody == null) {
                                return new ResponseEntity<>(
                                                "NOT_FOUND ProductMfoody with ID: "
                                                                + newFavoriteProductMfoodyPOJO.getIdProduct(),
                                                HttpStatus.NOT_FOUND);
                        }
                        if (newFavoriteListProductsMfoody == null) {
                                return new ResponseEntity<>(
                                                "NOT_FOUND FavoriteListProductsMfoody with ID: "
                                                                + newFavoriteProductMfoodyPOJO
                                                                                .getIdFavoriteListProducts(),
                                                HttpStatus.NOT_FOUND);
                        }
                        if (newFavoriteProductMfoody == null) {
                                return new ResponseEntity<>(
                                                "NOT_FOUND FavoriteProductMfoody with idFavoriteListProducts: "
                                                                + newFavoriteProductMfoodyPOJO
                                                                                .getIdFavoriteListProducts()
                                                                + " and idProduct: "
                                                                + newFavoriteProductMfoodyPOJO.getIdProduct(),
                                                HttpStatus.NOT_FOUND);
                        }

                        // Check if the current UserMfoody has role ADMIN or the owner of
                        // FavoriteProductMfoody
                        if (!applicationCheckAuthorController.checkAuthorization(principal,
                                        newFavoriteListProductsMfoody.getUser().getIdUser())) {
                                return new ResponseEntity<>("FORBIDDEN Authorization failed!", HttpStatus.FORBIDDEN);
                        }

                        // Update IdFavoriteProductMfoody, ProductMfoody, and FavoriteListProductsMfoody
                        // newFavoriteProductMfoody.setQuantityFavoriteProduct(
                        // newFavoriteProductMfoodyPOJO.getQuantityFavoriteProduct());
                        newFavoriteProductMfoody.setProduct(newProductMfoody);
                        // newFavoriteProductMfoody.setSalePriceFavoriteProduct(newProductMfoody.getSalePriceProduct());
                        // newFavoriteProductMfoody.setFullPriceFavoriteProduct(newProductMfoody.getFullPriceProduct());

                        // Save to DB and Update associated FavoriteListProductsMfoody
                        favoriteProductMfoodyInterfaceService.updateFavoriteProductMfoody(newFavoriteProductMfoody);
                        log.info("FavoriteProductMfoody with ID: {} by {} is edited",
                                        newFavoriteProductMfoody.getIdFavoriteProductMFoody(), principal.getName());
                        // processFavoriteListProductsMfoody(newFavoriteProductMfoodyPOJO.getIdFavoriteListProducts());
                        return new ResponseEntity<>(HttpStatus.OK);
                } catch (Exception e) {
                        log.error("An error occurred while editing FavoriteProductMfoody");
                        log.error("Detail Error: " + e);
                        return new ResponseEntity<>("BAD_REQUEST Something Wrong!", HttpStatus.BAD_REQUEST);
                }
        }

        /*
         * 1. idFavoriteListProducts and idProduct in Json must be accurate,
         * salePrice/fullPrice in Json are ignored
         * 2. salePrice/fullPrice of FavoriteProductMfoody are fetched from
         * ProductMfoody
         * 3. The threat is any UserMfoodys can create FavoriteProductMfoody using
         * different idFavoriteListProducts and idProduct
         * (There is no double check)
         */
        @Secured({ ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY })
        @PostMapping(URL_ADD)
        public ResponseEntity<?> addNewFavoriteProductMfoody(@RequestBody String FavoriteProductPOJOJsonObject,
                        Principal principal) {
                log.info("Add FavoriteProductMfoodys by {}", principal.getName());
                try {
                        // Convert JsonObject to FavoriteProductPOJO object and Add new
                        // FavoriteListProductsMfoody and ProductMfoody to FavoriteProduct
                        Gson gson = new Gson();
                        FavoriteProductMfoodyPOJO newFavoriteProductMfoodyPOJO = gson.fromJson(
                                        FavoriteProductPOJOJsonObject,
                                        FavoriteProductMfoodyPOJO.class);
                        FavoriteProductMfoody newFavoriteProductMfoody = newFavoriteProductMfoodyPOJO
                                        .renderFavoriteProductMfoody();

                        // Check valid idFavoriteListProducts and idProduct
                        FavoriteProductMfoody checkNewFavoriteProductMfoody = favoriteProductMfoodyInterfaceService
                                        .getFavoriteProductMfoodyByIFavoriteListProductsAndIdProduct(
                                                        newFavoriteProductMfoodyPOJO.getIdFavoriteListProducts(),
                                                        newFavoriteProductMfoodyPOJO.getIdProduct());
                        ProductMfoody productMfoody = productMfoodyInterfaceService
                                        .getProductMfoodyByID(newFavoriteProductMfoodyPOJO.getIdProduct());
                        if (productMfoodyInterfaceService
                                        .getProductMfoodyByID(newFavoriteProductMfoodyPOJO.getIdProduct()) == null) {
                                return new ResponseEntity<>("NOT_FOUND ProductMfoody with ID: "
                                                + newFavoriteProductMfoodyPOJO.getIdProduct(), HttpStatus.NOT_FOUND);
                        }
                        if (favoriteListProductsMfoodyInterfaceService.getFavoriteListProductsMfoodyByID(
                                        newFavoriteProductMfoodyPOJO.getIdFavoriteListProducts()) == null) {
                                return new ResponseEntity<>("NOT_FOUND FavoriteListProductsMfoody with ID: "
                                                + newFavoriteProductMfoodyPOJO.getIdFavoriteListProducts(),
                                                HttpStatus.NOT_FOUND);
                        }

                        // If the FavoriteProductMfoody is already in the DB, update it
                        if (checkNewFavoriteProductMfoody != null) {
                                // checkNewFavoriteProductMfoody.setQuantityFavoriteProduct(
                                // checkNewFavoriteProductMfoody.getQuantityFavoriteProduct()
                                // + newFavoriteProductMfoodyPOJO.getQuantityFavoriteProduct());
                                // FavoriteProductMfoodyInterfaceService.saveFavoriteProductMfoody(checkNewFavoriteProductMfoody);
                                // processFavoriteListProductsMfoody(newFavoriteProductMfoodyPOJO.getIdFavoriteListProducts());
                                return new ResponseEntity<>("OK FavoriteProductMfoody updated", HttpStatus.OK);
                        }

                        // If the FavoriteProductMfoody is completely new, add new
                        newFavoriteProductMfoody.setIdFavoriteProductMfoody(
                                        new FavoriteProductMfoodyId(
                                                        newFavoriteProductMfoodyPOJO.getIdFavoriteListProducts(),
                                                        newFavoriteProductMfoodyPOJO.getIdProduct()));
                        newFavoriteProductMfoody.setProduct(productMfoody);
                        // newFavoriteProductMfoody.setSalePriceFavoriteProduct(productMfoody.getSalePriceProduct());
                        // newFavoriteProductMfoody.setFullPriceFavoriteProduct(productMfoody.getFullPriceProduct());
                        newFavoriteProductMfoody.setFavoriteListProducts(favoriteListProductsMfoodyInterfaceService
                                        .getFavoriteListProductsMfoodyByID(
                                                        newFavoriteProductMfoodyPOJO.getIdFavoriteListProducts()));

                        // Save to DB and Update associated FavoriteListProductsMfoody
                        favoriteProductMfoodyInterfaceService.saveFavoriteProductMfoody(newFavoriteProductMfoody);
                        log.info("A new FavoriteProductMfoody is created by " + principal.getName());
                        // processFavoriteListProductsMfoody(newFavoriteProductMfoodyPOJO.getIdFavoriteListProducts());
                        return new ResponseEntity<>(HttpStatus.CREATED);
                } catch (Exception e) {
                        log.error("An error occurred while adding FavoriteProductMfoody");
                        log.error("Detail Error: " + e);
                        return new ResponseEntity<>("BAD_REQUEST Something Wrong!", HttpStatus.BAD_REQUEST);
                }
        }

        @Secured({ ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY })
        @PostMapping(URL_UPDATE_PRODUCT_INFORMATION_IN_FAVORITE_LIST_PRODUCTS)
        public ResponseEntity<?> saveListFavoriteProductMfoodyToFavoriteListProducts(
                        @RequestBody String FavoriteListProductsJson, Principal principal) {
                log.info("Update List of FavoriteProductMfoodys by {}", principal.getName());
                try {
                        // Parse the JSON string into a list of FavoriteProductMfoodyPOJO objects
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<FavoriteProductMfoodyPOJO>>() {
                        }.getType();
                        List<FavoriteProductMfoodyPOJO> productList = gson.fromJson(FavoriteListProductsJson, listType);

                        for (FavoriteProductMfoodyPOJO product : productList) {
                                // Check if the product is already in the FavoriteListProducts
                                FavoriteProductMfoody existingProductFavoriteListProducts = favoriteProductMfoodyInterfaceService
                                                .getFavoriteProductMfoodyByIFavoriteListProductsAndIdProduct(
                                                                product.getIdFavoriteListProducts(),
                                                                product.getIdProduct());

                                if (existingProductFavoriteListProducts == null) {
                                        // If the product is not in the FavoriteListProducts, add a new product
                                        ResponseEntity<?> addResult = addNewFavoriteProductMfoody(gson.toJson(product),
                                                        principal);
                                        if (!addResult.getStatusCode().equals(HttpStatus.CREATED)) {
                                                return addResult;
                                        }
                                } else {
                                        // If the product is in the FavoriteListProducts, update the product information
                                        ResponseEntity<?> updateResult = editFavoriteProductMfoody(gson.toJson(product),
                                                        principal);
                                        if (!updateResult.getStatusCode().equals(HttpStatus.OK)) {
                                                return updateResult;
                                        }
                                }
                        }

                        return new ResponseEntity<>(HttpStatus.OK);
                } catch (Exception e) {
                        log.error("An error occurred while saving the FavoriteListProducts");
                        log.error("Detail Error: " + e);
                        return new ResponseEntity<>("BAD_REQUEST Something Wrong!", HttpStatus.BAD_REQUEST);
                }
        }

        // Update associated FavoriteListProductsMfoody
        // public void processFavoriteListProductsMfoody(int
        // idFavoriteListProductsMfoody) {
        // FavoriteListProductsMfoody FavoriteListProductsMfoody =
        // FavoriteListProductsMfoodyInterfaceService.getFavoriteListProductsMfoodyByID(idFavoriteListProductsMfoody);
        // int quantityAllProductInFavoriteListProducts = 0;
        // float totalSalePriceFavoriteListProducts = 0;
        // float totalFullPriceFavoriteListProducts = 0;

        // List<FavoriteProductMfoody> FavoriteProductMfoodyList =
        // FavoriteProductMfoodyInterfaceService.
        // getListFavoriteProductMfoodysByIdFavoriteListProducts(idFavoriteListProductsMfoody);
        // if (!FavoriteProductMfoodyList.isEmpty()) {
        // for (FavoriteProductMfoody element : FavoriteProductMfoodyList) {
        // quantityAllProductInFavoriteListProducts +=
        // element.getQuantityFavoriteProduct();
        // totalSalePriceFavoriteListProducts += element.getQuantityFavoriteProduct() *
        // element.getSalePriceFavoriteProduct();
        // totalFullPriceFavoriteListProducts += element.getQuantityFavoriteProduct() *
        // element.getFullPriceFavoriteProduct();
        // }
        // }

        // FavoriteListProductsMfoody.setQuantityAllProductsInFavoriteListProducts(quantityAllProductInFavoriteListProducts);
        // FavoriteListProductsMfoody.setTotalSalePriceFavoriteListProducts(totalSalePriceFavoriteListProducts);
        // FavoriteListProductsMfoody.setTotalFullPriceFavoriteListProducts(totalFullPriceFavoriteListProducts);

        // FavoriteListProductsMfoodyInterfaceService.updateFavoriteListProductsMfoody(FavoriteListProductsMfoody);
        // log.info("FavoriteListProductsMfoody with ID: {} is edited! ",
        // FavoriteListProductsMfoody.getIdFavoriteListProducts());
        // }
}
