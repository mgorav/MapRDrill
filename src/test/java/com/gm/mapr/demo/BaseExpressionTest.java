package com.gm.mapr.demo;

import com.gm.drill.sql.parser.ComparisonExpression;
import com.gm.drill.sql.parser.Expression;
import com.gm.drill.sql.parser.Operator;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

public class BaseExpressionTest {

    protected void checkComparisonExpression(Expression expression, List<String> attributeList, Operator op, List<String> arguments) {

        assertThat("The expression should be of type ComparisonExpression", expression instanceof ComparisonExpression,
                is(true));
        ComparisonExpression ce = (ComparisonExpression) expression;
        assertThat("Attribute(s) size check:", ce.getAttributes().size(), is(attributeList.size()));
        for (int ctr = 0; ctr < attributeList.size(); ++ctr) {
            assertThat("Attribute(s) check:", ce.getAttributes().get(ctr), is(attributeList.get(ctr)));
        }

        assertThat("Operator check:", ce.getOperator(), is(op));
        if (arguments == null) {
            assertThat("Arguments should be null", ce.getArguments(), nullValue());
        } else {
            assertThat("Argument(s) size check:", ce.getArguments().size(), is(arguments.size()));
            for (int ctr = 0; ctr < arguments.size(); ++ctr) {
                assertThat("Argument(s) check:", ce.getArguments().get(ctr), is(arguments.get(ctr)));
            }
        }

    }
}

