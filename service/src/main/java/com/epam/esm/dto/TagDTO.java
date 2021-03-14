package com.epam.esm.dto;

import com.epam.esm.util.View;
import com.fasterxml.jackson.annotation.JsonView;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Class {@code TagDTO}
 *
 * @author Oksana Minchuk
 * @version 1.0 27/6/2019
 */
public class TagDTO extends AbstractDTO<Long> {

  @NotNull(message = "Name cannot be null")
  @Size(min = 1, max = 30, message = "Name should be from 1 to 30 symbols")
  @JsonView(View.Public.class)
  private String name;

  public TagDTO() {}

  public TagDTO(String name) {
    this.name = name;
  }

  public TagDTO(Long id, String name) {
    super(id);
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String nameTag) {
    this.name = nameTag;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    TagDTO tagDTO = (TagDTO) o;
    return name.equals(tagDTO.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), name);
  }

  @Override
  public String toString() {
    return "TagDTO{" + super.toString() + ", name='" + name + '\'' + '}';
  }
}
