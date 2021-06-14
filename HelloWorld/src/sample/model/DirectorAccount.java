package sample.model;

import javafx.beans.property.SimpleStringProperty;

public class DirectorAccount extends PersonAccount {

    protected DirectorAccount(SimpleStringProperty username, SimpleStringProperty password, SimpleStringProperty firstName, SimpleStringProperty lastName, SimpleStringProperty email, SimpleStringProperty address, SimpleStringProperty contactNumber) {
        super(username, password, firstName, lastName, email, address, contactNumber);
    }
}
