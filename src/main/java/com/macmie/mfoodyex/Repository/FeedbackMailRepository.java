package com.macmie.mfoodyex.Repository;

import com.macmie.mfoodyex.Model.FeedbackMail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// JpaRepository<name entity class, type of id of entity class>
public interface FeedbackMailRepository extends JpaRepository<FeedbackMail, Integer> {

}

