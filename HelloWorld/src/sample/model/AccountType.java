package sample.model;

import sample.classes.ICreateAccountByType;
import sample.classes.ICreateWindowByAccountType;
import sample.controller.AccountWindowController;
import sample.controller.DirectorAccountWindowController;

public enum AccountType implements ICreateWindowByAccountType, ICreateAccountByType {

    DIRECTOR("Director"){
        @Override
        public AccountWindowController createWindow() {
            return new DirectorAccountWindowController();
        }

        @Override
        public Account createAccount(String username, String password, String firstName, String lastName,
                                     String email, String address, String contactNumber) {
            return null;
        }
    },

    LIBRARIAN("Librarian"){
        @Override
        public AccountWindowController createWindow() {
            return null;
        }

        @Override
        public Account createAccount(String username, String password, String firstName, String lastName,
                                     String email, String address, String contactNumber) {
            return null;
        }
    },

    CLERK("Clerk"){
        @Override
        public AccountWindowController createWindow() {
            return null;
        }

        @Override
        public Account createAccount(String username, String password, String firstName, String lastName,
                                     String email, String address, String contactNumber) {
            return null;
        }
    };


    private final String accountType;

    AccountType(String accountType) {
        this.accountType = accountType;
    }

    @Override
    public String toString() {
        return accountType;
    }
}
