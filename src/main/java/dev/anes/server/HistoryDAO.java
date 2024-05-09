package dev.anes.server;

import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;

import javax.sql.rowset.CachedRowSet;

import dev.anes.App;
import dev.anes.core.model.Debtor;
import dev.anes.core.model.History;
import dev.anes.core.util.DateUtil;
import dev.anes.service.db.DBCommand;
import dev.anes.service.db.DBParam;
import dev.anes.service.db.DBService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class HistoryDAO {
    public static final String DATABASE = "lending";
    public static final String TABLE = "history";

    public static int getLastID() {
        String sql = "SELECT MAX(historyID) AS last_id FROM " + TABLE;
        CachedRowSet crs = DBService.QUERY(DATABASE, TABLE, sql, new DBParam[] {});

        try {
            if (crs.next()) {
                return crs.getInt("last_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static ObservableList<History> getMasterList(App app) throws SQLException {
        ObservableList<History> list = FXCollections.observableArrayList();
        CachedRowSet crs = DBCommand.READ(DATABASE, TABLE, new DBParam[] {});

        while (crs.next()) {
            History loan = _data(crs, app);
            if (loan != null)
                list.add(loan);
        }
        return list;

    }

    public static void insert(History history) {
        DBCommand.CREATE(DATABASE, TABLE, _params(history).toArray(new DBParam[] {}));
        
    }

    public static void update(History history) {
        DBCommand.UPDATE(DATABASE, TABLE, new DBParam(Types.INTEGER, "historyID", history.getHistoryID()),
                _params(history).toArray(new DBParam[] {}));
    }

    public static void remove(History history) {
        DBCommand.DELETE(DATABASE, TABLE, new DBParam(Types.INTEGER, "historyID", history.getHistoryID()));
    }

    private static History _data(CachedRowSet crs, App app) throws SQLException {
        int historyID = crs.getInt("historyID");
        int debtorID = crs.getInt("debtorID");
        int countloan = crs.getInt("countloan");
        LocalDate loandate = DateUtil.parse(crs.getString("loandate"));
        long loanamount = crs.getLong("loanAmount");
        int  monthtopay = crs.getInt("month_to_pay");
        long  interest = crs.getLong("penalty");
        long  penalty = crs.getLong("interest");
        Debtor debtor = app.getDeptorMasterList()
        .stream()
        .filter(dep -> dep.getDebtorID() == debtorID)
        .findFirst().get();
        return new History(historyID,debtor,countloan,loandate,loanamount,monthtopay,penalty,interest);
    }

    private static ObservableList<DBParam> _params(History history) {
        ObservableList<DBParam> parameters = FXCollections.observableArrayList();
        parameters.add(new DBParam(Types.INTEGER, "historyID", history.getHistoryID()));
        parameters.add(new DBParam(Types.INTEGER, "debtorID", history.getDebtorID().getDebtorID()));
        parameters.add(new DBParam(Types.INTEGER, "countloan", history.getCountloan()));
        parameters.add(new DBParam(Types.VARCHAR, "loandate", DateUtil.format(history.getLoanDate())));
        parameters.add(new DBParam(Types.BIGINT, "loanAmount", history.getLoanAmount()));
        parameters.add(new DBParam(Types.INTEGER, "month_to_pay", history.getMonthtoPay()));
        parameters.add(new DBParam(Types.BIGINT, "penalty", history.getPenalty()));
        parameters.add(new DBParam(Types.BIGINT, "interest", history.getInterest()));
        return parameters;
    }
}
