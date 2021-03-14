package com.epam.esm.service.impl;

import com.epam.esm.db.UserRepository;
import com.epam.esm.db.specification.impl.user.GetUserByIdSpecification;
import com.epam.esm.db.specification.impl.user.GetUserByLoginSpecification;
import com.epam.esm.db.specification.impl.user.GetUserListSpecification;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.exception.DataServiceException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.model.RoleType;
import com.epam.esm.model.User;
import com.epam.esm.service.UserService;
import com.epam.esm.util.EntityDTOConverter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;;

@Service
@Transactional
public class UserServiceImpl implements UserService<User, UserDTO, Long> {

  private static final Logger LOGGER = Logger.getRootLogger();
  private final PasswordEncoder passwordEncoder;
  private UserRepository<User, Long> userRepository;
  private EntityDTOConverter entityDTOConverter;

  @Autowired
  public UserServiceImpl(
      UserRepository userRepository,
      EntityDTOConverter entityDTOConverter,
      PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.entityDTOConverter = entityDTOConverter;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * Sign up operation
   *
   * @param userDTO
   * @return user id
   * @throws DataServiceException
   */
  @Override
  public UserDTO create(UserDTO userDTO) throws DataServiceException {
    LOGGER.debug("UserServiceImpl : create method for login: " + userDTO.getLogin());

    userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
    User user =
        userRepository.getEntityBySpecification(
            new GetUserByLoginSpecification(userDTO.getLogin()));
    if (user != null) {
      throw new DataServiceException("User with login: " + userDTO.getLogin() + " already exist.");
    }
    user = (User) entityDTOConverter.convertDtoToEntity(userDTO, new User());
    user.setRole(RoleType.CLIENT);

    UserDTO userDTO1 =
        (UserDTO) entityDTOConverter.convertEntityToDto(userRepository.create(user), new UserDTO());
    return userDTO1;
  }

  @Override
  public void update(UserDTO userDTO) throws DataServiceException {
    LOGGER.debug("UserServiceImpl : updateEntity method for id:" + userDTO.getId());

    if (getById(userDTO.getId()) == null) {
      throw new EntityNotFoundException("User for id: " + userDTO.getId() + " not found.");
    }

    User user =
        userRepository.getEntityBySpecification(
            new GetUserByLoginSpecification(userDTO.getLogin()));
    if (user != null) {
      throw new DataServiceException("User with login: " + userDTO.getLogin() + " already exist.");
    }
    user = (User) entityDTOConverter.convertDtoToEntity(userDTO, new User());
    user.setRole(RoleType.CLIENT);
    userRepository.update(user);
  }

  @Override
  public void delete(Long id) {
    LOGGER.debug("UserServiceImpl : delete method for id: " + id);

    User user = userRepository.getEntityBySpecification(new GetUserByIdSpecification(id));
    if (user == null) {
      throw new EntityNotFoundException("User for id: " + id + " not found.");
    }
    userRepository.delete(user);
  }

  @Override
  public UserDTO getById(Long id) throws DataServiceException {
    LOGGER.debug("UserServiceImpl : getEntityBySpecification method for id: " + id);

    User user = userRepository.getEntityBySpecification(new GetUserByIdSpecification(id));
    if (user == null) {
      throw new EntityNotFoundException("User for id: " + id + " not found.");
    }
    return (UserDTO) entityDTOConverter.convertEntityToDto(user, new UserDTO());
  }

  @Override
  public UserDTO getByLogin(String login) throws DataServiceException {
    LOGGER.debug("UserServiceImpl : getByLogin method for login: " + login);

    User user = userRepository.getEntityBySpecification(new GetUserByLoginSpecification(login));
    if (user == null) {
      throw new EntityNotFoundException("User for login: " + login + " not found.");
    }
    return (UserDTO) entityDTOConverter.convertEntityToDto(user, new UserDTO());
  }

  @Override
  public List<UserDTO> getAll(int page, int size) {
    LOGGER.debug("UserServiceImpl : getAll <UserDTO> method");
    List<User> users =
        userRepository.getListBySpecification(new GetUserListSpecification(), page, size);

    List<UserDTO> usersDTO = new ArrayList<>();
    for (User user : users) {
      usersDTO.add((UserDTO) entityDTOConverter.convertEntityToDto(user, new UserDTO()));
    }
    return usersDTO;
  }

  @Override
  public Long getEntityCount() {
    return userRepository.getEntityCount();
  }
}
