package com.gm.drill.sql.parser;

import com.gm.util.Return2;
import com.google.common.collect.Iterables;

import java.util.ArrayList;
import java.util.List;

public class QueryValueInterpreter {

    public Object getValue(List<String> arguments, boolean nullAllowed) {

        if (arguments == null) {
            if (nullAllowed) {
                return null;
            }
            throw new IllegalArgumentException("Null Arguments are not allowed");
        }
        return valueOf(Iterables.getFirst(arguments, null));
    }

    public Return2<Object, Object> between(List<String> values) {

        if (values.size() != 2) {
            throw new RuntimeException("ERROR_CODE" +
                    String.format("Search Operator between accepts 2 arguments. The current arguments are %s", values));
        }
        return new Return2<>(valueOf(values.get(0)), valueOf(values.get(1)));
    }

    public Iterable<?> in(List<String> values) {

        if (values == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }
        return valueOf(values);
    }

    private List<Object> valueOf(List<String> values) {

        List<Object> interpretedValues = new ArrayList<>(values.size());
        for (String value : values) {
            interpretedValues.add(valueOf(value));
        }
        return interpretedValues;
    }

    public Object valueOf(String value) {

        Object valueObject = interpretValue(value);
        return valueObject;
    }


    private Object interpretValue(String value) {

        // String value, add quotes if not there already
        return value.contains("'") ? value : "'" + value + "'";
    }

}
