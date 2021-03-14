package com.epam.esm.dto;

/**
 * Class {@code OrderDTO}
 *
 * @author Oksana Minchuk
 * @version 1.0 27/6/2019
 */
public class OrderDTO {

  private Long userId;

  private Long giftCertificateId;

  public OrderDTO() {}

  public OrderDTO(Long userId, Long giftCertificateId) {
    this.userId = userId;
    this.giftCertificateId = giftCertificateId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getGiftCertificateId() {
    return giftCertificateId;
  }

  public void setGiftCertificateId(Long giftCertificateId) {
    this.giftCertificateId = giftCertificateId;
  }

  @Override
  public String toString() {
    return "OrderDTO{" + "userId=" + userId + ", giftCertificateId=" + giftCertificateId + '}';
  }
}
