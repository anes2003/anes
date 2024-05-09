package dev.anes;

import java.io.IOException;
import java.sql.SQLException;

import dev.anes.core.model.Debtor;
import dev.anes.core.model.History;
import dev.anes.core.model.Loan_Info;
import dev.anes.core.model.Owner;
import dev.anes.core.model.Payment;
import dev.anes.server.DebtorDAO;
import dev.anes.server.HistoryDAO;
import dev.anes.server.Loan_InfoDAO;
import dev.anes.server.OwnerDAO;
import dev.anes.server.PaymentDAO;
import dev.anes.service.ui.Content;
import javafx.application.Application;
import javafx.collections.ObservableList;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {
    private Stage applicationStage;
    private BorderPane applicationRoot;
    private StackPane stackPaneRoot;
    private StackPane loaninfohistoryRoot;

    private ObservableList<Owner> LoginMasterList;
    private ObservableList<Debtor> deptorMasterList;
    private ObservableList<Loan_Info> loan_InfoMasterList;
    private ObservableList<Payment> paymentMasterList;
    private ObservableList<History> historyMasterList;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.applicationStage = primaryStage;
        _load_resources();
        _initialize();
    }

    private void _initialize() throws IOException {
        Content.load_root(this);
        Content.load_Loan(this);
    }

    private void _load_resources() throws SQLException {
        LoginMasterList = OwnerDAO.getList(this);
        deptorMasterList = DebtorDAO.getMasterLsit(this);
        loan_InfoMasterList = Loan_InfoDAO.getMasterList(this);
        paymentMasterList = PaymentDAO.getMasterList(this);
        historyMasterList = HistoryDAO.getMasterList(this);
    }

    public Stage getApplicationStage() {
        return applicationStage;
    }

    public BorderPane getApplicationRoot() {
        return applicationRoot;
    }

    public StackPane getstackPaneRoot() {
        return stackPaneRoot;
    }

    public StackPane getloaninfohistoryRoot() {
        return loaninfohistoryRoot;
    }

    public ObservableList<Owner> getLoginMasterList() {
        return LoginMasterList;
    }

    public ObservableList<Debtor> getDeptorMasterList() {
        return deptorMasterList;
    }

    public ObservableList<Loan_Info> getLoan_InfoMasterList() {
        return loan_InfoMasterList;
    }

    public ObservableList<Payment> getPaymentMasterList() {
        return paymentMasterList;
    }

    public ObservableList<History> getHistoryMasterList() {
        return historyMasterList;
    }

    public void setApplicationRoot(BorderPane applicationRoot) {
        this.applicationRoot = applicationRoot;
    }

    public void setstackPaneRoot(StackPane applicationRoot) {
        this.stackPaneRoot = applicationRoot;
    }

    public void setloaninfohistoryRoot(StackPane applicationRoot) {
        this.loaninfohistoryRoot = applicationRoot;
    }

    public static void main(String[] args) {
        launch(args);
    }
}