package com.mativimu.eventsappservice.entities.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository
            extends JpaRepository<User, Long> {
    
    @Query("SELECT u FROM User u WHERE u.userEmail = :email")
    public List<User> findUserByEmail(@Param("email") String userEmail);
}