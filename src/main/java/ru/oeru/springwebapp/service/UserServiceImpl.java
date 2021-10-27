package ru.oeru.springwebapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.oeru.springwebapp.dao.RoleDao;
import ru.oeru.springwebapp.dao.UserDao;
import ru.oeru.springwebapp.model.PossibleRoles;
import ru.oeru.springwebapp.model.Role;
import ru.oeru.springwebapp.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    UserDao userDao;
    RoleDao roleDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public boolean add(User user) {
        try {
            findUserByUsername(user.getUsername());
            return false;
        } catch (RuntimeException ignore) {

        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(new Role(PossibleRoles.getUserRole()));
        if (user.getConfirm() != null) {
            user.getRoles().add(new Role(PossibleRoles.getAdminRole()));
        }
        user.setRoles(user.getRoles());
        userDao.add(user);
        return true;
    }

    @Override
    @Transactional
    public void remove(User user) {
        userDao.removeByUser(user);
    }

    @Override
    @Transactional
    public void removeById(Long id) {
        userDao.removeById(id);
    }

    @Override
    @Transactional
    public void update(User user) {
        User user1 = userDao.findUserByUsername(user.getUsername());
        if (user.getConfirm() == null){
            user1.getRoles().stream()
                    .filter(role -> role.getName()
                    .equals(PossibleRoles.getAdminRole()))
                    .forEach(role -> roleDao.delete(role));
            user1.getRoles().removeIf(role -> role.getName().equals(PossibleRoles.getAdminRole()));

        } else {
            user1.getRoles().add(new Role(PossibleRoles.getAdminRole()));
        }
        userDao.update(user1);
    }

    @Override
    @Transactional
    public User find(Long id) {
        return userDao.find(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> listUsers() {
        return userDao.listUsers();
    }

    @Override
    @Transactional
    public User findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return findUserByUsername(s);
    }
}
