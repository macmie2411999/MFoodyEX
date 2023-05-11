package com.macmie.mfoodyex.Service.InterfaceService;

import com.macmie.mfoodyex.Model.Subscriber;

import java.util.List;

public interface SubscriberInterfaceService {
    public List<Subscriber> getListSubscribers();

    public Subscriber getSubscriberByID(int idSubscriber);

    public Subscriber getSubscriberByEmail(String emailSubscriber);

    public Subscriber saveSubscriber(Subscriber subscriber);

    public Subscriber updateSubscriber(Subscriber newSubscriber);

    public void deleteSubscriberByID(int idSubscriber);

    public Long countTotalNumberOfSubscribers();
}
