package com.ai.sys.utils;

import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelUtils {

    public static final String include_id = "id";
    public static final String ignore_serialVersionUID = "serialVersionUID";

    public static boolean checkIfOnlyOneFieldHasValue(Class<?> clazz, Object obj, String fieldName, List<String> ignores) throws NoSuchFieldException, IllegalAccessException {
        Map<String, Object> results = new HashMap<>();
        ReflectionUtils.doWithFields(clazz, field -> {
            String name = field.getName();
            field.setAccessible(true);
            Object value = field.get(obj);
            if (!ignores.contains(name)){
                results.put(name, value);
            }
        });
        Object fieldValue = results.get(fieldName);
        if (ObjectUtils.isEmpty(fieldValue)) {
            return false;
        } else {
            return results.entrySet().stream()
                    .filter(entry -> !entry.getKey().equals(fieldName))
                    //keep fields except the field to check
                    .allMatch(entry -> ObjectUtils.isEmpty(entry.getValue()));
        }
    }
}
