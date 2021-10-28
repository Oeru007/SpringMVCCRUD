package ru.oeru.springwebapp.dao;

import ru.oeru.springwebapp.model.Role;

public interface RoleDao {
    void delete(Role role);
    Role findByRole(String name);
    void createRoleIfNotExist(Role role);
}
