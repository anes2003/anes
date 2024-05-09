package dev.anes.layout.items;

import java.io.IOException;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import dev.anes.App;
import dev.anes.core.model.Payment;
import dev.anes.service.ui.Content;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Paymentitem extends VBox{
    @FXML
    private Label monthLabel;
    @FXML
    private Label paymentamountLabel;
    @FXML
    private Label paymentdateLabel;
    private App app;
    private Payment payment;
    public Paymentitem(Payment payment ,App app){
        this.app = app;
        this.payment = payment;
        FXMLLoader loader = Content.load("items/Paymentitem");
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
    private void _load_fields() {
     _label();
    }
    
    private void _load_bindings() {
       
    }
    private void _load_listeners() {
       
    }
    public static int monthsrunning=0;
    private void _label() {
        monthsrunning++;
        CurrencyUnit as = CurrencyUnit.of("PHP");
        Money amountmoney = Money.ofMinor(as, payment.getPay_amount());
       monthLabel.setText(String.valueOf(monthsrunning));
       paymentamountLabel.setText(amountmoney.toString());
       paymentdateLabel.setText(payment.getPaymnet_date().toString());
    }
}
