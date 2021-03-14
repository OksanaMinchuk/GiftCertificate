package com.epam.esm.db.specification.impl.gift_certificate;

import com.epam.esm.db.specification.Specification;
import com.epam.esm.model.GiftCertificate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class GetGiftCertificateByIdSpecification implements Specification {

  private Long id;

  public GetGiftCertificateByIdSpecification(Long id) {
    this.id = id;
  }

  @Override
  public CriteriaQuery getQuery(CriteriaBuilder criteriaBuilder) {
    CriteriaQuery<GiftCertificate> criteria = criteriaBuilder.createQuery(GiftCertificate.class);
    Root<GiftCertificate> root = criteria.from(GiftCertificate.class);
    criteria.where(criteriaBuilder.equal(root.get("id"), id));
    return criteria;
  }
}
