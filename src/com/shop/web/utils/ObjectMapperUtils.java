package com.shop.web.utils;


import com.shop.web.config.JsonModule;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;

/**
 * Created by guan on 2016/4/12.
 */

public class ObjectMapperUtils {
    protected static final Logger logger = LoggerFactory.getLogger(ObjectMapperUtils.class);
    public static ObjectMapper getObjectMapper() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.serializationInclusion(JsonInclude.Include.NON_NULL)
                .indentOutput(true)
                .simpleDateFormat(DateUtil.DATE_TIME_FORMAT_PATTERN)
                .featuresToDisable(
                        SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
                        DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,
                        DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
                );

        return builder.build()
                .configure(JsonParser.Feature.ALLOW_COMMENTS, true)
                .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
                .registerModule(new JsonModule());

    }

    public static String beanToJson(Object object){
        String json = null;
        try {
            json = getObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error("beanToJson convert fail.");
        }
        return json;
    }

    public static <T> T jsonToBean(String json, Class<T> clazz){
        Object bean = null;
        try {
            bean = getObjectMapper().readValue(json, clazz);
        } catch (IOException e) {
            logger.error("jsonToBean convert fail.");
        }
        return clazz.cast(bean);
    }
}
