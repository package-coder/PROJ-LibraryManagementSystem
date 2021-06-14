package sample.model;

import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;

public class BookReturnTransactionRecord extends TransactionRecord{
    private final SimpleObjectProperty<LocalDate> returnedDate;

    public BookReturnTransactionRecord(Integer transactionID, String bookISBN, Integer studentID,
                                       Integer numberOfBookCopies, LocalDate releasedDate,
                                       LocalDate dueDate, Boolean isBookReturned, LocalDate returnedDate) {
        super(transactionID, bookISBN, studentID, numberOfBookCopies, releasedDate, dueDate, isBookReturned);
        this.returnedDate = new SimpleObjectProperty<>(returnedDate);
    }


    public LocalDate getReturnedDate() {
        return returnedDate.get();
    }

    public SimpleObjectProperty<LocalDate> returnedDateProperty() {
        return returnedDate;
    }

    public void setReturnedDate(LocalDate returnedDate) {
        this.returnedDate.set(returnedDate);
    }
}
