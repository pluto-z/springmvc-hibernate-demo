package com.ptsisi.common.dao;

import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.Lists;

public class Operation {
  public static enum OperationType {
    SAVE_UPDATE, REMOVE
  }

  public final OperationType type;
  public final Object data;

  public Operation(OperationType type, Object data) {
    super();
    this.type = type;
    this.data = data;
  }

  public static Builder saveOrUpdate(Collection<?> entities) {
    return new Builder().saveOrUpdate(entities);
  }

  public static Builder saveOrUpdate(Object... entities) {
    return new Builder().saveOrUpdate(entities);
  }

  public static Builder remove(Collection<?> entities) {
    return new Builder().remove(entities);
  }

  public static Builder remove(Object... entities) {
    return new Builder().remove(entities);
  }

  public static class Builder {
    private List<Operation> operations = Lists.newArrayList();

    public Builder saveOrUpdate(Collection<?> entities) {
      if (CollectionUtils.isEmpty(entities)) { return this; }
      for (Object entity : entities) {
        if (null != entity) operations.add(new Operation(OperationType.SAVE_UPDATE, entity));
      }
      return this;
    }

    public Builder saveOrUpdate(Object... entities) {
      if (ArrayUtils.isEmpty(entities)) { return this; }
      for (Object entity : entities) {
        if (null != entity) operations.add(new Operation(OperationType.SAVE_UPDATE, entity));
      }
      return this;
    }

    public Builder remove(Collection<?> entities) {
      if (CollectionUtils.isEmpty(entities)) { return this; }
      for (Object entity : entities) {
        if (null != entity) operations.add(new Operation(OperationType.REMOVE, entity));
      }
      return this;
    }

    public Builder remove(Object... entities) {
      if (ArrayUtils.isEmpty(entities)) { return this; }
      for (Object entity : entities) {
        if (null != entity) operations.add(new Operation(OperationType.REMOVE, entity));
      }
      return this;
    }

    public List<Operation> build() {
      return operations;
    }
  }

}
