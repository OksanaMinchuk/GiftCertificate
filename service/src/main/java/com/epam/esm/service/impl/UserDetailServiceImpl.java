package com.epam.esm.service.impl;

import com.epam.esm.db.UserRepository;
import com.epam.esm.db.specification.impl.user.GetUserByLoginSpecification;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service(value = "userDetailsService")
public class UserDetailServiceImpl implements UserDetailsService {

  private final UserRepository<User, Long> userRepository;

  @Autowired
  public UserDetailServiceImpl(UserRepository<User, Long> userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

    User user = userRepository.getEntityBySpecification(new GetUserByLoginSpecification(login));
    if (user == null) {
      throw new EntityNotFoundException("User for login: " + login + " not found.");
    }
    GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().name());
    return new org.springframework.security.core.userdetails.User(
        user.getLogin(), user.getPassword(), Arrays.asList(authority));
  }
}
