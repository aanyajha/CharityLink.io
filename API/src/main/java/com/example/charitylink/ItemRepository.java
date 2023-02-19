package com.example.charitylink;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.example.charitylink.Item;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends CrudRepository<Item, Integer>{
    @Query(value = "SELECT MAX(item.itemid) AS itemid FROM item WHERE item.userid = :userID GROUP BY item.userid", nativeQuery = true)
    List<Integer> findMaxItemIdByUser(@Param("userID") Integer userID);
}
