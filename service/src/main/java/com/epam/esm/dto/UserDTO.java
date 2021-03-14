package com.epam.esm.dto;

import com.epam.esm.model.RoleType;
import com.epam.esm.util.View;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Class {@code UserDTO}
 *
 * @author Oksana Minchuk
 * @version 1.0 05/8/2019
 */
public class UserDTO extends AbstractDTO<Long> {

  @NotNull(message = "Login cannot be null")
  @Size(min = 1, max = 30, message = "Login should be from 1 to 30 symbols")
  @JsonView({View.Public.class})
  private String login;

  @NotNull(message = "Password cannot be null")
  @Pattern(
      regexp = "[A-Za-z\\d]{4,15}",
      message = "Invalid password, use 4..8 symbols, only letters and digits")
  private String password;

  @Enumerated(EnumType.STRING)
  @JsonView(View.Public.class)
  private RoleType role;

  public UserDTO() {}

  public UserDTO(Long id, String login, String password, RoleType role) {

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
    UserDTO userDTO = (UserDTO) o;
    return login.equals(userDTO.login)
        && password.equals(userDTO.password)
        && role.equals(userDTO.role);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), login, password, role);
  }

  @Override
  public String toString() {
    return "UserDTO{ "
        + super.toString()
        + " login='"
        + login
        + '\''
        + ", role='"
        + role
        + '\''
        + '}';
  }
}
