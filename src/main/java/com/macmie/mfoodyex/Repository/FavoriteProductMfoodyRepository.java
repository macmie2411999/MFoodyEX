package com.macmie.mfoodyex.Repository;

import com.macmie.mfoodyex.Model.FavoriteProductMfoody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
// JpaRepository<name entity class, type of id of entity class>
public interface FavoriteProductMfoodyRepository extends JpaRepository<FavoriteProductMfoody, Integer> {
    @Query("SELECT c FROM FavoriteProductMfoody c WHERE c.idFavoriteProductMFoody.idFavoriteListProducts = :idFavoriteListProducts")
    List<FavoriteProductMfoody> findAllByIdFavoriteListProducts(@Param("idFavoriteListProducts") int idFavoriteListProducts);

    @Query("SELECT c FROM FavoriteProductMfoody c WHERE c.idFavoriteProductMFoody.idProduct = :idProduct")
    List<FavoriteProductMfoody> findAllByIdProduct(@Param("idProduct") int idProduct);

    @Query("SELECT d FROM FavoriteProductMfoody d " +
            "WHERE d.favoriteListProducts.idFavoriteListProducts = :idFavoriteListProducts " +
            "AND d.product.idProduct = :idProduct")
    FavoriteProductMfoody findByIdFavoriteListProductsAndIdProduct(@Param("idFavoriteListProducts") int idFavoriteListProducts,
                                                     @Param("idProduct") int idProduct);

    @Modifying
    @Query("DELETE FROM FavoriteProductMfoody d " +
            "WHERE d.idFavoriteProductMFoody.idProduct = :idProduct " +
            "AND d.idFavoriteProductMFoody.idFavoriteListProducts = :idFavoriteListProducts")
    void deleteFavoriteProductMfoodyByIdProductAndIdFavoriteListProducts( @Param("idFavoriteListProducts") int idFavoriteListProducts,
                                                            @Param("idProduct") int idProduct);

    @Modifying
    @Query("DELETE FROM FavoriteProductMfoody dpom WHERE dpom.idFavoriteProductMFoody.idFavoriteListProducts= :idFavoriteListProducts")
    void deleteAllFavoriteProductMfoodysByIdFavoriteListProducts(@Param("idFavoriteListProducts") int idFavoriteListProducts);

    @Modifying
    @Query("DELETE FROM FavoriteProductMfoody dpom WHERE dpom.idFavoriteProductMFoody.idProduct = :idProduct")
    void deleteAllFavoriteProductMfoodysByIdProduct(@Param("idProduct") int idProduct);

//    @Query("SELECT d FROM FavoriteProductMfoody d WHERE d.idFavoriteProductMFoody.idProduct = :idProduct")
//    List<FavoriteProductMfoody> findAllByIdProduct(@Param("idProduct") Long idProduct);
//
//    @Query("SELECT d FROM FavoriteProductMfoody d WHERE d.idFavoriteProductMFoody.idFavoriteListProducts = :idFavoriteListProducts")
//    List<FavoriteProductMfoody> findAllByIdFavoriteListProducts(@Param("idFavoriteListProducts") Long idFavoriteListProducts);
}

