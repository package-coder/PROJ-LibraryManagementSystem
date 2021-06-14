package sample.dbutil;

import sample.classes.Request;
import sample.model.*;

import java.sql.SQLException;
import java.util.List;

public class DBController {
    private static final DBTemplate handler = new DBHandler();

    public static Request requestLogin(String username, String password, AccountType accountType){
        return handler.requestLogin(username, password, accountType.toString());
    }

    public static Account login(String username, String password, AccountType accountType){
        return handler.login(username, password, accountType);
    }

    public static TransactionRecord getTransactionRecordByID(Integer transactionID, Integer studentID, TransactionRecordType type) {
        return handler.getTransactionRecordByID(transactionID, studentID, type);
    }

    public static List<Book> getBookList(){
        return handler.getBookList();
    }

    public static void insertBook(Book book){
        handler.insertBook(book);
    }

    public static int getTransactionCounter(){
        return handler.getTransactionRecordCounter();
    }

    public static Book getBookByID(Integer ISBN){
        return handler.getBookByID(ISBN);
    }

    public static Student getStudentByID(Integer studentID){
        return handler.getStudentByID(studentID);
    }
}
