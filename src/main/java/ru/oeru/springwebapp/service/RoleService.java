package ru.oeru.springwebapp.service;

import ru.oeru.springwebapp.model.Role;

public interface RoleService {
    void delete(Role role);
    Role findByRole(String name);
    void createRoleIfNotExist(Role role);
}
