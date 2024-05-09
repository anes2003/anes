package dev.anes.layout.Center.Deptor;

import dev.anes.App;
import dev.anes.core.model.Debtor;
import dev.anes.core.model.forenum.CivilStatus;
import dev.anes.core.model.forenum.Gender;
import dev.anes.server.DebtorDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class InfoController {
    @FXML
    private ComboBox<Gender> genderComboBox;
    @FXML
    private ComboBox<CivilStatus> civilStatusComboBox;
    @FXML
    private Button submitButton;
    @FXML
    private TextField firstname;
    @FXML
    private TextField middlename;
    @FXML
    private TextField lastname;
    @FXML
    private TextField extensionname;
    @FXML
    private TextField phonenumber;
    @FXML
    private TextField email;
    @FXML
    private TextField postalcode;
    @FXML
    private TextField barangay;
    @FXML
    private TextField town_city;
    @FXML
    private TextField province;
    @FXML
    private TextField monthlyincome;
    @FXML
    private TextField tinid;
    @FXML
    private TextField occupation;
    @FXML
    private DatePicker birthdate;
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
        genderComboBox.getItems().setAll(Gender.values());
        civilStatusComboBox.getItems().setAll(CivilStatus.values());
        _details();
        _button();
    }

    private void _load_bindings() {
    }

    private void _load_listeners() {
        _TextFieldslisteners();
    }

    private void _button() {
        submitButton.setOnMouseClicked(ev -> {
            String address = "";

            if (postalcode.getText().isBlank()) {
                address = String.format("%s, %s, %s", barangay.getText().trim(), town_city.getText().trim(),
                        province.getText().trim());
            } else {
                address = String.format("%s, %s, %s, %s", postalcode.getText().trim(), barangay.getText().trim(),
                        town_city.getText().trim(),
                        province.getText().trim());
            }
            debtor.setFirstname(firstname.getText().trim());
            debtor.setLastname(lastname.getText().trim());
            debtor.setMiddleName(middlename.getText().trim());
            debtor.setExtensionName(extensionname.getText());
            debtor.setDateOfBirth(birthdate.getValue());
            debtor.setEmail(email.getText());
            debtor.setPhoneNumber(phonenumber.getText().trim());
            debtor.setAddress(address);
            debtor.setGender(genderComboBox.getSelectionModel().getSelectedIndex());
            debtor.setStatus(civilStatusComboBox.getSelectionModel().getSelectedIndex());
            debtor.setjobName(occupation.getText().trim());
            debtor.setTinid(tinid.getText());
            debtor.setmonthly_income(Integer.parseInt(monthlyincome.getText()));
            if (middlename.getText().trim().isBlank() || middlename.getText().trim().isEmpty()) {
                debtor.setFullname(firstname.getText().trim() + " " + middlename.getText().trim() + " "
                        + lastname.getText().trim() + " " + extensionname.getText());
            } else {

                debtor.setFullname(
                        firstname.getText().trim() + " " + middlename.getText().trim().charAt(0) + " "
                                + lastname.getText().trim() + " " + extensionname.getText());
            }
            DebtorDAO.update(debtor);
        });

    }

    private void _details() {
        firstname.setText(debtor.getFirstname());
        middlename.setText(debtor.getMiddleName());
        lastname.setText(debtor.getLastname());
        extensionname.setText(debtor.getExtensionName());
        birthdate.setValue(debtor.getbirthDate());
        genderComboBox.getSelectionModel().select(debtor.getGender());
        civilStatusComboBox.getSelectionModel().select(debtor.getStatus());
        phonenumber.setText(debtor.getPhoneNumber());
        email.setText(debtor.getEmail());
        String[] address = debtor.getAddress().split(", ");
        if (address.length == 4) {
            postalcode.setText(address[0]);
            barangay.setText(address[1]);
            town_city.setText(address[2]);
            province.setText(address[3]);
        } else {
            barangay.setText(address[0]);
            town_city.setText(address[1]);
            province.setText(address[2]);
        }
        monthlyincome.setText(String.valueOf(debtor.getmonthly_income()));
        tinid.setText(debtor.getTinid());
        occupation.setText(debtor.getJobName());
    }

    private void _TextFieldslisteners() {
        firstname.textProperty().addListener((ob, ov, nv) -> {
            if (nv.length() <= 45) {
                firstname.setText(nv);
            } else {
                firstname.setText(ov);
            }
        });
        middlename.textProperty().addListener((ob, ov, nv) -> {
            if (nv.length() <= 45) {
                middlename.setText(nv);
            } else {
                middlename.setText(ov);
            }
        });
        lastname.textProperty().addListener((ob, ov, nv) -> {
            if (nv.length() <= 45) {
                lastname.setText(nv);
            } else {
                lastname.setText(ov);
            }
        });
        phonenumber.textProperty().addListener((ob, ov, nv) -> {
            if (nv.length() <= 20) {
                phonenumber.setText(nv);
            } else {
                phonenumber.setText(ov);
            }
        });
        barangay.textProperty().addListener((ob, ov, nv) -> {
            if (nv.length() <= 45) {
                barangay.setText(nv);
            } else {
                barangay.setText(ov);
            }
        });
        town_city.textProperty().addListener((ob, ov, nv) -> {
            if (nv.length() <= 45) {
                town_city.setText(nv);
            } else {
                town_city.setText(ov);
            }
        });
        province.textProperty().addListener((ob, ov, nv) -> {
            if (nv.length() <= 45) {
                province.setText(nv);
            } else {
                province.setText(ov);
            }
        });
        occupation.textProperty().addListener((ob, ov, nv) -> {
            if (nv.length() <= 45) {
                occupation.setText(nv);
            } else {
                occupation.setText(ov);
            }
        });
        monthlyincome.textProperty().addListener((observable, oldValue,
                newValue) -> {

            if (!newValue.matches("\\d{0,14}")) {
                monthlyincome.setText(oldValue);
            }

        });
        extensionname.textProperty().addListener((ob, ov, nv) -> {

            if (nv.length() <= 5) {
                extensionname.setText(nv);
            } else {
                extensionname.setText(ov);
            }
            String newValueWithoutSpaces = nv.replaceAll("\\s", "");
            if (!nv.equals(newValueWithoutSpaces)) {
                extensionname.setText(newValueWithoutSpaces);
            }
        });
        email.textProperty().addListener((ob, ov, nv) -> {
            if (nv.length() <= 45) {
                email.setText(nv);
            } else {
                email.setText(ov);
            }
            String newValueWithoutSpaces = nv.replaceAll("\\s", "");
            if (!nv.equals(newValueWithoutSpaces)) {
                email.setText(newValueWithoutSpaces);
            }
        });
        tinid.textProperty().addListener((ob, ov, nv) -> {
            if (nv.length() <= 30) {
                tinid.setText(nv);
            } else {
                tinid.setText(ov);
            }
            String newValueWithoutSpaces = nv.replaceAll("\\s", "");
            if (!nv.equals(newValueWithoutSpaces)) {
                tinid.setText(newValueWithoutSpaces);
            }
        });
        postalcode.textProperty().addListener((ob, ov, nv) -> {
            if (nv.length() <= 20) {
                postalcode.setText(nv);
            } else {
                postalcode.setText(ov);
            }
            String newValueWithoutSpaces = nv.replaceAll("\\s", "");
            if (!nv.equals(newValueWithoutSpaces)) {
                postalcode.setText(newValueWithoutSpaces);
            }
        });

    }
}
