package dev.anes.layout.TrashButusefull;

import dev.anes.App;
import dev.anes.core.model.Debtor;
import dev.anes.layout.items.loandetailsitem;
import dev.anes.service.fx.observable.ObservableListMapper;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javafx.scene.layout.VBox;

public class loandetailsController {
    @FXML
    private TextField searchField;
    @FXML
    private TableView<Debtor> TableView;
    @FXML
    private TableColumn<Debtor, String> fullnameColumn;
    @FXML
    private TableColumn<Debtor, String> emailColumn;
    @FXML
    private TableColumn<Debtor, String> phonenumberColumn;
    @FXML
    private TableColumn<Debtor, String> addressColumn;

    @FXML
    private Label nameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label phonenumberLabel;
    @FXML
    private Label addressLabel;

    @FXML
    private VBox vBox;

    private App app;
    private Debtor debtor;
    private FilteredList<Debtor> debtorsFilteredList;
    private SortedList<Debtor> debtorsSortedList;
    private ObservableList<loandetailsitem> obserloandetails;

    public void load(App app) {
        this.app = app;

        _load_fields();
        _load_bindings();
        _load_listeners();
    }

    private void _load_fields() {
        obserloandetails = FXCollections.observableArrayList();
       
        searchField.requestFocus();
        _table();

    }

    private void _load_bindings() {

    }
    private static int months;
    private void _load_listeners() {
        TableView.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> {
            debtor = nv;
            loandetailsitem.monthsrunning =0;
            ObservableList<Debtor> items = FXCollections.observableArrayList();
            app.getLoan_InfoMasterList().forEach(loaninfo -> {
                if (debtor.getDebtorID() == loaninfo.getDebtorID().getDebtorID()) {
                
                 months=loaninfo.getMonths_to_pay();
                }
    
            });
            for (int i = 0; i < months; i++) {
               items.add(nv);
            }
            debtorsFilteredList = new FilteredList<>(items);
            debtorsSortedList = new SortedList<>(debtorsFilteredList);
            _details();
        });
    }

    private void _details() {
        String fullname;
        String email;
        String phonenumber;
        String address;
        if (debtor == null) {
            fullname = "FullName";
            email = "Email";
            phonenumber = "PhoneNumber";
            address = "Address";
        } else {
            fullname = String.format("%s, %s", debtor.getFirstname(), debtor.getLastname());
            email = debtor.getEmail();
            phonenumber = String.valueOf(debtor.getPhoneNumber());
            address = debtor.getAddress();
            ObservableListMapper.mapContent(obserloandetails, debtorsSortedList,
                    Debtor -> new loandetailsitem(Debtor, app));
            Bindings.bindContent(vBox.getChildren(), obserloandetails);
            System.out.println(obserloandetails + "otherTagsDisplay");
            System.out.println(debtorsSortedList + "movieSortedList");

        }
        nameLabel.setText(fullname);
        emailLabel.setText(email);
        phonenumberLabel.setText(phonenumber);
        addressLabel.setText(address);
    }

    private void _table() {
        fullnameColumn.setCellValueFactory(celldata -> celldata.getValue().fullnameProperty());
        emailColumn.setCellValueFactory(celldata -> celldata.getValue().emailProperty());
        phonenumberColumn.setCellValueFactory(celldata -> celldata.getValue().phoneNumberProperty());
        addressColumn.setCellValueFactory(celldata -> celldata.getValue().addressProperty());
         TableView.setRowFactory(tv -> new TableRow<>() {
            {
                itemProperty().addListener((obs, oldItem, newItem) -> {
                    if (newItem != null) {
                        setCursor(javafx.scene.Cursor.HAND);
                    } else {
                        setText(null);
                        setCursor(null);
                    }
                });
            }
        });
        app.getLoan_InfoMasterList().forEach(loaninfo -> {
            TableView.getItems().addAll(loaninfo.getDebtorID());
           
        });
    }

   
}
