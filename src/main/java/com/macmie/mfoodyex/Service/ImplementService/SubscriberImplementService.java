package com.macmie.mfoodyex.Service.ImplementService;

import com.macmie.mfoodyex.Model.Subscriber;
import com.macmie.mfoodyex.Repository.SubscriberRepository;
import com.macmie.mfoodyex.Service.InterfaceService.SubscriberInterfaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class SubscriberImplementService implements SubscriberInterfaceService {
    @Autowired
    private SubscriberRepository subscriberRepository;

    @Override
    public List<Subscriber> getListSubscribers() {
        log.info("Fetching all Subscribers ");
        return subscriberRepository.findAll();
    }

    @Override
    public Subscriber getSubscriberByID(int idSubscriber) {
        log.info("Fetching Subscriber with ID: {}", idSubscriber);
        return subscriberRepository.findById(idSubscriber).orElse(null);
    }

    @Override
    public Subscriber getSubscriberByEmail(String emailSubscriber) {
        log.info("Fetching Subscriber with email: {}", emailSubscriber);
        return subscriberRepository.findByEmailSubscriber(emailSubscriber);
    }

    @Override
    public Subscriber saveSubscriber(Subscriber subscriber) {
        log.info("Saving Subscriber with ID: {}", subscriber.getIdSubscriber());
        return subscriberRepository.save(subscriber);
    }

    @Override
    public Subscriber updateSubscriber(Subscriber newSubscriber) {
        log.info("Updating Subscriber with ID: {}", newSubscriber.getIdSubscriber());
        return subscriberRepository.save(newSubscriber);
    }

    @Override
    public void deleteSubscriberByID(int idSubscriber) {
        log.info("Deleting Subscriber with ID: {}", idSubscriber);
        subscriberRepository.deleteById(idSubscriber);
    }

    @Override
    public Long countTotalNumberOfSubscribers() {
        log.info("Count Total Number of Subscribers");
        return subscriberRepository.count();
    }
}
