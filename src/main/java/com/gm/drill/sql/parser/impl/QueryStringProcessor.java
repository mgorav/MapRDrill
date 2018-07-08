package com.gm.drill.sql.parser.impl;

import com.gm.drill.sql.parser.DrillQueryStringParser;
import org.springframework.util.StringUtils;

public class QueryStringProcessor {

    public void process(String queryString, String resourceName,String fn) {

        // q=a.eq(2).and.a2.lt(4).and.a3.in('v1','v2','v3')
        // q=[a.eq(2).and(b.eq(3)].or.[d.eq('f')] for groups
        // q=a#b#c.eq(3) for joins
        /*
         * QueryExpression = FieldName.operator(value)
         * operator = {'eq', 'in', 'lt', 'gt', 'lte', 'gte', 'ne', 'bet'}
         * complex expression = [expression.and|or.expression]
         */
        // if the query string is empty then search all, no restrictions
        if (!StringUtils.isEmpty(queryString)) {

            new DrillQueryStringParser().parse(queryString).accept(new DrillSqlBuilderQueryStringVisitor(), new QueryContext(resourceName,fn));
        }

    }

    public static void main(String[] args) {
        String queryString = "a.eq(1).and.b.gt(2).or.c.lt(3)";
        // select a1,a2 from table1 t, table t2 where t1.id = t2.id
        // a.b.eq(1)

        DrillQueryStringParser drillQueryStringParser = new DrillQueryStringParser();
        QueryContext qc = new QueryContext("table1",null);
        drillQueryStringParser.parse(queryString).accept(new DrillSqlBuilderQueryStringVisitor(),qc);

        System.out.println(qc.getQuery().toString());
    }

}
