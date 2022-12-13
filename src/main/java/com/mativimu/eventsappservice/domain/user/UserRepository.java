package com.mativimu.eventsappservice.domain.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserRepository
            extends JpaRepository<User, Long> {
    
    @Query("SELECT u FROM User u WHERE u.userEmail = :email")
    public List<User> findUserByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.username = :username")
    public List<User> findUserByUsername(@Param("username") String username);
}