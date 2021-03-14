package com.epam.esm.db.impl;

import com.epam.esm.db.UserRepository;
import com.epam.esm.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository()
public class UserRepositoryImpl extends AbstractRepositoryImpl<User, Long>
    implements UserRepository<User, Long> {

  @PersistenceContext private EntityManager entityManager;

  @Autowired
  public UserRepositoryImpl(EntityManager entityManager) {
    super(entityManager);
  }
}
