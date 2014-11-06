package com.ptsisi.ui.render;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class DefaultActionUriRender implements ActionUriRender {

  private UriRender render;

  public String render(HttpServletRequest request, String uri) {
    if (render == null) {
      render = new UriRender(request.getContextPath(), "");
    }
    return render.render(request.getRequestURI(), uri);
  }

}
