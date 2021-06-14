package sample.controller;

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

public class BookCatalogSceneController extends SceneController implements Initializable {

    @FXML private Button btnClearSearch;
    @FXML private MenuButton menuBtnSearchFilter;
    @FXML private TextField txtBoxSearch;
    @FXML private TableView<Book> dataTableView;
    @FXML private TableColumn<Book, Number> tableISBNColumn;
    @FXML private TableColumn<Book, String> tableTitleColumn;
    @FXML private TableColumn<Book, String> tablePublisherColumn;
    @FXML private TableColumn<Book, Number> tableCopiesColumn;
    @FXML private TableColumn<Book, String> tableEditionColumn;
    @FXML private TableColumn<Book, String> tableAuthorColumn;
    @FXML private TableColumn<Book, LocalDate> tableDatePublishedColumn;

    private boolean dataIsSynced = true;
    private TableView<Book> tempDataTableView = new TableView<>();

    public BookCatalogSceneController() {
        super("Book Catalog", "../view/fxml/bookCatalogScene.fxml");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        var thread = new SyncData();
        thread.setDaemon(true);

        btnClearSearch.setText("");
        btnClearSearch.setDisable(txtBoxSearch.getText().isEmpty());

        txtBoxSearch.textProperty().addListener((args, oldV, newV) -> {
            btnClearSearch.setDisable(newV.isEmpty());

            if(newV.isEmpty()){
                dataTableView.setItems(tempDataTableView.getItems());
            }
            else {
                tempDataTableView.setItems(dataTableView.getItems());

                var list = search(newV, tempDataTableView.getItems());
                dataTableView.setItems(list);
            }
        });

        initializeTable();

        thread.start();
    }


    private ObservableList<Book> search(String text, ObservableList<Book> bookList){
        ObservableList<Book> list = FXCollections.observableArrayList();

        return null;
    }


    private void initializeTable(){
        setupCellValueFactory(tableISBNColumn, Book::serialIDProperty);
        setupCellValueFactory(tableTitleColumn, Book::titleProperty);
        setupCellValueFactory(tablePublisherColumn, Book::publisherProperty);
        setupCellValueFactory(tableCopiesColumn, Book::bookCopiesProperty);
        setupCellValueFactory(tableEditionColumn, Book::editionProperty);
        setupCellValueFactory(tableAuthorColumn, Book::authorProperty);
        setupCellValueFactory(tableDatePublishedColumn, Book::datePublishedProperty);
    }

    private <T> void setupCellValueFactory(TableColumn<Book, T> column, Function<Book, ObservableValue<T>> mapper) {
        column.setCellValueFactory((TableColumn.CellDataFeatures<Book, T> param)
                -> mapper.apply(param.getValue()));
    }

    private class SyncData extends Thread{

        @Override
        public void run() {
            while (dataIsSynced){
                try {
                    var items = DBController.getBookList();
                    dataTableView.setItems(FXCollections.observableList(items));

                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
