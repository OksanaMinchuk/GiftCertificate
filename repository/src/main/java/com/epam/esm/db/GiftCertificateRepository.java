package com.epam.esm.db;

import com.epam.esm.db.specification.impl.gift_certificate.GetGiftCertificateByQuery;
import com.epam.esm.db.specification.impl.gift_certificate.GetGiftCertificateCountByQuery;
import com.epam.esm.model.AbstractEntity;
import com.epam.esm.model.GiftCertificate;

import java.util.List;

public interface GiftCertificateRepository<T extends AbstractEntity, K>
        extends GenericRepository<GiftCertificate, Long> {

    /**
     * Returns entity list from database by specification with SQL query
     * @param specification
     * @param page
     * @param size
     * @return
     */
    List<T> getBySQLQuery(GetGiftCertificateByQuery specification, int page, int size);

    /**
     * Returns entity count from database by specification with SQL query
     *
     * @param specification
     * @return
     */
    K getCountGiftCertificates(GetGiftCertificateCountByQuery specification);

}
