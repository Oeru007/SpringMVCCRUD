package ru.oeru.springwebapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.oeru.springwebapp.dao.RoleDao;
import ru.oeru.springwebapp.model.Role;
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    RoleDao roleDao;

    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public void delete(Role role) {

    }

    @Override
    public Role findByRole(String name) {
        return roleDao.findByRole(name);
    }

    @Override
    public void createRoleIfNotExist(Role role) {
        roleDao.createRoleIfNotExist(role);
    }
}
