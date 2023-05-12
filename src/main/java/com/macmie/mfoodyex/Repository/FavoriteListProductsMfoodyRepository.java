package com.macmie.mfoodyex.Repository;

import com.macmie.mfoodyex.Model.FavoriteListProductsMfoody;
import com.macmie.mfoodyex.Model.UserMfoody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
// JpaRepository<name entity class, type of id of entity class>
public interface FavoriteListProductsMfoodyRepository extends JpaRepository<FavoriteListProductsMfoody, Integer> {
    @Modifying //  indicate that modify the database
    @Query("DELETE FROM FavoriteListProductsMfoody c WHERE c.user.idUser = :idUser")
    void deleteByIdUser(@Param("idUser") int idUser);

    @Query("SELECT c FROM FavoriteListProductsMfoody c WHERE c.user.idUser = :idUser")
    FavoriteListProductsMfoody findByIdUser(@Param("idUser") int idUser);

    @Query("SELECT u FROM UserMfoody u INNER JOIN FavoriteListProductsMfoody c ON u.idUser = c.user.idUser WHERE c.idFavoriteListProducts = :idFavoriteListProducts")
    UserMfoody findUserByFavoriteListProductsId(@Param("idFavoriteListProducts") Integer idFavoriteListProducts);
}

