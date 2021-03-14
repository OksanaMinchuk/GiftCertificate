package com.epam.esm.audit;

import com.epam.esm.model.AbstractEntity;
import com.epam.esm.util.BeanUtil;

import javax.persistence.EntityManager;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.transaction.Transactional;

import static javax.transaction.Transactional.TxType.MANDATORY;

public class ActionListener {

  @PostPersist
  public void postPersist(AbstractEntity entity) {
    perform(entity, "CREATE");
  }

  @PostRemove
  public void postRemove(AbstractEntity entity) {
    perform(entity, "DELETE");
  }

  @PostUpdate
  public void postUpdate(AbstractEntity entity) {
    perform(entity, "UPDATE");
  }

  @Transactional(MANDATORY)
  private void perform(AbstractEntity entity, String action) {
    EntityManager entityManager = BeanUtil.getBean(EntityManager.class);
    entityManager.persist(new Audit(entity, action));
  }
}
