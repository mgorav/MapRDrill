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

    R visit(AndQueryExpression expression, A param);

    R visit(OrQueryExpression expression, A param);

    R visit(ComparisonQueryExpression expression, A param);

    R visit(GroupQueryExpression expression, A param);
}
