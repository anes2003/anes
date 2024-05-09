package dev.anes.layout.Modal.ownerAccount.login;

import java.io.IOException;

import org.kordamp.ikonli.javafx.FontIcon;

import dev.anes.App;
import dev.anes.core.model.Owner;
import dev.anes.service.ui.Modal;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LoginController {
    @FXML
    private ImageView favicon;
    @FXML
    private FontIcon invalidusername;
    @FXML
    private FontIcon invalidpassword;
    @FXML
    private FontIcon eyepassword;
    @FXML
    private FontIcon closeeyepassword;
    @FXML
    private Button doneButton;
    @FXML
    private TextField nameField;
    @FXML
    private PasswordField passField;
    @FXML
    private TextField passeyeField;
    @FXML
    private TableView<Owner> loginTable;
    @FXML
    private TableColumn<Owner, String> nameColumn;

    @FXML
    private void handledonebutton() throws IOException {
        handlelogindone();
    }

    @FXML
    private void handleRegister() throws IOException {
        Modal.load_Register(app);
    }

    @FXML
    private void handledone() {
        Modal.close(app);
    }

    private App app;
    private Owner login;
    private Image image;
    private FilteredList<Owner> loginFilterList;
    private SortedList<Owner> loginSortedList;

    public void load(App app) {
        this.app = app;
        _load_fields();
        _load_bindings();
        _load_listeners();
    }

    private void _load_fields() {
        String FAVICON = App.class.getResource("assets/img/fa.png").toExternalForm();
        image = new Image(FAVICON);
        favicon.setImage(image);
        favicon.setFitHeight(432);
        favicon.setFitWidth(460);
        nameColumn.setCellValueFactory(celldata -> celldata.getValue().userNameProperty());

        loginFilterList = new FilteredList<>(app.getLoginMasterList());
        loginSortedList = new SortedList<>(loginFilterList);
        loginTable.setItems(loginSortedList);

        _TextFields();
        _passwordeye();
    }

    private void _load_bindings() {

        invalidusername.setVisible(false);
        invalidpassword.setVisible(false);
        eyepassword.setVisible(false);

    }

    private void _load_listeners() {
        _TextFieldslisteners();
        nameField.textProperty().addListener((o, ov, nv) -> {

            loginFilterList.setPredicate(tblthesis -> {
                if (nv == null || nv.isEmpty()) {
                    return true;
                }
                // lower case all possible
                String lowerCaseFilter = nv.toLowerCase();

                if (tblthesis.getUsername().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }

                return false;

            });
        });

        loginTable.getSelectionModel().selectedItemProperty().addListener((obserble, oldValue, newValue) -> {
            login = newValue;

        });

    }

    private static String passwString = "1";

    private void handlelogindone() {

        if (passwString.equals("1")) {
            passeyeField.setText(passField.getText());
        } else {
            passField.setText(passeyeField.getText());
        }

        loginTable.getSelectionModel().selectFirst();

        String pass = passField.getText();
        String name = nameField.getText();
        nameField.setStyle("");
        passField.setStyle("");
        passeyeField.setStyle("");
        invalidusername.setVisible(false);
        invalidpassword.setVisible(false);
        if (nameField.getText().isBlank()) {

            _namefield();
            nameField.setPromptText("Name Must Not Be Empty");
            invalidusername.setVisible(true);

        } else if (login == null) {

            invalidusername.setVisible(true);
            nameField.setPromptText("Invalid UserName");
            _namefield();

        } else if (passField.getText().isBlank()) {
            _passfield();
            invalidpassword.setVisible(true);

        } else {

            if (!name.equals(login.getUsername())) {

                _namefield();
                nameField.setPromptText("Invalid UserName");
                invalidusername.setVisible(true);

            }
            if (name.equals(login.getUsername())) {
                invalidpassword.setVisible(true);
                _passfield();

                if (pass.equals(login.getPassword())) {

                    Modal.close(app);

                }
            }

        }

    }

    private void _passfield() {
        passField.setStyle(" -fx-border-color: rgb(207, 0, 0); -fx-border-radius: 13px");
        passeyeField.setStyle("-fx-border-color: rgb(207, 0, 0); -fx-border-radius: 13px");
        passeyeField.setPromptText("Invalid Password");
        passField.setText("");
        passeyeField.setText("");
        passField.setPromptText("Invalid Password");
    }

    private void _namefield() {
        nameField.setStyle(" -fx-border-color: rgb(207, 0, 0); -fx-border-radius: 13px");
        nameField.setText("");

    }

    private void _passwordeye() {
        eyepassword.setOnMouseClicked(event -> {
            closeeyepassword.setVisible(true);
            eyepassword.setVisible(false);
            passField.setVisible(true);
            passwString = "1";
            passField.setText(passeyeField.getText());
        });
        closeeyepassword.setOnMouseClicked(event -> {
            closeeyepassword.setVisible(false);
            eyepassword.setVisible(true);
            passField.setVisible(false);
            passeyeField.setText(passField.getText());
            passwString = "2";
        });
    }

    private void _TextFields() {
        nameField.setPromptText("");
        passField.setPromptText("");

        nameField.setOnMouseClicked(event -> {
            nameField.setStyle("");
            invalidusername.setVisible(false);
        });
        passField.setOnMouseClicked(event -> {
            passField.setStyle("");
            passeyeField.setStyle("");
            invalidpassword.setVisible(false);
        });
        passeyeField.setOnMouseClicked(event -> {
            passField.setStyle("");
            passeyeField.setStyle("");
            invalidpassword.setVisible(false);
        });
        nameField.setOnAction(event -> {

            passField.requestFocus();
        });
        passField.setOnAction(event -> {

            doneButton.requestFocus();
        });
    }

    private void _TextFieldslisteners() {
        nameField.textProperty().addListener((ob, ov, nv) -> {
            String newValueWithoutSpaces = nv.replaceAll("\\s", "");
            if (!nv.equals(newValueWithoutSpaces)) {
                nameField.setText(newValueWithoutSpaces);
            }
        });
        passField.textProperty().addListener((ob, ov, nv) -> {
            String newValueWithoutSpaces = nv.replaceAll("\\s", "");
            if (!nv.equals(newValueWithoutSpaces)) {
                passField.setText(newValueWithoutSpaces);
            }
        });
        passeyeField.textProperty().addListener((ob, ov, nv) -> {
            String newValueWithoutSpaces = nv.replaceAll("\\s", "");
            if (!nv.equals(newValueWithoutSpaces)) {
                passeyeField.setText(newValueWithoutSpaces);
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
