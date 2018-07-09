/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Copyright (C) 2009-2016, Oracle Corporation, All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
package com.gm.drill.sql.parser;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.util.Collections.unmodifiableList;

/**
 * Superclass of all logical expressions that represents a logical operation that connects
 * children expressions.
 */
public abstract class LogicalQueryExpression extends AbstractQueryExpression implements Iterable<QueryExpression> {

    private final List<QueryExpression> children;

    private final LogicalOperator operator;

    /**
     * @param operator
     *            Must not be <tt>null</tt>.
     * @param children
     *            Children expressions, i.e. operands; must not be <tt>null</tt>.
     */
    protected LogicalQueryExpression(LogicalOperator operator, List<? extends QueryExpression> children) {

        assert operator != null : "QueryOperator must not be null";
        assert children != null : "Children must not be null";

        this.operator = operator;
        this.children = unmodifiableList(new ArrayList<>(children));
    }

    @Override
    public Iterator<QueryExpression> iterator() {

        return children.iterator();
    }

    public LogicalOperator getOperator() {

        return operator;
    }

    /**
     * Returns a copy of the children expressions.
     */
    public List<QueryExpression> getChildren() {

        return new ArrayList<>(children);
    }

    @Override
    public String toString() {

        return "(" + StringUtils.collectionToDelimitedString(children, operator.toString()) + ")";
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof LogicalQueryExpression))
            return false;

        LogicalQueryExpression nodes = (LogicalQueryExpression) o;
        if (!children.equals(nodes.children))
            return false;
        if (operator != nodes.operator)
            return false;

        return true;
    }

    @Override
    public int hashCode() {

        int result = children.hashCode();
        result = 31 * result + operator.hashCode();
        return result;
    }
}
