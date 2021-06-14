package sample.model;

import javafx.beans.property.SimpleStringProperty;

public abstract class PersonAccount extends Account {
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty email;
    private final SimpleStringProperty address;
    private final SimpleStringProperty contactNumber;

    protected PersonAccount(SimpleStringProperty username, SimpleStringProperty password,
                            SimpleStringProperty firstName, SimpleStringProperty lastName,
                            SimpleStringProperty email, SimpleStringProperty address,
                            SimpleStringProperty contactNumber) {

        super(username, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.contactNumber = contactNumber;
    }


    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getContactNumber() {
        return contactNumber.get();
    }

    public SimpleStringProperty contactNumberProperty() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber.set(contactNumber);
    }
}
