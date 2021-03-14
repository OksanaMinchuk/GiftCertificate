package com.epam.esm.db;

import com.epam.esm.model.AbstractEntity;
import com.epam.esm.model.User;

public interface UserRepository<T extends AbstractEntity, K>
    extends GenericRepository<User, Long> {}
