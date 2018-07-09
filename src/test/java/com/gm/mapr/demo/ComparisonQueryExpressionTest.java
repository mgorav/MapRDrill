package com.gm.mapr.demo;

import com.gm.drill.sql.parser.DrillQueryStringParser;
import com.gm.drill.sql.parser.QueryExpression;
import com.gm.drill.sql.parser.QueryOperator;
import org.junit.Before;
import org.junit.Test;

import static com.google.common.collect.Lists.newArrayList;

public class ComparisonQueryExpressionTest extends BaseExpressionTest {

    private DrillQueryStringParser ohiParser;

    @Before
    public void setup() {

        ohiParser = new DrillQueryStringParser();
    }

    @Test
    public void testEqual() {

        QueryExpression queryExpression = ohiParser.parse("servicedMember.eq('abc')");

        checkComparisonExpression(queryExpression, newArrayList("servicedMember"), QueryOperator.EQUAL, newArrayList("abc"));

    }

    @Test
    public void testEqualNumber() {

        QueryExpression queryExpression = ohiParser.parse("servicedMember.eq(123)");

        checkComparisonExpression(queryExpression, newArrayList("servicedMember"), QueryOperator.EQUAL, newArrayList("123"));

    }

    @Test
    public void testEqualNull() {

        QueryExpression queryExpression = ohiParser.parse("servicedMember.eq(null)");

        checkComparisonExpression(queryExpression, newArrayList("servicedMember"), QueryOperator.EQUAL, null);

    }

    @Test
    public void testEqualIgnoreCase() {

        QueryExpression queryExpression = ohiParser.parse("servicedMember.eqic('abc')");

        checkComparisonExpression(queryExpression, newArrayList("servicedMember"), QueryOperator.EQUAL_IGNORE_CASE, newArrayList("abc"));

    }

    @Test
    public void testLessThan() {

        QueryExpression queryExpression = ohiParser.parse("servicedMember.lt('abc')");

        checkComparisonExpression(queryExpression, newArrayList("servicedMember"), QueryOperator.LESS_THAN, newArrayList("abc"));

    }

    @Test
    public void testLessThanEqualTo() {

        QueryExpression queryExpression = ohiParser.parse("servicedMember.lte('abc')");

        checkComparisonExpression(queryExpression, newArrayList("servicedMember"), QueryOperator.LESS_THAN_OR_EQUAL, newArrayList("abc"));

    }

    @Test
    public void testGreaterThan() {

        QueryExpression queryExpression = ohiParser.parse("servicedMember.gt('abc')");

        checkComparisonExpression(queryExpression, newArrayList("servicedMember"), QueryOperator.GREATER_THAN, newArrayList("abc"));

    }

    @Test
    public void testGreaterThanEqualTo() {

        QueryExpression queryExpression = ohiParser.parse("servicedMember.gte('abc')");

        checkComparisonExpression(queryExpression, newArrayList("servicedMember"), QueryOperator.GREATER_THAN_OR_EQUAL, newArrayList("abc"));

    }

    @Test
    public void testNotEqual() {

        QueryExpression queryExpression = ohiParser.parse("servicedMember.neq('abc')");

        checkComparisonExpression(queryExpression, newArrayList("servicedMember"), QueryOperator.NOT_EQUAL, newArrayList("abc"));

    }

    @Test
    public void testNotEqualNull() {

        QueryExpression queryExpression = ohiParser.parse("servicedMember.neq(null)");

        checkComparisonExpression(queryExpression, newArrayList("servicedMember"), QueryOperator.NOT_EQUAL, null);

    }

    @Test
    public void testLike() {

        QueryExpression queryExpression = ohiParser.parse("servicedMember.like('abc')");

        checkComparisonExpression(queryExpression, newArrayList("servicedMember"), QueryOperator.LIKE, newArrayList("abc"));

    }

    @Test
    public void testLikeIgnoreCase() {

        QueryExpression queryExpression = ohiParser.parse("servicedMember.likeic('abc')");

        checkComparisonExpression(queryExpression, newArrayList("servicedMember"), QueryOperator.LIKE_IGNORE_CASE, newArrayList("abc"));

    }

    @Test
    public void testIn() {

        QueryExpression queryExpression = ohiParser.parse("servicedMember.in('abc')");

        checkComparisonExpression(queryExpression, newArrayList("servicedMember"), QueryOperator.IN, newArrayList("abc"));

    }

    @Test
    public void testInMultiValue() {

        QueryExpression queryExpression = ohiParser.parse("servicedMember.in('abc', 'def')");

        checkComparisonExpression(queryExpression, newArrayList("servicedMember"), QueryOperator.IN, newArrayList("abc", "def"));

    }

    @Test
    public void testBetween() {

        QueryExpression queryExpression = ohiParser.parse("servicedMember.bet('abc')");

        checkComparisonExpression(queryExpression, newArrayList("servicedMember"), QueryOperator.BET, newArrayList("abc"));

    }

    @Test
    public void testBetweenMultivalue() {

        QueryExpression queryExpression = ohiParser.parse("servicedMember.bet('abc', 'def')");

        checkComparisonExpression(queryExpression, newArrayList("servicedMember"), QueryOperator.BET, newArrayList("abc", "def"));

    }

    @Test
    public void testNotIn() {

        QueryExpression queryExpression = ohiParser.parse("servicedMember.out('abc')");

        checkComparisonExpression(queryExpression, newArrayList("servicedMember"), QueryOperator.NOT_IN, newArrayList("abc"));

    }

    @Test
    public void testNotInMultivalue() {

        QueryExpression queryExpression = ohiParser.parse("servicedMember.out('abc', 'def')");

        checkComparisonExpression(queryExpression, newArrayList("servicedMember"), QueryOperator.NOT_IN, newArrayList("abc", "def"));

    }

    @Test
    public void testJoinedAttributesEqual() {

        QueryExpression queryExpression = ohiParser.parse("servicedMember.code.eq('abc')");

        checkComparisonExpression(queryExpression, newArrayList("servicedMember", "code"), QueryOperator.EQUAL, newArrayList("abc"));

    }

    @Test
    public void testJoinedAttributesIn() {

        QueryExpression queryExpression = ohiParser.parse("servicedMember.code.in('abc','def')");

        checkComparisonExpression(queryExpression, newArrayList("servicedMember", "code"), QueryOperator.IN, newArrayList("abc", "def"));

    }

    @Test
    public void testOuterJoinExpressions() {

        QueryExpression queryExpression = ohiParser.parse("servicedMember(+).code.eq('abc')");

        checkComparisonExpression(queryExpression, newArrayList("servicedMember(+)", "code"), QueryOperator.EQUAL, newArrayList("abc"));

    }

}
