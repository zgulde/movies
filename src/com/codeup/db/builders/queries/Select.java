/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.db.builders.queries;

import com.codeup.db.builders.HasSQLRepresentation;

public class Select implements HasSQLRepresentation {
    private Columns columns;
    private String table;
    private String alias;
    private Where where;
    private Join join;
    private int limit;
    private int offset;
    private boolean determineCount = false;

    private Select(String table) {
        this(table, null);
    }

    public Select(String table, String alias) {
        assertValidTableName(table);
        this.table = table;
        this.alias = alias;
        columns = Columns.empty().defaultTo("*");
        where = Where.empty();
        join = Join.empty();
        limit = -1;
        offset = -1;
    }

    private void assertValidTableName(String table) {
        if (table.indexOf(' ') != -1) {
            throw new IllegalArgumentException("Invalid table name given");
        }
    }

    public static Select from(String table) {
        return new Select(table);
    }

    public static Select from(String table, String alias) {
        return new Select(table, alias);
    }

    /**
     * Add alias to original table name in order to remove ambiguity, possibly due to
     * a criteria object trying to add a join. For instance:
     *
     * `Select.from("users").addTableAlias("u").toSQL()`
     *
     * will result in:
     *
     * `SELECT * FROM users u`
     *
     * @param alias
     * @return Select
     */
    public Select addTableAlias(String alias) {
        table = String.format("%s %s", table, alias);
        return this;
    }

    public Select addColumns(String ...columns) {
        this.columns.add(columns);
        return this;
    }

    public Select columns(String ...columns) {
        this.columns.clear().add(columns);
        return this;
    }

    public Select count() {
        determineCount = true;
        return this;
    }

    private String alias() {
        if (alias == null) return Character.toString(table.charAt(0)).toLowerCase();

        return alias;
    }

    public Select where(String expression) {
        where.and(expression);
        return this;
    }

    public Select where(String column, int parametersCount) {
        where.and(column, parametersCount);
        return this;
    }

    public Select orWhere(String expression) {
        where.or(expression);
        return this;
    }

    public Select orWhere(String column, int parametersCount) {
        where.or(column, parametersCount);
        return this;
    }

    public Select join(String table, String on) {
        join.inner(table, on);
        return this;
    }

    public Select outerJoin(String table, String on) {
        join.outer(table, on);
        return this;
    }

    public Select limit(int limit) {
        this.limit = limit;
        return this;
    }

    public Select offset(int offset) {
        this.offset = offset;
        return this;
    }

    @Override
    public String toSQL() {
        return String.format(
            "SELECT %s FROM %s %s %s %s %s",
            columnsToSQL(),
            fromToSQL(),
            join.toSQL(),
            where.toSQL(),
            limitToSQL(),
            offsetToSQL()
        ).trim().replaceAll("( )+", " ");
    }

    private String columnsToSQL() {
        if (determineCount) {
            determineCount();
        }
        return columns.toSQL();
    }

    private void determineCount() {
        columns.clear();
        offset = -1;
        limit = -1;
        if (join.isEmpty()) {
            columns.add("COUNT(*)");
        } else {
            columns.add(String.format("COUNT(DISTINCT %s.id)", alias()));
        }
    }

    private String fromToSQL() {
        return table + ((alias == null) ? "" : " " + alias);
    }

    private String offsetToSQL() {
        if (offset < 0) return "";

        return String.format("OFFSET %d", offset);
    }

    private String limitToSQL() {
        if (limit < 0) return "";

        return String.format("LIMIT %d", limit);
    }
}
