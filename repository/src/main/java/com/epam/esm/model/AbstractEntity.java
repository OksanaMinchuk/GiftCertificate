package com.epam.esm.model;

import com.epam.esm.audit.ActionListener;

import javax.persistence.*;
import java.util.Objects;

/**
 * Class {@code AbstractEntity}
 *
 * @author Oksana Minchuk
 * @version 1.0 27/6/2019
 */
@EntityListeners(ActionListener.class)
@MappedSuperclass
public abstract class AbstractEntity<T> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected T id;

  public AbstractEntity() {}

  public AbstractEntity(T id) {
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
    AbstractEntity<?> projectEntity = (AbstractEntity<?>) o;
    return id.equals(projectEntity.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "id=" + id + ", ";
  }
}
