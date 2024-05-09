package dev.anes.layout.items;

import java.io.IOException;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import dev.anes.App;
import dev.anes.core.model.History;
import dev.anes.core.model.Payment;
import dev.anes.service.fx.observable.ObservableListMapper;
import dev.anes.service.ui.Content;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

public class HistoryDetailsitem extends TitledPane {
    @FXML
    private VBox paymentVbox;

    private App app;
    private History history;
    private FilteredList<Payment> paymentFilteredList;
    private SortedList<Payment> paymentSortedList;
    private ObservableList<Paymentitem> obserloandetails;
    
    public HistoryDetailsitem(History history, App app) {
        this.app = app;
        this.history = history;
        FXMLLoader loader = Content.load("items/HistoryDetailsitem");
        loader.setController(this);

        try {
            Parent pane = loader.load();
            setContent(pane);

        } catch (IOException e) {

            e.printStackTrace();
        }

        _load_fields();
        _load_bindings();
        _load_listeners();
    }

    private void _load_fields() {
        _titlepanetext();
        obserloandetails = FXCollections.observableArrayList();
        ObservableList<Payment> items = FXCollections.observableArrayList();
        app.getPaymentMasterList().forEach(payment->{
           if(payment.getDebtor().getDebtorID()==history.getDebtorID().getDebtorID()){
        
            if(history.getLoanDate().equals(payment.getLoanDate())){
                items.add(payment);
            }
           }
        });
        
        paymentFilteredList = new FilteredList<>(items);
        paymentSortedList = new SortedList<>(paymentFilteredList);
        ObservableListMapper.mapContent(obserloandetails, paymentSortedList,
                Payment -> new Paymentitem(Payment, app));
        Bindings.bindContent(paymentVbox.getChildren(), obserloandetails);
        Paymentitem.monthsrunning=0;
    }

    private void _load_bindings() {

    }

    private void _load_listeners() {

    }

    private void _titlepanetext() {
        CurrencyUnit as = CurrencyUnit.of("PHP");
        Money amountmoney = Money.ofMinor(as, history.getLoanAmount());
        Money interestmoney = Money.ofMinor(as, history.getInterest());
        Money penaltymoney = Money.ofMinor(as, history.getPenalty());
        setText(history.getCountloan() + "  :  " + history.getLoanDate().toString() + "    LoanAmount  :  "
                + amountmoney
                + "     " + history.getMonthtoPay() + " Months" + "    |    "
                + penaltymoney.getAmountMajor().toString() + "%" + " Penalty     |     "
                + interestmoney.getAmountMajor().toString() + "%" + " Interest     |     ");
    }
}
