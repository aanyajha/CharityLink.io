package com.example.charitylink;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.example.charitylink.Item;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends CrudRepository<Item, Integer>{
    @Query(value = "SELECT MAX(item.itemid) AS itemid FROM item WHERE item.userid = :userID GROUP BY item.userid", nativeQuery = true)
    List<Integer> findMaxItemIdByUser(@Param("userID") Integer userID);
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM item WHERE item.itemid = :itemId AND item.userid = :userId", nativeQuery = true)
    void deleteByItemIdAndUserId(@Param("itemId") Integer itemId, @Param("userId") Integer userId);
}