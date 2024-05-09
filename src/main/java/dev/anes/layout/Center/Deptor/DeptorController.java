package dev.anes.layout.Center.Deptor;

import java.io.IOException;
import java.time.LocalDate;

import dev.anes.App;
import dev.anes.core.model.Debtor;
import dev.anes.core.model.forenum.CivilStatus;
import dev.anes.core.model.forenum.Gender;
import dev.anes.core.model.forenum.Remarks;
import dev.anes.server.DebtorDAO;
import dev.anes.service.ui.Content;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javafx.scene.layout.VBox;

public class DeptorController {
    @FXML
    private Button loanerButton;
    @FXML
    private Button addloanerButton;
    @FXML
    private VBox addloanerVBox;
    @FXML
    private VBox loanerVBox;
    // addlaoner==================================
    // =========================================
    @FXML
    private ComboBox<Gender> addloaner_GenderComboBox;
    @FXML
    private ComboBox<CivilStatus> addloaner_CivilStatusComboBox;
    @FXML
    private Button addloaner_submitButton;
    @FXML
    private TextField addloaner_firstname;
    @FXML
    private TextField addloaner_middlename;
    @FXML
    private TextField addloaner_lastname;
    @FXML
    private TextField addloaner_extensionname;
    @FXML
    private TextField addloaner_phonenumber;
    @FXML
    private TextField addloaner_email;
    @FXML
    private TextField addloaner_postalcode;
    @FXML
    private TextField addloaner_barangay;
    @FXML
    private TextField addloaner_town_city;
    @FXML
    private TextField addloaner_province;
    @FXML
    private TextField addloaner_monthlyincome;
    @FXML
    private TextField addloaner_tinid;
    @FXML
    private TextField addloaner_occupation;
    @FXML
    private DatePicker addloaner_birthdate;

    // laoner==================================
    // =========================================
    @FXML
    private TextField loaner_searhField;
    @FXML
    private TableView<Debtor> loaner_TableView;
    @FXML
    private TableColumn<Debtor, String> loaner_fullnameColumn;
    @FXML
    private TableColumn<Debtor, LocalDate> loaner_ApprovedateColumn;
    @FXML
    private TableColumn<Debtor, String> loaner_RemarkColumn;

    private App app;

    private FilteredList<Debtor> loanerFilteredList;
    private SortedList<Debtor> loanerSortedList;

    private int couerror = 0;

    public void load(App app) {
        this.app = app;
        _load_fields();
        _load_bindings();
        _load_listeners();

    }

    private void _load_fields() {
        // addloaner

        _addloaner_combobox();
        _add_deptors();
        _RequestFocus();
        // loaner
        _load_loaner();
        _up_Button();
        _remarks();
    }

    private void _load_bindings() {
        // addloaner

        // loaner
    }

    private void _load_listeners() {
        // addloaner
        _TextFieldslisteners();

        // loaner
        _searchfield();
    }

    // addlaoner==================================
    // =========================================
    private void _addloaner_combobox() {
        addloaner_GenderComboBox.getItems().setAll(Gender.values());
        addloaner_CivilStatusComboBox.getItems().setAll(CivilStatus.values());

    }

