package com.ptsisi.freemarker.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoding on 14-10-22.
 */
public enum Engine {

  /**
   * Trident is the the Microsoft layout engine, mainly used by Internet
   * Explorer.
   */
  Trident("Trident"),
  /**
   * Open source and cross platform layout engine, used by Firefox and many
   * other browsers.
   */
  Gecko("Gecko"),
  /**
   * Layout engine based on KHTML, used by Safari, Chrome and some other
   * browsers.
   */
  WebKit("WebKit"),
  /**
   * Proprietary layout engine by Opera Software ASA
   */
  Presto("Presto"),
  /**
   * Original layout engine of the Mozilla browser and related products.
   * Predecessor of Gecko.
   */
  Mozilla("Mozilla"),
  /**
   * Layout engine of the KDE project
   */
  Khtml("KHTML"),
  /**
   * HTML parsing and rendering engine of Microsoft Office Word, used by some
   * other products of the Office suite instead of Trident.
   */
  Word("Microsoft Office Word"),
  /**
   * Other or unknown layout engine.
   */
  Other("Other");

  String name;

  List<BrowserCategory> browserCategories = new ArrayList<BrowserCategory>();

  private Engine(String name) {
    this.name = name;
  }

  public List<BrowserCategory> getBrowserCategories() {
    return browserCategories;
  }

  void addCategory(BrowserCategory category) {
    browserCategories.add(category);
  }
}
