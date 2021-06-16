package sample.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import sample.dbutil.DBController;
import sample.model.Book;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.function.Function;



//TODO
// Icons, Progress Bar, Search Filters

public class BookCatalogSceneController extends CatalogSceneController implements Initializable {


    @FXML private TableView<Book> bookTableView;

    @FXML private TableColumn<Book, Number> bookSerialTableColumn;
    @FXML private TableColumn<Book, String> bookTitleTableColumn;
    @FXML private TableColumn<Book, String> bookPublisherTableColumn;
    @FXML private TableColumn<Book, Number> bookCopiesTableColumn;
    @FXML private TableColumn<Book, String> bookEditionTableColumn;
    @FXML private TableColumn<Book, String> bookAuthorTableColumn;
    @FXML private TableColumn<Book, LocalDate> bookDatePublishedTableColumn;


    private final BooleanProperty dataSyncProperty = new SimpleBooleanProperty();
    private TableView<Book> tempDataTableView = new TableView<>();

    public BookCatalogSceneController() {
        super("Book Catalog", "../view/fxml/bookCatalogScene.fxml");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        getSearchTextField().textProperty().addListener((args, oldV, newV) -> {
            getResetButton().setDisable(newV.isEmpty());

            if(newV.isEmpty()){
                bookTableView.setItems(tempDataTableView.getItems());
            }
            else {
                tempDataTableView.setItems(bookTableView.getItems());

                var list = search(newV, tempDataTableView.getItems());
                bookTableView.setItems(list);
            }
        });

        initializeTable();

        super.initialize(location, resources);
    }

    @Override
    public void notifySyncDataThread() {

        var items = DBController.getBookList();
        bookTableView.setItems(FXCollections.observableList(items));

    }


    private ObservableList<Book> search(String text, ObservableList<Book> bookList){
        ObservableList<Book> list = FXCollections.observableArrayList();

        return null;
    }


    private void initializeTable(){
        setupCellValueFactory(bookSerialTableColumn, Book::serialIDProperty);
        setupCellValueFactory(bookTitleTableColumn, Book::titleProperty);
        setupCellValueFactory(bookPublisherTableColumn, Book::publisherProperty);
        setupCellValueFactory(bookCopiesTableColumn, Book::bookCopiesProperty);
        setupCellValueFactory(bookEditionTableColumn, Book::editionProperty);
        setupCellValueFactory(bookAuthorTableColumn, Book::authorProperty);
        setupCellValueFactory(bookDatePublishedTableColumn, Book::datePublishedProperty);
    }

    private <T> void setupCellValueFactory(TableColumn<Book, T> column, Function<Book, ObservableValue<T>> mapper) {
        column.setCellValueFactory((TableColumn.CellDataFeatures<Book, T> param)
                -> mapper.apply(param.getValue()));
    }

}
