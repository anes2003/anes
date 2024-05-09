package dev.anes.layout.items;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.kordamp.ikonli.javafx.FontIcon;

import dev.anes.App;
import dev.anes.core.model.Debtor;
import dev.anes.core.model.forenum.Remarks;
import dev.anes.server.DebtorDAO;
import dev.anes.service.ui.Content;
import dev.anes.service.ui.Modall;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class loandetailsitem extends HBox {

    @FXML
    private Label months;
    @FXML
    private Label amount;

    @FXML
    private HBox hbox;

    @FXML
    private FontIcon errorIcon;
    @FXML
    private StackPane payPane;
    @FXML
    private Button payButton;
    @FXML
    private HBox paidHBox;
    @FXML
    private Label dateoverdue;

    private App app;
    private Debtor debtor;

    public loandetailsitem(Debtor debtor, App app) {
        this.app = app;
        this.debtor = debtor;
        FXMLLoader loader = Content.load("items/loandetailsitem");
        loader.setController(this);

        try {
            Parent pane = loader.load();
            getChildren().setAll(pane);

        } catch (IOException e) {

            e.printStackTrace();
        }

        _load_fields();
        _load_bindings();
        _load_listeners();
    }

    public static int monthsrunning = 0;
    private static int timelyrunning = 0;
    private static int monthspay;
    private static long loanamount;
    private static long interest;
    public static long penalty;
    public static LocalDate dateinfo;
    public static Money result;
    private static Money totalAmount;
    public static int count;

    private void _load_fields() {
        months.setStyle("-fx-font-size: 29px;");
        amount.setStyle("-fx-font-size: 23px;");

        monthsrunning++;

        _label();
        _dateerror();
        _paidpay();

    }

    private void _load_bindings() {

    }

    private void _load_listeners() {
    }

    private void _paidpay() {

        payPane.setVisible(false);

        payButton.setOnAction(ev -> {

            try {
                Modall.load_payment(app, debtor);
            } catch (IOException e) {

                e.printStackTrace();
            }
        });
        timelyrunning = 0;

        app.getPaymentMasterList().forEach(ev -> {
            if (debtor.getDebtorID() == ev.getDebtor().getDebtorID()) {

                if (ev.getLoanDate().isEqual(dateinfo)) {

                    timelyrunning++;

                    if (timelyrunning == monthsrunning) {
                        if (ev.getTimely() == 1) {
                            hbox.setStyle(" -fx-border-color: rgb(207, 0, 0);");
                            errorIcon.setVisible(true);

                        } else {
                            hbox.setStyle(null);
                            errorIcon.setVisible(false);
                            amount.setText("" + result);
                            debtor.setRemark(Remarks.ACTIVE);
                            DebtorDAO.update(debtor);
                        }

                    }

                }

            }

        });

        if (monthsrunning <= count) {
            payButton.setVisible(false);
            paidHBox.setVisible(true);
            payPane.setVisible(true);
            if (monthsrunning == count) {
                paidHBox.setVisible(false);
                payButton.setVisible(true);
            }
        }

    }

    private void _dateerror() {

        int asd = monthsrunning * 30;
        LocalDate dateInDays = dateinfo.plusDays(asd);
        LocalDate currentDate = LocalDate.now();
        dateoverdue.setText(dateInDays.toString());
        long daysUntilDue = ChronoUnit.DAYS.between(currentDate, dateInDays);
        if (daysUntilDue == 10) {
            debtor.setRemark(Remarks.TENDAYS);
            DebtorDAO.update(debtor);
        }
        if (currentDate.isAfter(dateInDays)) {
            hbox.setStyle(" -fx-border-color: rgb(207, 0, 0);");
            errorIcon.setVisible(false);
            CurrencyUnit as = CurrencyUnit.of("PHP");
            Money penaltymoney = Money.ofMinor(as, penalty);

            double amountDouble = penaltymoney.getAmount().doubleValue();
            double penaltyRate = amountDouble / 100;
            BigDecimal penaltyRateDecimal = BigDecimal.valueOf(penaltyRate);
            Money penaltyAmount = result.multipliedBy(penaltyRateDecimal, RoundingMode.DOWN);
            Money newtotalAmount = result.plus(penaltyAmount);

            amount.setText("" + newtotalAmount);
            debtor.setRemark(Remarks.OVERDUE);
            DebtorDAO.update(debtor);
        }
    }

    private void _label() {
        app.getLoan_InfoMasterList().forEach(loaninfo -> {
            if (debtor.getDebtorID() == loaninfo.getDebtorID().getDebtorID()) {

                monthspay = loaninfo.getMonths_to_pay();
                loanamount = loaninfo.getLoanAmount();
                interest = loaninfo.getInterest();
                dateinfo = loaninfo.getLoadDate();
                penalty = loaninfo.getPenalty();
                count = loaninfo.getCount();

            }

        });

        CurrencyUnit as = CurrencyUnit.of("PHP");

        Money monthsMoney = Money.of(as, monthspay);
        Money amountmoney = Money.ofMinor(as, loanamount);
        Money interestmoney = Money.ofMinor(as, interest);

        double amountDouble = interestmoney.getAmount().doubleValue();
        double interestRate = amountDouble / 100;
        BigDecimal interestRateDecimal = BigDecimal.valueOf(interestRate);
        // Calculate interest amount
        Money interestAmount = amountmoney.multipliedBy(interestRateDecimal, RoundingMode.DOWN);
        totalAmount = amountmoney.plus(interestAmount);

        BigDecimal moneyAmount = totalAmount.getAmount();
        BigDecimal divisorAmount = monthsMoney.getAmount();

        BigDecimal resultAmount = moneyAmount.divide(divisorAmount, 2, RoundingMode.HALF_UP);

        result = Money.of(as, resultAmount);
        amount.setText("" + result);

        months.setText(String.valueOf(monthsrunning));
    }

    // private Money parseMoney(String input) {
    // Money money = null;
    // try {
    // money = Money.parse("PHP " + input); // Assuming input is in USD currency
    // } catch (IllegalArgumentException ex) {
    // // Handle invalid input here if needed
    // }
    // return money;
    // }
}
