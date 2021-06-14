package sample.dbutil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.classes.ICreateAccountByType;
import sample.classes.ICreateTransactionRecordByType;
import sample.classes.Request;
import sample.classes.TrackProgress;
import sample.model.Account;
import sample.model.Book;
import sample.model.Student;
import sample.model.TransactionRecord;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBHandler implements DBTemplate {

    @Override
    public Request requestLogin(String username, String password, String accountType) {

        var query =   """
                    SELECT Username, LibraryRole,
                        CASE
                            WHEN Password = ? THEN 1 ELSE 0
                        END isPasswordMatched
                    FROM account
                    WHERE Username = ? AND LibraryRole = ?;
                    """;

        try(var connection = DBConnection.getConnection()) {

            if(!validateConnection(connection, "DBHandler.requestLogin"))
                return null;

            var preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, password);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, accountType);

            var resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
                return resultSet.getBoolean("isPasswordMatched")
                        ? Request.SUCCESS : Request.INVALID_PASSWORD;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Request.INVALID_CREDENTIALS;
    }

    @Override
    public Account login(String username, String password, ICreateAccountByType iCreateAccountByType){

        var sql =   """
                    SELECT *
                    FROM account
                    WHERE Username = ? AND Password = ?;
                    """;

        try(var connection = DBConnection.getConnection()){

            if(!validateConnection(connection, "DBHandler.login"))
                return null;

            var preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            var resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                var firstname = resultSet.getString("FirstName");
                var lastname = resultSet.getString("LastName");
                var email = resultSet.getString("Email");
                var address = resultSet.getString("Address");
                var contactNumber = resultSet.getString("ContactNumber");

                return iCreateAccountByType.createAccount(username, password, firstname, lastname,
                        email, address, contactNumber);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return  null;
    }

    @Override
    public TransactionRecord getTransactionRecordByID(Integer transactionID, Integer studentID, ICreateTransactionRecordByType type) {

        var sql =   """
                    SELECT *
                    FROM transactionRecord
                    WHERE TransactionID = ? AND StudentID = ?;
                    """;

        try(var connection = DBConnection.getConnection()){

            if(!validateConnection(connection, "DBHandler.getTransactionRecordByID"))
                return null;

            var preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, transactionID);
            preparedStatement.setInt(2, studentID);

            var resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){

                var bookISBN = resultSet.getString("BookISBN");
                var numberOfBookCopies = resultSet.getInt("NumberOfBookCopies");
                var releasedDate = resultSet.getDate("ReleasedDate");
                var dueDate = resultSet.getDate("DueDate");
                var bookReturnDate = resultSet.getDate("BookReturnDate");
                var isBookReturned = resultSet.getBoolean("isBookReturned");

                return type.createTransactionRecord(transactionID, bookISBN, studentID, numberOfBookCopies,
                        releasedDate.toLocalDate(), dueDate.toLocalDate(), isBookReturned, bookReturnDate.toLocalDate());
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    public List<Book> getBookList() {
        var sql =   """
                        SELECT *
                        FROM book;
                        """;

        try(var connection = DBConnection.getConnection()){

            if(!validateConnection(connection, "DBHandler.getBookList"))
                return null;

            var preparedStatement = connection.prepareStatement(sql);
            var resultSet = preparedStatement.executeQuery();

            var bookList = new ArrayList<Book>();

            while (resultSet.next()){
                var bookISBN = resultSet.getInt("ISBN");
                var title = resultSet.getString("Title");
                var author = resultSet.getString("Author");
                var publisher = resultSet.getString("Publisher");
                var edition = resultSet.getString("Edition");
                var bookCopies = resultSet.getInt("BookCopies");
                var dataPublished = resultSet.getDate("DatePublished").toLocalDate();

                var book = new Book(bookISBN, title, edition, author, publisher, bookCopies, dataPublished);
                bookList.add(book);
            }

            return bookList;

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Book getBookByID(Integer ISBN){
        var sql =   """
                    SELECT *
                    FROM book
                    WHERE ISBN = ?;
                    """;

        try(var connection = DBConnection.getConnection()){

            if(!validateConnection(connection, "DBHandler.getBook"))
                return null;

            var preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, ISBN);

            var resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                var bookISBN = resultSet.getInt("ISBN");
                var title = resultSet.getString("Title");
                var author = resultSet.getString("Author");
                var publisher = resultSet.getString("Publisher");
                var edition = resultSet.getString("Edition");
                var bookCopies = resultSet.getInt("BookCopies");
                var dataPublished = resultSet.getDate("DatePublished").toLocalDate();

                var book = new Book(bookISBN, title, edition, author, publisher, bookCopies, dataPublished);
                return book;
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Integer getTotalAvailableBookCopies(Integer ISBN){
        var sql =   """
                    SELECT TotalAvailableBook
                    FROM book
                    WHERE ISBN = ?;
                    """;

        try(var connection = DBConnection.getConnection()){

            if(!validateConnection(connection, "DBHandler.getTotalAvailableBook"))
                return null;

            var preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, ISBN);

            var resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                return resultSet.getInt("TotalAvailableBook");

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Integer getTransactionRecordCounter(){
        var sql =   """
                    SELECT COUNT(*) AS total
                    FROM book;
                    """;

        try(var connection = DBConnection.getConnection()){

            if(!validateConnection(connection, "DBHandler.getBook"))
                return null;

            var preparedStatement = connection.prepareStatement(sql);

            var resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                return resultSet.getInt("total");

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    @Override
    public Student getStudentByID(Integer studentID){
        var sql =   """
                    SELECT *
                    FROM student
                    WHERE StudentID = ?;
                    """;

        try(var connection = DBConnection.getConnection()){

            if(!validateConnection(connection, "DBHandler.getBook"))
                return null;

            var preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, studentID);

            var resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                var firstName = resultSet.getString("FirstName");
                var lastName = resultSet.getString("LastName");
                var email = resultSet.getString("Email");
                var yearLevel = resultSet.getInt("YearLevel");
                var department = resultSet.getString("Department");
                var address = resultSet.getString("Address");
                var gender = resultSet.getString("Gender");
                var contactNumber = resultSet.getString("ContactNumber");
                var course = resultSet.getString("Course");
                var age = resultSet.getInt("Age");


                var student = new Student(studentID, firstName, lastName, email, course, department,
                        yearLevel, age, gender, contactNumber);

                return student;
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void insertBook(Book book) {
        var sql =   """
                    INSERT INTO book (Title, Publisher, BookCopies, Edition, Author, DatePublished) 
                    VALUES (?, ?, ?, ?, ?, ?);
                    """;

        try(var connection = DBConnection.getConnection()){

            if(!validateConnection(connection, "DBHandler.getBookList"))
                return;

            var preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getPublisher());
            preparedStatement.setInt(3, book.getBookCopies());
            preparedStatement.setString(4, book.getEdition());
            preparedStatement.setString(5, book.getAuthor());
            preparedStatement.setDate(6, Date.valueOf(book.getDatePublished()));

            preparedStatement.executeUpdate();

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean containsBook(int bookSerial) {
        var sql =   """
                    SELECT ISBN
                    FROM book
                    WHERE ISBN = ?;
                    """;

        try(var connection = DBConnection.getConnection()){

            if(!validateConnection(connection, "DBHandler.containsBook"))
                return false;

            var preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, bookSerial);

            var resultSet = preparedStatement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean isBookAvailable(int bookSerial) {

        var sql =   """
                    SELECT ISBN, BookCopies
                    FROM book
                    WHERE ISBN = ? AND BookCopies > 0;
                    """;

        try(var connection = DBConnection.getConnection()){

            if(!validateConnection(connection, "DBHandler.isBookAvailable"))
                return false;

            var preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, bookSerial);

            var resultSet = preparedStatement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean isStudentCanBorrow(int studentID) {
        var sql =   """
                    SELECT COUNT(*) AS total
                    FROM transactionRecord
                    WHERE StudentID = ?;
                    """;

        //TODO return value for student not found

        final var MAX_BORROW = 2;

        try(var connection = DBConnection.getConnection()){

            if(!validateConnection(connection, "DBHandler.canBorrow"))
                return false;

            var preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, studentID);

            var resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
                return resultSet.getInt("total") <= MAX_BORROW;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    @Override
    public void insertStudent(Student student) {

    }




    private boolean validateConnection(Connection connection, String msg){
        if (connection == null){
            System.err.println(msg);
            System.err.println("DBConnection.getConnection is null");
            return false;
        }

        return true;
    }

    public static class GetBookProgress extends TrackProgress {
        public ObservableList<Book> getBooksFromDB(){
            var sql =   """
                        SELECT *
                        FROM book;
                        """;

            try(var connection = DBConnection.getConnection()){

                var preparedStatement = connection.prepareStatement(sql);
                var resultSet = preparedStatement.executeQuery();

                ObservableList<Book> bookList = FXCollections.observableArrayList();


                for(int i = 0; resultSet.next(); i++){

                    var bookISBN = resultSet.getInt("BookISBN");
                    var title = resultSet.getString("Title");
                    var author = resultSet.getString("Author");
                    var publisher = resultSet.getString("Publisher");
                    var edition = resultSet.getString("Edition");
                    var bookCopies = resultSet.getInt("BookCopies");
                    var dataPublished = resultSet.getDate("DatePublished").toLocalDate();

                    var book = new Book(bookISBN, title, edition, author, publisher, bookCopies, dataPublished);
                    bookList.add(book);

                    setProgress(1.0*i / 100);
                }

                return bookList;

            }catch (SQLException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}