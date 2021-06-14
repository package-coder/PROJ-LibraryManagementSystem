package sample.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class BookBorrowTransactionRecord extends TransactionRecord{


    public BookBorrowTransactionRecord(Integer transactionID, String bookISBN, Integer studentID, Integer numberOfBookCopies, LocalDate releasedDate, LocalDate dueDate, Boolean isBookReturned) {
        super(transactionID, bookISBN, studentID, numberOfBookCopies, releasedDate, dueDate, isBookReturned);
    }
}
