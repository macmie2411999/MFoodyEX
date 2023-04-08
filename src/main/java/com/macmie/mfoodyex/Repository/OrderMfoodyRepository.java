package com.macmie.mfoodyex.Repository;

import com.macmie.mfoodyex.Model.CommentMfoody;
import com.macmie.mfoodyex.Model.OrderMfoody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
// JpaRepository<name entity class, type of id of entity class>
public interface OrderMfoodyRepository extends JpaRepository<OrderMfoody, Integer> {
    @Modifying //  indicate that modify the database
    @Query("DELETE FROM OrderMfoody c WHERE c.user.idUser = :idUser")
    void deleteAllByIdUser(@Param("idUser") int idUser);

    @Query("SELECT c FROM OrderMfoody c WHERE c.user.idUser = :idUser")
    List<OrderMfoody> findAllByIdUser(@Param("idUser") int idUser);

    @Query("SELECT COUNT(u) FROM OrderMfoody u")
    Long countTotalNumberOfOrderMfoodys();
}

