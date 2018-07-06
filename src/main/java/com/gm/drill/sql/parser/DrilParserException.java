/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Copyright (C) 2009-2016, Oracle Corporation, All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
package com.gm.drill.sql.parser;

/**
 * A top level exception of DrillParser that wraps all exceptions occurred in parsing.
 */
public class DrilParserException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DrilParserException(Throwable cause) {

        super(cause);
    }
}
