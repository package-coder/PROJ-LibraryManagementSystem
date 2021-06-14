package sample.model;

import javafx.beans.property.SimpleStringProperty;

public class LibrarianAccount extends PersonAccount{

    protected LibrarianAccount(SimpleStringProperty username, SimpleStringProperty password, SimpleStringProperty firstName, SimpleStringProperty lastName, SimpleStringProperty email, SimpleStringProperty address, SimpleStringProperty contactNumber) {
        super(username, password, firstName, lastName, email, address, contactNumber);
    }
}
