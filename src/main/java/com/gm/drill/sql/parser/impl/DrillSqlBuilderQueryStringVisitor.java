package com.gm.drill.sql.parser.impl;

import com.gm.drill.sql.parser.*;

import java.util.List;

import static com.google.common.collect.Iterables.getLast;

public class DrillSqlBuilderQueryStringVisitor implements DrillQueryStringVisitor<QueryContext, QueryContext> {
    private QueryValueInterpreter vi = new QueryValueInterpreter();

    @Override
    public QueryContext visit(AndQueryExpression expression, QueryContext sql) {
        sql.appendStartBrackets(expression.getChildren().size()-1);
        for (int i = 0; i < expression.getChildren().size() - 1; ++i) {
            expression.getChildren().get(i).accept(this, sql);
            if(i>0) {
                sql.append(")");
            }
            sql = sql.append(" and ");
        }
        getLast(expression.getChildren()).accept(this, sql);
        sql.append(")");
        return sql;
    }

    @Override
    public QueryContext visit(OrQueryExpression expression, QueryContext sql) {
        sql.appendStartBrackets(expression.getChildren().size()-1);
        for (int i = 0; i < expression.getChildren().size() - 1; ++i) {
            expression.getChildren().get(i).accept(this, sql);
            if(i>0) {
                sql.append(")");
            }
            sql = sql.append(" or ");
        }
        getLast(expression.getChildren()).accept(this, sql);
        sql.append(")");
        return sql;
    }

    @Override
    public QueryContext visit(ComparisonQueryExpression expression, QueryContext sql) {
//        for (int i = 0; i < expression.getAttributes().size() - 1; ++i) {
//            sql = sql.append(" " + expression.getAttributes().get(i) + " ");
//        }
        sql = sql.append(" (");
        sql = sql.appendArgument(getLast(expression.getAttributes()));
        sql = apply(sql, expression.getQueryOperator(), expression.getArguments());
        sql = sql.append(") ");
        return sql;
    }

    @Override
    public QueryContext visit(GroupQueryExpression expression, QueryContext sql) {
        return null;
    }


    public QueryContext apply(QueryContext sb, QueryOperator comparisonQueryOperator, List<String> arguments) {

        switch (comparisonQueryOperator) {
//            case BET:
//                Return2<Object, Object> betweenParams = vi.between(arguments);
//                sb = sb.between(betweenParams._1, betweenParams._2);
//                break;
            case EQUAL:
                Object val = vi.getValue(arguments, true);
                if (val != null) {
                    sb = sb.append(" = " + val);
                } else {
                    sb = sb.append("is null ");
                }
                break;
//            case EQUAL_IGNORE_CASE:
//                sb = sb.eqIgnoreCase((String.class.cast(vi.getValue(arguments, false))));
//                break;
            case GREATER_THAN:
                sb = sb.append(" > " + vi.getValue(arguments, false));
                break;
            case GREATER_THAN_OR_EQUAL:
                sb = sb.append(" >= " + vi.getValue(arguments, false));
                break;
            case IN:
                sb = sb.append(" in( " + vi.getValue(arguments, false) + ")");
                break;
            case NOT_IN:
                sb = sb.append(" not in( " + vi.getValue(arguments, false) + ")");
                break;
            case LIKE:
                sb = sb.append(" like " + vi.getValue(arguments, false));
                break;
//            case LIKE_IGNORE_CASE:
//                sb = sb.likeIgnoreCase(String.class.cast(vi.getValue(arguments, false)));
//                break;
            case LESS_THAN:
                sb = sb.append(" < " + vi.getValue(arguments, false));
                break;
            case LESS_THAN_OR_EQUAL:
                sb = sb.append(" <= " + vi.getValue(arguments, false));
                break;
            case NOT_EQUAL:
                sb = sb.append(" <> " + vi.getValue(arguments, true));
                break;
            default:
                break;

        }

        return sb;
    }


}
