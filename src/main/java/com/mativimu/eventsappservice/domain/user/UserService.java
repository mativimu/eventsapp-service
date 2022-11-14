package com.mativimu.eventsappservice.domain.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long userId) {
        boolean exists = userRepository.existsById(userId);
        if(!exists) {
            throw new IllegalStateException("User id " + userId + " not found");
        }
        return userRepository.findById(userId).get();
    }

    public User getUserByEmail(String userEmail) {
        List<User> users = userRepository.findUserByEmail(userEmail);
        if(users.isEmpty()) {
            throw new IllegalStateException("User " + userEmail + " not found");
        }
        return users.get(0);
    }

    public void addUser(String username, String userEmail,
                        String userPassword, String fullName, String userOccupation) {
        List<User> users = userRepository.findUserByEmail(userEmail);
        if(!users.isEmpty()) {
            throw new IllegalStateException("User email " + userEmail + " already exists");
        }
        userRepository.save(
            new User(username, userEmail, userPassword, fullName, userOccupation)
        );
    }

    public void deleteUser(Long userID) {
        boolean exists = userRepository.existsById(userID);
        if(!exists) {
            throw new IllegalStateException("User id "+ userID + " not found");
        }
        userRepository.deleteById(userID);
    }

    @Transactional
    public void updateUsername(Long userID, String newUsername) {
        boolean exists = userRepository.existsById(userID);
        if(!exists) {
            throw new IllegalStateException("User id " + userID + " not found");
        }
        userRepository.findById(userID).get().
            setUsername(newUsername);
    }

    @Transactional
    public void updateUserEmail(Long userID, String newUserEmail) {
        boolean exists = userRepository.existsById(userID);
        if(!exists) {
            throw new IllegalStateException("User id " + userID + " not found");
        }
        userRepository.findById(userID).get()
            .setUserEmail(newUserEmail);
    }

    @Transactional
    public void updateUserPassword(Long userId, String newPassword) {
        boolean exists = userRepository.existsById(userId);
        if(!exists) {
            throw new IllegalStateException("User id " + userId + " not found");
        }
        userRepository.findById(userId).get()
            .setUserPassword(newPassword);
    }

    @Transactional
    public void updateUserFullName(Long userID, String newFullName) {
        boolean exists = userRepository.existsById(userID);
        if(!exists) {
            throw new IllegalStateException("User id " + userID + " not found");
        }
        userRepository.findById(userID).get()
            .setFullName(newFullName);
    }

    @Transactional
    public void updateUserOccupation(Long userID, String newUserOccupation) {
        boolean exists = userRepository.existsById(userID);
        if(!exists) {
            throw new IllegalStateException("User id " + userID + " not found");
        }
        userRepository.findById(userID).get()
            .setUserOccupation(newUserOccupation);
    }
    
}