package com.macmie.mfoodyex.Service.ImplementService;

import com.macmie.mfoodyex.Model.FavoriteListMfoody;
import com.macmie.mfoodyex.Repository.FavoriteListMfoodyRepository;
import com.macmie.mfoodyex.Service.InterfaceService.FavoriteListMfoodyInterfaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class FavoriteListMfoodyImplementService implements FavoriteListMfoodyInterfaceService {
    @Autowired
    private FavoriteListMfoodyRepository favoriteListMfoodyRepository;

    @Override
    public List<FavoriteListMfoody> getListFavoriteListMfoodys() {
        log.info("Fetching all FavoriteListMfoodys ");
        return favoriteListMfoodyRepository.findAll();
    }

    @Override
    public List<FavoriteListMfoody> getListFavoriteListMfoodysByIdProduct(int idProduct) {
        log.info("Fetching all FavoriteListMfoodys by idProduct: {}", idProduct);
        return favoriteListMfoodyRepository.findAllByIdProduct(idProduct);
    }

    @Override
    public List<FavoriteListMfoody> getListFavoriteListMfoodysByIdUser(int idUser) {
        log.info("Fetching all FavoriteListMfoodys by idUser: {}", idUser);
        return favoriteListMfoodyRepository.findAllByIdUser(idUser);
    }

    @Override
    public FavoriteListMfoody getFavoriteListMfoodyByID(int idFavoriteListMfoody) {
        log.info("Fetching FavoriteListMfoody with ID: {}", idFavoriteListMfoody);
        return favoriteListMfoodyRepository.findById(idFavoriteListMfoody).orElse(null);
    }

    @Override
    public FavoriteListMfoody saveFavoriteListMfoody(FavoriteListMfoody favoriteListMfoody) {
        log.info("Saving FavoriteListMfoody with ID: {}", favoriteListMfoody.getIdFavoriteList());
        return favoriteListMfoodyRepository.save(favoriteListMfoody);
    }

    @Override
    public FavoriteListMfoody updateFavoriteListMfoody(FavoriteListMfoody newFavoriteListMfoody) {
        log.info("Updating FavoriteListMfoody with ID: {}", newFavoriteListMfoody.getIdFavoriteList());
        return favoriteListMfoodyRepository.save(newFavoriteListMfoody);
    }

    @Override
    public void deleteFavoriteListMfoodyByID(int idFavoriteListMfoody) {
        log.info("Deleting FavoriteListMfoody with ID: {}", idFavoriteListMfoody);
        favoriteListMfoodyRepository.deleteById(idFavoriteListMfoody);
    }

    @Override
    public void deleteAllFavoriteListMfoodysByIdUser(int idUser) {
        log.info("Deleting All FavoriteListMfoodys with idUser: {}", idUser);
        favoriteListMfoodyRepository.deleteAllByIdUser(idUser);
    }

    @Override
    public void deleteAllFavoriteListMfoodysByIdProduct(int idProduct) {
        log.info("Deleting All FavoriteListMfoodys with idProduct: {}", idProduct);
        favoriteListMfoodyRepository.deleteAllByIdProduct(idProduct);
    }

    @Override
    public Long countTotalNumberOfFavoriteListMfoodys() {
        log.info("Count Total Number of FavoriteListMfoodys");
        return favoriteListMfoodyRepository.countTotalNumberOfFavoriteLists();
    }
}
