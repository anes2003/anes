package dev.anes.server;

import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;

import javax.sql.rowset.CachedRowSet;

import dev.anes.App;
import dev.anes.core.model.Debtor;
import dev.anes.core.model.Loan_Info;
import dev.anes.core.util.DateUtil;
import dev.anes.service.db.DBCommand;
import dev.anes.service.db.DBParam;
import dev.anes.service.db.DBService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Loan_InfoDAO {
    public static final String DATABASE = "lending";
    public static final String TABLE = "loan_info";

    public static int getLastID() {
        String sql = "SELECT MAX(infoID) AS last_id FROM " + TABLE;
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

    public static ObservableList<Loan_Info> getMasterList(App app) throws SQLException {
        ObservableList<Loan_Info> list = FXCollections.observableArrayList();
        CachedRowSet crs = DBCommand.READ(DATABASE, TABLE, new DBParam[] {});

        while (crs.next()) {
            Loan_Info loan = _data(crs, app);
            if (loan != null)
                list.add(loan);
        }
        return list;

    }

    public static void insert(Loan_Info loan) {
        DBCommand.CREATE(DATABASE, TABLE, _params(loan).toArray(new DBParam[] {}));

    }

    public static void update(Loan_Info loan) {
        DBCommand.UPDATE(DATABASE, TABLE, new DBParam(Types.INTEGER, "infoID", loan.getInfoID()),
                _params(loan).toArray(new DBParam[] {}));
    }

    public static void remove(Loan_Info loan) {
        DBCommand.DELETE(DATABASE, TABLE, new DBParam(Types.INTEGER, "infoID", loan.getInfoID()));
    }

    private static Loan_Info _data(CachedRowSet crs, App app) throws SQLException {
        int infoID = crs.getInt("infoID");
        int debtorID = crs.getInt("debtorID");
        LocalDate loanDate = DateUtil.parse(crs.getString("loanDate"));
        long loanAmount = crs.getLong("loanAmount");
        int months_to_pay = crs.getInt("months_to_pay");
        long interest = crs.getLong("interest");
        long penalty = crs.getLong("penalty");
        Debtor debtor = app.getDeptorMasterList()
                .stream()
                .filter(dep -> dep.getDebtorID() == debtorID)
                .findFirst().get();
        int count = crs.getInt("count");
        return new Loan_Info(infoID, debtor, loanDate, loanAmount, months_to_pay, interest, penalty, count);
    }

    private static ObservableList<DBParam> _params(Loan_Info loan) {
        ObservableList<DBParam> parameters = FXCollections.observableArrayList();

        parameters.add(new DBParam(Types.INTEGER, "infoID", loan.getInfoID()));
        parameters.add(new DBParam(Types.INTEGER, "debtorID", loan.getDebtorID().getDebtorID()));
        parameters.add(new DBParam(Types.VARCHAR, "loanDate", DateUtil.format(loan.getLoadDate())));
        parameters.add(new DBParam(Types.BIGINT, "loanAmount", loan.getLoanAmount()));
        parameters.add(new DBParam(Types.INTEGER, "months_to_pay", loan.getMonths_to_pay()));
        parameters.add(new DBParam(Types.BIGINT, "interest", loan.getInterest()));
        parameters.add(new DBParam(Types.BIGINT, "penalty", loan.getPenalty()));
        parameters.add(new DBParam(Types.INTEGER, "count", loan.getCount()));
        return parameters;
    }
}
