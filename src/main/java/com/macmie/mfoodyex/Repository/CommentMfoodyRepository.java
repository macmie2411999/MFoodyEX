package com.macmie.mfoodyex.Repository;

import com.macmie.mfoodyex.Model.CommentMfoody;
import com.macmie.mfoodyex.Model.FeedbackMail;
import com.macmie.mfoodyex.Model.UserMfoody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
// JpaRepository<name entity class, type of id of entity class>
public interface CommentMfoodyRepository extends JpaRepository<CommentMfoody, Integer> {
    @Modifying //  indicate that modify the database
    @Query("DELETE FROM CommentMfoody c WHERE c.user.idUser = :idUser")
    void deleteAllByIdUser(@Param("idUser") int idUser);

    @Modifying
    @Query("DELETE FROM CommentMfoody c WHERE c.product.idProduct = :idProduct")
    void deleteAllByIdProduct(@Param("idProduct") int idProduct);
}

