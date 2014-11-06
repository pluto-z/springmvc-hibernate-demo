package com.ptsisi.ui.tagModel;

import java.util.Map;

public class TagLibraryProviderFactory {

  private Map<String, TagLibraryProvider> tagLibraryProviders;

  public TagLibraryProviderFactory(Map<String, TagLibraryProvider> tagLibraryProviders) {
    this.tagLibraryProviders = tagLibraryProviders;
  }

  /**
   * @return the tagLibraryProviders
   */
  public Map<String, TagLibraryProvider> getTagLibraryProviders() {
    return tagLibraryProviders;
  }

}
