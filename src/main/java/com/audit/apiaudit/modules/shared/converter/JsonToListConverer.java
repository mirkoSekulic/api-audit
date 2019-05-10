package com.audit.apiaudit.modules.shared.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.List;

@Slf4j
@Converter
public class JsonToListConverer implements AttributeConverter<List<Object>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<Object> list) {

        String customerInfoJson = null;
        try {
            customerInfoJson = objectMapper.writeValueAsString(list);
        } catch (final JsonProcessingException e) {
            log.error("JSON writing error", e);
        }

        return customerInfoJson;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Object> convertToEntityAttribute(String jsonString) {

        List<Object> list = null;
        try {
            list = objectMapper.readValue(jsonString, new TypeReference<List<Object>>() {});
        } catch (final IOException e) {
            log.error("JSON reading error", e);
        }

        return list;
    }
}
