/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Copyright (C) 2009-2016, Oracle Corporation, All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
package com.gm.drill.sql.parser;

public final class GroupExpression extends AbstractExpression {

    private final Expression expression;

    /**
     * Can be an AndExpression or OrExpression, in which case, grouping has to be applied.
     * 
     * @param expression
     */
    public GroupExpression(Expression expression) {

        this.expression = expression;
    }

    @Override
    public <R, A> R accept(DrillVisitor<R, A> visitor, A param) {

        return visitor.visit(this, param);
    }

    public Expression getExpression() {

        return expression;
    }
}
