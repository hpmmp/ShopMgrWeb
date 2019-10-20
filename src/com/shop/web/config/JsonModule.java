package com.shop.web.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


/**
 */
@Component
public class JsonModule extends SimpleModule {


    public JsonModule() {

        addSerializer(java.sql.Date.class, new JsonSerializer<Date>() {

            private final DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

            @Override
            public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeString(df.format(value));
            }
        });

        addSerializer(Enum.class, new JsonSerializer<Enum>() {
            @Override
            public void serialize(Enum value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
                gen.writeNumber(value.ordinal());
            }
        });

        addDeserializer(java.sql.Date.class, new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                try {
                    return new Date(Date.parse(p.getText()));
                } catch (Exception e) {
                    return null;
                }
            }
        });

    }

}
