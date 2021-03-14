package com.epam.esm.util;

import com.epam.esm.dto.UserDTO;
import com.epam.esm.model.User;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AccessValidator {

  private static UserService<User, UserDTO, Long> userService;

  @Autowired
  public AccessValidator(UserService<User, UserDTO, Long> userService) {
    this.userService = userService;
  }

  public static void isAccessAllowed(Long userId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDTO currentUsers = userService.getByLogin(authentication.getName());
    if (!currentUsers.getId().equals(userId)) {
      throw new AccessDeniedException("Access is denied.");
    }
  }
}
