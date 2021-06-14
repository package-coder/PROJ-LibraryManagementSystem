package sample.model;

import javafx.beans.property.SimpleStringProperty;

public abstract class Account{
    private final SimpleStringProperty username;
    private final SimpleStringProperty password;

    protected Account(SimpleStringProperty username, SimpleStringProperty password) {
        this.username = username;
        this.password = password;
    }

    private void setUsername(String username) {
        this.username.set(username);
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

}
