/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Copyright (C) 2009-2016, Oracle Corporation, All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
package com.gm.drill.sql.parser;

public enum LogicalOperator {

    AND(".and."),
    OR(".or.");

    private final String symbol;

    private LogicalOperator(String symbol) {

        this.symbol = symbol;
    }

    @Override
    public String toString() {

        return symbol;
    }
}
