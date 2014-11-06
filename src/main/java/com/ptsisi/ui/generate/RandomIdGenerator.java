package com.ptsisi.ui.generate;

import java.util.Random;

import org.apache.commons.lang3.StringUtils;

public class RandomIdGenerator implements UIIdGenerator {

  protected final Random seed = new Random();

  @Override
  public String generate(Class<?> clazz) {
    int nextInt = seed.nextInt();
    nextInt = (nextInt == Integer.MIN_VALUE) ? Integer.MAX_VALUE : Math.abs(nextInt);
    return StringUtils.uncapitalize(clazz.getSimpleName()) + String.valueOf(nextInt);
  }

}
