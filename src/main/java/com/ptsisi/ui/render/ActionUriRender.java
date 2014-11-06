package com.ptsisi.ui.render;

import javax.servlet.http.HttpServletRequest;

public interface ActionUriRender {

  String render(HttpServletRequest request, String uri);
}
