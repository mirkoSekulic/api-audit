package com.audit.apiaudit.modules.shared.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Converter
public class JsonToMapConverer implements AttributeConverter<Map<String, Object>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, Object> map) {

        String customerInfoJson = null;
        try {
            customerInfoJson = objectMapper.writeValueAsString(map);
        } catch (final JsonProcessingException e) {
            log.error("JSON writing error", e);
        }

        return customerInfoJson;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> convertToEntityAttribute(String jsonString) {

        Map<String, Object> map = null;
        try {
            map = objectMapper.readValue(jsonString, Map.class);
        } catch (final IOException e) {
            log.error("JSON reading error", e);
        }

        return map;
    }
}
