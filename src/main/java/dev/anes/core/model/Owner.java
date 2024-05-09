package dev.anes.core.model;

import dev.anes.server.OwnerDAO;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Owner {
    private IntegerProperty ownerID;
    private StringProperty userName;
    private StringProperty password;
    private StringProperty firstName;
    private StringProperty lastName;
    private IntegerProperty gender;
    private StringProperty email;
    private StringProperty phoneNumber;

    public Owner(String userName, String password, String firstName,
            String lastName, int gender, String email, String phoneNumber) {
        
                this(OwnerDAO.getLastId() + 1, userName,
                password, firstName, lastName, gender, email, phoneNumber);

    }

    public Owner(int ownerID, String userName, String password, String firstName,
            String lastName, int gender, String email, String phoneNumber) {
        this.ownerID = new SimpleIntegerProperty(ownerID);
        this.userName = new SimpleStringProperty(userName);
        this.password = new SimpleStringProperty(password);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.gender = new SimpleIntegerProperty(gender);
        this.email = new SimpleStringProperty(email);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
    }

    // getters
    public int getOwnerID() {
        return ownerID.get();
    }

    public String getUsername() {
        return userName.get();
    }

    public String getPassword() {
        return password.get();
    }

    public String getFirstname() {
        return firstName.get();
    }

    public String getLastname() {
        return lastName.get();
    }

    public int getGender() {
        return gender.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    // setters
    public void setOwnerID(int ownerID) {
        this.ownerID.set(ownerID);
    }

    public void setUsername(String userName) {
        this.userName.set(userName);
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public void setFirstname(String firstName) {
        this.firstName.set(firstName);
    }

    public void setLastname(String lastName) {
        this.lastName.set(lastName);
    }

    public void setGender(int gender) {
        this.gender.set(gender);
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    // properties
    public IntegerProperty ownerIDProperty() {
        return ownerID;
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public IntegerProperty genderProperty() {
        return gender;
    }

    public StringProperty emailProperty() {
        return email;
    }

    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }
}
