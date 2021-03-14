package com.epam.esm.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends AbstractEntity<Long> {

  @Column(name = "user_login")
  private String login;

  @Column(name = "user_password")
  private String password;

  @Column(name = "user_role")
  @Enumerated(EnumType.STRING)
  private RoleType role;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Set<Purchase> purchases = new HashSet<>();

  public User() {}

  public User(Long id, String login, String password, RoleType role) {
    super(id);
    this.login = login;
    this.password = password;
    this.role = role;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public RoleType getRole() {
    return role;
  }

  public void setRole(RoleType role) {
    this.role = role;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    User user = (User) o;
    return login.equals(user.login) && password.equals(user.password) && role.equals(user.role);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), login, password, role);
  }

  @Override
  public String toString() {
    return "User{" + super.toString() + "login='" + login + '\'' + ", role='" + role + '\'' + '}';
  }
}
