package com.macmie.mfoodyex.Controller;

import com.google.gson.Gson;
import com.macmie.mfoodyex.Model.*;
import com.macmie.mfoodyex.POJO.DetailProductOrderMfoodyPOJO;
import com.macmie.mfoodyex.POJO.DetailProductOrderMfoodyPOJO;
import com.macmie.mfoodyex.Service.InterfaceService.DetailProductOrderMfoodyInterfaceService;
import com.macmie.mfoodyex.Service.InterfaceService.OrderMfoodyInterfaceService;
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
@RequestMapping(DETAIL_PRODUCT_ORDER_MFOODY)
public class DetailProductOrderMfoodyController {
    @Autowired
    private DetailProductOrderMfoodyInterfaceService detailProductOrderMfoodyInterfaceService;

    @Autowired
    private OrderMfoodyInterfaceService orderMfoodyInterfaceService;

    @Autowired
    private ProductMfoodyInterfaceService productMfoodyInterfaceService;

    @GetMapping(URL_GET_ALL)
    public ResponseEntity<?> getAllDetailProductOrderMfoodys() {
        List<DetailProductOrderMfoody> detailProductOrderMfoodyList = detailProductOrderMfoodyInterfaceService.getListDetailProductOrderMfoodys();
        if (detailProductOrderMfoodyList.isEmpty()) {
            return new ResponseEntity<>("NO_CONTENT List of DetailProductOrderMfoodys", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(detailProductOrderMfoodyList, HttpStatus.OK);
    }

    @GetMapping(URL_GET_BY_ID_ORDER)
    public ResponseEntity<?> getAllDetailProductOrderMfoodysByIdOrder(@PathVariable("ID") int ID) {
        List<DetailProductOrderMfoody> detailProductOrderMfoodyList = detailProductOrderMfoodyInterfaceService.getListDetailProductOrderMfoodysByIdOrder(ID);
        if (detailProductOrderMfoodyList.isEmpty()) {
            return new ResponseEntity<>("NO_CONTENT List of DetailProductOrderMfoodys with idOrder: " + ID, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(detailProductOrderMfoodyList, HttpStatus.OK);
    }

    @GetMapping(URL_GET_BY_ID_PRODUCT)
    public ResponseEntity<?> getAllDetailProductOrderMfoodysByIdProduct(@PathVariable("ID") int ID) {
        List<DetailProductOrderMfoody> detailProductOrderMfoodyList = detailProductOrderMfoodyInterfaceService.getListDetailProductOrderMfoodysByIdProduct(ID);
        if (detailProductOrderMfoodyList.isEmpty()) {
            return new ResponseEntity<>("NO_CONTENT List of DetailProductOrderMfoodys with idProduct: " + ID, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(detailProductOrderMfoodyList, HttpStatus.OK);
    }

    @GetMapping(URL_GET_BY_ID_ORDER_AND_ID_PRODUCT)
    public ResponseEntity<?> getDetailProductOrderMfoodyByID(@PathVariable("IdOrder") int idOrder, @PathVariable("IdProduct") int idProduct) {
        DetailProductOrderMfoody detailProductOrderMfoody = detailProductOrderMfoodyInterfaceService.getDetailProductOrderMfoodyByIOrderAndIdProduct(idOrder, idProduct);
        if (detailProductOrderMfoody == null) {
            return new ResponseEntity<>("NOT_FOUND DetailProductOrderMfoody with idOrder: " + idOrder + ", idProduct: " + idProduct, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(detailProductOrderMfoody, HttpStatus.OK);
    }

    @DeleteMapping(URL_DELETE_BY_ID_ORDER_AND_ID_PRODUCT)
    public ResponseEntity<?> deleteDetailProductOrderMfoodyByID(@PathVariable("IdOrder") int idOrder, @PathVariable("IdProduct") int idProduct) {
        DetailProductOrderMfoody detailProductOrderMfoody = detailProductOrderMfoodyInterfaceService.getDetailProductOrderMfoodyByIOrderAndIdProduct(idOrder, idProduct);
        if (detailProductOrderMfoody == null) {
            return new ResponseEntity<>("NOT_FOUND DetailProductOrderMfoody with idOrder: " + idOrder + ", idProduct: " + idProduct, HttpStatus.NOT_FOUND);
        }
        detailProductOrderMfoodyInterfaceService.deleteDetailProductOrderMfoodyByIdDetailProductOrderMfoody(idOrder, idProduct);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(URL_DELETE_BY_ID_ORDER)
    public ResponseEntity<?> deleteDetailProductOrderMfoodyByIdOrder(@PathVariable("ID") int ID) {
        if (orderMfoodyInterfaceService.getOrderMfoodyByID(ID) == null) {
            return new ResponseEntity<>("NOT_FOUND OrderMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }
        detailProductOrderMfoodyInterfaceService.deleteAllDetailProductOrdersMfoodyByIdOrder(ID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(URL_DELETE_BY_ID_PRODUCT)
    public ResponseEntity<?> deleteDetailProductOrderMfoodyByIdProduct(@PathVariable("ID") int ID) {
        if (productMfoodyInterfaceService.getProductMfoodyByID(ID) == null) {
            return new ResponseEntity<>("NOT_FOUND ProductMfoody with ID: " + ID, HttpStatus.NOT_FOUND);
        }
        detailProductOrderMfoodyInterfaceService.deleteAllDetailProductOrdersMfoodyByIdProduct(ID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
     * 1. idOrder and idProduct in Json must be accurate, salePrice/fullPrice in Json are ignored
     * 2. salePrice/fullPrice of DetailProductOrderMfoody are fetched from ProductMfoody
     * */
    @PutMapping(URL_EDIT)
    public ResponseEntity<?> editDetailProductOrderMfoody(@RequestBody String detailProductOrderPOJOJsonObject, BindingResult errors) {
        // Check Error
        if (errors.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to DetailProductOrderPOJO object
        Gson gson = new Gson();
        DetailProductOrderMfoodyPOJO newDetailProductOrderMfoodyPOJO = gson.fromJson(detailProductOrderPOJOJsonObject, DetailProductOrderMfoodyPOJO.class);
        DetailProductOrderMfoody newDetailProductOrderMfoody = newDetailProductOrderMfoodyPOJO.renderDetailProductOrderMfoody();

        // Check valid idOrder and idProduct
        if (productMfoodyInterfaceService.getProductMfoodyByID(newDetailProductOrderMfoodyPOJO.getIdProduct()) == null) {
            return new ResponseEntity<>("NOT_FOUND ProductMfoody with ID: " + newDetailProductOrderMfoodyPOJO.getIdProduct(), HttpStatus.NOT_FOUND);
        }
        if (orderMfoodyInterfaceService.getOrderMfoodyByID(newDetailProductOrderMfoodyPOJO.getIdOrder()) == null) {
            return new ResponseEntity<>("NOT_FOUND OrderMfoody with ID: " + newDetailProductOrderMfoodyPOJO.getIdOrder(), HttpStatus.NOT_FOUND);
        }
        if (detailProductOrderMfoodyInterfaceService.getDetailProductOrderMfoodyByIOrderAndIdProduct(
                newDetailProductOrderMfoodyPOJO.getIdOrder(),
                newDetailProductOrderMfoodyPOJO.getIdProduct()) == null) {
            return new ResponseEntity<>(
                    "NOT_FOUND DetailProductOrderMfoody with idOrder: " + newDetailProductOrderMfoodyPOJO.getIdOrder()
                            + " and idProduct: " + newDetailProductOrderMfoodyPOJO.getIdProduct(), HttpStatus.NOT_FOUND);
        }

        // Add IdDetailProductOrderMfoody, ProductMfoody, and OrderMfoody; re-assign values for salePrice/fullPrice from ProductMfoody
        newDetailProductOrderMfoody.setIdDetailProductOrderMfoody(new
                DetailProductOrderMfoodyId(newDetailProductOrderMfoodyPOJO.getIdOrder(),
                newDetailProductOrderMfoodyPOJO.getIdProduct()));
        ProductMfoody productMfoody = productMfoodyInterfaceService.getProductMfoodyByID(
                newDetailProductOrderMfoodyPOJO.getIdProduct());
        newDetailProductOrderMfoody.setProduct(productMfoody);
        newDetailProductOrderMfoody.setFullPriceDetailProductOrder(productMfoody.getFullPriceProduct());
        newDetailProductOrderMfoody.setSalePriceDetailProductOrder(productMfoody.getSalePriceProduct());
        processOrderMfoody(newDetailProductOrderMfoodyPOJO, newDetailProductOrderMfoody);

        // Save to DB and return
        detailProductOrderMfoodyInterfaceService.updateDetailProductOrderMfoody(newDetailProductOrderMfoody);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
     * 1. idOrder and idProduct in Json must be accurate, salePrice/fullPrice in Json are ignored
     * 2. salePrice/fullPrice of DetailProductOrderMfoody are fetched from ProductMfoody
     * */
    @PostMapping(URL_ADD)
    public ResponseEntity<?> addNewDetailProductOrderMfoody(@RequestBody String detailProductOrderPOJOJsonObject, BindingResult errors) {
        // Check Error
        if (errors.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Convert JsonObject to DetailProductOrderPOJO object
        Gson gson = new Gson();
        DetailProductOrderMfoodyPOJO newDetailProductOrderMfoodyPOJO = gson.fromJson(detailProductOrderPOJOJsonObject, DetailProductOrderMfoodyPOJO.class);
        DetailProductOrderMfoody newDetailProductOrderMfoody = newDetailProductOrderMfoodyPOJO.renderDetailProductOrderMfoody();

        // Check valid idOrder and idProduct
        if (productMfoodyInterfaceService.getProductMfoodyByID(newDetailProductOrderMfoodyPOJO.getIdProduct()) == null) {
            return new ResponseEntity<>("NOT_FOUND ProductMfoody with ID: " + newDetailProductOrderMfoodyPOJO.getIdProduct(), HttpStatus.NOT_FOUND);
        }
        if (orderMfoodyInterfaceService.getOrderMfoodyByID(newDetailProductOrderMfoodyPOJO.getIdOrder()) == null) {
            return new ResponseEntity<>("NOT_FOUND OrderMfoody with ID: " + newDetailProductOrderMfoodyPOJO.getIdOrder(), HttpStatus.NOT_FOUND);
        }

        // Add IdDetailProductOrderMfoody, ProductMfoody, and OrderMfoody; re-assign values for salePrice/fullPrice from ProductMfoody
        newDetailProductOrderMfoody.setIdDetailProductOrderMfoody(new
                DetailProductOrderMfoodyId(newDetailProductOrderMfoodyPOJO.getIdOrder(),
                newDetailProductOrderMfoodyPOJO.getIdProduct()));
        ProductMfoody productMfoody = productMfoodyInterfaceService.getProductMfoodyByID(
                newDetailProductOrderMfoodyPOJO.getIdProduct());
        newDetailProductOrderMfoody.setProduct(productMfoody);
        newDetailProductOrderMfoody.setFullPriceDetailProductOrder(productMfoody.getFullPriceProduct());
        newDetailProductOrderMfoody.setSalePriceDetailProductOrder(productMfoody.getSalePriceProduct());
        processOrderMfoody(newDetailProductOrderMfoodyPOJO, newDetailProductOrderMfoody);

        // Save to DB and return (Updated Order in DB could have ID differs from user's request)
        detailProductOrderMfoodyInterfaceService.saveDetailProductOrderMfoody(newDetailProductOrderMfoody);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public void processOrderMfoody(DetailProductOrderMfoodyPOJO newDetailProductOrderMfoodyPOJO, DetailProductOrderMfoody newDetailProductOrderMfoody) {
        DetailProductOrderMfoody oldDetailProductOrderMfoody = detailProductOrderMfoodyInterfaceService.getDetailProductOrderMfoodyByIOrderAndIdProduct(
                newDetailProductOrderMfoodyPOJO.getIdOrder(), newDetailProductOrderMfoodyPOJO.getIdProduct());
        OrderMfoody newOrderMfoody = orderMfoodyInterfaceService.getOrderMfoodyByID(newDetailProductOrderMfoodyPOJO.getIdOrder());
        newOrderMfoody.setQuantityAllProductsInOrder(newOrderMfoody.getQuantityAllProductsInOrder()
                + newDetailProductOrderMfoody.getQuantityDetailProductOrder() - oldDetailProductOrderMfoody.getQuantityDetailProductOrder());
        newOrderMfoody.setTotalSalePriceOrder(newOrderMfoody.getTotalSalePriceOrder()
                + newDetailProductOrderMfoody.getSalePriceDetailProductOrder() - oldDetailProductOrderMfoody.getSalePriceDetailProductOrder());
        newOrderMfoody.setTotalFullPriceOrder(newOrderMfoody.getTotalFullPriceOrder()
                + newDetailProductOrderMfoody.getFullPriceDetailProductOrder() - oldDetailProductOrderMfoody.getFullPriceDetailProductOrder());
        orderMfoodyInterfaceService.updateOrderMfoody(newOrderMfoody);
        newDetailProductOrderMfoody.setOrder(newOrderMfoody);
    }
}
