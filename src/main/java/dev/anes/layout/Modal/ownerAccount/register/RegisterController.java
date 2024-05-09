package dev.anes.layout.Modal.ownerAccount.register;


import org.kordamp.ikonli.javafx.FontIcon;

import dev.anes.App;
import dev.anes.core.model.Owner;
import dev.anes.core.model.forenum.Gender;
import dev.anes.server.OwnerDAO;
import dev.anes.service.ui.Modal;
import dev.anes.service.ui.Modall;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {
    @FXML
    private TextField userNameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmpasswordField;
    @FXML
    private TextField openeyepasswordField;
    @FXML
    private TextField openeyeconfirmpasswordField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private ComboBox<Gender> genderComboBox;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phonenumberField;
    @FXML
    private FontIcon eyepassword;
    @FXML
    private FontIcon closeeyepassword;
    @FXML
    private FontIcon eyepassword1;
    @FXML
    private FontIcon closeeyepassword1;
    @FXML
    private Button doneButton;
    private static String passwString ="1";
    private static String passwString1="1";
    @FXML
    private void handledone() {
       
        String username = userNameField.getText();
        String password = passwordField.getText();
        String name = nameField.getText();
        String lastname = lastNameField.getText();
        String email = emailField.getText();
        String phonenumber = phonenumberField.getText();
        int  gender =genderComboBox.getSelectionModel().getSelectedIndex();

        System.out.println(gender);
        if(passwString.equals("1")){
            openeyepasswordField.setText(passwordField.getText());
        }else {
            passwordField.setText(openeyepasswordField.getText());
        }
        if(passwString1.equals("1")){
            openeyeconfirmpasswordField.setText(confirmpasswordField.getText());
        }else {
            confirmpasswordField.setText(openeyeconfirmpasswordField.getText());
        }
          
        user1 = null;
        app.getLoginMasterList().forEach(user -> {
            if (user.getUsername().equalsIgnoreCase(userNameField.getText().trim())) {

                userNameField.clear();

                user1 = user.getUsername();
            }

        });

        if (user1 == null) {
            if (username.isEmpty() || username.isBlank() || username == null) {
                System.out.println("UserName Empty");
            } else if (name.isEmpty() || name.isBlank() || name == null) {
                System.out.println("FirstName Empty");
            } else if (lastname.isEmpty() || lastname.isBlank() || lastname == null) {
                System.out.println("LastName Empty");
            } else if (genderComboBox.getSelectionModel().getSelectedItem() == null) {
                System.out.println("Gender is empty");
            } else if (email.isEmpty() || email.isBlank() || email == null) {
                System.out.println("Email Empty");
            } else if (phonenumber.isEmpty() || phonenumber.isBlank() || phonenumber == null) {
                System.out.println("Phonenumber Empty");
            } else if (password.isEmpty() || password.isBlank() || password == null) {
                System.out.println("password Empty");
            }

            else if (!passwordField.getText().equals(confirmpasswordField.getText())) {

                System.out.println("Try Again Password Not Match");

            } else if (passwordField.getText().equals(confirmpasswordField.getText())) {
                System.out.println("pass");

                Owner newOwner = new Owner(username, password, name, lastname,
                      gender, email,
                        phonenumber);
                OwnerDAO.insert(newOwner);
                app.getLoginMasterList().add(newOwner);
                Modall.close(app);

            }

        } else {

            System.out.println("Already Use");
        }
    }

    private static String user1 = null;

    @FXML
    private void handleExit() {
        Modal.close(app);
    }

    private App app;

    public void load(App app) {
        this.app = app;
        _load_fields();
        _load_bindings();
        _load_listeners();

    }

    private void _load_fields() {
        genderComboBox.getItems().setAll(Gender.values());

        _TextFields();
        _passwordeye();
    }

    private void _load_bindings() {
        eyepassword.setVisible(false);
        eyepassword1.setVisible(false);
    }

    private void _load_listeners() {
        _TextFields_listeners();
      
    }

    private void _TextFields() {

        userNameField.setOnAction(event -> {

            passwordField.requestFocus();
        });
       
        nameField.setOnAction(event -> {

            lastNameField.requestFocus();
        });

        genderComboBox.setOnAction(event -> {

            emailField.requestFocus();
        });
        emailField.setOnAction(event -> {

            phonenumberField.requestFocus();
        });
        phonenumberField.setOnAction(event -> {

            doneButton.requestFocus();
        });

    }
    private void _passwordeye() {
        eyepassword.setOnMouseClicked(event -> {
            closeeyepassword.setVisible(true);
            eyepassword.setVisible(false);
            passwordField.setVisible(true);
                passwString="1";
                passwordField.setText(openeyepasswordField.getText());
        });
        closeeyepassword.setOnMouseClicked(event -> {
            closeeyepassword.setVisible(false);
            eyepassword.setVisible(true);
            passwordField.setVisible(false);
            openeyepasswordField.setText(passwordField.getText());
            passwString="2";
        });
        eyepassword1.setOnMouseClicked(event -> {
            closeeyepassword1.setVisible(true);
            eyepassword1.setVisible(false);
            confirmpasswordField.setVisible(true);
                passwString1="1";
                confirmpasswordField.setText(openeyeconfirmpasswordField.getText());
        });
        closeeyepassword1.setOnMouseClicked(event -> {
            closeeyepassword1.setVisible(false);
            eyepassword1.setVisible(true);
            confirmpasswordField.setVisible(false);
            openeyeconfirmpasswordField.setText(confirmpasswordField.getText());
            passwString1="2";
        });
    }
    private void _TextFields_listeners(){
        nameField.textProperty().addListener((ob,ov,nv)->{
            String newValueWithoutSpaces = nv.replaceAll("\\s", "");
            if (!nv.equals(newValueWithoutSpaces)) {
                nameField.setText(newValueWithoutSpaces);
            }
        });
        lastNameField.textProperty().addListener((ob,ov,nv)->{
            String newValueWithoutSpaces = nv.replaceAll("\\s", "");
            if (!nv.equals(newValueWithoutSpaces)) {
                lastNameField.setText(newValueWithoutSpaces);
            }
        });
        // TODO -sdadsads
        emailField.textProperty().addListener((ob,ov,nv)->{
            String newValueWithoutSpaces = nv.replaceAll("\\s", "");
            if (!nv.equals(newValueWithoutSpaces)) {
                emailField.setText(newValueWithoutSpaces);
            }
        });
        userNameField.textProperty().addListener((ob,ov,nv)->{
            String newValueWithoutSpaces = nv.replaceAll("\\s", "");
            if (!nv.equals(newValueWithoutSpaces)) {
                userNameField.setText(newValueWithoutSpaces);
            }
        });
        passwordField.textProperty().addListener((ob,ov,nv)->{
            String newValueWithoutSpaces = nv.replaceAll("\\s", "");
            if (!nv.equals(newValueWithoutSpaces)) {
                passwordField.setText(newValueWithoutSpaces);
            }
        });
        openeyepasswordField.textProperty().addListener((ob,ov,nv)->{
            String newValueWithoutSpaces = nv.replaceAll("\\s", "");
            if (!nv.equals(newValueWithoutSpaces)) {
                openeyepasswordField.setText(newValueWithoutSpaces);
            }
        });
        confirmpasswordField.textProperty().addListener((ob,ov,nv)->{
            String newValueWithoutSpaces = nv.replaceAll("\\s", "");
            if (!nv.equals(newValueWithoutSpaces)) {
                confirmpasswordField.setText(newValueWithoutSpaces);
            }
        });
        openeyeconfirmpasswordField.textProperty().addListener((ob,ov,nv)->{
            String newValueWithoutSpaces = nv.replaceAll("\\s", "");
            if (!nv.equals(newValueWithoutSpaces)) {
                openeyeconfirmpasswordField.setText(newValueWithoutSpaces);
            }
        });

        // ----
        phonenumberField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                phonenumberField.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if (newValue.length() > 11) {
                phonenumberField.setText(oldValue);
            }
        });
    }
    public String toString(int Integer) {
        return String.valueOf(Integer);
    }

    public int toInteger(String str) {
        return Integer.valueOf(str);
    }
}
