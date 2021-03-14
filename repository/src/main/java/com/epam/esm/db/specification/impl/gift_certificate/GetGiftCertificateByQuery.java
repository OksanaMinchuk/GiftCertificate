package com.epam.esm.db.specification.impl.gift_certificate;

import com.epam.esm.model.GiftCertificate;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class GetGiftCertificateByQuery {

  private static final String SQL_FUNCTION_GET_ALL_BY_TAG_AND_NAME_OR_DESCRIPTION_PART =
      "SELECT id, gift_name, description, price, date_of_creation, date_of_modification, duration "
          + "FROM find_and_sort_certificates(:tagName, :queryPart, :sortBy, :descending)";

  private boolean descending = false;
  private String tagName;
  private String queryPart;
  private String sortBy;

  public GetGiftCertificateByQuery(String tagName, String queryPart, String sortBy) {
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
        .createNativeQuery(
            SQL_FUNCTION_GET_ALL_BY_TAG_AND_NAME_OR_DESCRIPTION_PART, GiftCertificate.class)
        .setParameter("tagName", tagName == null ? "" : tagName)
        .setParameter("queryPart", queryPart == null ? "" : queryPart)
        .setParameter("sortBy", sortBy == null ? "" : sortBy)
        .setParameter("descending", descending);
  }
}
