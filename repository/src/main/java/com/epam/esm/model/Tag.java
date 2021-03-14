package com.epam.esm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Objects;
import java.util.Set;

/**
 * Class {@code Tag}
 *
 * @author Oksana Minchuk
 * @version 1.0 27/6/2019
 */
@Entity
@Table(name = "tag")
public class Tag extends AbstractEntity<Long> {

  @Column(name = "name_tag")
  private String nameTag;

  @ManyToMany(mappedBy = "tags")
  private Set<GiftCertificate> giftCertificates;

  public Tag() {}

  public Tag(String nameTag) {
    this.nameTag = nameTag;
  }

  public Tag(Long id, String name) {
    super(id);
    this.nameTag = name;
  }

  public String getName() {
    return nameTag;
  }

  public void setName(String nameTag) {
    this.nameTag = nameTag;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    Tag tag = (Tag) o;
    return nameTag.equals(tag.nameTag);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), nameTag);
  }

  @Override
  public String toString() {
    return "Tag{" + super.toString() + "nameTag='" + nameTag + '\'' + '}';
  }
}
