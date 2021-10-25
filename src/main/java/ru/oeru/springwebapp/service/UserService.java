package ru.oeru.springwebapp.service;

import ru.oeru.springwebapp.model.User;

import java.util.List;

public interface UserService {
    void add(User user);
    void remove(User user);
    void removeById(Long id);
    void update(User user);
    User find(Long id);
    List<User> listUsers();
}
