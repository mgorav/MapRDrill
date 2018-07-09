/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Copyright (C) 2009-2016, Oracle Corporation, All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
package com.gm.drill.sql.parser;

public final class GroupQueryExpression extends AbstractQueryExpression {

    private final QueryExpression queryExpression;

    /**
     * Can be an AndQueryExpression or OrQueryExpression, in which case, grouping has to be applied.
     * 
     * @param queryExpression
     */
    public GroupQueryExpression(QueryExpression queryExpression) {

        this.queryExpression = queryExpression;
    }

    @Override
    public <R, A> R accept(DrillQueryStringVisitor<R, A> visitor, A param) {

        return visitor.visit(this, param);
    }

    public QueryExpression getQueryExpression() {

        return queryExpression;
    }
}
