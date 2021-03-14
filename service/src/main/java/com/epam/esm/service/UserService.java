package com.epam.esm.service;

import com.epam.esm.dto.AbstractDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.model.AbstractEntity;
import com.epam.esm.model.User;

import java.util.List;

public interface UserService<T extends AbstractEntity, D extends AbstractDTO, K>
        extends GenericService<User, UserDTO, Long> {

    /**
     * Returns user by login
     *
     * @param login
     * @return userDTO
     */
    UserDTO getByLogin(String login);

    /**
     * Returns list of users
     *
     * @param page - pages quantity
     * @param size - items quantity on the page
     * @return
     */
    List<UserDTO> getAll(int page, int size);
}
