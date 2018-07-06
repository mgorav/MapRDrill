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
 * This node represents a comparison with operator, attributes and arguments,
 * e.g. <tt>attribute1.attrubute2.eq('abc')</tt>.
 */
public final class ComparisonExpression extends AbstractExpression {

    private static final String NULL = "null";

    private final Operator operator;

    private final List<String> attributes;

    private final List<String> arguments;

    /**
     * @param operator   Must not be <tt>null</tt>.
     * @param attributes Must not be <tt>null</tt> or empty.
     * @param arguments  Must not be <tt>null</tt> or empty. If the operator is not {@link Operator#isMultiValue() multiValue},
     *                   then it must contain exactly one argument.
     * @throws IllegalArgumentException If one of the conditions specified above it not met.
     */
    public ComparisonExpression(Operator operator, List<String> attributes, List<String> arguments) {

        notNull(operator, "Operator must not be null");
        notEmpty(attributes, "Attribute(s) list must not be empty");
        notEmpty(arguments, "Arguments list must not be empty");
        isValid(operator.isMultiValue() || arguments.size() == 1,
                "Operator %s expects single argument, but multiple values given", operator);

        this.operator = operator;
        this.attributes = attributes;
        checkArgumentsAndOperator(operator, arguments);
        this.arguments = parseArguments(arguments);
    }

    private void checkArgumentsAndOperator(Operator operator, List<String> arguments) {

        if (arguments.size() == 1 && arguments.get(0).equalsIgnoreCase(NULL)) {
            isValid(operator == Operator.EQUAL || operator == Operator.NOT_EQUAL,
                    "If the arguments are null, operator can only be eq and neq and not %s", operator);
        }

    }

    private List<String> parseArguments(List<String> arguments) {

        if (arguments.size() == 1 && arguments.get(0).equalsIgnoreCase(NULL)) {
            return null;
        }
        return new ArrayList<>(arguments);
    }

    @Override
    public <R, A> R accept(DrillVisitor<R, A> visitor, A param) {

        return visitor.visit(this, param);
    }

    public Operator getOperator() {

        return operator;
    }

    public List<String> getAttributes() {

        return attributes;
    }

    /**
     * Returns a copy of the arguments list. It's guaranteed that it contains at least one item.
     * When the operator is not {@link Operator#isMultiValue() multiValue}, then it
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
        return attributesString + operator + args;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof ComparisonExpression))
            return false;

        ComparisonExpression that = (ComparisonExpression) o;

        if (!arguments.equals(that.arguments))
            return false;
        if (!operator.equals(that.operator))
            return false;
        if (!attributes.equals(that.attributes))
            return false;

        return true;
    }

    @Override
    public int hashCode() {

        int result = attributes.hashCode();
        result = 31 * result + arguments.hashCode();
        result = 31 * result + operator.hashCode();
        return result;
    }
}
