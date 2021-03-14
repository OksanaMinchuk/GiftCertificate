package com.epam.esm.db.specification.impl.user;

import com.epam.esm.db.specification.Specification;
import com.epam.esm.model.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class GetUserByLoginSpecification implements Specification {

  private String login;

  public GetUserByLoginSpecification(String login) {
    this.login = login;
  }

  @Override
  public CriteriaQuery getQuery(CriteriaBuilder criteriaBuilder) {
    CriteriaQuery<User> criteria = criteriaBuilder.createQuery(User.class);
    Root<User> root = criteria.from(User.class);
    criteria.where(criteriaBuilder.equal(root.get("login"), login));
    return criteria;
  }
}
