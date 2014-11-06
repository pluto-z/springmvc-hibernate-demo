package com.ptsisi.ui.tagModel;

import javax.servlet.http.HttpServletRequest;

public interface TagLibraryProvider {

  public TagLibrary getTagLibrary(HttpServletRequest request);
}
