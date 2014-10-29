package com.ptsisi.common.predicate;

import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringUtils;

public class ValidEntityKeyPredicate implements Predicate {

  public static final ValidEntityKeyPredicate Instance = new ValidEntityKeyPredicate();

  public boolean evaluate(Object value) {
    if (null == value) return Boolean.FALSE;
    if (value instanceof Number) return 0 != ((Number) value).intValue();
    return (value instanceof String) && StringUtils.isNotEmpty((String) value);
  }

}
