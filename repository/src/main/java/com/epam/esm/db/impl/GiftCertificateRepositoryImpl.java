package com.epam.esm.db.impl;

import com.epam.esm.db.GiftCertificateRepository;
import com.epam.esm.db.specification.impl.gift_certificate.GetGiftCertificateByQuery;
import com.epam.esm.db.specification.impl.gift_certificate.GetGiftCertificateCountByQuery;
import com.epam.esm.model.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;

@Repository
public class GiftCertificateRepositoryImpl extends AbstractRepositoryImpl<GiftCertificate, Long>
    implements GiftCertificateRepository<GiftCertificate, Long> {

  @PersistenceContext private EntityManager entityManager;

  @Autowired
  public GiftCertificateRepositoryImpl(EntityManager entityManager) {
    super(entityManager);
  }

  @Override
  public List<GiftCertificate> getBySQLQuery(
      GetGiftCertificateByQuery specification, int page, int size) {
    Query query = specification.getBySQLQuery(entityManager);
    query.setFirstResult((page - 1) * size);
    query.setMaxResults(size);
    return (List<GiftCertificate>) query.getResultList();
  }

  @Override
  public Long getCountGiftCertificates(GetGiftCertificateCountByQuery specification) {
    Query query = specification.getBySQLQuery(entityManager);
    BigInteger result = (BigInteger) query.getSingleResult();
    return result.longValue();
  }
}
