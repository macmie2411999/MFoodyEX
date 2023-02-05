package com.macmie.mfoodyex.Repository;

import com.macmie.mfoodyex.Model.CartMfoody;
import com.macmie.mfoodyex.Model.UserMfoody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
// JpaRepository<name entity class, type of id of entity class>
public interface CartMfoodyRepository extends JpaRepository<CartMfoody, Integer> {
    @Modifying //  indicate that modify the database
    @Query("DELETE FROM CartMfoody c WHERE c.user.idUser = :idUser")
    void deleteByIdUser(@Param("idUser") int idUser);

    @Query("SELECT c FROM CartMfoody c WHERE c.user.idUser = :idUser")
    CartMfoody findByIdUser(@Param("idUser") int idUser);

    @Query("SELECT u FROM UserMfoody u INNER JOIN CartMfoody c ON u.idUser = c.user.idUser WHERE c.idCart = :idCart")
    UserMfoody findUserByCartId(@Param("idCart") Integer idCart);
}

