/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Copyright (C) 2009-2016, Oracle Corporation, All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
package com.gm.drill.sql.parser;

public abstract class AbstractQueryExpression implements QueryExpression {

    /**
     * Accepts the visitor, calls its visit() method and returns the result.
     * This method just calls {@link #accept(DrillQueryStringVisitor, Object)} with <tt>null</tt> as the second argument.
     */
    @Override
    public <R, A> R accept(DrillQueryStringVisitor<R, A> visitor) {

        return accept(visitor, null);
    }
}
