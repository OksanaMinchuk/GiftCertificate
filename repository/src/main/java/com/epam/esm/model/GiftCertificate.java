package com.epam.esm.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Class {@code GiftCertificate}
 *
 * @author Oksana Minchuk
 * @version 1.0 27/6/2019
 */
@Entity
@Table(name = "certificate")
public class GiftCertificate extends AbstractEntity<Long> {

  private static final int SCALE = 2;

  @Column(name = "gift_name")
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "price")
  private BigDecimal price;

  @CreationTimestamp
  @Column(name = "date_of_creation")
  private LocalDate dateOfCreation;

  @UpdateTimestamp
  @Column(name = "date_of_modification")
  private LocalDate dateOfModification;

  @Column(name = "duration")
  private byte duration;

  @ManyToMany
  @JoinTable(
      name = "tag_certificate",
      joinColumns = {@JoinColumn(name = "certificate_id")},
      inverseJoinColumns = {@JoinColumn(name = "tag_id")})
  private Set<Tag> tags = new HashSet<>();

  @OneToMany(mappedBy = "giftCertificate", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Set<Purchase> purchases = new HashSet<>();

  public GiftCertificate() {}

  public GiftCertificate(
      String name,
      String description,
      BigDecimal price,
      LocalDate dateOfCreation,
      LocalDate dateOfModification,
      byte duration) {
    this.name = name;
    this.description = description;
    this.price = price.setScale(SCALE, BigDecimal.ROUND_HALF_EVEN);
    this.dateOfCreation = dateOfCreation;
    this.dateOfModification = dateOfModification;
    this.duration = duration;
  }

  public GiftCertificate(
      Long id,
      String name,
      String description,
      BigDecimal price,
      LocalDate dateOfCreation,
      LocalDate dateOfModification,
      byte duration) {
    super(id);
    this.name = name;
    this.description = description;
    this.price = price.setScale(SCALE, BigDecimal.ROUND_HALF_EVEN);
    this.dateOfCreation = dateOfCreation;
    this.dateOfModification = dateOfModification;
    this.duration = duration;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price.setScale(SCALE, BigDecimal.ROUND_HALF_EVEN);
  }

  public LocalDate getDateOfCreation() {
    return dateOfCreation;
  }

  public void setDateOfCreation(LocalDate dateOfCreation) {
    this.dateOfCreation = dateOfCreation;
  }

  public LocalDate getDateOfModification() {
    return dateOfModification;
  }

  public void setDateOfModification(LocalDate dateOfModification) {
    this.dateOfModification = dateOfModification;
  }

  public byte getDuration() {
    return duration;
  }

  public void setDuration(byte duration) {
    this.duration = duration;
  }

  public Set<Tag> getTags() {
    return tags;
  }

  public void setTags(Set<Tag> tags) {
    this.tags = tags;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    GiftCertificate that = (GiftCertificate) o;
    return duration == that.duration
        && name.equals(that.name)
        && description.equals(that.description)
        && price.equals(that.price)
        && dateOfCreation.equals(that.dateOfCreation)
        && dateOfModification.equals(that.dateOfModification);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        super.hashCode(), name, description, price, dateOfCreation, dateOfModification, duration);
  }

  @Override
  public String toString() {
    return "GiftCertificate{"
        + super.toString()
        + " name='"
        + name
        + '\''
        + ", description='"
        + description
        + '\''
        + ", price="
        + price
        + ", dateOfCreation="
        + dateOfCreation
        + ", dateOfModification="
        + dateOfModification
        + ", duration="
        + duration
        + '}';
  }
}
