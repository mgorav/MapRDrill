/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Copyright (C) 2009-2016, Oracle Corporation, All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
package com.gm.drill.sql.parser;

import com.gm.util.Return2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

/**
 * Factory that creates {@link QueryExpression} instances for the parser.
 */
public class QueryExpressionFactory {

    private final Map<String, Operator> operators;

    public QueryExpressionFactory(Operator[] operators) {

        this.operators = new HashMap<>(operators.length);
        for (Operator op : operators) {
            this.operators.put(op.getSymbol(), op);
        }
    }

    /**
     * Creates a specific {@link LogicalQueryExpression} instance for the specified operator and with the
     * given children expressions.
     *
     * @param operator The logical operator to create a node for.
     * @param children Children expressions, i.e. operands.
     * @return A subclass of the {@link LogicalQueryExpression} according to the specified operator.
     */
    public LogicalQueryExpression createLogicalExpression(LogicalOperator operator, List<QueryExpression> children) {

        switch (operator) {
            case AND:
                return new AndQueryExpression(children);
            case OR:
                return new OrQueryExpression(children);

            // this normally can't happen
            default:
                throw new IllegalStateException("Unknown operator: " + operator);
        }
    }

    public GroupQueryExpression createGroupExpression(QueryExpression groupQueryExpression) {

        return new GroupQueryExpression(groupQueryExpression);

    }

    /**
     * Creates a {@link ComparisonQueryExpression} instance with the given parameters.
     *
     * @param attributeAndOperator Attribute and Operator
     * @param arguments            A list of arguments that specifies the right side of the comparison.
     * @throws UnknownOperatorException If no operator for the specified operator token exists.
     */
    public ComparisonQueryExpression createComparisonExpression(
            String attributeAndOperator, List<String> arguments) throws UnknownOperatorException {

        Return2<List<String>, String> tokens = splitAttributesAndOperator(attributeAndOperator);
        Operator op = operators.get(tokens._2);
        if (op != null) {
            return new ComparisonQueryExpression(op, tokens._1, arguments);
        }
        throw new UnknownOperatorException(tokens._2);

    }

    private Return2<List<String>, String> splitAttributesAndOperator(String attributeAndOperator) {

        String[] tokens = attributeAndOperator.split("\\.");
        String operator = tokens[tokens.length - 1];
        return new Return2<>(asList(tokens).subList(0, tokens.length - 1), operator);
    }

}
