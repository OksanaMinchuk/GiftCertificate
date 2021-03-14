package com.epam.esm.db.specification.impl.tag;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class GetTagOfUserWithTheHighestCost {

  private static final String SORT_CRITERIA = "popularity";

  private static final String SQL_GET_WIDELY_USED_TAG_FOR_USER =
      "SELECT tag_certificate.tag_id "
          + "FROM certificate "
          + "JOIN purchase ON certificate.id = purchase.certificate_id "
          + "JOIN tag_certificate ON certificate.id = tag_certificate.certificate_id "
          + "WHERE purchase.user_id = :userId GROUP BY tag_certificate.tag_id";

  private Long userId;
  private String sort;
  private int limit;

  public GetTagOfUserWithTheHighestCost(Long userId, String sort, int limit) {
    this.userId = userId;
    this.sort = sort;
    this.limit = limit;
  }

  public Query getBySQLQuery(EntityManager entityManager) {
    String SQL = SQL_GET_WIDELY_USED_TAG_FOR_USER;

    if (sort != null) {
      if (sort.equals(SORT_CRITERIA)) {
        SQL += " ORDER BY sum(purchase.price) DESC";
      }
    }
    SQL += " limit :limit";

    return entityManager
        .createNativeQuery(SQL)
        .setParameter("userId", userId)
        .setParameter("limit", limit);
  }
}
