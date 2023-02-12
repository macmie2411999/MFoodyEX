package com.macmie.mfoodyex.Controller;

import com.google.gson.Gson;
import com.macmie.mfoodyex.Model.*;
import com.macmie.mfoodyex.POJO.DetailProductOrderMfoodyPOJO;
import com.macmie.mfoodyex.Repository.DetailProductCartMfoodyRepository;
import com.macmie.mfoodyex.Service.InterfaceService.DetailProductOrderMfoodyInterfaceService;
import com.macmie.mfoodyex.Service.InterfaceService.OrderMfoodyInterfaceService;
import com.macmie.mfoodyex.Service.InterfaceService.ProductMfoodyInterfaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
@RequestMapping(DETAIL_PRODUCT_ORDER_MFOODY)
public class DetailProductOrderMfoodyController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DetailProductOrderMfoodyInterfaceService detailProductOrderMfoodyInterfaceService;

    @Autowired
    private OrderMfoodyInterfaceService orderMfoodyInterfaceService;

    @Autowired
    private ProductMfoodyInterfaceService productMfoodyInterfaceService;

    @Autowired
    DetailProductCartMfoodyRepository detailProductCartMfoodyRepository;

    @Autowired
    private ApplicationCheckAuthorController applicationCheckAuthorController;

    @Secured({ROLE_ADMIN_SECURITY})
    @GetMapping(URL_GET_ALL)
    public ResponseEntity<?> getAllDetailProductOrderMfoodys(Principal principal) {
        log.info("Get List of DetailProductOrderMfoodys by " + principal.getName());
        List<DetailProductOrderMfoody> detailProductOrderMfoodyList =
                detailProductOrderMfoodyInterfaceService.getListDetailProductOrderMfoodys();
        if (detailProductOrderMfoodyList.isEmpty()) {
            return new ResponseEntity<>("NO_CONTENT List of DetailProductOrderMfoodys", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(detailProductOrderMfoodyList, HttpStatus.OK);
    }

    @Secured({ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY})
    @GetMapping(URL_GET_BY_ID_ORDER)
    public ResponseEntity<?> getAllDetailProductOrderMfoodysByIdOrder(@PathVariable("ID") int ID, Principal principal) {
        log.info("Get List of DetailProductOrderMfoodys with idOrder: {} by {}", ID, principal.getName());

        // Check if the current UserMfoody has role ADMIN or the owner of DetailProductOrderMfoody
        if(!applicationCheckAuthorController.checkAuthorization(principal,
                orderMfoodyInterfaceService.getOrderMfoodyByID(ID).getUser().getIdUser())){
            return  new ResponseEntity<>("FORBIDDEN Authorization failed!", HttpStatus.FORBIDDEN);
        }

        List<DetailProductOrderMfoody> detailProductOrderMfoodyList =
                detailProductOrderMfoodyInterfaceService.getListDetailProductOrderMfoodysByIdOrder(ID);
        if (detailProductOrderMfoodyList.isEmpty()) {
            return new ResponseEntity<>("NO_CONTENT List of DetailProductOrderMfoodys with idOrder: " + ID,
                    HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(detailProductOrderMfoodyList, HttpStatus.OK);
    }

    @Secured({ROLE_ADMIN_SECURITY})
    @GetMapping(URL_GET_BY_ID_PRODUCT)
    public ResponseEntity<?> getAllDetailProductOrderMfoodysByIdProduct(@PathVariable("ID") int ID, Principal principal) {
        log.info("Get List of DetailProductCartMfoodys with idProduct: {} by {}", ID, principal.getName());
        List<DetailProductOrderMfoody> detailProductOrderMfoodyList =
                detailProductOrderMfoodyInterfaceService.getListDetailProductOrderMfoodysByIdProduct(ID);
        if (detailProductOrderMfoodyList.isEmpty()) {
            return new ResponseEntity<>(
                    "NO_CONTENT List of DetailProductOrderMfoodys with idProduct: " + ID, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(detailProductOrderMfoodyList, HttpStatus.OK);
    }

    @Secured({ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY})
    @GetMapping(URL_GET_BY_ID_ORDER_AND_ID_PRODUCT)
    public ResponseEntity<?> getDetailProductOrderMfoodyByID(@PathVariable("IdOrder") int idOrder,
                                                             @PathVariable("IdProduct") int idProduct,
                                                             Principal principal) {
        log.info("Get DetailProductOrderMfoody with idOrder: {} and idProduct: {} by {}", idOrder, idProduct, principal.getName());

        // Check if the current UserMfoody has role ADMIN or the owner of DetailProductOrderMfoody
        if(!applicationCheckAuthorController.checkAuthorization(principal,
                orderMfoodyInterfaceService.getOrderMfoodyByID(idOrder).getUser().getIdUser())){
            return  new ResponseEntity<>("FORBIDDEN Authorization failed!", HttpStatus.FORBIDDEN);
        }

        DetailProductOrderMfoody detailProductOrderMfoody =
                detailProductOrderMfoodyInterfaceService.getDetailProductOrderMfoodyByIOrderAndIdProduct(idOrder, idProduct);
        if (detailProductOrderMfoody == null) {
            return new ResponseEntity<>(
                    "NOT_FOUND DetailProductOrderMfoody with idOrder: " + idOrder + ", idProduct: " + idProduct,
                    HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(detailProductOrderMfoody, HttpStatus.OK);
    }

    @Secured({ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY})
    @DeleteMapping(URL_DELETE_BY_ID_ORDER_AND_ID_PRODUCT)
    public ResponseEntity<?> deleteDetailProductOrderMfoodyByID(@PathVariable("IdOrder") int idOrder,
                                                                @PathVariable("IdProduct") int idProduct,
                                                                Principal principal) {
        log.info("Delete DetailProductOrderMfoody with idCart: {} and idProduct: {} by {}",
                idOrder, idProduct, principal.getName());
        DetailProductOrderMfoody detailProductOrderMfoody =
                detailProductOrderMfoodyInterfaceService.getDetailProductOrderMfoodyByIOrderAndIdProduct(
                        idOrder, idProduct);
        if (detailProductOrderMfoody == null) {
            return new ResponseEntity<>(
                    "NOT_FOUND DetailProductOrderMfoody with idOrder: " + idOrder + ", idProduct: " + idProduct,
                    HttpStatus.NOT_FOUND);
        }

        // Check if the current UserMfoody has role ADMIN or the owner of DetailProductOrderMfoody
        OrderMfoody orderMfoody = orderMfoodyInterfaceService.getOrderMfoodyByID(idOrder);
        if(orderMfoody != null){
            if(!applicationCheckAuthorController.checkAuthorization(principal, orderMfoody.getUser().getIdUser())){
                return  new ResponseEntity<>("FORBIDDEN Authorization failed!", HttpStatus.FORBIDDEN);
            }
        }

        try {
            detailProductOrderMfoodyInterfaceService.deleteDetailProductOrderMfoodyByIdDetailProductOrderMfoody(
                    idOrder, idProduct);
        } catch (Exception e) {
            log.error("An error occurred while deleting DetailProductOrder with idOrder: {} and idProduct: {}",
                    idOrder, idProduct);
            log.error("Detail Error: " + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "INTERNAL_SERVER_ERROR Exceptions occur when deleting DetailProductOrder");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Secured({ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY})
    @DeleteMapping(URL_DELETE_BY_ID_ORDER)
    public ResponseEntity<?> deleteAllDetailProductOrderMfoodysByIdOrder(@PathVariable("ID") int ID,
                                                                         Principal principal) {
        log.info("Delete List of DetailProductOrderMfoodys with idOrder: {} by {}", ID, principal.getName());
        OrderMfoody orderMfoody = orderMfoodyInterfaceService.getOrderMfoodyByID(ID);
        if (orderMfoody == null) {
            return new ResponseEntity<>("NOT_FOUND OrderMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }

        // Check if the current UserMfoody has role ADMIN or the owner of DetailProductOrderMfoody
        if(!applicationCheckAuthorController.checkAuthorization(principal, orderMfoody.getUser().getIdUser())){
            return  new ResponseEntity<>("FORBIDDEN Authorization failed!", HttpStatus.FORBIDDEN);
        }

        try {
            detailProductOrderMfoodyInterfaceService.deleteAllDetailProductOrdersMfoodyByIdOrder(ID);
        } catch (Exception e) {
            log.error("An error occurred while deleting List of DetailProductOrderMfoodys with idOrder: " + ID);
            log.error("Detail Error: " + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "INTERNAL_SERVER_ERROR Exceptions occur when deleting List of DetailProductOrderMfoodys");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Secured({ROLE_ADMIN_SECURITY})
    @DeleteMapping(URL_DELETE_BY_ID_PRODUCT)
    public ResponseEntity<?> deleteAllDetailProductOrderMfoodysByIdProduct(@PathVariable("ID") int ID,
                                                                           Principal principal) {
        log.info("Delete List of DetailProductOrderMfoodys with idProduct: {} by {}", ID, principal.getName());
        if (productMfoodyInterfaceService.getProductMfoodyByID(ID) == null) {
            return new ResponseEntity<>("NOT_FOUND ProductMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }

        try {
            detailProductOrderMfoodyInterfaceService.deleteAllDetailProductOrdersMfoodyByIdProduct(ID);
        } catch (Exception e) {
            log.error("An error occurred while deleting List of DetailProductCartMfoodys with idProduct: " + ID);
            log.error("Detail Error: " + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "INTERNAL_SERVER_ERROR Exceptions occur when deleting List of DetailProductCartMfoodys");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
     * 1. idOrder and idProduct in Json must be accurate, salePrice/fullPrice in Json are ignored
     * 2. salePrice/fullPrice of DetailProductOrderMfoody are fetched from ProductMfoody
     * */
    @Secured({ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY})
    @PutMapping(URL_EDIT)
    public ResponseEntity<?> editDetailProductOrderMfoody(@RequestBody String detailProductOrderPOJOJsonObject,
                                                          Principal principal) {
        try {
            // Convert JsonObject to DetailProductOrderPOJO object
            Gson gson = new Gson();
            DetailProductOrderMfoodyPOJO newDetailProductOrderMfoodyPOJO =
                    gson.fromJson(detailProductOrderPOJOJsonObject, DetailProductOrderMfoodyPOJO.class);
            DetailProductOrderMfoody newDetailProductOrderMfoody = detailProductOrderMfoodyInterfaceService.
                    getDetailProductOrderMfoodyByIOrderAndIdProduct(
                            newDetailProductOrderMfoodyPOJO.getIdOrder(),
                            newDetailProductOrderMfoodyPOJO.getIdProduct());
            ProductMfoody newProductMfoody = productMfoodyInterfaceService.getProductMfoodyByID(
                    newDetailProductOrderMfoodyPOJO.getIdProduct());
            OrderMfoody newOrderMfoody = orderMfoodyInterfaceService.getOrderMfoodyByID(
                    newDetailProductOrderMfoodyPOJO.getIdOrder());

            // Check valid idOrder and idProduct
            if (newProductMfoody == null) {
                return new ResponseEntity<>(
                        "NOT_FOUND ProductMfoody with ID: " + newDetailProductOrderMfoodyPOJO.getIdProduct(),
                        HttpStatus.NOT_FOUND);
            }
            if (newOrderMfoody == null) {
                return new ResponseEntity<>(
                        "NOT_FOUND OrderMfoody with ID: " + newDetailProductOrderMfoodyPOJO.getIdOrder(),
                        HttpStatus.NOT_FOUND);
            }
            if (newDetailProductOrderMfoody == null) {
                return new ResponseEntity<>(
                        "NOT_FOUND DetailProductOrderMfoody with idOrder: "
                                + newDetailProductOrderMfoodyPOJO.getIdOrder()
                                + " and idProduct: " + newDetailProductOrderMfoodyPOJO.getIdProduct(),
                        HttpStatus.NOT_FOUND);
            }

            // Check if the current UserMfoody has role ADMIN or the owner of DetailProductOrderMfoody
            if(!applicationCheckAuthorController.checkAuthorization(principal, newOrderMfoody.getUser().getIdUser())){
                return  new ResponseEntity<>("FORBIDDEN Authorization failed!", HttpStatus.FORBIDDEN);
            }

            // Add IdDetailProductOrderMfoody, ProductMfoody, and OrderMfoody; re-assign values for salePrice/fullPrice from ProductMfoody
            newDetailProductOrderMfoody.setQuantityDetailProductOrder(
                    newDetailProductOrderMfoodyPOJO.getQuantityDetailProductOrder());
            newDetailProductOrderMfoody.setProduct(newProductMfoody);
            newDetailProductOrderMfoody.setSalePriceDetailProductOrder(newProductMfoody.getSalePriceProduct());
            newDetailProductOrderMfoody.setFullPriceDetailProductOrder(newProductMfoody.getFullPriceProduct());

            // Save to DB and Update associated OrderMfoody
            detailProductOrderMfoodyInterfaceService.updateDetailProductOrderMfoody(newDetailProductOrderMfoody);
            log.info("DetailProductOrderMfoody with ID: {} by {} is edited",
                    newDetailProductOrderMfoody.getIdDetailProductOrderMfoody(), principal.getName());
            processOrderMfoody(newDetailProductOrderMfoodyPOJO.getIdOrder());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred while editing DetailProductOrderMfoody");
            log.error("Detail Error: " + e);
            return new ResponseEntity<>("BAD_REQUEST Something Wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    /*
     * 1. idOrder and idProduct in Json must be accurate, salePrice/fullPrice in Json are ignored
     * 2. salePrice/fullPrice of DetailProductOrderMfoody are fetched from ProductMfoody
     * 3. The threat is any UserMfoodys can create DetailProductCartMfoody using different idCart and idProduct
     *    (There is no double check)
     * */
    @Secured({ROLE_ADMIN_SECURITY, ROLE_USER_SECURITY})
    @PostMapping(URL_ADD)
    public ResponseEntity<?> addNewDetailProductOrderMfoody(@RequestBody String detailProductOrderPOJOJsonObject,
                                                            Principal principal) {
        try {
            // Convert JsonObject to DetailProductOrderPOJO object
            Gson gson = new Gson();
            DetailProductOrderMfoodyPOJO newDetailProductOrderMfoodyPOJO =
                    gson.fromJson(detailProductOrderPOJOJsonObject, DetailProductOrderMfoodyPOJO.class);
            DetailProductOrderMfoody newDetailProductOrderMfoody =
                    newDetailProductOrderMfoodyPOJO.renderDetailProductOrderMfoody();

            // Check valid idOrder and idProduct
            DetailProductOrderMfoody checkNewDetailProductOrderMfoody = detailProductOrderMfoodyInterfaceService.
                    getDetailProductOrderMfoodyByIOrderAndIdProduct(
                            newDetailProductOrderMfoodyPOJO.getIdOrder(),
                            newDetailProductOrderMfoodyPOJO.getIdProduct());
            ProductMfoody productMfoody = productMfoodyInterfaceService.
                    getProductMfoodyByID(newDetailProductOrderMfoodyPOJO.getIdProduct());
            if (productMfoodyInterfaceService.getProductMfoodyByID(
                    newDetailProductOrderMfoodyPOJO.getIdProduct()) == null) {
                return new ResponseEntity<>(
                        "NOT_FOUND ProductMfoody with ID: " + newDetailProductOrderMfoodyPOJO.getIdProduct(),
                        HttpStatus.NOT_FOUND);
            }
            if (orderMfoodyInterfaceService.getOrderMfoodyByID(
                    newDetailProductOrderMfoodyPOJO.getIdOrder()) == null) {
                return new ResponseEntity<>(
                        "NOT_FOUND OrderMfoody with ID: " + newDetailProductOrderMfoodyPOJO.getIdOrder(),
                        HttpStatus.NOT_FOUND);
            }

            // If the DetailProductCartMfoody is already in the DB, update it and update associated OrderMfoody
            if (checkNewDetailProductOrderMfoody != null) {
                checkNewDetailProductOrderMfoody.setQuantityDetailProductOrder(
                        checkNewDetailProductOrderMfoody.getQuantityDetailProductOrder()
                                + newDetailProductOrderMfoodyPOJO.getQuantityDetailProductOrder());
                detailProductOrderMfoodyInterfaceService.saveDetailProductOrderMfoody(
                        checkNewDetailProductOrderMfoody);
                processOrderMfoody(newDetailProductOrderMfoodyPOJO.getIdOrder());
                return new ResponseEntity<>("OK DetailProductOrderMfoody updated", HttpStatus.OK);
            }

            // If the DetailProductCartMfoody is completely new, add new
            newDetailProductOrderMfoody.setIdDetailProductOrderMfoody(new
                    DetailProductOrderMfoodyId(newDetailProductOrderMfoodyPOJO.getIdOrder(),
                    newDetailProductOrderMfoodyPOJO.getIdProduct()));
            newDetailProductOrderMfoody.setProduct(productMfoody);
            newDetailProductOrderMfoody.setFullPriceDetailProductOrder(productMfoody.getFullPriceProduct());
            newDetailProductOrderMfoody.setSalePriceDetailProductOrder(productMfoody.getSalePriceProduct());
            newDetailProductOrderMfoody.setOrder(orderMfoodyInterfaceService.
                    getOrderMfoodyByID(newDetailProductOrderMfoodyPOJO.getIdOrder()));

            // Save to DB and Update associated OrderMfoody
            // entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=0").executeUpdate();
            detailProductOrderMfoodyInterfaceService.saveDetailProductOrderMfoody(newDetailProductOrderMfoody);
            log.info("A new DetailProductOrderMfoody is created by " + principal.getName());
            processOrderMfoody(newDetailProductOrderMfoodyPOJO.getIdOrder());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("An error occurred while adding DetailProductOrderMfoody");
            log.error("Detail Error: " + e);
            return new ResponseEntity<>("BAD_REQUEST Something Wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    // Update associated OrderMfoody
    public void processOrderMfoody(int idOrderMfoody) {
        OrderMfoody orderMfoody = orderMfoodyInterfaceService.getOrderMfoodyByID(idOrderMfoody);
        int quantityAllProductInOrder = 0;
        float totalSalePriceOrder = 0;
        float totalFullPriceOrder = 0;
        List<DetailProductOrderMfoody> detailProductOrderMfoodyList = detailProductOrderMfoodyInterfaceService.
                getListDetailProductOrderMfoodysByIdOrder(idOrderMfoody);
        if (!detailProductOrderMfoodyList.isEmpty()) {
            for (DetailProductOrderMfoody element : detailProductOrderMfoodyList) {
                quantityAllProductInOrder += element.getQuantityDetailProductOrder();
                totalSalePriceOrder += element.getQuantityDetailProductOrder() * element.getSalePriceDetailProductOrder();
                totalFullPriceOrder += element.getQuantityDetailProductOrder() * element.getFullPriceDetailProductOrder();
            }
        }

        orderMfoody.setQuantityAllProductsInOrder(quantityAllProductInOrder);
        orderMfoody.setTotalSalePriceOrder(totalSalePriceOrder);
        orderMfoody.setTotalFullPriceOrder(totalFullPriceOrder);

        orderMfoodyInterfaceService.updateOrderMfoody(orderMfoody);
        log.info("OrderMfoody with ID: {} is edited! ", orderMfoody.getIdOrder());
    }

    public void processOrderMfoody(int idOrder, DetailProductOrderMfoody newDetailProductOrderMfoody) {
        OrderMfoody orderMfoody = orderMfoodyInterfaceService.getOrderMfoodyByID(idOrder);
        orderMfoody.setQuantityAllProductsInOrder(orderMfoody.getQuantityAllProductsInOrder()
                + newDetailProductOrderMfoody.getQuantityDetailProductOrder());
        orderMfoody.setTotalSalePriceOrder(orderMfoody.getTotalSalePriceOrder()
                + newDetailProductOrderMfoody.getQuantityDetailProductOrder()
                * newDetailProductOrderMfoody.getSalePriceDetailProductOrder());
        orderMfoody.setTotalFullPriceOrder(orderMfoody.getTotalFullPriceOrder()
                + newDetailProductOrderMfoody.getQuantityDetailProductOrder()
                * newDetailProductOrderMfoody.getFullPriceDetailProductOrder());
        orderMfoodyInterfaceService.updateOrderMfoody(orderMfoody);
        newDetailProductOrderMfoody.setOrder(orderMfoody);
    }
}

