package com.ifsp.edu.hto.sge.contratos.event.utils;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifsp.edu.hto.sge.contratos.event.exception.JsonParseException;

import java.io.IOException;
import java.util.Objects;

public class JsonUtils {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private JsonUtils() {
    }

    public static String toJson(Object value) {
        if (Objects.nonNull(value)) {
            try {
                return MAPPER.writeValueAsString(value);
            } catch (JsonProcessingException var2) {
                throw new JsonParseException("JSON parsing error", var2);
            }
        } else {
            return null;
        }
    }

    public static <T> T toObject(String value, Class<T> entityClass) {
        if (Objects.nonNull(value)) {
            try {
                return MAPPER.readValue(value, entityClass);
            } catch (IOException var3) {
                throw new JsonParseException("Object parsing error", var3);
            }
        } else {
            return null;
        }
    }

    public static <T> T toObject(String value, TypeReference<?> typeReference) {
        if (Objects.nonNull(value)) {
            try {
                return MAPPER.readValue(value, typeReference);
            } catch (IOException var3) {
                throw new JsonParseException("Object parsing error", var3);
            }
        } else {
            return null;
        }
    }

    public static <T> T convert(Object object, Class<T> entityClass) {
        if (Objects.nonNull(object)) {
            try {
                return MAPPER.convertValue(object, entityClass);
            } catch (Exception var3) {
                throw new JsonParseException("Object parsing error", var3);
            }
        } else {
            return null;
        }
    }

    public static <T> T convert(Object object, TypeReference<?> typeReference) {
        if (Objects.nonNull(object)) {
            try {
                return MAPPER.convertValue(object, typeReference);
            } catch (Exception var3) {
                throw new JsonParseException("Object parsing error", var3);
            }
        } else {
            return null;
        }
    }
}