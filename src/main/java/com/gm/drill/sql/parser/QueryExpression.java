/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Copyright (C) 2009-2016, Oracle Corporation, All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
package com.gm.drill.sql.parser;

/**
 * Common interface of the expressions.
 */
public interface QueryExpression {

    /**
     * This method accepts the visitor, calls its <tt>visit()</tt> method and returns a result.
     * It takes in an optional parameter for context etc.
     */
    <R, A> R accept(DrillQueryStringVisitor<R, A> visitor, A param);

    /**
     * This method accepts the visitor, calls its <tt>visit()</tt> method and returns the result.
     * This method should just call {@link #accept(DrillQueryStringVisitor, Object)} with <tt>null</tt> as the second argument.
     */
    <R, A> R accept(DrillQueryStringVisitor<R, A> visitor);
}
