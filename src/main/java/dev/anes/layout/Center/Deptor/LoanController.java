package dev.anes.layout.Center.Deptor;

import java.io.IOException;
import java.time.LocalDate;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import dev.anes.App;
import dev.anes.core.model.Debtor;
import dev.anes.core.model.Loan_Info;
import dev.anes.layout.items.loandetailsitem;
import dev.anes.service.fx.observable.ObservableListMapper;
import dev.anes.service.ui.Modall;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class LoanController {

    @FXML
    private VBox vBoxscrollpane;
    @FXML
    private VBox newloanVbox;
    @FXML
    private VBox loanVBox;
    @FXML
    private VBox samedateVBox;
    @FXML
    private Label penaltyLabel;
    @FXML
    private Label interestlLabel;
    @FXML
    private Label loanamountLabel;
    @FXML
    private Label loanDateLabel;
    @FXML
    private Label month;
    @FXML
    private Button addnewloanButton;

    private App app;
    private Debtor debtor;
    private Loan_Info loan_Info;
    private FilteredList<Debtor> debtorsFilteredList;
    private SortedList<Debtor> debtorsSortedList;
    private ObservableList<loandetailsitem> obserloandetails;

    public void load(App app, Debtor debtor) {
        this.app = app;
        this.debtor = debtor;
        _load_fields();
        _load_bindings();
        _load_listeners();
    }

    private void _load_fields() {
        loanDate = null;
        newloanVbox.setVisible(false);
        loanVBox.setVisible(false);
        app.getLoan_InfoMasterList().forEach(loaninfo -> {
            if (debtor.getDebtorID() == loaninfo.getDebtorID().getDebtorID()) {

                loan_Info = loaninfo;
            }

        });

        if (loan_Info == null) {
            newloanVbox.setVisible(true);
            app.getHistoryMasterList().forEach(history -> {

                if (debtor.getDebtorID() == history.getDebtorID().getDebtorID()) {
                    loanDate = history.getLoanDate();

                }
            });
          
            if (debtor.equals(null)) {
            } else {
                if (loanDate != null && loanDate.equals(LocalDate.now())) {
                    newloanVbox.setVisible(false);
                    samedateVBox.setVisible(true);
                
                }
          

            
            }

        } else {
            loanVBox.setVisible(true);
            _scrollpane();
        }

        addnewloanButton.setOnMouseClicked(ev -> {
            try {
                Modall.load_newloan(app, debtor);
            } catch (IOException e) {

                e.printStackTrace();
            }
        });
    }

    private void _load_bindings() {
    }

    private void _load_listeners() {
    }

    private static int months;
    private static LocalDate loanDate ;
    private static long loanamount;
    private static long interest;
    private static long penalty;

    private void _scrollpane() {
        obserloandetails = FXCollections.observableArrayList();
        loandetailsitem.monthsrunning = 0;
        ObservableList<Debtor> items = FXCollections.observableArrayList();
        app.getLoan_InfoMasterList().forEach(loaninfo -> {
            if (debtor.getDebtorID() == loaninfo.getDebtorID().getDebtorID()) {

                months = loaninfo.getMonths_to_pay();
                loanDate = loaninfo.getLoadDate();
                loanamount = loaninfo.getLoanAmount();
                interest = loaninfo.getInterest();
                penalty = loaninfo.getPenalty();
            }

        });
        CurrencyUnit as = CurrencyUnit.of("PHP");
        Money amountmoney = Money.ofMinor(as, loanamount);
        Money interestmoney = Money.ofMinor(as, interest);
        Money penaltymoney = Money.ofMinor(as, penalty);
        loanDateLabel.setText(loanDate.toString());
        loanamountLabel.setText(amountmoney.toString());
        interestlLabel.setText(interestmoney.getAmountMajor().toString() + "%");
        penaltyLabel.setText(penaltymoney.getAmountMajor().toString() + "%");
        month.setText(String.valueOf(months));

        for (int i = 0; i < months; i++) {
            items.add(debtor);
        }
        debtorsFilteredList = new FilteredList<>(items);
        debtorsSortedList = new SortedList<>(debtorsFilteredList);
        ObservableListMapper.mapContent(obserloandetails, debtorsSortedList,
                Debtor -> new loandetailsitem(Debtor, app));
        Bindings.bindContent(vBoxscrollpane.getChildren(), obserloandetails);

    }

}
