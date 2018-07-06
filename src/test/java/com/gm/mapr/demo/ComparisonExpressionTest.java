package com.gm.mapr.demo;

import com.gm.drill.sql.parser.DrillParser;
import com.gm.drill.sql.parser.Expression;
import com.gm.drill.sql.parser.Operator;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import static com.google.common.collect.Lists.newArrayList;

public class ComparisonExpressionTest extends BaseExpressionTest {

    private DrillParser ohiParser;

    @Before
    public void setup() {

        ohiParser = new DrillParser();
    }

    @Test
    public void testEqual() {

        Expression expression = ohiParser.parse("servicedMember.eq('abc')");

        checkComparisonExpression(expression, newArrayList("servicedMember"), Operator.EQUAL, newArrayList("abc"));

    }

    @Test
    public void testEqualNumber() {

        Expression expression = ohiParser.parse("servicedMember.eq(123)");

        checkComparisonExpression(expression, newArrayList("servicedMember"), Operator.EQUAL, newArrayList("123"));

    }

    @Test
    public void testEqualNull() {

        Expression expression = ohiParser.parse("servicedMember.eq(null)");

        checkComparisonExpression(expression, newArrayList("servicedMember"), Operator.EQUAL, null);

    }

    @Test
    public void testEqualIgnoreCase() {

        Expression expression = ohiParser.parse("servicedMember.eqic('abc')");

        checkComparisonExpression(expression, newArrayList("servicedMember"), Operator.EQUAL_IGNORE_CASE, newArrayList("abc"));

    }

    @Test
    public void testLessThan() {

        Expression expression = ohiParser.parse("servicedMember.lt('abc')");

        checkComparisonExpression(expression, newArrayList("servicedMember"), Operator.LESS_THAN, newArrayList("abc"));

    }

    @Test
    public void testLessThanEqualTo() {

        Expression expression = ohiParser.parse("servicedMember.lte('abc')");

        checkComparisonExpression(expression, newArrayList("servicedMember"), Operator.LESS_THAN_OR_EQUAL, newArrayList("abc"));

    }

    @Test
    public void testGreaterThan() {

        Expression expression = ohiParser.parse("servicedMember.gt('abc')");

        checkComparisonExpression(expression, newArrayList("servicedMember"), Operator.GREATER_THAN, newArrayList("abc"));

    }

    @Test
    public void testGreaterThanEqualTo() {

        Expression expression = ohiParser.parse("servicedMember.gte('abc')");

        checkComparisonExpression(expression, newArrayList("servicedMember"), Operator.GREATER_THAN_OR_EQUAL, newArrayList("abc"));

    }

    @Test
    public void testNotEqual() {

        Expression expression = ohiParser.parse("servicedMember.neq('abc')");

        checkComparisonExpression(expression, newArrayList("servicedMember"), Operator.NOT_EQUAL, newArrayList("abc"));

    }

    @Test
    public void testNotEqualNull() {

        Expression expression = ohiParser.parse("servicedMember.neq(null)");

        checkComparisonExpression(expression, newArrayList("servicedMember"), Operator.NOT_EQUAL, null);

    }

    @Test
    public void testLike() {

        Expression expression = ohiParser.parse("servicedMember.like('abc')");

        checkComparisonExpression(expression, newArrayList("servicedMember"), Operator.LIKE, newArrayList("abc"));

    }

    @Test
    public void testLikeIgnoreCase() {

        Expression expression = ohiParser.parse("servicedMember.likeic('abc')");

        checkComparisonExpression(expression, newArrayList("servicedMember"), Operator.LIKE_IGNORE_CASE, newArrayList("abc"));

    }

    @Test
    public void testIn() {

        Expression expression = ohiParser.parse("servicedMember.in('abc')");

        checkComparisonExpression(expression, newArrayList("servicedMember"), Operator.IN, newArrayList("abc"));

    }

    @Test
    public void testInMultiValue() {

        Expression expression = ohiParser.parse("servicedMember.in('abc', 'def')");

        checkComparisonExpression(expression, newArrayList("servicedMember"), Operator.IN, newArrayList("abc", "def"));

    }

    @Test
    public void testBetween() {

        Expression expression = ohiParser.parse("servicedMember.bet('abc')");

        checkComparisonExpression(expression, newArrayList("servicedMember"), Operator.BET, newArrayList("abc"));

    }

    @Test
    public void testBetweenMultivalue() {

        Expression expression = ohiParser.parse("servicedMember.bet('abc', 'def')");

        checkComparisonExpression(expression, newArrayList("servicedMember"), Operator.BET, newArrayList("abc", "def"));

    }

    @Test
    public void testNotIn() {

        Expression expression = ohiParser.parse("servicedMember.out('abc')");

        checkComparisonExpression(expression, newArrayList("servicedMember"), Operator.NOT_IN, newArrayList("abc"));

    }

    @Test
    public void testNotInMultivalue() {

        Expression expression = ohiParser.parse("servicedMember.out('abc', 'def')");

        checkComparisonExpression(expression, newArrayList("servicedMember"), Operator.NOT_IN, newArrayList("abc", "def"));

    }

    @Test
    public void testJoinedAttributesEqual() {

        Expression expression = ohiParser.parse("servicedMember.code.eq('abc')");

        checkComparisonExpression(expression, newArrayList("servicedMember", "code"), Operator.EQUAL, newArrayList("abc"));

    }

    @Test
    public void testJoinedAttributesIn() {

        Expression expression = ohiParser.parse("servicedMember.code.in('abc','def')");

        checkComparisonExpression(expression, newArrayList("servicedMember", "code"), Operator.IN, newArrayList("abc", "def"));

    }

    @Test
    public void testOuterJoinExpressions() {

        Expression expression = ohiParser.parse("servicedMember(+).code.eq('abc')");

        checkComparisonExpression(expression, newArrayList("servicedMember(+)", "code"), Operator.EQUAL, newArrayList("abc"));

    }

}