    private void _add_deptors() {

        addloaner_birthdate.getEditor().setOnMouseClicked(ev -> {
            addloaner_birthdate.show();
        });

        addloaner_submitButton.setOnAction(ev -> {
            String firstname = addloaner_firstname.getText().trim();
            String lastname = addloaner_lastname.getText().trim();
            String middlename = addloaner_middlename.getText().trim();
            String extensionName = addloaner_extensionname.getText();
            int gender = addloaner_GenderComboBox.getSelectionModel().getSelectedIndex();
            String email = addloaner_email.getText();
            String phonenumber = addloaner_phonenumber.getText().trim();
            String job = addloaner_occupation.getText().trim();
            String tinid = addloaner_tinid.getText();
            String postalcode = addloaner_postalcode.getText();
            String brgy = addloaner_barangay.getText().trim();
            String town = addloaner_town_city.getText().trim();
            String province = addloaner_province.getText().trim();
            LocalDate birthdate = addloaner_birthdate.getValue();
            int status = addloaner_CivilStatusComboBox.getSelectionModel().getSelectedIndex();
            LocalDate approvedate = LocalDate.now();
            Remarks remarks = Remarks.NOLOAN;
            String address = "";
            couerror = 0;
            if (addloaner_birthdate.getValue() == null) {
                // couerror++;
                System.out.println("birthday IsEmpty");
            }
            if (postalcode.isBlank()) {
                address = String.format("%s, %s, %s", brgy, town, province);

            } else {

                address = String.format("%s, %s, %s, %s", postalcode, brgy, town, province);
            }
            if (job.isBlank()) {
                couerror++;
                System.out.println("job IsEmpty");
            }

            if (firstname.isBlank()) {
                couerror++;
                System.out.println("firstname IsEmpty");
            }
            if (lastname.isBlank()) {
                couerror++;
                System.out.println("lastname IsEmpty");
            }
            if (addloaner_GenderComboBox.getSelectionModel().getSelectedItem() == null) {
                couerror++;
                System.out.println("Pick Gender");
            }
            if (addloaner_CivilStatusComboBox.getSelectionModel().getSelectedItem() == null) {
                couerror++;
                System.out.println("Pick civilstatus");
            }
            if (email.isBlank()) {
                couerror++;
                System.out.println("email IsEmpty");
            }
            if (phonenumber.isBlank()) {
                couerror++;
                System.out.println("phonenumber IsEmpty");
            }
            if (addloaner_monthlyincome.getText().isBlank()) {
                // couerror++;
                System.out.println("monthlyincome IsEmpty");
            }
            if (brgy.isBlank()) {
                couerror++;
                System.out.println("Brgy IsEmpty");
            }
            if (town.isBlank()) {
                couerror++;
                System.out.println("town IsEmpty");
            }
            if (province.isBlank()) {
                couerror++;
                System.out.println("city IsEmpty");
            }
            System.out.println(couerror);
            if (couerror == 0) {
                System.out.println("pass");
                int monthlyincomeMoney = Integer.parseInt(addloaner_monthlyincome.getText());

                Debtor newDebtor = new Debtor(firstname, lastname, middlename, extensionName,
                        email, phonenumber,
                        address, gender, job, tinid, monthlyincomeMoney, birthdate, status,
                        approvedate, remarks);

                app.getDeptorMasterList().add(newDebtor);
                DebtorDAO.insert(newDebtor);

                addloaner_firstname.clear();
                addloaner_middlename.clear();
                addloaner_lastname.clear();
                addloaner_extensionname.clear();
                addloaner_phonenumber.clear();
                addloaner_email.clear();
                addloaner_postalcode.clear();
                addloaner_barangay.clear();
                addloaner_town_city.clear();
                addloaner_province.clear();
                addloaner_monthlyincome.clear();
                addloaner_tinid.clear();
                addloaner_occupation.clear();
                addloaner_birthdate.setValue(null);

                loanerButton.setStyle(" -fx-background-color: rgb(5, 36, 19);");
                addloanerButton.setStyle(null);
                loanerVBox.setVisible(true);
                addloanerVBox.setVisible(false);

            }

        });
    }

    private void _RequestFocus() {
        addloaner_firstname.setOnAction(event -> {

            addloaner_middlename.requestFocus();
        });
        addloaner_middlename.setOnAction(event -> {

            addloaner_lastname.requestFocus();
        });
        addloaner_lastname.setOnAction(event -> {

            addloaner_extensionname.requestFocus();
        });
        addloaner_birthdate.setOnAction(ev -> {
            addloaner_GenderComboBox.requestFocus();
            addloaner_GenderComboBox.show();
        });
        addloaner_GenderComboBox.setOnAction(ev -> {
            addloaner_CivilStatusComboBox.requestFocus();
            addloaner_CivilStatusComboBox.show();
        });
        addloaner_CivilStatusComboBox.setOnAction(ev -> {
            addloaner_phonenumber.requestFocus();
        });
        addloaner_phonenumber.setOnAction(ev -> {
            addloaner_email.requestFocus();
        });
        addloaner_email.setOnAction(ev -> {
            addloaner_postalcode.requestFocus();
        });
        addloaner_postalcode.setOnAction(ev -> {
            addloaner_barangay.requestFocus();
        });
        addloaner_barangay.setOnAction(ev -> {
            addloaner_town_city.requestFocus();
        });
        addloaner_town_city.setOnAction(ev -> {
            addloaner_province.requestFocus();
        });
        addloaner_province.setOnAction(ev -> {
            addloaner_monthlyincome.requestFocus();
        });
        addloaner_monthlyincome.setOnAction(ev -> {
            addloaner_tinid.requestFocus();
        });
        addloaner_tinid.setOnAction(ev -> {
            addloaner_occupation.requestFocus();
        });

    }

