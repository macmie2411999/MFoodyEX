package com.macmie.mfoodyex.Repository;

import com.macmie.mfoodyex.Model.CartMfoody;
import com.macmie.mfoodyex.Model.OrderMfoody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
// JpaRepository<name entity class, type of id of entity class>
public interface OrderMfoodyRepository extends JpaRepository<OrderMfoody, Integer> {
    @Modifying //  indicate that modify the database
    @Query("DELETE FROM OrderMfoody c WHERE c.user.idUser = :idUser")
    void deleteByIdUser(@Param("idUser") int idUser);

    @Query("SELECT c FROM OrderMfoody c WHERE c.user.idUser = :idUser")
    OrderMfoody findByIdUser(@Param("idUser") int idUser);
}

