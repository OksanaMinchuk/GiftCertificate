package com.epam.esm.db.specification.impl.tag;

import com.epam.esm.db.specification.Specification;
import com.epam.esm.model.Tag;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class GetTagListSpecification implements Specification {

  public GetTagListSpecification() {}

  @Override
  public CriteriaQuery getQuery(CriteriaBuilder criteriaBuilder) {
    CriteriaQuery<Tag> criteria = criteriaBuilder.createQuery(Tag.class);
    Root<Tag> root = criteria.from(Tag.class);
    criteria.select(root);
    return criteria;
  }
}
