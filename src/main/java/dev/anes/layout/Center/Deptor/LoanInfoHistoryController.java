package dev.anes.layout.Center.Deptor;

import java.io.IOException;

import org.kordamp.ikonli.javafx.FontIcon;

import dev.anes.App;
import dev.anes.core.model.Debtor;
import dev.anes.core.model.History;
import dev.anes.service.ui.Content;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class LoanInfoHistoryController {
    @FXML
    private StackPane stackPane;
    @FXML
    private FontIcon leftarrowIcon;
    @FXML
    private Button loanButton;
    @FXML
    private Button infoButton;
    @FXML
    private Button historyButton;

    private App app;
    private Debtor debtor;
    private History historyid;

    public void load(App app, Debtor debtor) {
        this.app = app;
        this.debtor = debtor;
        _load_fields();
        _load_bindings();
        _load_listeners();
    }

    private void _load_fields() {
        historyid = null;
        app.setloaninfohistoryRoot(stackPane);
        _up_Button();

        try {

            Content.load_loan(app, debtor);
        } catch (IOException e) {

            e.printStackTrace();
        }
        app.getHistoryMasterList().forEach(history -> {
            if (debtor.getDebtorID() == history.getDebtorID().getDebtorID()) {
                historyid = history;

            }

        });

        if (historyid == null) {
            historyButton.setDisable(true);
        } else {
            historyButton.setDisable(false);
        }

    }

    private void _load_bindings() {

    }

    private void _load_listeners() {

    }

    private void _up_Button() {
        loanButton.setStyle(" -fx-background-color: rgb(5, 36, 19);");
        infoButton.setStyle(null);
        historyButton.setStyle(null);
        leftarrowIcon.setOnMouseClicked(ev -> {
            try {
                Content.load_debtor(app);
            } catch (IOException e) {

                e.printStackTrace();
            }
        });
        loanButton.setOnMouseClicked(ev -> {
            loanButton.setStyle(" -fx-background-color: rgb(5, 36, 19);");
            infoButton.setStyle(null);
            historyButton.setStyle(null);
            try {

                Content.load_loan(app, debtor);
            } catch (IOException e) {

                e.printStackTrace();
            }
        });
        infoButton.setOnMouseClicked(ev -> {
            loanButton.setStyle(null);
            infoButton.setStyle(" -fx-background-color: rgb(5, 36, 19);");
            historyButton.setStyle(null);
            try {

                Content.load_info(app, debtor);
            } catch (IOException e) {

                e.printStackTrace();
            }
        });
        historyButton.setOnMouseClicked(ev -> {
            loanButton.setStyle(null);
            infoButton.setStyle(null);
            historyButton.setStyle(" -fx-background-color: rgb(5, 36, 19);");
            try {

                Content.load_history(app, debtor);
            } catch (IOException e) {

                e.printStackTrace();
            }
        });
    }
}
