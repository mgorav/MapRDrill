/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Copyright (C) 2009-2016, Oracle Corporation, All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
package com.gm.drill.sql.parser;

import java.util.List;

public final class AndQueryExpression extends LogicalQueryExpression {

    public AndQueryExpression(List<? extends QueryExpression> children) {

        super(LogicalOperator.AND, children);
    }

    @Override
    public <R, A> R accept(DrillQueryStringVisitor<R, A> visitor, A param) {

        return visitor.visit(this, param);
    }
}
