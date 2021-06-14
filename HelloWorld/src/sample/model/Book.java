package sample.model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class Book extends RecursiveTreeObject<Book> {

    private final SimpleIntegerProperty serialID;
    private final SimpleStringProperty title;
    private final SimpleStringProperty edition;
    private final SimpleStringProperty author;
    private final SimpleStringProperty publisher;
    private final SimpleIntegerProperty bookCopies;
    private final SimpleObjectProperty<LocalDate> datePublished;


    public Book(Integer serialID, String title, String edition,
                String author, String publisher, Integer bookCopies,
                LocalDate datePublished) {

        this.serialID = new SimpleIntegerProperty(serialID);
        this.title = new SimpleStringProperty(title);
        this.edition = new SimpleStringProperty(edition);
        this.author = new SimpleStringProperty(author);
        this.publisher = new SimpleStringProperty(publisher);
        this.bookCopies = new SimpleIntegerProperty(bookCopies);
        this.datePublished = new SimpleObjectProperty<>(datePublished);
    }

    public int getSerialID() {
        return serialID.get();
    }

    public SimpleIntegerProperty serialIDProperty() {
        return serialID;
    }

    public void setSerialID(int serialID) {
        this.serialID.set(serialID);
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getEdition() {
        return edition.get();
    }

    public SimpleStringProperty editionProperty() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition.set(edition);
    }

    public String getAuthor() {
        return author.get();
    }

    public SimpleStringProperty authorProperty() {
        return author;
    }

    public void setAuthor(String author) {
        this.author.set(author);
    }

    public String getPublisher() {
        return publisher.get();
    }

    public SimpleStringProperty publisherProperty() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher.set(publisher);
    }

    public int getBookCopies() {
        return bookCopies.get();
    }

    public SimpleIntegerProperty bookCopiesProperty() {
        return bookCopies;
    }

    public void setBookCopies(int bookCopies) {
        this.bookCopies.set(bookCopies);
    }

    public LocalDate getDatePublished() {
        return datePublished.get();
    }

    public SimpleObjectProperty<LocalDate> datePublishedProperty() {
        return datePublished;
    }

    public void setDatePublished(LocalDate datePublished) {
        this.datePublished.set(datePublished);
    }

}
