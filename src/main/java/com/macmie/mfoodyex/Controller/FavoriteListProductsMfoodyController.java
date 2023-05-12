package com.macmie.mfoodyex.Controller;

import com.google.gson.Gson;
import com.macmie.mfoodyex.Model.FavoriteListProductsMfoody;
import com.macmie.mfoodyex.Model.UserMfoody;
import com.macmie.mfoodyex.POJO.FavoriteListProductsMfoodyPOJO;
import com.macmie.mfoodyex.Service.InterfaceService.FavoriteListProductsMfoodyInterfaceService;
import com.macmie.mfoodyex.Service.InterfaceService.UserMfoodyInterfaceService;
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
@RequestMapping(FAVORITE_LIST_PRODUCTS_MFOODY)
public class FavoriteListProductsMfoodyController {
    @Autowired
    private FavoriteListProductsMfoodyInterfaceService favoriteListProductsMfoodyInterfaceService;

    @Autowired
    private UserMfoodyInterfaceService userMfoodyInterfaceService;

    @Autowired
    private ApplicationCheckAuthorController applicationCheckAuthorController;

    @Secured({ ROLE_ADMIN_SECURITY })
    @GetMapping(URL_GET_ALL)
    public ResponseEntity<?> getAllFavoriteListProductsMfoodys(Principal principal) {
        log.info("Get List of FavoriteListProductsMfoodys by " + principal.getName());
        List<FavoriteListProductsMfoody> favoriteListProductsMfoodyList = favoriteListProductsMfoodyInterfaceService
                .getListFavoriteListProductsMfoodys();
        if (favoriteListProductsMfoodyList.isEmpty()) {
            return new ResponseEntity<>("NO_CONTENT List of FavoriteListProductsMfoodys", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(favoriteListProductsMfoodyList, HttpStatus.OK);
    }

    @Secured({ ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY })
    @GetMapping(URL_GET_BY_ID)
    public ResponseEntity<?> getFavoriteListProductsMfoodyByID(@PathVariable("ID") int ID, Principal principal) {
        log.info("Get FavoriteListProductsMfoody with ID: {} by {}", ID, principal.getName());
        FavoriteListProductsMfoody favoriteListProductsMfoody = favoriteListProductsMfoodyInterfaceService
                .getFavoriteListProductsMfoodyByID(ID);
        if (favoriteListProductsMfoody == null) {
            return new ResponseEntity<>("NOT_FOUND FavoriteListProductsMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }

        // Check if the current UserMfoody has role ADMIN or the owner of the
        // FavoriteListProductsMfoody
        if (!applicationCheckAuthorController.checkAuthorization(principal,
                favoriteListProductsMfoody.getUser().getIdUser())) {
            return new ResponseEntity<>("FORBIDDEN Authorization failed!", HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(favoriteListProductsMfoody, HttpStatus.OK);
    }

    @Secured({ ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY })
    @GetMapping(URL_GET_BY_ID_USER)
    public ResponseEntity<?> getFavoriteListProductsMfoodyByIdUser(@PathVariable("ID") int ID, Principal principal) {
        log.info("Get FavoriteListProductsMfoody with idUser: {} by {}", ID, principal.getName());
        if (userMfoodyInterfaceService.getUserMfoodyByID(ID) == null) {
            return new ResponseEntity<>("NOT_FOUND UserMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }

        // Check if the current UserMfoody has role ADMIN or the owner of the
        // FavoriteListProductsMfoody
        if (!applicationCheckAuthorController.checkAuthorization(principal, ID)) {
            return new ResponseEntity<>("FORBIDDEN Authorization failed!", HttpStatus.FORBIDDEN);
        }

        FavoriteListProductsMfoody favoriteListProductsMfoody = favoriteListProductsMfoodyInterfaceService
                .getFavoriteListProductsMfoodyByIdUser(ID);
        if (favoriteListProductsMfoody == null) {
            return new ResponseEntity<>("NOT_FOUND FavoriteListProductsMfoody with idUser: " + ID,
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(favoriteListProductsMfoody, HttpStatus.OK);
    }

    @Secured({ ROLE_ADMIN_SECURITY })
    @DeleteMapping(URL_DELETE)
    public ResponseEntity<?> deleteFavoriteListProductsMfoodyByID(@PathVariable("ID") int ID, Principal principal) {
        log.info("Delete FavoriteListProductsMfoody with ID: {} by {}", ID, principal.getName());
        FavoriteListProductsMfoody favoriteListProductsMfoody = favoriteListProductsMfoodyInterfaceService
                .getFavoriteListProductsMfoodyByID(ID);
        if (favoriteListProductsMfoody == null) {
            return new ResponseEntity<>("NOT_FOUND FavoriteListProductsMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }

        // Check if the current UserMfoody has role ADMIN or the owner of the
        // FavoriteListProductsMfoody
        // if(!applicationCheckAuthorController.checkAuthorization(principal,
        // FavoriteListProductsMfoody.getUser().getIdUser())){
        // return new ResponseEntity<>("FORBIDDEN Authorization failed!",
        // HttpStatus.FORBIDDEN);
        // }

        try {
            favoriteListProductsMfoodyInterfaceService.deleteFavoriteListProductsMfoodyByID(ID);
        } catch (Exception e) {
            log.error("An error occurred while deleting FavoriteListProductsMfoody with ID: " + ID);
            log.error("Detail Error: " + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "INTERNAL_SERVER_ERROR Exceptions occur when deleting FavoriteListProductsMfoody");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Secured({ ROLE_ADMIN_SECURITY })
    @DeleteMapping(URL_DELETE_BY_ID_USER)
    public ResponseEntity<?> deleteFavoriteListProductsMfoodyByIdUser(@PathVariable("ID") int ID, Principal principal) {
        log.info("Delete FavoriteListProductsMfoody with idUser: {} by {}", ID, principal.getName());
        if (userMfoodyInterfaceService.getUserMfoodyByID(ID) == null) {
            return new ResponseEntity<>("NOT_FOUND UserMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }

        // Check if the current UserMfoody has role ADMIN or the owner of the
        // FavoriteListProductsMfoody
        // if(!applicationCheckAuthorController.checkAuthorization(principal, ID)){
        // return new ResponseEntity<>("FORBIDDEN Authorization failed!",
        // HttpStatus.FORBIDDEN);
        // }

        try {
            favoriteListProductsMfoodyInterfaceService.deleteFavoriteListProductsMfoodyByIdUser(ID);
        } catch (Exception e) {
            log.error("An error occurred while deleting FavoriteListProductsMfoody with idUser: " + ID);
            log.error("Detail Error: " + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "INTERNAL_SERVER_ERROR Exceptions occur when deleting FavoriteListProductsMfoody");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
     * 1. idFavoriteListProducts in Json must be accurate,
     * idUser/quantityAllProductsInFavoriteListProducts/totalSalPrice/totalFullPrice
     * are ignored
     * 2. This API is quite redundant coz
     * quantityAllProductsInFavoriteListProducts/totalSalPrice/totalFullPrice (all
     * of its attributes)
     * will be updated later with APIs of DetailProductFavoriteListProductsMfoody
     * (coz the oneToMany table is always created first)
     */
    @Secured({ ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY })
    @PutMapping(URL_EDIT)
    public ResponseEntity<?> editFavoriteListProductsMfoody(
            @RequestBody String FavoriteListProductsMfoodyPOJOJsonObject, Principal principal) {
        try {
            // Convert JsonObject to FavoriteListProductsMfoodyPOJO object, Check input
            // idFavoriteListProducts and update FavoriteListProductsMfoody
            Gson gson = new Gson();
            FavoriteListProductsMfoodyPOJO newFavoriteListProductsMfoodyPOJO = gson
                    .fromJson(FavoriteListProductsMfoodyPOJOJsonObject, FavoriteListProductsMfoodyPOJO.class);
            if (!newFavoriteListProductsMfoodyPOJO.checkFavoriteListProductsMfoodyValidAttributes()) {
                return new ResponseEntity<>(
                        "BAD_REQUEST Invalid Input" + newFavoriteListProductsMfoodyPOJO.getIdFavoriteListProducts(),
                        HttpStatus.BAD_REQUEST);
            }

            FavoriteListProductsMfoody newFavoriteListProductsMfoody = favoriteListProductsMfoodyInterfaceService
                    .getFavoriteListProductsMfoodyByID(newFavoriteListProductsMfoodyPOJO.getIdFavoriteListProducts());
            if (newFavoriteListProductsMfoody == null) {
                return new ResponseEntity<>(
                        "NOT_FOUND FavoriteListProductsMfoody with ID: "
                                + newFavoriteListProductsMfoodyPOJO.getIdFavoriteListProducts(),
                        HttpStatus.NOT_FOUND);
            }

            // Check if the current UserMfoody has role ADMIN or the owner of the
            // FavoriteListProductsMfoody
            if (!applicationCheckAuthorController.checkAuthorization(principal,
                    newFavoriteListProductsMfoody.getUser().getIdUser())) {
                return new ResponseEntity<>("FORBIDDEN Authorization failed!", HttpStatus.FORBIDDEN);
            }

            // newFavoriteListProductsMfoody.setQuantityAllProductsInFavoriteListProducts(0);
            // newFavoriteListProductsMfoody.setTotalSalePriceFavoriteListProducts(0);
            // newFavoriteListProductsMfoody.setTotalFullPriceFavoriteListProducts(0);

            // Save to DB and return
            favoriteListProductsMfoodyInterfaceService.updateFavoriteListProductsMfoody(newFavoriteListProductsMfoody);
            log.info("FavoriteListProductsMfoody with ID: {} by {} is edited",
                    newFavoriteListProductsMfoody.getIdFavoriteListProducts(), principal.getName());
            return new ResponseEntity<>(newFavoriteListProductsMfoody, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred while editing FavoriteListProductsMfoody");
            log.error("Detail Error: " + e);
            return new ResponseEntity<>("BAD_REQUEST Something Wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    /*
     * 1. idUser in Json must be accurate,
     * quantityAllProductsInFavoriteListProducts/totalSalPrice/totalFullPrice are
     * ignored
     * 2. There is no need to create a new FavoriteListProductsMfoody coz every
     * UserMfoody will have one automatically right after
     * their account is created
     * 3. Every FavoriteListProductsMfoody is created with
     * quantityAllProductsInFavoriteListProducts/totalSalPrice/totalFullPrice = 0
     * and will be
     * updated later with APIs of DetailProductFavoriteListProductsMfoody coz the
     * oneToMany table is always created first
     */
    @Secured({ ROLE_ADMIN_SECURITY })
    @PostMapping(URL_ADD)
    public ResponseEntity<?> addNewFavoriteListProductsMfoody(
            @RequestBody String FavoriteListProductsMfoodyPOJOJsonObject, Principal principal) {
        try {
            // Convert JsonObject to CardMfoodyPOJO object and set default value
            Gson gson = new Gson();
            FavoriteListProductsMfoodyPOJO newFavoriteListProductsMfoodyPOJO = gson
                    .fromJson(FavoriteListProductsMfoodyPOJOJsonObject, FavoriteListProductsMfoodyPOJO.class);

            FavoriteListProductsMfoody newFavoriteListProductsMfoody = newFavoriteListProductsMfoodyPOJO
                    .renderFavoriteListProductsMfoody();
            // newFavoriteListProductsMfoody.setQuantityAllProductsInFavoriteListProducts(0);
            // newFavoriteListProductsMfoody.setTotalFullPriceFavoriteListProducts(0);
            // newFavoriteListProductsMfoody.setTotalSalePriceFavoriteListProducts(0);

            // Check input idUser and attach UserMfoody to FavoriteListProductsMfoody
            UserMfoody attachUserMfoody = userMfoodyInterfaceService
                    .getUserMfoodyByID(newFavoriteListProductsMfoodyPOJO.getIdUser());
            if (attachUserMfoody == null) {
                return new ResponseEntity<>(
                        "NOT_FOUND UserMfoody with ID: " + newFavoriteListProductsMfoodyPOJO.getIdUser(),
                        HttpStatus.NOT_FOUND);
            }

            // Check duplicate by idUser (UserMfoody and FavoriteListProductsMfoody have
            // one-to-one relationship)
            if (favoriteListProductsMfoodyInterfaceService
                    .getFavoriteListProductsMfoodyByIdUser(newFavoriteListProductsMfoodyPOJO.getIdUser()) != null) {
                return new ResponseEntity<>(
                        "CONFLICT - A FavoriteListProductsMfoody with the same idUser already exists!",
                        HttpStatus.CONFLICT);
            }

            newFavoriteListProductsMfoody.setUser(attachUserMfoody);

            // Save to DB and return (Updated FavoriteListProductsMfoody in DB could have ID
            // differs from user's request)
            favoriteListProductsMfoodyInterfaceService.saveFavoriteListProductsMfoody(newFavoriteListProductsMfoody);
            log.info("A new FavoriteListProductsMfoody is created by " + principal.getName());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("An error occurred while adding FavoriteListProductsMfoody");
            log.error("Detail Error: " + e);
            return new ResponseEntity<>("BAD_REQUEST Something Wrong!", HttpStatus.BAD_REQUEST);
        }
    }
}
