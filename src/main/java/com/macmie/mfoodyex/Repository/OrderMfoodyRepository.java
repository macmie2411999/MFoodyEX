package com.macmie.mfoodyex.Repository;

import com.macmie.mfoodyex.Model.CartMfoody;
import com.macmie.mfoodyex.Model.OrderMfoody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// JpaRepository<name entity class, type of id of entity class>
public interface OrderMfoodyRepository extends JpaRepository<OrderMfoody, Integer> {

}

