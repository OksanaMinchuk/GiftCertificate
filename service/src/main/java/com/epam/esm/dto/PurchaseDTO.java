package com.epam.esm.dto;

import com.epam.esm.util.View;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Class {@code PurchaseDTO}
 *
 * @author Oksana Minchuk
 * @version 1.0 27/6/2019
 */
public class PurchaseDTO extends AbstractDTO<Long> {

  private static final int SCALE = 2;

  @NotNull(message = "Price cannot be null")
  @DecimalMin(value = "0.00", message = "Price should not be less than 0.0")
  @JsonView({View.Public.class, View.Closed.class})
  private BigDecimal price;

  @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
  @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
  @JsonView({View.Public.class, View.Closed.class})
  private LocalDateTime timestamp;

  @JsonView(View.Public.class)
  private UserDTO userDTO;

  @JsonView(View.Public.class)
  private GiftCertificateDTO giftCertificateDTO;

  public PurchaseDTO() {}

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

  public UserDTO getUserDTO() {
    return userDTO;
  }

  public void setUserDTO(UserDTO userDTO) {
    this.userDTO = userDTO;
  }

  public GiftCertificateDTO getGiftCertificateDTO() {
    return giftCertificateDTO;
  }

  public void setGiftCertificateDTO(GiftCertificateDTO giftCertificateDTO) {
    this.giftCertificateDTO = giftCertificateDTO;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    PurchaseDTO that = (PurchaseDTO) o;
    return price.equals(that.price)
        && timestamp.equals(that.timestamp)
        && userDTO.equals(that.userDTO)
        && giftCertificateDTO.equals(that.giftCertificateDTO);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), price, timestamp, userDTO, giftCertificateDTO);
  }

  @Override
  public String toString() {
    return "PurchaseDTO {"
        + super.toString()
        + " price="
        + price
        + ", timestamp="
        + timestamp
        + ", userDTO="
        + userDTO
        + ", giftCertificateDTO="
        + giftCertificateDTO
        + '}';
  }
}
