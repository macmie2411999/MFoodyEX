package com.macmie.mfoodyex.Repository;

import com.macmie.mfoodyex.Model.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Integer> {
    Subscriber findByEmailSubscriber(String emailSubscriber);

    @Query("SELECT COUNT(u) FROM Subscriber u")
    Long countTotalNumberOfSubscribers();
}
