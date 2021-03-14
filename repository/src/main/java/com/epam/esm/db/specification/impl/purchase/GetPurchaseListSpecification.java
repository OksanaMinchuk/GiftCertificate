package com.epam.esm.db.specification.impl.purchase;

import com.epam.esm.db.specification.Specification;
import com.epam.esm.model.Purchase;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class GetPurchaseListSpecification implements Specification {

  public GetPurchaseListSpecification() {}

  @Override
  public CriteriaQuery getQuery(CriteriaBuilder criteriaBuilder) {
    CriteriaQuery<Purchase> criteria = criteriaBuilder.createQuery(Purchase.class);
    Root<Purchase> root = criteria.from(Purchase.class);
    criteria.select(root);
    return criteria;
  }
}
