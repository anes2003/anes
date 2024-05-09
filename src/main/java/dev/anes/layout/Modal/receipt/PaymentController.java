package dev.anes.layout.Modal.receipt;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import dev.anes.App;
import dev.anes.core.model.Debtor;
import dev.anes.core.model.History;
import dev.anes.core.model.Loan_Info;
import dev.anes.core.model.Payment;
import dev.anes.core.model.forenum.Remarks;
import dev.anes.layout.items.loandetailsitem;
import dev.anes.server.DebtorDAO;
import dev.anes.server.HistoryDAO;
import dev.anes.server.Loan_InfoDAO;
import dev.anes.server.PaymentDAO;
import dev.anes.service.ui.Content;
import dev.anes.service.ui.Modal;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class PaymentController {
    @FXML
    private Button closeButton;
    @FXML
    private Button submitButton;
    @FXML
    private Label fullnameLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label amountLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label currentbalanceLabel;

    private App app;
    private Debtor debtor;
    public static LocalDate dateinfo;

    public void load(App app, Debtor debtor) {
        this.app = app;
        this.debtor = debtor;
        _load_fields();
        _load_bindings();
        _load_listeners();
    }

    private void _load_fields() {

        _Label();
        _button();
        _close();
    }

    private void _load_bindings() {
    }

    private void _load_listeners() {
    }

    private Loan_Info loan_Info;
    private long pay_amnout;
    private int month;
    private int timely = 0;
    private LocalDate loanDate;
    private int countloan = 0;
    private long loanamount;
    private int month_to_pay;
    private long penalty;
    private long interest;

    private void _button() {

        submitButton.setOnMouseClicked(ev -> {
            loandetailsitem.count++;
            app.getLoan_InfoMasterList().forEach(loaninfo -> {
                if (debtor.getDebtorID() == loaninfo.getDebtorID().getDebtorID()) {
                    loan_Info = loaninfo;
                    loaninfo.setCount(loandetailsitem.count);
                    Loan_InfoDAO.update(loaninfo);
                    month = loaninfo.getMonths_to_pay();
                    loanDate = loaninfo.getLoadDate();
                    penalty = loaninfo.getPenalty();
                    interest = loaninfo.getInterest();
                }
            });

            LocalDate datenow = LocalDate.now();
            Payment payment = new Payment(debtor, loanDate, pay_amnout, datenow, timely);
            PaymentDAO.insert(payment);
            app.getPaymentMasterList().add(payment);
            if (month < loandetailsitem.count) {
                app.getLoan_InfoMasterList().forEach(loaninfo -> {
                    if (debtor.getDebtorID() == loaninfo.getDebtorID().getDebtorID()) {

                        dateinfo = loaninfo.getLoadDate();
                        loanamount = loaninfo.getLoanAmount();
                        month_to_pay = loaninfo.getMonths_to_pay();
                    }
                });
                app.getHistoryMasterList().forEach(history -> {

                    if (debtor.getDebtorID() == history.getDebtorID().getDebtorID()) {
                        countloan = history.getCountloan();
                 
                    }

                });

                countloan++;
                
                debtor.setRemark(Remarks.PAYMENTCOMPLETE);
                DebtorDAO.update(debtor);
                History history = new History(debtor, countloan, dateinfo, loanamount, month_to_pay,penalty,interest);
                HistoryDAO.insert(history);
                app.getHistoryMasterList().add(history);
                app.getLoan_InfoMasterList().remove(loan_Info);
                Loan_InfoDAO.remove(loan_Info);
                
            }
            
            Modal.close(app);
            try {
                Content.load_loaninfoloanhistory(app, debtor);
            } catch (IOException e) {

                e.printStackTrace();
            }
        
        });

    }

    private void _Label() {
        fullnameLabel.setText(debtor.getfullname());
        addressLabel.setText(debtor.getAddress());
        int monthsrunning = loandetailsitem.count;
        int asd = monthsrunning * 30;
        dateLabel.setText(LocalDate.now().toString());
        LocalDate dateInDays = loandetailsitem.dateinfo.plusDays(asd);
        LocalDate currentDate = LocalDate.now();

        if (currentDate.isAfter(dateInDays)) {
            CurrencyUnit as = CurrencyUnit.of("PHP");
            Money penaltymoney = Money.ofMinor(as, loandetailsitem.penalty);

            double amountDouble = penaltymoney.getAmount().doubleValue();
            double penaltyRate = amountDouble / 100;
            BigDecimal penaltyRateDecimal = BigDecimal.valueOf(penaltyRate);
            Money penaltyAmount = loandetailsitem.result.multipliedBy(penaltyRateDecimal, RoundingMode.DOWN);
            Money newtotalAmount = loandetailsitem.result.plus(penaltyAmount);

            amountLabel.setText("" + newtotalAmount);
            pay_amnout = newtotalAmount.getAmountMinorLong();
            timely = 1;
        } else {
            amountLabel.setText("" + loandetailsitem.result);
            pay_amnout = loandetailsitem.result.getAmountMinorLong();

        }
    }

    private void _close() {
        closeButton.setOnMouseClicked(ev -> {
            Modal.close(app);
        });
    }
}
