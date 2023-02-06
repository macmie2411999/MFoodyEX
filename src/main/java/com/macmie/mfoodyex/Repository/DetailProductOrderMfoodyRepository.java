package com.macmie.mfoodyex.Repository;

import com.macmie.mfoodyex.Model.DetailProductOrderMfoody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Query("SELECT d FROM DetailProductOrderMfoody d " +
            "WHERE d.order.idOrder = :idOrder " +
            "AND d.product.idProduct = :idProduct")
    DetailProductOrderMfoody findByIdOrderAndIdProduct(@Param("idOrder") int idOrder,
                                                       @Param("idProduct") int idProduct);

    @Modifying
    @Query("DELETE FROM DetailProductOrderMfoody d " +
            "WHERE d.idDetailProductOrderMfoody.idProduct = :idProduct " +
            "AND d.idDetailProductOrderMfoody.idOrder = :idOrder")
    void deleteDetailProductOrderMfoodyByIdOrderAndIdProduct( @Param("idOrder") int idOrder,
                                                            @Param("idProduct") int idProduct);
}

