package com.ptsisi.common.query;

public enum Lang {
  SQL("sql"), HQL("hql");
  private final String lang;

  private Lang(String name) {
    this.lang = name;
  }

  public String getLang() {
    return lang;
  }

}
