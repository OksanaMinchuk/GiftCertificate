package com.epam.esm.db;

import com.epam.esm.model.AbstractEntity;
import com.epam.esm.model.Purchase;

public interface PurchaseRepository<T extends AbstractEntity, K> extends GenericRepository<Purchase, Long> {


}
