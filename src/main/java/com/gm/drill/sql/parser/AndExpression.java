/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Copyright (C) 2009-2016, Oracle Corporation, All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
package com.gm.drill.sql.parser;

import java.util.List;

public final class AndExpression extends LogicalExpression {

    public AndExpression(List<? extends Expression> children) {

        super(LogicalOperator.AND, children);
    }

    @Override
    public <R, A> R accept(DrillVisitor<R, A> visitor, A param) {

        return visitor.visit(this, param);
    }
}
