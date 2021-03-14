package com.epam.esm.db.specification.impl.gift_certificate;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class GetGiftCertificateCountByQuery {

  private static final String SQL_FUNCTION_COUNT_GET_ALL_BY_TAG_AND_NAME_OR_DESCRIPTION_PART =
      "SELECT count(id) FROM find_and_sort_certificates(:tagName, :queryPart, :sortBy, :descending)";

  private boolean descending = false;
  private String tagName;
  private String queryPart;
  private String sortBy;

  public GetGiftCertificateCountByQuery(String tagName, String queryPart, String sortBy) {
    this.tagName = tagName;
    this.queryPart = queryPart;
    this.sortBy = sortBy;
  }

  public Query getBySQLQuery(EntityManager entityManager) {
    if (sortBy != null) {
      descending = sortBy.startsWith("-");
    }
    if (descending) {
      sortBy = sortBy.substring(1);
    }

    return entityManager
        .createNativeQuery(SQL_FUNCTION_COUNT_GET_ALL_BY_TAG_AND_NAME_OR_DESCRIPTION_PART)
        .setParameter("tagName", tagName == null ? "" : tagName)
        .setParameter("queryPart", queryPart == null ? "" : queryPart)
        .setParameter("sortBy", sortBy == null ? "" : sortBy)
        .setParameter("descending", descending);
  }
}
