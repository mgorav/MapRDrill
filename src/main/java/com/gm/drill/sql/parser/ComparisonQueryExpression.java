/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Copyright (C) 2009-2016, Oracle Corporation, All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
package com.gm.drill.sql.parser;

import java.util.ArrayList;
import java.util.List;

import static com.gm.util.Check.*;
import static org.springframework.util.StringUtils.collectionToCommaDelimitedString;
import static org.springframework.util.StringUtils.collectionToDelimitedString;

/**
 * This node represents a comparison with queryOperator, attributes and arguments,
 * e.g. <tt>attribute1.attrubute2.eq('abc')</tt>.
 */
public final class ComparisonQueryExpression extends AbstractQueryExpression {

    private static final String NULL = "null";

    private final QueryOperator queryOperator;

    private final List<String> attributes;

    private final List<String> arguments;

    /**
     * @param queryOperator   Must not be <tt>null</tt>.
     * @param attributes Must not be <tt>null</tt> or empty.
     * @param arguments  Must not be <tt>null</tt> or empty. If the queryOperator is not {@link QueryOperator#isMultiValue() multiValue},
     *                   then it must contain exactly one argument.
     * @throws IllegalArgumentException If one of the conditions specified above it not met.
     */
    public ComparisonQueryExpression(QueryOperator queryOperator, List<String> attributes, List<String> arguments) {

        notNull(queryOperator, "QueryOperator must not be null");
        notEmpty(attributes, "Attribute(s) list must not be empty");
        notEmpty(arguments, "Arguments list must not be empty");
        isValid(queryOperator.isMultiValue() || arguments.size() == 1,
                "QueryOperator %s expects single argument, but multiple values given", queryOperator);

        this.queryOperator = queryOperator;
        this.attributes = attributes;
        checkArgumentsAndOperator(queryOperator, arguments);
        this.arguments = parseArguments(arguments);
    }

    private void checkArgumentsAndOperator(QueryOperator queryOperator, List<String> arguments) {

        if (arguments.size() == 1 && arguments.get(0).equalsIgnoreCase(NULL)) {
            isValid(queryOperator == QueryOperator.EQUAL || queryOperator == QueryOperator.NOT_EQUAL,
                    "If the arguments are null, queryOperator can only be eq and neq and not %s", queryOperator);
        }

    }

    private List<String> parseArguments(List<String> arguments) {

        if (arguments.size() == 1 && arguments.get(0).equalsIgnoreCase(NULL)) {
            return null;
        }
        return new ArrayList<>(arguments);
    }

    @Override
    public <R, A> R accept(DrillQueryStringVisitor<R, A> visitor, A param) {

        return visitor.visit(this, param);
    }

    public QueryOperator getQueryOperator() {

        return queryOperator;
    }

    public List<String> getAttributes() {

        return attributes;
    }

    /**
     * Returns a copy of the arguments list. It's guaranteed that it contains at least one item.
     * When the queryOperator is not {@link QueryOperator#isMultiValue() multiValue}, then it
     * contains one argument
     */
    public List<String> getArguments() {

        return arguments == null ? null : new ArrayList<>(arguments);
    }

    @Override
    public String toString() {

        String args = arguments.size() > 1
                ? "('" + collectionToCommaDelimitedString(arguments) + "')"
                : "'" + arguments.get(0) + "'";

        String attributesString = attributes.size() > 1
                ? collectionToDelimitedString(attributes, "'.'") : attributes.get(0);
        return attributesString + queryOperator + args;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof ComparisonQueryExpression))
            return false;

        ComparisonQueryExpression that = (ComparisonQueryExpression) o;

        if (!arguments.equals(that.arguments))
            return false;
        if (!queryOperator.equals(that.queryOperator))
            return false;
        if (!attributes.equals(that.attributes))
            return false;

        return true;
    }

    @Override
    public int hashCode() {

        int result = attributes.hashCode();
        result = 31 * result + arguments.hashCode();
        result = 31 * result + queryOperator.hashCode();
        return result;
    }
}
