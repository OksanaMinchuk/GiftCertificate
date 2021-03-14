package com.epam.esm.db.specification.impl.purchase;

import com.epam.esm.db.specification.Specification;
import com.epam.esm.model.Purchase;
import com.epam.esm.model.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class GetPurchaseListByUserIdSpecification implements Specification {

  private User user;

  public GetPurchaseListByUserIdSpecification(User user) {
    this.user = user;
  }

  @Override
  public CriteriaQuery getQuery(CriteriaBuilder criteriaBuilder) {
    CriteriaQuery<Purchase> criteria = criteriaBuilder.createQuery(Purchase.class);
    Root<Purchase> root = criteria.from(Purchase.class);
    criteria.where(criteriaBuilder.equal(root.get("user"), user));
    return criteria;
  }
}
