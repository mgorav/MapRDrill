package com.gm.drill.sql.parser.impl;

import com.gm.drill.sql.parser.*;

import java.util.List;

import static com.google.common.collect.Iterables.getLast;

public class DrillSqlBuilderVisitor implements DrillVisitor<StringBuilder, StringBuilder> {
    private ValueInterpreter vi = new ValueInterpreter();

    @Override
    public StringBuilder visit(AndExpression expression, StringBuilder sql) {
        for (int i = 0; i < expression.getChildren().size() - 1; ++i) {
            expression.getChildren().get(i).accept(this, sql);
            sql = sql.append(" and ");
        }
        getLast(expression.getChildren()).accept(this, sql);
        return sql;
    }

    @Override
    public StringBuilder visit(OrExpression expression, StringBuilder sql) {
        for (int i = 0; i < expression.getChildren().size() - 1; ++i) {
            expression.getChildren().get(i).accept(this, sql);
            sql = sql.append(" or ");
        }
        getLast(expression.getChildren()).accept(this, sql);
        return sql;
    }

    @Override
    public StringBuilder visit(ComparisonExpression expression, StringBuilder sql) {
        for (int i = 0; i < expression.getAttributes().size() - 1; ++i) {
            sql = sql.append(" " + expression.getAttributes().get(i) + " ");
        }
        sql = sql.append(" " + getLast(expression.getAttributes()) + " ");
        sql = apply(sql, expression.getOperator(), expression.getArguments());
        return sql;
    }

    @Override
    public StringBuilder visit(GroupExpression expression, StringBuilder sql) {
        return null;
    }


    public StringBuilder apply(StringBuilder sb, Operator comparisonOperator, List<String> arguments) {

        switch (comparisonOperator) {
//            case BET:
//                Return2<Object, Object> betweenParams = vi.between(arguments);
//                sb = sb.between(betweenParams._1, betweenParams._2);
//                break;
            case EQUAL:
                sb = sb.append(" = " + vi.getValue(arguments, true) );
                break;
//            case EQUAL_IGNORE_CASE:
//                sb = sb.eqIgnoreCase((String.class.cast(vi.getValue(arguments, false))));
//                break;
//            case GREATER_THAN:
//                sb = sb.gt(vi.getValue(arguments, false));
//                break;
//            case GREATER_THAN_OR_EQUAL:
//                sb = sb.gte(vi.getValue(arguments, false));
//                break;
//            case IN:
//                sb = sb.in(vi.in(arguments));
//                break;
//            case NOT_IN:
//                sb = sb.notin(vi.in(arguments));
//                break;
//            case LIKE:
//                sb = sb.like(String.class.cast(vi.getValue(arguments, false)));
//                break;
//            case LIKE_IGNORE_CASE:
//                sb = sb.likeIgnoreCase(String.class.cast(vi.getValue(arguments, false)));
//                break;
//            case LESS_THAN:
//                sb = sb.lt(vi.getValue(arguments, false));
//                break;
//            case LESS_THAN_OR_EQUAL:
//                sb = sb.lte(vi.getValue(arguments, false));
//                break;
//            case NOT_EQUAL:
//                sb = sb.neq(vi.getValue(arguments, true));
//                break;
            default:
                break;

        }

        return sb;
    }
}
