package com.epam.esm.db.specification.impl.tag;

import com.epam.esm.db.specification.Specification;
import com.epam.esm.model.Tag;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class GetTagByIdSpecification implements Specification {

  private Long id;

  public GetTagByIdSpecification(Long id) {
    this.id = id;
  }

  @Override
  public CriteriaQuery getQuery(CriteriaBuilder criteriaBuilder) {
    CriteriaQuery<Tag> criteria = criteriaBuilder.createQuery(Tag.class);
    Root<Tag> root = criteria.from(Tag.class);
    criteria.where(criteriaBuilder.equal(root.get("id"), id));
    return criteria;
  }
}
