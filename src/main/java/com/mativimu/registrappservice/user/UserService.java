package com.mativimu.registrappservice.user;

import java.util.List;

public interface UserService {
    public User saveUser(User user);
    public User getUser(String username);
    public List<User> getUsers();
}
