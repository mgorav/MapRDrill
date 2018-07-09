/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Copyright (C) 2009-2016, Oracle Corporation, All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
package com.gm.drill.sql.parser;

import java.util.List;

public final class OrQueryExpression extends LogicalQueryExpression {

    public OrQueryExpression(List<? extends QueryExpression> children) {

        super(LogicalOperator.OR, children);
    }

    @Override
    public <R, A> R accept(DrillQueryStringVisitor<R, A> visitor, A param) {

        return visitor.visit(this, param);
    }
}
