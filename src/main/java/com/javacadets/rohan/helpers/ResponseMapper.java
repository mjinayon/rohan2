package com.javacadets.rohan.helpers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.javacadets.rohan.entities.SME;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ResponseMapper {
    public static JsonNode mapObject(Object obj, Map<String, String[]> mFilters) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        SimpleFilterProvider simpleFilterProvider = new SimpleFilterProvider();
        for (Map.Entry<String, String[]> entry: mFilters.entrySet()) {
            if (entry.getKey().equals("filter_user")) {
                simpleFilterProvider.addFilter((obj instanceof SME ? "filter_sme": "filter_student"), SimpleBeanPropertyFilter.serializeAllExcept(entry.getValue()));
                continue;
            }
            simpleFilterProvider.addFilter(entry.getKey(), SimpleBeanPropertyFilter.serializeAllExcept(entry.getValue()));
        }
        ObjectWriter objectWriter = mapper.writer(simpleFilterProvider);
        return mapper.readTree(objectWriter.writeValueAsString(obj));
    }

    public static List<JsonNode> mapObjects(List<? extends Object> objs, Map<String, String[]> mFilters) throws JsonProcessingException {
        List<JsonNode> data = new ArrayList<>();
        for (Object obj: objs) {
            data.add(mapObject(obj, mFilters));
        }
        return data;
    }
}
