package com.example.charitylink;

import org.springframework.data.repository.CrudRepository;

public interface RequestRepository extends CrudRepository<Request, Integer> {
    Iterable<Request> findAllByRequestor(Integer requestor);

}
