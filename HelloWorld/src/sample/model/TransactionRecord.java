package sample.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public abstract class TransactionRecord {
    private final SimpleIntegerProperty transactionID;
    private final SimpleStringProperty bookISBN;
    private final SimpleIntegerProperty studentID;
    private final SimpleIntegerProperty numberOfBookCopies;
    private final SimpleObjectProperty<LocalDate> releasedDate;
    private final SimpleObjectProperty<LocalDate> dueDate;
    private final SimpleBooleanProperty isBookReturned;

    public TransactionRecord(Integer transactionID, String bookISBN, Integer studentID,
                             Integer numberOfBookCopies, LocalDate releasedDate, LocalDate dueDate, Boolean isBookReturned) {

        this.transactionID = new SimpleIntegerProperty(transactionID);
        this.bookISBN = new SimpleStringProperty(bookISBN);
        this.studentID = new SimpleIntegerProperty(studentID);
        this.numberOfBookCopies = new SimpleIntegerProperty(numberOfBookCopies);
        this.releasedDate = new SimpleObjectProperty<>(releasedDate);
        this.dueDate = new SimpleObjectProperty<>(dueDate);
        this.isBookReturned = new SimpleBooleanProperty(isBookReturned);
    }

    public int getTransactionID() {
        return transactionID.get();
    }

    public SimpleIntegerProperty transactionIDProperty() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID.set(transactionID);
    }

    public String getBookISBN() {
        return bookISBN.get();
    }

    public SimpleStringProperty bookISBNProperty() {
        return bookISBN;
    }

    public void setBookISBN(String bookISBN) {
        this.bookISBN.set(bookISBN);
    }

    public int getStudentID() {
        return studentID.get();
    }

    public SimpleIntegerProperty studentIDProperty() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID.set(studentID);
    }

    public int getNumberOfBookCopies() {
        return numberOfBookCopies.get();
    }

    public SimpleIntegerProperty numberOfBookCopiesProperty() {
        return numberOfBookCopies;
    }

    public void setNumberOfBookCopies(int numberOfBookCopies) {
        this.numberOfBookCopies.set(numberOfBookCopies);
    }

    public LocalDate getReleasedDate() {
        return releasedDate.get();
    }

    public SimpleObjectProperty<LocalDate> releasedDateProperty() {
        return releasedDate;
    }

    public void setReleasedDate(LocalDate releasedDate) {
        this.releasedDate.set(releasedDate);
    }

    public LocalDate getDueDate() {
        return dueDate.get();
    }

    public SimpleObjectProperty<LocalDate> dueDateProperty() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate.set(dueDate);
    }

    public boolean isIsBookReturned() {
        return isBookReturned.get();
    }

    public SimpleBooleanProperty isBookReturnedProperty() {
        return isBookReturned;
    }

    public void setIsBookReturned(boolean isBookReturned) {
        this.isBookReturned.set(isBookReturned);
    }
}
