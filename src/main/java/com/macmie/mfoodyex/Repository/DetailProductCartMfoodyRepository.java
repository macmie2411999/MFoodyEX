package com.macmie.mfoodyex.Repository;

import com.macmie.mfoodyex.Model.DetailProductCartMfoody;
import com.macmie.mfoodyex.Model.ProductMfoody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
// JpaRepository<name entity class, type of id of entity class>
public interface DetailProductCartMfoodyRepository extends JpaRepository<DetailProductCartMfoody, Integer> {
    @Query("SELECT c FROM DetailProductCartMfoody c WHERE c.idDetailProductCartMFoody.idCart = :idCart")
    List<DetailProductCartMfoody> findAllByIdCart(@Param("idCart") int idCart);

    @Query("SELECT c FROM DetailProductCartMfoody c WHERE c.idDetailProductCartMFoody.idProduct = :idProduct")
    List<DetailProductCartMfoody> findAllByIdProduct(@Param("idProduct") int idProduct);

    @Query("SELECT d FROM DetailProductCartMfoody d " +
            "WHERE d.cart.idCart = :idCart " +
            "AND d.product.idProduct = :idProduct")
    DetailProductCartMfoody findByIdCartAndIdProduct(@Param("idCart") int idCart,
                                                     @Param("idProduct") int idProduct);
}

