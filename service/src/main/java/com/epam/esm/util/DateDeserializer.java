package com.epam.esm.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class DateDeserializer extends StdDeserializer<LocalDate> {

  protected DateDeserializer() {
    super(LocalDate.class);
  }

  @Override
  public LocalDate deserialize(JsonParser parser, DeserializationContext context)
      throws IOException {
    try {
      return LocalDate.parse(parser.readValueAs(String.class));
    } catch (DateTimeParseException e) {
      return null;
    }
  }
}
