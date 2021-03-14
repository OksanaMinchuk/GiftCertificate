package com.epam.esm.db.impl;

import com.epam.esm.db.TagRepository;
import com.epam.esm.db.specification.Specification;
import com.epam.esm.db.specification.impl.tag.GetTagOfUserWithTheHighestCost;
import com.epam.esm.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigInteger;
import java.util.List;

@Repository
@Primary
public class TagRepositoryImpl extends AbstractRepositoryImpl<Tag, Long>
    implements TagRepository<Tag, Long> {

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  public TagRepositoryImpl(EntityManager entityManager) {
    super(entityManager);
  }

  @Override
  public Long getIdTag(Specification specification) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    Query query = entityManager.createQuery(specification.getQuery(criteriaBuilder));
    BigInteger bigInteger = (BigInteger) query.getSingleResult();
    return bigInteger.longValue();
  }

  @Override
  public List<Tag> getAllListBySpecification(Specification specification) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    Query query = entityManager.createQuery(specification.getQuery(criteriaBuilder));
    return (List<Tag>) query.getResultList();
  }

  @Override
  public List<BigInteger> getBySQLQuery(GetTagOfUserWithTheHighestCost specification) {
    Query query = specification.getBySQLQuery(entityManager);
    return (List<BigInteger>) query.getResultList();
  }
}
