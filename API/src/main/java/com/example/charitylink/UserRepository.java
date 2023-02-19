package com.example.charitylink;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.charitylink.User;
import org.springframework.data.repository.query.Param;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<User, Integer> {
    @Query(value = "SELECT user.id FROM user WHERE user.email = :email", nativeQuery = true)
    List<Integer> findUserIdByEmail(@Param("email") String email);
}
