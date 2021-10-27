package ru.oeru.springwebapp.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.oeru.springwebapp.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    boolean add(User user);
    void remove(User user);
    void removeById(Long id);
    void update(User user);
    User find(Long id);
    List<User> listUsers();
    User findUserByUsername(String username);
}
