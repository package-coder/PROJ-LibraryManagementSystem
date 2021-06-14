package sample.dbutil;

import javafx.collections.ObservableList;
import sample.classes.ICreateAccountByType;
import sample.classes.ICreateTransactionRecordByType;
import sample.classes.Request;
import sample.model.Account;
import sample.model.Book;
import sample.model.Student;
import sample.model.TransactionRecord;

import java.util.List;

public interface DBTemplate {

    Request requestLogin(String username, String password, String accountType);

    Account login(String username, String password, ICreateAccountByType iCreateAccountByType);

    TransactionRecord getTransactionRecordByID(Integer transactionID, Integer studentID, ICreateTransactionRecordByType type);

    List<Book> getBookList();

    Book getBookByID(Integer ISBN);

    Integer getTotalAvailableBookCopies(Integer ISBN);

    Student getStudentByID(Integer studentID);

    Integer getTransactionRecordCounter();

    void insertBook(Book book);

    boolean containsBook(int bookSerial);

    boolean isBookAvailable(int bookSerial);

    boolean isStudentCanBorrow(int studentID);

    void insertStudent(Student student);

}
