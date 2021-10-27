package ru.oeru.springwebapp.dao;

import ru.oeru.springwebapp.model.User;

import java.util.List;

public interface UserDao {
    void add(User user);
    void removeByUser(User user);
    void removeById(Long id);
    void update(User user);
    User find(Long id);
    List<User> listUsers();
    User findUserByUsername(String username);
}
