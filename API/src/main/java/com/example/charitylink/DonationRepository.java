package com.example.charitylink;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DonationRepository extends CrudRepository<Donation, Integer> {
    List<Donation> findAllByRequester(Integer requester);
    List<Donation> findAllByDonator(Integer donator);
}
