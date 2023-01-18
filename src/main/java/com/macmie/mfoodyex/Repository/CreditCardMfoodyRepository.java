package com.macmie.mfoodyex.Repository;

import com.macmie.mfoodyex.Model.CreditCardMfoody;
import com.macmie.mfoodyex.Model.FeedbackMail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// JpaRepository<name entity class, type of id of entity class>
public interface CreditCardMfoodyRepository extends JpaRepository<CreditCardMfoody, Integer> {

}

