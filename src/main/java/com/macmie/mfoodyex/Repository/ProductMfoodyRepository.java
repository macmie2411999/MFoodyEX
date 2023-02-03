package com.macmie.mfoodyex.Repository;

import com.macmie.mfoodyex.Model.CartMfoody;
import com.macmie.mfoodyex.Model.ProductMfoody;
import com.macmie.mfoodyex.Model.UserMfoody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// JpaRepository<name entity class, type of id of entity class>
public interface ProductMfoodyRepository extends JpaRepository<ProductMfoody, Integer> {
    ProductMfoody findByNameProduct(String nameProduct);
    ProductMfoody findByAlbumProduct(String albumProduct);
}

