package com.macmie.mfoodyex.Repository;

import com.macmie.mfoodyex.Model.UserMfoody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
// JpaRepository<name entity class, type of id of entity class>
public interface UserMfoodyRepository extends JpaRepository<UserMfoody, Integer> {

    UserMfoody findByNameUser(String nameUserMfoody);

    UserMfoody findByEmailUser(String emailUserMfoody);

    UserMfoody findByPhoneNumberUser(String phoneNumberUserMfoody);

    @Query("SELECT u FROM UserMfoody u JOIN CreditCardMfoody c ON u.idUser = c.user.idUser WHERE c.idCard = :idCard")
    UserMfoody findUserMfoodyByIdCard(@Param("idCard") int idCard);

    @Query("SELECT u FROM UserMfoody u JOIN CommentMfoody c ON u.idUser = c.user.idUser WHERE c.idComment = :idComment")
    UserMfoody findUserMfoodyByIdComment(@Param("idComment") int idComment);

    @Query("SELECT u FROM UserMfoody u JOIN CartMfoody c ON u.idUser = c.user.idUser WHERE c.idCart = :idCart")
    UserMfoody findUserMfoodyByIdCart(@Param("idCart") int idCart);

    @Query("SELECT u FROM UserMfoody u JOIN OrderMfoody o ON u.idUser = o.user.idUser WHERE o.idOrder = :idOrder")
    UserMfoody findUserMfoodyByIdOrder(@Param("idOrder") int idOrder);

    @Query("SELECT COUNT(u) FROM UserMfoody u")
    Long countTotalNumberOfUsers();

}

