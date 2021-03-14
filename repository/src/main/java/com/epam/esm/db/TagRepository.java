package com.epam.esm.db;

import com.epam.esm.db.specification.Specification;
import com.epam.esm.db.specification.impl.tag.GetTagOfUserWithTheHighestCost;
import com.epam.esm.model.AbstractEntity;
import com.epam.esm.model.Tag;

import java.math.BigInteger;
import java.util.List;

public interface TagRepository<T extends AbstractEntity, K> extends GenericRepository<Tag, Long> {

  K getIdTag(Specification specification);

  /**
   * Returns entity list from database by specification
   *
   * @param specification
   * @return entity list
   */
  List<T> getAllListBySpecification(Specification specification);

  /**
   * Returns id list from database by specification
   *
   * @param specification
   * @return id list
   */
  List<BigInteger> getBySQLQuery(GetTagOfUserWithTheHighestCost specification);
}
