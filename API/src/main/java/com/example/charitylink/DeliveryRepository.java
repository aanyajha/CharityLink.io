package com.example.charitylink;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DeliveryRepository extends CrudRepository<Delivery, Integer> {
    List<Delivery> findAllByRequester(Integer requester);
    List<Delivery> findAllByDonator(Integer donator);
    void deleteAllByItemID(Integer itemID);
}

