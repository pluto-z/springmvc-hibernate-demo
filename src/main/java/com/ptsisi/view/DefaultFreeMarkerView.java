package com.ptsisi.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import com.google.common.collect.Maps;
import com.ptsisi.ui.tagModel.TagLibraryProvider;
import com.ptsisi.ui.tagModel.TagLibraryProviderFactory;

public class DefaultFreeMarkerView extends FreeMarkerView {

  private static final String CONTEXT_PATH = "base";

  private Map<String, TagLibraryProvider> tagLibraryProviders = Maps.newHashMap();

  @Override
  public void afterPropertiesSet() throws Exception {
    TagLibraryProviderFactory factory = BeanFactoryUtils.beanOfTypeIncludingAncestors(getApplicationContext(),
        TagLibraryProviderFactory.class, true, false);
    if (null != factory) {
      this.tagLibraryProviders = factory.getTagLibraryProviders();
    }
    super.afterPropertiesSet();
  }

  @Override
  protected void exposeHelpers(Map<String, Object> model, HttpServletRequest request) throws Exception {
    model.put(CONTEXT_PATH, request.getContextPath());
    for (Map.Entry<String, TagLibraryProvider> entry : tagLibraryProviders.entrySet()) {
      model.put(entry.getKey(), entry.getValue().getTagLibrary(request));
    }
    super.exposeHelpers(model, request);
  }

}
