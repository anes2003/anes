package dev.anes.layout.Modal.Loan;

import java.io.IOException;
import java.time.LocalDate;


import org.joda.money.Money;
import org.kordamp.ikonli.javafx.FontIcon;

import dev.anes.App;
import dev.anes.core.model.Debtor;
import dev.anes.core.model.Loan_Info;
import dev.anes.core.model.forenum.Remarks;
import dev.anes.server.DebtorDAO;
import dev.anes.server.Loan_InfoDAO;
import dev.anes.service.ui.Content;
import dev.anes.service.ui.Modal;
import dev.anes.service.ui.Modall;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddLoanController {
    @FXML
    private FontIcon closeIcon;
    @FXML
    private TextField amountField;
    @FXML
    private TextField monthField;
    @FXML
    private TextField interestField;
    @FXML
    private TextField penaltyField;
    @FXML
    private Button submitButton;
    private App app;
    private Debtor debtor;

    public void load(App app, Debtor debtor) {
        this.app = app;
        this.debtor = debtor;
        _load_fields();
        _load_bindings();
        _load_listeners();
    }

    private void _load_fields() {
        _close();
        _submitbutton();

    }

    private void _load_bindings() {
    }

    private void _load_listeners() {
    }

    private void _submitbutton() {
        submitButton.setOnMouseClicked(ev -> {
       
            Money amountmoney = parseMoney(amountField.getText());
            Money interestmoney = parseMoney(interestField.getText());
            Money penaltyMoney = parseMoney(penaltyField.getText());
            if (amountmoney != null && interestmoney != null && penaltyMoney != null
                    && monthField.getText() != null) {
                long amount = amountmoney.getAmountMinorLong();
                long interest = interestmoney.getAmountMinorLong();
                long penalty = penaltyMoney.getAmountMinorLong();
                // CurrencyUnit as = CurrencyUnit.of("PHP");
                // Money money1 = Money.ofMinor(as, amount);
                // Money money2 = Money.ofMinor(as, amount2);
                // Money money4 = Money.ofMinor(as, amount4);

                int months = Integer.valueOf(monthField.getText());
                int count = 1;
                LocalDate currentDate = LocalDate.now();

                Loan_Info newLoan_Info = new Loan_Info(debtor, currentDate, amount, months, interest,
                        penalty,count);

                Loan_InfoDAO.insert(newLoan_Info);
                app.getLoan_InfoMasterList().add(newLoan_Info);
                      
                debtor.setRemark(Remarks.ACTIVE);
                DebtorDAO.update(debtor);
                Modall.close(app);
                try {
          
                    Content.load_loan(app, debtor);
                } catch (IOException e) {
                  
                    e.printStackTrace();
                }
            } else {
                System.out.println("error");
            }
        });
    }

    private void _close() {
        closeIcon.setOnMouseClicked(ev -> {
            Modal.close(app);
        });
    }

    private Money parseMoney(String input) {
        Money money = null;
        try {
            money = Money.parse("PHP " + input); // Assuming input is in USD currency
        } catch (IllegalArgumentException ex) {
            // Handle invalid input here if needed
        }
        return money;
    }
}
