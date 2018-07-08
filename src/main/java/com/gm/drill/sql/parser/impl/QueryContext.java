package com.gm.drill.sql.parser.impl;

import static java.lang.String.format;

public class QueryContext {
    private final StringBuilder query;
    private final String currentAlias;
    private final String resourceName;

    public QueryContext(String resourceName, String fn) {
        currentAlias = "r";
        query = new StringBuilder(format("select %s from ", fn == null ? "*" : fn)).append(resourceName).append(" ").append(currentAlias).append(" where ");
        this.resourceName = resourceName;
    }

    public QueryContext append(String queryPart) {
        query.append(queryPart);
        return this;
    }

    public QueryContext appendArgument(String argument) {
        query.append(currentAlias).append(".").append(argument);
        return this;
    }

    public StringBuilder getQuery() {
        return query;
    }

    public QueryContext appendStartBrackets(int length) {
        for (int i = 1; i <= length; ++i) {
            query.append("(");
        }
        return this;
    }

}
