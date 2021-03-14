package com.epam.esm.db.specification.impl.purchase;

import com.epam.esm.db.specification.Specification;
import com.epam.esm.model.Purchase;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class GetPurchaseByUserIdAndGiftcertificateIdSpecification implements Specification {

  private Long userId;
  private Long giftCertificateId;

  public GetPurchaseByUserIdAndGiftcertificateIdSpecification(Long userId, Long giftCertificateId) {
    this.userId = userId;
    this.giftCertificateId = giftCertificateId;
  }

  @Override
  public CriteriaQuery getQuery(CriteriaBuilder criteriaBuilder) {
    CriteriaQuery<Purchase> criteria = criteriaBuilder.createQuery(Purchase.class);
    Root<Purchase> root = criteria.from(Purchase.class);
    criteria.where(
        criteriaBuilder.equal(root.get("user_id"), userId),
        criteriaBuilder.equal(root.get("certificate_id"), giftCertificateId));
    return criteria;
  }
}
