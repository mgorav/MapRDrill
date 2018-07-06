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
public abstract class LogicalExpression extends AbstractExpression implements Iterable<Expression> {

    private final List<Expression> children;

    private final LogicalOperator operator;

    /**
     * @param operator
     *            Must not be <tt>null</tt>.
     * @param children
     *            Children expressions, i.e. operands; must not be <tt>null</tt>.
     */
    protected LogicalExpression(LogicalOperator operator, List<? extends Expression> children) {

        assert operator != null : "Operator must not be null";
        assert children != null : "Children must not be null";

        this.operator = operator;
        this.children = unmodifiableList(new ArrayList<>(children));
    }

    @Override
    public Iterator<Expression> iterator() {

        return children.iterator();
    }

    public LogicalOperator getOperator() {

        return operator;
    }

    /**
     * Returns a copy of the children expressions.
     */
    public List<Expression> getChildren() {

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
        if (!(o instanceof LogicalExpression))
            return false;

        LogicalExpression nodes = (LogicalExpression) o;
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
