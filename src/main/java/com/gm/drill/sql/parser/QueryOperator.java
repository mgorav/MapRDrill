/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Copyright (C) 2009-2016, Oracle Corporation, All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
package com.gm.drill.sql.parser;

public enum QueryOperator {

    EQUAL("eq"),
    EQUAL_IGNORE_CASE("eqic"),
    NOT_EQUAL("neq"),
    GREATER_THAN("gt"),
    GREATER_THAN_OR_EQUAL("gte"),
    LESS_THAN("lt"),
    LESS_THAN_OR_EQUAL("lte"),
    IN("in", true),
    BET("bet", true),
    NOT_IN("out", true),
    LIKE("like"),
    LIKE_IGNORE_CASE("likeic");

    private final String symbol;

    private final boolean multiValue;

    private QueryOperator(String symbol, boolean multiValue) {

        this.multiValue = multiValue;
        this.symbol = symbol;
    }

    private QueryOperator(String symbol) {

        this.multiValue = false;
        this.symbol = symbol;
    }

    public String getSymbol() {

        return symbol;
    }

    public boolean isMultiValue() {

        return multiValue;
    }

    @Override
    public String toString() {

        return getSymbol();
    }

}
