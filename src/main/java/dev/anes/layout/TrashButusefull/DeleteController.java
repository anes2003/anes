package dev.anes.layout.TrashButusefull;

import dev.anes.App;
import dev.anes.core.model.Debtor;
import dev.anes.core.model.Loan_Info;
import dev.anes.server.DebtorDAO;
import dev.anes.server.Loan_InfoDAO;
import dev.anes.service.ui.Modal;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DeleteController {
    @FXML
    private Label text;

    @FXML
    private void handleExit() {
        Modal.close(app);
    }

    private static String a = "";

    @FXML
    private void handledelete() {
        a = "1";
        app.getLoan_InfoMasterList().forEach(loaninfo -> {
            if (debtor.getDebtorID() == loaninfo.getDebtorID().getDebtorID()) {
                a = "0";
                loan_Info = loaninfo;
            }

        });

        if (a.equals("0")) {
            app.getLoan_InfoMasterList().remove(loan_Info);
            Loan_InfoDAO.remove(loan_Info);
            app.getDeptorMasterList().remove(debtor);
            DebtorDAO.remove(debtor);

        }
        if (loan_Info == null) {
            app.getDeptorMasterList().remove(debtor);
            DebtorDAO.remove(debtor);
        }

        Modal.close(app);
    }

    private App app;
    private Debtor debtor;
    private Loan_Info loan_Info;

    public void load(App app, Debtor debtor) {
        this.app = app;
        this.debtor = debtor;
        _load_fields();
        _load_bindings();
        _load_listeners();
    }

    private void _load_fields() {
        text.setText(debtor.getfullname());

    }

    private void _load_bindings() {
    }

    private void _load_listeners() {
    }

}
