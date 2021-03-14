package com.epam.esm.dto;

import com.epam.esm.util.View;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.Objects;

/**
 * Class {@code AbstractDTO}
 *
 * @author Oksana Minchuk
 * @version 1.0 27/6/2019
 */
public abstract class AbstractDTO<T> {

  @JsonView(View.Public.class)
  protected T id;

  public AbstractDTO() {}

  public AbstractDTO(T id) {
    this.id = id;
  }

  public T getId() {
    return id;
  }

  public void setId(T id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AbstractDTO<?> that = (AbstractDTO<?>) o;
    return id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "id=" + id;
  }
}
