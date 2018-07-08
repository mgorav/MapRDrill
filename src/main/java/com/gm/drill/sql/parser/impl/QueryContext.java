package com.gm.drill.sql.parser.impl;

public class QueryContext {
    private final StringBuilder query;
    private final String currentAlias;
    private final String resourceName;

    public QueryContext(String resourceName) {
        currentAlias = "r";
        query = new StringBuilder("select * from ").append(resourceName).append(" ").append(currentAlias).append(" where ");
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

    public StringBuilder getQuery()  {
        return query;
    }

    public QueryContext appendStartBrackets(int length) {
        for(int i=1 ; i<=length;++i) {
            query.append("(");
        }
        return this;
    }

}
