package dev.anes.service.db;

import javax.sql.rowset.CachedRowSet;

import org.apache.commons.lang3.ArrayUtils;

public class DBCommand {
    public static void CREATE(String db, String table, DBParam... params) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO " + table + "(");

        for (DBParam param : params)
            sql.append(param.getField() + ", ");

        sql.delete(sql.length() - 2, sql.length());
        sql.append(") VALUES (");

        for (int i = 0; i < params.length; i++) {
            sql.append("?, ");
        }
        sql.delete(sql.length() - 2, sql.length());
        sql.append(");");

        DBService.UPDATE(db, table, sql.toString(), params);
    }

    public static CachedRowSet READ(String db, String table, DBParam... params) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM " + table);

        if (!ArrayUtils.isEmpty(params)) {
            sql.append(" WHERE ");

            for (DBParam param : params) {
                sql.append(param.getField());
                sql.append(" = ? AND ");
            }
            sql.delete(sql.length() - 5, sql.length());
        }

        return DBService.QUERY(db, table, sql.toString(), params);
    }

    public static void UPDATE(String db, String table, DBParam id, DBParam... params) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE " + table + " SET ");
        for (DBParam param : params) {
            sql.append(param.getField());
            sql.append(" = ? , ");
        }
        sql.delete(sql.length() - 2, sql.length());
        if (id != null) {
            sql.append(" WHERE ");
            sql.append(id.getField());
            sql.append(" = ?");
        }

        DBService.UPDATE(db, table, sql.toString(), ArrayUtils.addAll(params, id));
    }

    public static void DELETE(String db, String table, DBParam... params) {
        StringBuilder sql = new StringBuilder();

        sql.append("DELETE FROM " + table);

        if (!ArrayUtils.isEmpty(params)) {
            sql.append(" WHERE ");
            for (DBParam param : params) {
                sql.append(param.getField());
                sql.append(" = ? AND ");
            }
            sql.delete(sql.length() - 5, sql.length());
        }

        sql.append(";");

        DBService.UPDATE(db, table, sql.toString(), params);
    }
}
