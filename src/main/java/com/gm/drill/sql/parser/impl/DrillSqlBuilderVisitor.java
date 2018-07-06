package com.gm.drill.sql.parser.impl;

import com.gm.drill.sql.parser.*;
import static com.google.common.collect.Iterables.getLast;

public class DrillSqlBuilderVisitor implements DrillVisitor<StringBuilder, StringBuilder> {
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
//        for (int i = 0; i < expression.getAttributes().size() - 1; ++i) {
//            sql = sql.append(" " + expression.getAttributes().get(i) + " ");
//        }
//        sb = sb.by(getLast(expression.getAttributes()));
//        sb = apply(sb, expression.getOperator(), expression.getArguments());
//        return sb;
        return null;
    }

    @Override
    public StringBuilder visit(GroupExpression expression, StringBuilder param) {
        return null;
    }
}
