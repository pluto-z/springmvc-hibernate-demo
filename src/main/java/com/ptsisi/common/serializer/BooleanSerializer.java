package com.ptsisi.common.serializer;

import java.io.IOException;
import java.util.Locale;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class BooleanSerializer extends JsonSerializer<Boolean> {

  @Override
  public void serialize(Boolean value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
      JsonProcessingException {
    Locale locale = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getLocale();
    if (locale.getLanguage().equals(Locale.CHINA.getLanguage())) {
      if (null == value) {
        jgen.writeNull();
      } else {
        jgen.writeString(value.booleanValue() ? "是" : "否");
      }
    } else {
      jgen.writeBoolean(value.booleanValue());
    }
  }

}
