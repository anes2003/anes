package dev.anes.service.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

public class DBService {
    private static final String HOST = "127.0.0.1";
    private static final String CONNECTION_STRING = "jdbc:mysql://" + HOST + "/";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection CONNECT(String db) {

        try {
            return DriverManager.getConnection(CONNECTION_STRING + db, USER, PASS);
        } catch (SQLException e) {
            System.out.print("Could not connect to MySQL Server");
            e.printStackTrace();
        }
        return null;
    }

    public static CachedRowSet QUERY(
            String db,
            String table,
            String sql,
            DBParam... params) {
        int idx = 1;
        try (Connection conn = CONNECT(db)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            for (DBParam param : params) {
                try {
                    switch (param.getType()) {
                        case Types.VARCHAR:
                            statement.setString(idx++, String.valueOf(param.getData()));
                            break;
                        case Types.REAL:
                            statement.setDouble(idx++, (double) param.getData());
                            break;
                        case Types.INTEGER:
                            statement.setInt(idx++, (int) param.getData());
                            break;
                        case Types.BIGINT:
                            statement.setLong(idx++, (long) param.getData());
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            ResultSet rs = statement.executeQuery();
            RowSetFactory factory = RowSetProvider.newFactory();
            CachedRowSet dataset = factory.createCachedRowSet();
            dataset.populate(rs);

            rs.close();
            statement.close();
            conn.close();

            return dataset;
        } catch (SQLException e) {
            System.out.println(" Could not execute SQL ");
            e.printStackTrace();
        }
        return null;
    }

    public static void UPDATE(String db, String table, String sql, DBParam... params) {
        int idx = 1;
        try (Connection conn = CONNECT(db)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            for (DBParam param : params) {
                try {
                    switch (param.getType()) {
                        case Types.VARCHAR:
                            statement.setString(idx++, String.valueOf(param.getData()));
                            break;
                        case Types.REAL:
                            statement.setDouble(idx++, (double) param.getData());
                            break;
                        case Types.INTEGER:
                            statement.setInt(idx++, (int) param.getData());
                            break;
                        case Types.BIGINT:
                            statement.setLong(idx++, (long) param.getData());
                            break;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
            statement.executeUpdate();
            statement.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println(" Could not update statement ");
            e.printStackTrace();
        }
    }

}
