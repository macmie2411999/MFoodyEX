package com.macmie.mfoodyex.Repository;

import com.macmie.mfoodyex.Model.DetailProductOrderMfoody;
import com.macmie.mfoodyex.Model.DetailProductOrderMfoody;
import com.macmie.mfoodyex.Model.FeedbackMail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
// JpaRepository<name entity class, type of id of entity class>
public interface DetailProductOrderMfoodyRepository extends JpaRepository<DetailProductOrderMfoody, Integer> {
    @Query("SELECT c FROM DetailProductOrderMfoody c WHERE c.idDetailProductOrderMfoody.idOrder = :idOrder")
    List<DetailProductOrderMfoody> findAllByIdOrder(@Param("idOrder") int idOrder);

    @Query("SELECT c FROM DetailProductOrderMfoody c WHERE c.idDetailProductOrderMfoody.idProduct = :idProduct")
    List<DetailProductOrderMfoody> findAllByIdProduct(@Param("idProduct") int idProduct);
}

