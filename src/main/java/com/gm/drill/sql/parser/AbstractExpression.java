/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Copyright (C) 2009-2016, Oracle Corporation, All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
package com.gm.drill.sql.parser;

public abstract class AbstractExpression implements Expression {

    /**
     * Accepts the visitor, calls its visit() method and returns the result.
     * This method just calls {@link #accept(DrillVisitor, Object)} with <tt>null</tt> as the second argument.
     */
    @Override
    public <R, A> R accept(DrillVisitor<R, A> visitor) {

        return accept(visitor, null);
    }
}
