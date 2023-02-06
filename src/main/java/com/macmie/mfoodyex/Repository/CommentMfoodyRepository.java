package com.macmie.mfoodyex.Repository;

import com.macmie.mfoodyex.Model.CommentMfoody;
import com.macmie.mfoodyex.Model.UserMfoody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
// JpaRepository<name entity class, type of id of entity class>
public interface CommentMfoodyRepository extends JpaRepository<CommentMfoody, Integer> {
    @Modifying // indicate that modify the database
    @Query("DELETE FROM CommentMfoody c WHERE c.user.idUser = :idUser")
    void deleteAllByIdUser(@Param("idUser") int idUser);

    @Modifying // indicate that modify the database
    @Query("DELETE FROM CommentMfoody c WHERE c.product.idProduct = :idProduct")
    void deleteAllByIdProduct(@Param("idProduct") int idProduct);

    @Query("SELECT u FROM UserMfoody u JOIN CommentMfoody c ON u.idUser = c.user.idUser WHERE c.idComment = :idComment")
    UserMfoody findUserByCommentId(@Param("idComment") int idComment);

    @Query("SELECT c FROM CommentMfoody c WHERE c.product.idProduct = :idProduct")
    List<CommentMfoody> findAllByIdProduct(@Param("idProduct") int idProduct);

    @Query("SELECT c FROM CommentMfoody c WHERE c.user.idUser = :idUser")
    List<CommentMfoody> findAllByIdUser(@Param("idUser") int idUser);

    CommentMfoody findCommentMfoodyByContentComment(String contentComment);
}

