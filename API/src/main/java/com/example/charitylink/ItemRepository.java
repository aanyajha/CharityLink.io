package com.example.charitylink;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.example.charitylink.Item;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends CrudRepository<Item, Integer>{
    @Query(value = "SELECT MAX(item.itemid) AS itemid FROM item WHERE item.userid = :userID GROUP BY item.userid", nativeQuery = true)
    List<Integer> findMaxItemIdByUser(@Param("userID") Integer userID);
    @Transactional
    Integer deleteByItemIDAndUserID(Integer itemId, Integer userId);
//    @Query(value = "", nativeQuery = true)
    List<Item> findItemsByUserID(Integer userID);
    Item findItemByUserIDAndItemID(Integer userID, Integer itemID);
}