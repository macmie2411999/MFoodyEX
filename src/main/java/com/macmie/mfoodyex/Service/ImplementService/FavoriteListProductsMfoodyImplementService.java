package com.macmie.mfoodyex.Service.ImplementService;

import com.macmie.mfoodyex.Model.FavoriteListProductsMfoody;
import com.macmie.mfoodyex.Model.FavoriteProductMfoody;
import com.macmie.mfoodyex.Model.OrderMfoody;
import com.macmie.mfoodyex.Repository.FavoriteListProductsMfoodyRepository;
import com.macmie.mfoodyex.Repository.FavoriteProductMfoodyRepository;
import com.macmie.mfoodyex.Repository.UserMfoodyRepository;
import com.macmie.mfoodyex.Service.InterfaceService.FavoriteListProductsMfoodyInterfaceService;
import com.macmie.mfoodyex.Util.StringUtil;
import com.macmie.mfoodyex.Util.TextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j

/* @Transactional: Handle rollback when exceptions occur
 * @Slf4j: Spring Boot Logging
 * */
public class FavoriteListProductsMfoodyImplementService implements FavoriteListProductsMfoodyInterfaceService {
    @Autowired
    private FavoriteListProductsMfoodyRepository favoriteListProductsMfoodyRepository;

    @Autowired
    private UserMfoodyRepository userMfoodyRepository;

    @Autowired
    private FavoriteProductMfoodyRepository favoriteProductMfoodyRepository;

    @Autowired
    private StringUtil stringUtil;

    @Autowired
    private TextUtil textUtil;

    @Override
    public List<FavoriteListProductsMfoody> getListFavoriteListProductsMfoodys() {
        log.info("Fetching all FavoriteListProductsMfoodys ");
        return favoriteListProductsMfoodyRepository.findAll();
    }

    @Override
    public FavoriteListProductsMfoody getFavoriteListProductsMfoodyByID(int idFavoriteListProductsMfoody) {
        log.info("Fetching FavoriteListProductsMfoody with ID: {}", idFavoriteListProductsMfoody);
        return favoriteListProductsMfoodyRepository.findById(idFavoriteListProductsMfoody).orElse(null);
    }

    @Override
    public FavoriteListProductsMfoody getFavoriteListProductsMfoodyByIdUser(int idUser) {
        log.info("Fetching FavoriteListProductsMfoody with idUser: {}", idUser);
        return favoriteListProductsMfoodyRepository.findByIdUser(idUser);
    }

    @Override
    public FavoriteListProductsMfoody saveFavoriteListProductsMfoody(FavoriteListProductsMfoody favoriteListProductsMfoody) {
        log.info("Saving FavoriteListProductsMfoody with ID: {}", favoriteListProductsMfoody.getIdFavoriteListProducts());
        return favoriteListProductsMfoodyRepository.save(favoriteListProductsMfoody);
    }

    @Override
    public FavoriteListProductsMfoody updateFavoriteListProductsMfoody(FavoriteListProductsMfoody newFavoriteListProductsMfoody) {
//        FavoriteListProductsMfoody FavoriteListProductsMfoodyToUpdate = FavoriteListProductsMfoodyRepository.getById(newFavoriteListProductsMfoody.getIdFavoriteListProducts());
//        FavoriteListProductsMfoodyToUpdate.setQuantityAllProductsInFavoriteListProducts((newFavoriteListProductsMfoody.getQuantityAllProductsInFavoriteListProducts()));
//        FavoriteListProductsMfoodyToUpdate.setSalePriceFavoriteListProducts((newFavoriteListProductsMfoody.getSalePriceFavoriteListProducts()));
//        FavoriteListProductsMfoodyToUpdate.setFullPriceFavoriteListProducts((newFavoriteListProductsMfoody.getFullPriceFavoriteListProducts()));
//        FavoriteListProductsMfoodyToUpdate.setUser((newFavoriteListProductsMfoody.getUser()));

        log.info("Updating FavoriteListProductsMfoody with ID: {}", newFavoriteListProductsMfoody.getIdFavoriteListProducts());
        return favoriteListProductsMfoodyRepository.save(newFavoriteListProductsMfoody);
    }

    @Override
    public void deleteFavoriteListProductsMfoodyByID(int idFavoriteListProductsMfoody) {
        log.info("Deleting FavoriteListProductsMfoody with ID: {}", idFavoriteListProductsMfoody);

        // Delete all FavoriteProductMfoodys associate with FavoriteListProductsMfoody
        List<FavoriteProductMfoody> favoriteProductMfoodyList = favoriteProductMfoodyRepository.findAllByIdFavoriteListProducts(idFavoriteListProductsMfoody);
        favoriteProductMfoodyRepository.deleteAll(favoriteProductMfoodyList);

        favoriteListProductsMfoodyRepository.deleteById(idFavoriteListProductsMfoody);
    }

    @Override
    public void deleteFavoriteListProductsMfoodyByIdUser(int idUser) {
        log.info("Deleting FavoriteListProductsMfoody with idUser: {}", idUser);

        // Delete all FavoriteProductMfoodys associate with FavoriteListProductsMfoody
        FavoriteListProductsMfoody favoriteListProductsMfoody = favoriteListProductsMfoodyRepository.findByIdUser(idUser);
        favoriteProductMfoodyRepository.deleteAllFavoriteProductMfoodysByIdFavoriteListProducts(favoriteListProductsMfoody.getIdFavoriteListProducts());
        favoriteListProductsMfoodyRepository.deleteByIdUser(idUser);
    }
}
