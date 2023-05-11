package com.macmie.mfoodyex.Repository;

import com.macmie.mfoodyex.Model.FavoriteListMfoody;
import com.macmie.mfoodyex.Model.UserMfoody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
// JpaRepository<name entity class, type of id of entity class>
public interface FavoriteListMfoodyRepository extends JpaRepository<FavoriteListMfoody, Integer> {
    @Modifying // indicate that modify the database
    @Query("DELETE FROM FavoriteListMfoody f WHERE f.user.idUser = :idUser")
    void deleteAllByIdUser(@Param("idUser") int idUser);

    @Modifying // indicate that modify the database
    @Query("DELETE FROM FavoriteListMfoody f WHERE f.product.idProduct = :idProduct")
    void deleteAllByIdProduct(@Param("idProduct") int idProduct);

    @Query("SELECT u FROM UserMfoody u JOIN FavoriteListMfoody f ON u.idUser = f.user.idUser WHERE f.idFavoriteList = :idFavoriteList")
    UserMfoody findUserByFavoriteListId(@Param("idFavoriteList") int idFavoriteList);

    @Query("SELECT f FROM FavoriteListMfoody f WHERE f.product.idProduct = :idProduct")
    List<FavoriteListMfoody> findAllByIdProduct(@Param("idProduct") int idProduct);

    @Query("SELECT f FROM FavoriteListMfoody f WHERE f.user.idUser = :idUser")
    List<FavoriteListMfoody> findAllByIdUser(@Param("idUser") int idUser);

    @Query("SELECT COUNT(f) FROM FavoriteListMfoody f")
    Long countTotalNumberOfFavoriteLists();
}
