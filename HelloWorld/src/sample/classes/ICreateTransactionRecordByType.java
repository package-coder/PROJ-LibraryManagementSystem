package sample.classes;

import sample.model.TransactionRecord;

import java.time.LocalDate;

public interface ICreateTransactionRecordByType {
    TransactionRecord createTransactionRecord(Integer transactionID, String bookISBN,
                                              Integer studentID, Integer numberOfBookCopies,
                                              LocalDate releasedDate, LocalDate dueDate, Boolean isBookReturned, LocalDate returnedDate);
}
