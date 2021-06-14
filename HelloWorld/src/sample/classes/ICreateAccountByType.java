package sample.classes;

import sample.model.Account;

public interface ICreateAccountByType {
    Account createAccount(String username, String password, String firstName, String lastName,
                          String email, String address, String contactNumber);
}
