package com.epam.esm.model;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "purchase")
public class Purchase extends AbstractEntity<Long> {

  private static final int SCALE = 2;

  @Column(name = "price")
  private BigDecimal price;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @CreationTimestamp
  @Column(name = "timestamp")
  private LocalDateTime timestamp;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "certificate_id")
  private GiftCertificate giftCertificate;

  public Purchase() {}

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price.setScale(SCALE, BigDecimal.ROUND_HALF_EVEN);
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public GiftCertificate getGiftCertificate() {
    return giftCertificate;
  }

  public void setGiftCertificate(GiftCertificate giftCertificate) {
    this.giftCertificate = giftCertificate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    Purchase purchase = (Purchase) o;
    return price.equals(purchase.price)
        && timestamp.equals(purchase.timestamp)
        && user.equals(purchase.user)
        && giftCertificate.equals(purchase.giftCertificate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), price, timestamp, user, giftCertificate);
  }

  @Override
  public String toString() {
    return "Purchase {"
        + super.toString()
        + " price="
        + price
        + ", timestamp="
        + timestamp
        + ", user="
        + user
        + ", giftCertificate="
        + giftCertificate
        + '}';
  }
}
