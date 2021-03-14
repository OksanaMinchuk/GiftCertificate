package com.epam.esm.db.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public interface Specification {

    /**
     * Returns Query for specification
     *
     * @param criteriaBuilder
     * @return query
     */
    CriteriaQuery getQuery(CriteriaBuilder criteriaBuilder);
}
