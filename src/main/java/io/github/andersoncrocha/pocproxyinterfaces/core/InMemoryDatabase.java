package io.github.andersoncrocha.pocproxyinterfaces.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryDatabase {

    private static final Map<Class<?>, List<Object>> VALUES = new HashMap<>();

    private InMemoryDatabase() {
        throw new IllegalStateException("Utility class");
    }

    public static Object save(Object t) {
        List<Object> valuesByClazzType = Optional.ofNullable(VALUES.get(t.getClass()))
                .orElse(new ArrayList<>());
        valuesByClazzType.add(t);
        VALUES.put(t.getClass(), valuesByClazzType);
        return t;
    }

    public static List<Object> getByType(Class<?> clazz) {
        return Optional.ofNullable(VALUES.get(clazz))
                .orElse(Collections.emptyList());
    }

}
