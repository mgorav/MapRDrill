/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Copyright (C) 2009-2016, Oracle Corporation, All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
package com.gm.drill.sql.parser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

public final class DrillParser {

    private static final Charset ENCODING = Charset.forName("UTF-8");

    private ExpressionFactory expressionFactory;

    /**
     * Creates a new instance of {@code DrillParser} with the default set of operators.
     */
    public DrillParser() {

        this.expressionFactory = new ExpressionFactory(Operator.values());
    }

    /**
     * Parses the query expressions and returns AST.
     *
     * @param query
     *            The query expression to parse.
     * @return A root of the parsed AST.
     * @throws DrilParserException
     *             If some exception occurred during parsing, i.e. the {@code query} is syntactically invalid.
     * @throws IllegalArgumentException
     *             If the {@code query} is <tt>null</tt>.
     */
    public Expression parse(String query) throws DrilParserException {

        if (query == null) {
            throw new IllegalArgumentException("query must not be null");
        }
        InputStream is = new ByteArrayInputStream(query.getBytes(ENCODING));
        BaseParser parser = new BaseParser(is, ENCODING.name(), expressionFactory);
        parser.enable_tracing();
        try {
            return parser.Input();

        } catch (Exception | TokenMgrError ex) {
            throw new DrilParserException(ex);
        }
    }

    public static final void main(String args[]) {

        DrillParser parser = new DrillParser();
        parser.parse("serviced.eq('abc').and.serviced.code.lte('234')");
        parser.parse("serviced.code.lte('234')");
        parser.parse("serviced.eq('abc')");
        parser.parse("serviced.eq('abc').and.(abc.eq('123').or.def.gte('234'))");
    }
}
