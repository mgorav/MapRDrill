/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Copyright (C) 2009-2016, Oracle Corporation, All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
package com.gm.drill.sql.parser;

/**
 * An interface for visiting expressions of the DrillParser.
 *
 * @param <R>
 *            Return type of the visitor's method.
 * @param <A>
 *            Type of an optional parameter passed to the visitor's method.
 */
public interface DrillVisitor<R, A> {

    R visit(AndExpression expression, A param);

    R visit(OrExpression expression, A param);

    R visit(ComparisonExpression expression, A param);

    R visit(GroupExpression expression, A param);
}
