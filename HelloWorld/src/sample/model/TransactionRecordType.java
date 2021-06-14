package sample.model;

import sample.classes.ICreateTransactionRecordByType;

import java.time.LocalDate;

public enum TransactionRecordType implements ICreateTransactionRecordByType {
    BOOK_BORROW_TRANSACTION("Book Borrow Transaction"){
        @Override
        public TransactionRecord createTransactionRecord(Integer transactionID, String bookISBN,
                                                         Integer studentID, Integer numberOfBookCopies,
                                                         LocalDate releasedDate, LocalDate dueDate,
                                                         Boolean isBookReturned, LocalDate returnedDate) {

            return new BookBorrowTransactionRecord(transactionID, bookISBN, studentID,
                    numberOfBookCopies, releasedDate, dueDate, isBookReturned);
        }
    },

    BOOK_RETURN_TRANSACTION("Book Return Transaction"){
        @Override
        public TransactionRecord createTransactionRecord(Integer transactionID, String bookISBN,
                                                         Integer studentID, Integer numberOfBookCopies,
                                                         LocalDate releasedDate, LocalDate dueDate,
                                                         Boolean isBookReturned, LocalDate returnedDate) {

            return new BookReturnTransactionRecord(transactionID, bookISBN, studentID,
                    numberOfBookCopies, releasedDate, dueDate, isBookReturned, returnedDate);
        }
    };

    private final String message;
    TransactionRecordType(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }



}
