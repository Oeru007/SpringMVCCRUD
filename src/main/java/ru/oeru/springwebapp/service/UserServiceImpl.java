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
import ru.oeru.springwebapp.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private UserDao userDao;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public boolean add(User user) {
        User userFromDb = findUserByUsername(user.getUsername());
        if (userFromDb != null){
            return false;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        roleService.createRoleIfNotExist(PossibleRoles.createUserRole());
        user.addRole(roleService.findByRole(PossibleRoles.getUserRole()));
        if (user.getConfirm() != null) {
            roleService.createRoleIfNotExist(PossibleRoles.createAdminRole());
            user.addRole(roleService.findByRole(PossibleRoles.getAdminRole()));
        }
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
        User userFromDB = userDao.find(user.getId());
        if (user.getConfirm() == null){
            userFromDB.getRoles().remove(PossibleRoles.createAdminRole());

        } else {
            userFromDB.getRoles().add(PossibleRoles.createAdminRole());
        }
        userFromDB.setFirstName(user.getFirstName());
        userFromDB.setLastName(user.getLastName());
        userFromDB.setEmail(user.getEmail());
        userFromDB.setUsername(user.getUsername());
        userFromDB.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.update(userFromDB);
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
