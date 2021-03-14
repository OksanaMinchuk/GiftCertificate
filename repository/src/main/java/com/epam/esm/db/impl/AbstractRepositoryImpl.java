package com.epam.esm.db.impl;

import com.epam.esm.db.GenericRepository;
import com.epam.esm.db.specification.Specification;
import com.epam.esm.model.AbstractEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public abstract class AbstractRepositoryImpl<T extends AbstractEntity<K>, K>
    implements GenericRepository<T, K> {

  private final Class<T> entityClass;
  private final Class<K> primaryKeyClass;

  @PersistenceContext private EntityManager entityManager;

  @Autowired
  public AbstractRepositoryImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
    this.entityClass =
        (Class<T>)
            GenericTypeResolver.resolveTypeArguments(getClass(), AbstractRepositoryImpl.class)[0];
    this.primaryKeyClass =
        (Class<K>)
            GenericTypeResolver.resolveTypeArguments(getClass(), AbstractRepositoryImpl.class)[1];
  }

  @Override
  public T create(T entity) {
    return entityManager.merge(entity);
  }

  @Override
  public void update(T entity) {
    entityManager.merge(entity);
  }

  @Override
  public void delete(T entity) {
    entityManager.remove(entityManager.find(entityClass, entity.getId()));
  }

  @Override
  public List<T> getListBySpecification(Specification specification, int page, int size) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    Query query = entityManager.createQuery(specification.getQuery(criteriaBuilder));
    query.setFirstResult((page - 1) * size);
    query.setMaxResults(size);
    return (List<T>) query.getResultList();
  }

  @Override
  public T getEntityBySpecification(Specification specification) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    T result;
    try {
      Query query = entityManager.createQuery(specification.getQuery(criteriaBuilder));
      result = (T) query.getSingleResult();
      return result;
    } catch (NoResultException e) {
      result = null;
    }
    return result;
  }

  public K getEntityCount() {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
    criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(entityClass)));
    return (K) entityManager.createQuery(criteriaQuery).getSingleResult();
  }
}
