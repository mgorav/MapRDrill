/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Copyright (C) 2009-2016, Oracle Corporation, All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
package com.gm.drill.sql.parser;

/**
 * This exception is thrown when unknown/unsupported comparison operator is parsed.
 */
public class UnknownQueryOperatorException extends ParseException {

    private static final long serialVersionUID = 1L;
    private final String operator;

    public UnknownQueryOperatorException(String operator) {

        this(operator, "Unknown operator: " + operator);
    }

    public UnknownQueryOperatorException(String operator, String message) {

        super(message);
        this.operator = operator;
    }

    public String getOperator() {

        return operator;
    }
}
