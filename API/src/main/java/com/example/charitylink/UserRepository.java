package com.example.charitylink;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.charitylink.User;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    @Query(value = "SELECT user.id FROM user WHERE user.email = :email", nativeQuery = true)
    List<Integer> findUserIdByEmail(@Param("email") String email);
    @Query(value = "SELECT user.id FROM user WHERE user.username = :username", nativeQuery = true)
    List<Integer> findUserIdByUsername(@Param("username") String username);
    @Query(value = "SELECT * FROM user WHERE user.companyid = :companyId AND user.user_type < :userType", nativeQuery = true)
    List<User> findAllByCompanyID(@Param("companyId") Integer companyId, @Param("userType") Integer userType);
    List<User> findAllByCompanyIDAndUserType(Integer companyID, Integer userType);
}