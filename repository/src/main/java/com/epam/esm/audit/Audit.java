package com.epam.esm.audit;

import com.epam.esm.model.AbstractEntity;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Audit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;
  @Column(name = "user_name")
  private String userName;
  @Column(name = "action")
  private String action;
  @Column(name = "entity_id")
  private String entityId;
  @Column(name = "entity_name")
  private String entity;
  @Column(name = "created_date")
  @CreatedDate
  private Date createdDate;

  public Audit(AbstractEntity entity, String action) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if(authentication != null) {
      this.userName = authentication.getName();
    }
    this.action = action;
    this.entityId = entity.getId().toString();
    this.entity = entity.getClass().getSimpleName();
  }
}