    private void _TextFieldslisteners() {
        addloaner_firstname.textProperty().addListener((ob, ov, nv) -> {
            if (nv.length() <= 45) {
                addloaner_firstname.setText(nv);
            } else {
                addloaner_firstname.setText(ov);
            }
        });
        addloaner_middlename.textProperty().addListener((ob, ov, nv) -> {
            if (nv.length() <= 45) {
                addloaner_middlename.setText(nv);
            } else {
                addloaner_middlename.setText(ov);
            }
        });
        addloaner_lastname.textProperty().addListener((ob, ov, nv) -> {
            if (nv.length() <= 45) {
                addloaner_lastname.setText(nv);
            } else {
                addloaner_lastname.setText(ov);
            }
        });
        addloaner_phonenumber.textProperty().addListener((ob, ov, nv) -> {
            if (nv.length() <= 20) {
                addloaner_phonenumber.setText(nv);
            } else {
                addloaner_phonenumber.setText(ov);
            }
        });
        addloaner_barangay.textProperty().addListener((ob, ov, nv) -> {
            if (nv.length() <= 45) {
                addloaner_barangay.setText(nv);
            } else {
                addloaner_barangay.setText(ov);
            }
        });
        addloaner_town_city.textProperty().addListener((ob, ov, nv) -> {
            if (nv.length() <= 45) {
                addloaner_town_city.setText(nv);
            } else {
                addloaner_town_city.setText(ov);
            }
        });
        addloaner_province.textProperty().addListener((ob, ov, nv) -> {
            if (nv.length() <= 45) {
                addloaner_province.setText(nv);
            } else {
                addloaner_province.setText(ov);
            }
        });
        addloaner_occupation.textProperty().addListener((ob, ov, nv) -> {
            if (nv.length() <= 45) {
                addloaner_occupation.setText(nv);
            } else {
                addloaner_occupation.setText(ov);
            }
        });
        addloaner_monthlyincome.textProperty().addListener((observable, oldValue,
                newValue) -> {

            if (!newValue.matches("\\d{0,14}")) {
                addloaner_monthlyincome.setText(oldValue);
            }

        });
        addloaner_extensionname.textProperty().addListener((ob, ov, nv) -> {

            if (nv.length() <= 5) {
                addloaner_extensionname.setText(nv);
            } else {
                addloaner_extensionname.setText(ov);
            }
            String newValueWithoutSpaces = nv.replaceAll("\\s", "");
            if (!nv.equals(newValueWithoutSpaces)) {
                addloaner_extensionname.setText(newValueWithoutSpaces);
            }
        });
        addloaner_email.textProperty().addListener((ob, ov, nv) -> {
            if (nv.length() <= 45) {
                addloaner_email.setText(nv);
            } else {
                addloaner_email.setText(ov);
            }
            String newValueWithoutSpaces = nv.replaceAll("\\s", "");
            if (!nv.equals(newValueWithoutSpaces)) {
                addloaner_email.setText(newValueWithoutSpaces);
            }
        });
        addloaner_tinid.textProperty().addListener((ob, ov, nv) -> {
            if (nv.length() <= 30) {
                addloaner_tinid.setText(nv);
            } else {
                addloaner_tinid.setText(ov);
            }
            String newValueWithoutSpaces = nv.replaceAll("\\s", "");
            if (!nv.equals(newValueWithoutSpaces)) {
                addloaner_tinid.setText(newValueWithoutSpaces);
            }
        });
        addloaner_postalcode.textProperty().addListener((ob, ov, nv) -> {
            if (nv.length() <= 20) {
                addloaner_postalcode.setText(nv);
            } else {
                addloaner_postalcode.setText(ov);
            }
            String newValueWithoutSpaces = nv.replaceAll("\\s", "");
            if (!nv.equals(newValueWithoutSpaces)) {
                addloaner_postalcode.setText(newValueWithoutSpaces);
            }
        });

    }

    // laoner==================================
    // =========================================
    private void _searchfield() {
        loaner_searhField.textProperty().addListener((o, ov, nv) -> {
            loanerFilteredList.setPredicate(employee -> {
                if (nv == null || nv.isEmpty()) {
                    return true;
                }
                // lower case all possible
                String lowerCaseFilter = nv.toLowerCase();

                if (employee.getfullname().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                if (employee.getapproveDate().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }

                return false;
            });

        });
    }

    private void _up_Button() {
        loanerButton.setStyle(" -fx-background-color: rgb(5, 36, 19);");
        addloanerButton.setStyle(null);
        loanerButton.setOnMouseClicked(ev -> {
            loanerButton.setStyle(" -fx-background-color: rgb(5, 36, 19);");
            addloanerButton.setStyle(null);
            loanerVBox.setVisible(true);
            addloanerVBox.setVisible(false);
        });
        addloanerButton.setOnMouseClicked(ev -> {

            addloanerButton.setStyle("-fx-background-color: rgb(5, 36, 19);");
            loanerButton.setStyle(null);
            loanerVBox.setVisible(false);
            addloanerVBox.setVisible(true);
        });

    }

    private void _load_loaner() {
        loaner_fullnameColumn.setCellValueFactory(celldata -> celldata.getValue().fullnameProperty());
        loaner_ApprovedateColumn.setCellValueFactory(celldata -> celldata.getValue().approveDateProperty());
        loaner_RemarkColumn.setCellValueFactory(celldata -> celldata.getValue().remarkProperty().asString());

        loanerFilteredList = new FilteredList<>(app.getDeptorMasterList());
        loanerSortedList = new SortedList<>(loanerFilteredList);
        loaner_TableView.setItems(loanerSortedList);

        loaner_TableView.setRowFactory(tv -> new TableRow<>() {

            {

                itemProperty().addListener((obs, oldItem, newItem) -> {

                    setOnMouseClicked(event -> {
                        if (event.getClickCount() == 2 && newItem != null) {
                            try {
                                Content.load_loaninfoloanhistory(app, newItem);
                            } catch (IOException e) {

                                e.printStackTrace();
                            }
                        }
                    });

                    if (newItem != null) {
                        setCursor(javafx.scene.Cursor.DEFAULT);
                    } else {
                        setText(null);
                        setCursor(null);

                    }
                });
            }

        });
    }

    private void _remarks() {

    }

}
