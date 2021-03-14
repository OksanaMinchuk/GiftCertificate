package com.epam.esm.db.impl;

import com.epam.esm.db.PurchaseRepository;
import com.epam.esm.exception.DataRepositoryException;
import com.epam.esm.model.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class PurchaseRepositoryImpl extends AbstractRepositoryImpl<Purchase, Long>
    implements PurchaseRepository<Purchase, Long> {

  @PersistenceContext private EntityManager entityManager;

  @Autowired
  public PurchaseRepositoryImpl(EntityManager entityManager) {
    super(entityManager);
  }

  @Override
  public void update(Purchase purchase) {
    throw new DataRepositoryException("Not supported method update(PurchaseDTO purchaseDTO).");
  }
}
