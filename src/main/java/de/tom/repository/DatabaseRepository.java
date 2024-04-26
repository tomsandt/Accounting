package de.tom.repository;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DatabaseRepository {

    @PersistenceContext
    private EntityManager entityManager;
    @Transactional
    public <E> E save(E entity) {
        return entityManager.merge(entity);
    }

    public <E> E findById(Class<E> entityClass, int id) {
        return entityManager.find(entityClass, id);
    }
    @Transactional
    public <E> void delete(Class<E> entityClass, int id) {
        E entity = findById(entityClass, id);
        if(entity != null) {
            entityManager.remove(entity);
        }
    }

    public <E> List<E> findAll(Class<E> entityClass) {
        return entityManager.createQuery("SELECT e FROM " + entityClass.getName() + " e", entityClass).getResultList();
    }

    @Transactional
    public <E> E findByForeignKey(Class<E> entityClass, String foreignKeyName, Object foreignKeyValue) {
        TypedQuery<E> query = entityManager.createQuery(
                "SELECT e FROM " + entityClass.getSimpleName() + " e WHERE e." + foreignKeyName + " = :foreignKeyValue",
                entityClass
        );
        query.setParameter("foreignKeyValue", foreignKeyValue);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            throw new EntityNotFoundException("No Entity found for ID: " + foreignKeyValue);
        }
    }


}
