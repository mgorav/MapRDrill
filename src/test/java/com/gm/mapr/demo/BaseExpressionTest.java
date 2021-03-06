package com.gm.mapr.demo;

import com.gm.drill.sql.parser.ComparisonQueryExpression;
import com.gm.drill.sql.parser.QueryExpression;
import com.gm.drill.sql.parser.QueryOperator;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

public class BaseExpressionTest {

    protected void checkComparisonExpression(QueryExpression queryExpression, List<String> attributeList, QueryOperator op, List<String> arguments) {

        assertThat("The queryExpression should be of type ComparisonQueryExpression", queryExpression instanceof ComparisonQueryExpression,
                is(true));
        ComparisonQueryExpression ce = (ComparisonQueryExpression) queryExpression;
        assertThat("Attribute(s) size check:", ce.getAttributes().size(), is(attributeList.size()));
        for (int ctr = 0; ctr < attributeList.size(); ++ctr) {
            assertThat("Attribute(s) check:", ce.getAttributes().get(ctr), is(attributeList.get(ctr)));
        }

        assertThat("QueryOperator check:", ce.getQueryOperator(), is(op));
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

