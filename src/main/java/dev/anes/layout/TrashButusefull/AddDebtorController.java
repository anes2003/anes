package dev.anes.layout.TrashButusefull;

import dev.anes.App;
import dev.anes.core.model.Debtor;
import dev.anes.core.model.forenum.Gender;
import dev.anes.server.DebtorDAO;
import dev.anes.service.ui.Modal;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddDebtorController {

    @FXML
    private TextField debtors_Firstnamefield;
    @FXML
    private TextField debtors_LastNamefield;
    @FXML
    private TextField debtors_middleNameField;
    @FXML
    private TextField debtors_extensionNameField;
    @FXML
    private TextField debtors_Emailfield;
    @FXML
    private TextField debtors_PhoneNumberfield;
    @FXML
    private ComboBox<Gender> debtors_genderComboBox;
    @FXML
    private TextField debtors_Jobfield;
    @FXML
    private TextField debtors_tinidfield;
    @FXML
    private TextField debtors_MonthlyIncomefield;
    @FXML
    private Button confirmButton;
    // Address--------------->
    @FXML
    private TextField debtors_PostalCode;
    @FXML
    private TextField debtors_Brgy;
    @FXML
    private TextField debtors_Town;
    @FXML
    private TextField debtors_City;

    @FXML
    private Label ADDEdit;
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

        if (debtor == null) {
            ADDEdit.setText("ADD");

        } else {
            ADDEdit.setText("Edit");
            _Edit_debtors();
        }
        _confirmButton();
    }

    private void _load_bindings() {
    }

    private void _load_listeners() {
        _TextFieldslisteners();
    }

    private void _confirmButton() {
        debtors_genderComboBox.getItems().setAll(Gender.values());
        confirmButton.setOnAction(ev -> {
            String firstname = debtors_Firstnamefield.getText().trim();
            String lastname = debtors_LastNamefield.getText().trim();

            String middlename = debtors_middleNameField.getText().trim();
            String extensionName = debtors_extensionNameField.getText();
            int gender = debtors_genderComboBox.getSelectionModel().getSelectedIndex();
            String email = debtors_Emailfield.getText();
            String phonenumber = debtors_PhoneNumberfield.getText().trim();
            int monthlyincomeMoney = Integer.parseInt(debtors_MonthlyIncomefield.getText());
            String job = debtors_Jobfield.getText().trim();
            String tinid = debtors_tinidfield.getText();
            String postalcode = debtors_PostalCode.getText();
            String brgy = debtors_Brgy.getText().trim();
            String town = debtors_Town.getText().trim();
            String city = debtors_City.getText().trim();

            String address = "";

            if (postalcode.isBlank()) {
                address = String.format("%s, %s, %s", brgy, town, city);
            } else {
                address = String.format("%s, %s, %s, %s", postalcode, brgy, town, city);
            }

            ;
            if (firstname.isBlank()) {
                System.out.println("firstname IsEmpty");
            } else if (lastname.isBlank()) {
                System.out.println("lastname IsEmpty");
            } else if (debtors_genderComboBox.getSelectionModel().getSelectedItem() == null) {
                System.out.println("Pick Gender");
            } else if (email.isBlank()) {
                System.out.println("email IsEmpty");
            } else if (phonenumber.isBlank()) {
                System.out.println("phonenumber IsEmpty");
            } else if (debtors_MonthlyIncomefield.getText().isEmpty()
                    || debtors_MonthlyIncomefield.getText().isBlank()) {

                System.out.println("monthlyincome IsEmpty");
            } else if (brgy.isBlank()) {
                System.out.println("Brgy IsEmpty");
            } else if (town.isBlank()) {
                System.out.println("town IsEmpty");
            } else if (city.isBlank()) {
                System.out.println("city IsEmpty");
            } else {
                System.out.println("pass");

                if (debtor == null) {
                  

                  
                    
                    Modal.close(app);
                } else {

                    debtor.setFirstname(firstname);
                    debtor.setLastname(lastname);
                    debtor.setMiddleName(middlename);
                    debtor.setExtensionName(extensionName);
                    debtor.setEmail(email);
                    debtor.setPhoneNumber(phonenumber);
                    debtor.setAddress(address);
                    debtor.setGender(gender);
                    debtor.setjobName(job);
                    debtor.setTinid(tinid);
                    debtor.setmonthly_income(monthlyincomeMoney);
                    if (middlename.isBlank() || middlename.isEmpty()) {
                        debtor.setFullname(firstname + " " + middlename + " " + lastname + " " + extensionName);
                    } else {

                        debtor.setFullname(
                                firstname + " " + middlename.charAt(0) + " " + lastname + " " + extensionName);
                    }

                    DebtorDAO.update(debtor);
                    int index = app.getDeptorMasterList().indexOf(debtor);
                    if (index != -1) {
                        app.getDeptorMasterList().set(index, debtor);

                    } else {

                    }
                    Modal.close(app);
                }

            }

        });
    }

    private void _Edit_debtors() {
        String[] address = debtor.getAddress().split(", ");
        if (address.length == 4) {
            debtors_PostalCode.setText(address[0]);
            debtors_Brgy.setText(address[1]);
            debtors_Town.setText(address[2]);
            debtors_City.setText(address[3]);
        } else {
            debtors_Brgy.setText(address[0]);
            debtors_Town.setText(address[1]);
            debtors_City.setText(address[2]);
        }
        debtors_Emailfield.setText(debtor.getEmail());
        debtors_PhoneNumberfield.setText(debtor.getPhoneNumber());
        debtors_genderComboBox.getSelectionModel().select(debtor.getGender());
        debtors_Firstnamefield.setText(debtor.getFirstname());
        debtors_LastNamefield.setText(debtor.getLastname());
        debtors_middleNameField.setText(debtor.getMiddleName());
        debtors_extensionNameField.setText(debtor.getExtensionName());
        debtors_MonthlyIncomefield.setText(String.valueOf(debtor.getmonthly_income()));
        debtors_Jobfield.setText(debtor.getJobName());
        debtors_tinidfield.setText(debtor.getTinid());
        debtors_genderComboBox.getSelectionModel().select(debtor.getGender());
       
    }

   

    private void _TextFieldslisteners() {

        debtors_MonthlyIncomefield.textProperty().addListener((observable, oldValue,
                newValue) -> {

            if (!newValue.matches("\\d{0,14}")) {
                debtors_MonthlyIncomefield.setText(oldValue);
            }

        });
        debtors_extensionNameField.textProperty().addListener((ob, ov, nv) -> {
            String newValueWithoutSpaces = nv.replaceAll("\\s", "");
            if (!nv.equals(newValueWithoutSpaces)) {
                debtors_extensionNameField.setText(newValueWithoutSpaces);
            }
        });
        debtors_Emailfield.textProperty().addListener((ob, ov, nv) -> {
            String newValueWithoutSpaces = nv.replaceAll("\\s", "");
            if (!nv.equals(newValueWithoutSpaces)) {
                debtors_Emailfield.setText(newValueWithoutSpaces);
            }
        });
        debtors_tinidfield.textProperty().addListener((ob, ov, nv) -> {
            String newValueWithoutSpaces = nv.replaceAll("\\s", "");
            if (!nv.equals(newValueWithoutSpaces)) {
                debtors_tinidfield.setText(newValueWithoutSpaces);
            }
        });
        debtors_PostalCode.textProperty().addListener((ob, ov, nv) -> {
            String newValueWithoutSpaces = nv.replaceAll("\\s", "");
            if (!nv.equals(newValueWithoutSpaces)) {
                debtors_PostalCode.setText(newValueWithoutSpaces);
            }
        });

    }
    // private Money parseMoney(String input) {
    //     Money money = null;
    //     try {
    //         money = Money.parse("PHP " + input); // Assuming input is in USD currency
    //     } catch (IllegalArgumentException ex) {
    //         // Handle invalid input here if needed
    //     }
    //     return money;
    // }
}
