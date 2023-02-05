package com.macmie.mfoodyex.Repository;

import com.macmie.mfoodyex.Model.ProductMfoody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
// JpaRepository<name entity class, type of id of entity class>
public interface ProductMfoodyRepository extends JpaRepository<ProductMfoody, Integer> {
    ProductMfoody findByNameProduct(String nameProduct);

    ProductMfoody findByAlbumProduct(String albumProduct);

    @Query("SELECT p FROM ProductMfoody p JOIN CommentMfoody c ON p.idProduct = c.product.idProduct WHERE c.idComment = :idComment")
    ProductMfoody findProductMfoodyByIdComment(@Param("idComment") int idComment);

//    @Query("SELECT p FROM ProductMfoody p JOIN DetailProductOrderMfoody dp ON p.idProduct = dp.product.idProduct WHERE dp.idDetailProductOrderMfoody.idProduct = :idProduct")
//    ProductMfoody findByDetailProductOrderId(@Param("idProduct") int idProduct);
//
//    @Query("SELECT p FROM ProductMfoody p JOIN DetailProductCartMfoody dp ON p.idProduct = dp.product.idProduct WHERE dp.idDetailProductCart = :idDetailProductCart")
//    ProductMfoody findByDetailProductCartId(@Param("idDetailProductCart") int idDetailProductCart);
}

