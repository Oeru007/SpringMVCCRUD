package ru.oeru.springwebapp.dao;

import org.springframework.stereotype.Repository;
import ru.oeru.springwebapp.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class RoleDaoImpl implements RoleDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void delete(Role role) {
        entityManager.remove(role);
    }

    @Override
    public Role findByRole(String name) {
        try {
            return entityManager.createQuery("select r from Role r where r.name=:rolename", Role.class)
                    .setParameter("rolename", name).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void createRoleIfNotExist(Role role) {
        Role roleFromDB = findByRole(role.getName());
        if (roleFromDB == null) {
            entityManager.persist(role);
        }
    }
}
