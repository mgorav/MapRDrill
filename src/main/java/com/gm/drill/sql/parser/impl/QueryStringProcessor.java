package com.gm.drill.sql.parser.impl;

import com.gm.drill.sql.parser.DrillParser;
import org.springframework.util.StringUtils;

public class QueryStringProcessor {

    public void process(StringBuilder sb, String queryString) {

        // q=a.eq(2).and.a2.lt(4).and.a3.in('v1','v2','v3')
        // q=[a.eq(2).and(b.eq(3)].or.[d.eq('f')] for groups
        // q=a#b#c.eq(3) for joins
        /*
         * Expression = FieldName.operator(value)
         * operator = {'eq', 'in', 'lt', 'gt', 'lte', 'gte', 'ne', 'bet'}
         * complex expression = [expression.and|or.expression]
         */
        // if the query string is empty then search all, no restrictions
        if (!StringUtils.isEmpty(queryString)) {
            new DrillParser().parse(queryString).accept(new DrillSqlBuilderVisitor(), sb);
        }

    }

    public static void main(String[] args) {
        String queryString = "a.eq(1)";

        DrillParser drillParser = new DrillParser();
        StringBuilder generatedDrillSql = new StringBuilder();

        drillParser.parse(queryString).accept(new DrillSqlBuilderVisitor(),generatedDrillSql);

        System.out.println(generatedDrillSql.toString());
    }

}
