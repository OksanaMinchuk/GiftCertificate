package com.epam.esm.dto;

import com.epam.esm.util.DateDeserializer;
import com.epam.esm.util.View;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Class {@code GiftCertificateDTO}
 *
 * @author Oksana Minchuk
 * @version 1.0 27/6/2019
 */
public class GiftCertificateDTO extends AbstractDTO<Long> {

  private static final int SCALE = 2;

  @NotNull(message = "Title cannot be null")
  @Size(min = 1, max = 30, message = "Title size should be from 1 to 30 symbols")
  @JsonView(View.Public.class)
  private String name;

  @NotNull(message = "Description cannot be null")
  @Size(min = 1, max = 500, message = "Description size should be from 1 to 500 symbols")
  @JsonView(View.Public.class)
  private String description;

  @NotNull(message = "Price cannot be null")
  @DecimalMin(value = "0.00", message = "Price should not be less than 0.00")
  @JsonView(View.Public.class)
  private BigDecimal price;

  @JsonDeserialize(using = DateDeserializer.class)
  @JsonView(View.Public.class)
  private LocalDate dateOfCreation;

  @JsonDeserialize(using = DateDeserializer.class)
  @JsonView(View.Public.class)
  private LocalDate dateOfModification;

  @NotNull(message = "Duration cannot be null")
  @Min(value = 1, message = "Duration should not be less than 1")
  @JsonView(View.Public.class)
  private Byte duration;

  @JsonView(View.Public.class)
  private Set<TagDTO> tags = new HashSet<>();

  public GiftCertificateDTO() {}

  public GiftCertificateDTO(
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

  public Byte getDuration() {
    return duration;
  }

  public void setDuration(Byte duration) {
    this.duration = duration;
  }

  public Set<TagDTO> getTags() {
    return tags;
  }

  public void setTags(Set<TagDTO> tags) {
    this.tags = tags;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    GiftCertificateDTO that = (GiftCertificateDTO) o;
    return duration == that.duration
        && name.equals(that.name)
        && description.equals(that.description)
        && price.equals(that.price)
        && dateOfCreation.equals(that.dateOfCreation)
        && dateOfModification.equals(that.dateOfModification)
        && tags.equals(that.tags);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        name, description, price, dateOfCreation, dateOfModification, duration, tags);
  }

  @Override
  public String toString() {
    return "GiftCertificateDTO{"
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
        + ", tags="
        + tags
        + '}';
  }
}
