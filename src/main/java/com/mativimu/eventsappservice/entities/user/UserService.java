package com.mativimu.eventsappservice.entities.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public List<User> getUsers() throws DataAccessException {
        return userRepository.findAll();
    }

    public User getUserById(Long userId) throws DataAccessException {
        boolean exists = userRepository.existsById(userId);
        if(!exists) {
            throw new IllegalStateException("User with id " + userId + " not found");
        }
        return userRepository.findById(userId).get();
    }

    public User getUserByEmail(String userEmail) throws DataAccessException {
        List<User> users = userRepository.findUserByEmail(userEmail);
        if(users.isEmpty()) {
            throw new IllegalStateException("User with email " + userEmail + " not found");
        }
        return users.get(0);
    }

    public void addUser(String username, String userEmail, String userPassword, String fullName, String userOccupation) {
        List<User> users = userRepository.findUserByEmail(userEmail);
        if(!users.isEmpty()) {
            throw new IllegalStateException("User with email " + userEmail + "already exists");
        }
        userRepository.save(
            new User(username, userEmail, userPassword, fullName, userOccupation)
        );
    }

    public void deleteUser(Long userID) {
        boolean exists = userRepository.existsById(userID);
        if(!exists) {
            throw new IllegalStateException("User with id "+ userID + " not found");
        }
        userRepository.deleteById(userID);
    }

    @Transactional
    public void updateUsername(Long userID, String newUsername) throws DataAccessException{
        boolean exists = userRepository.existsById(userID);
        if(!exists) {
            throw new IllegalStateException("User with id " + userID + " not found");
        }
        userRepository.findById(userID).get().
            setUsername(newUsername);
    }

    @Transactional
    public void updateUserEmail(Long userID, String newUserEmail) throws DataAccessException {
        boolean exists = userRepository.existsById(userID);
        if(!exists) {
            throw new IllegalStateException("User with id " + userID + " not found");
        }
        userRepository.findById(userID).get().
            setUserEmail(newUserEmail);
    }

    @Transactional
    public void updateUserPassword(Long userID, String newPassword) throws DataAccessException {
        boolean exists = userRepository.existsById(userID);
        if(!exists) {
            throw new IllegalStateException("User with id " + userID + " not found");
        }
        userRepository.findById(userID).get()
            .setUserPassword(newPassword);
    }

    @Transactional
    public void updateUserFullName(Long userID, String newFullName) throws DataAccessException {
        boolean exists = userRepository.existsById(userID);
        if(!exists) {
            throw new IllegalStateException("User with id " + userID + " not found");
        }
        userRepository.findById(userID).get()
            .setFullName(newFullName);
    }

    @Transactional
    public void updateUserOccupation(Long userID, String newUserOccupation) throws DataAccessException {
        boolean exists = userRepository.existsById(userID);
        if(!exists) {
            throw new IllegalStateException("User with id " + userID + " not found");
        }
        userRepository.findById(userID).get()
            .setUserOccupation(newUserOccupation);
    }
}
