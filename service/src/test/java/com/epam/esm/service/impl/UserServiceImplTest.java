package com.epam.esm.service.impl;

import com.epam.esm.db.impl.UserRepositoryImpl;
import com.epam.esm.db.specification.impl.user.GetUserByIdSpecification;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.exception.DataServiceException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.model.RoleType;
import com.epam.esm.model.User;
import com.epam.esm.util.EntityDTOConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

  @Mock private ModelMapper modelMapper;
  @Mock private EntityDTOConverter entityDTOConverter;
  @Mock private UserRepositoryImpl userRepository;

  @InjectMocks private UserServiceImpl userService;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
  }

  @Test(expected = DataServiceException.class)
  public void testCreate() {
    User user = new User(1L, "login", "pass", RoleType.valueOf("CLIENT"));
    UserDTO userDTO = new UserDTO(1L, "login", "pass", RoleType.valueOf("CLIENT"));

    UserDTO userDTOMock = mock(UserDTO.class);
    when(userDTOMock.getLogin()).thenReturn("login");

    when(userRepository.getEntityBySpecification(anyObject())).thenReturn(user);
    when(userRepository.create(user)).thenReturn(user);
    when(userService.create(userDTO)).thenReturn(userDTO);

    verify(userDTOMock, times(1)).getLogin();
  }

  @Test(expected = EntityNotFoundException.class)
  public void testUpdate() {
    UserDTO userDTO = new UserDTO();
    when(userRepository.getEntityBySpecification(new GetUserByIdSpecification(anyLong())))
        .thenThrow(EntityNotFoundException.class);
    userService.update(userDTO);
    verify(userRepository, times(1)).update(anyObject());
  }

  @Test(expected = EntityNotFoundException.class)
  public void testDelete() {
    UserDTO userDTO = new UserDTO();
    when(userRepository.getEntityBySpecification(new GetUserByIdSpecification(anyLong())))
        .thenThrow(EntityNotFoundException.class);
    userService.delete(userDTO.getId());
    verify(userRepository, times(1)).delete(anyObject());
  }

  @Test(expected = EntityNotFoundException.class)
  public void testGetById() {
    when(userRepository.getEntityBySpecification(anyObject()))
        .thenThrow(EntityNotFoundException.class);

    UserDTO userDTO = userService.getById(anyLong());
    verify(userRepository, times(1)).getEntityBySpecification(anyObject());
  }

  @Test(expected = EntityNotFoundException.class)
  public void testGetByLogin() {
    when(userRepository.getEntityBySpecification(anyObject()))
        .thenThrow(EntityNotFoundException.class);

    UserDTO userDTO = userService.getByLogin(anyString());
    verify(userRepository, times(1)).getEntityBySpecification(anyObject());
  }

  @Test
  public void testGetAll() {
    List<User> users = new ArrayList<>();
    int page = 1;
    int size = 10;

    when(userRepository.getListBySpecification(anyObject(), eq(page), eq(size))).thenReturn(users);
    List<UserDTO> actual = userService.getAll(page, size);
    verify(userRepository, times(1)).getListBySpecification(anyObject(), eq(page), eq(size));
  }
}
