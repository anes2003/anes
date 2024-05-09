package dev.anes.server;

import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;

import javax.sql.rowset.CachedRowSet;

import dev.anes.App;
import dev.anes.core.model.Debtor;
import dev.anes.core.model.Payment;
import dev.anes.core.util.DateUtil;
import dev.anes.service.db.DBCommand;
import dev.anes.service.db.DBParam;
import dev.anes.service.db.DBService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PaymentDAO {
    public static final String DATABASE = "lending";
    public static final String TABLE = "payment_info";

    public static int getLastID() {
        String sql = "SELECT MAX(paymentID) AS last_id FROM " + TABLE;
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

    public static ObservableList<Payment> getMasterList(App app) throws SQLException {
        ObservableList<Payment> list = FXCollections.observableArrayList();
        CachedRowSet crs = DBCommand.READ(DATABASE, TABLE, new DBParam[] {});

        while (crs.next()) {
            Payment payment = _data(crs, app);
            if (payment != null)
                list.add(payment);
        }
        return list;

    }

    public static void insert(Payment payment) {
        DBCommand.CREATE(DATABASE, TABLE, _params(payment).toArray(new DBParam[] {}));
        ;
    }

    public static void update(Payment payment) {
        DBCommand.UPDATE(DATABASE, TABLE, new DBParam(Types.INTEGER, "paymentID", payment.getPayment()),
                _params(payment).toArray(new DBParam[] {}));
    }

    public static void remove(Payment payment) {
        DBCommand.DELETE(DATABASE, TABLE, new DBParam(Types.INTEGER, "paymentID", payment.getPayment()));
    }

    private static Payment _data(CachedRowSet crs, App app) throws SQLException {
        int paymnet = crs.getInt("paymentID");
        int debtorID = crs.getInt("debtorID");
        LocalDate loandate = DateUtil.parse(crs.getString("loandate"));
        long pay_amnout = crs.getLong("pay_amount");
        LocalDate payment_date = DateUtil.parse(crs.getString("payment_date"));
        int timely =crs.getInt("timely");
        Debtor debtor = app.getDeptorMasterList()
                .stream()
                .filter(dep -> dep.getDebtorID() == debtorID)
                .findFirst().get();

        return new Payment(paymnet, debtor,loandate, pay_amnout, payment_date,timely);
    }

    private static ObservableList<DBParam> _params(Payment payment) {
        ObservableList<DBParam> parameters = FXCollections.observableArrayList();

        parameters.add(new DBParam(Types.INTEGER, "paymentID", payment.getPayment()));
        parameters.add(new DBParam(Types.INTEGER, "DebtorID", payment.getDebtor().getDebtorID()));
        parameters.add(new DBParam(Types.VARCHAR, "loandate", DateUtil.format(payment.getLoanDate())));
        parameters.add(new DBParam(Types.BIGINT, "pay_amount", payment.getPay_amount()));
        parameters.add(new DBParam(Types.VARCHAR, "payment_date", DateUtil.format(payment.getPaymnet_date())));
        parameters.add(new DBParam(Types.INTEGER, "timely",payment.getTimely()));
       

        return parameters;
    }
}
