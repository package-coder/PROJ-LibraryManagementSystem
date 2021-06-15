package sample.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import sample.classes.AccessMode;
import sample.dbutil.DBController;
import sample.model.Book;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.function.Function;



//TODO
// Icons, Progress Bar, Search Filters

public class BookCatalogSceneController extends SceneController implements Initializable {

    @FXML private VBox accessModeGroupBox;
    @FXML private Label accessModeGroupTitle;
    @FXML private Label accessModeGroupSubTitle;
    @FXML private Button deleteButton;
    @FXML private Button updateButton;
    @FXML private Button resetButton;
    @FXML private Button refreshTableDataButton;
    @FXML private MenuButton searchFilterMenuButton;
    @FXML private MenuButton sortTableMenuButton;
    @FXML private SplitMenuButton addItemButton;
    @FXML private TextField searchTextField;
    @FXML private ChoiceBox<AccessMode> accessModeChoiceBox;
    @FXML private CheckBox dataSyncCheckBox;

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

        dataSyncCheckBox.selectedProperty().bindBidirectional(dataSyncProperty);

        dataSyncProperty.addListener((observable, oldValue, newValue) -> {
            if(!newValue)
                return;

            var thread = new SyncData();
            thread.setDaemon(true);
            thread.start();
        });

        dataSyncCheckBox.selectedProperty().setValue(true);

        searchTextField.textProperty().addListener((args, oldV, newV) -> {
            resetButton.setDisable(newV.isEmpty());

            if(newV.isEmpty()){
                bookTableView.setItems(tempDataTableView.getItems());
            }
            else {
                tempDataTableView.setItems(bookTableView.getItems());

                var list = search(newV, tempDataTableView.getItems());
                bookTableView.setItems(list);
            }
        });


        initializeValues();
        initializeTable();
    }


    private ObservableList<Book> search(String text, ObservableList<Book> bookList){
        ObservableList<Book> list = FXCollections.observableArrayList();

        return null;
    }

    private void initializeValues(){

        accessModeChoiceBox.getItems().addAll(
                new ReadOnlyAccessMode(),
                new RequestOnChangeAccessMode(),
                new FullAccessMode()

        );

        accessModeChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            newValue.access();
            accessModeGroupTitle.setText(newValue.toString());
            accessModeGroupSubTitle.setText(newValue.message());
        });

        accessModeChoiceBox.getSelectionModel().select(0);

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

    private class ReadOnlyAccessMode implements AccessMode{
        @Override
        public void access() {
            accessModeGroupBox.setDisable(true);
        }

        @Override
        public String message() {
            return null;
        }

        @Override
        public String toString() {
            return "Read Only Access Mode";
        }
    }

    private class RequestOnChangeAccessMode implements AccessMode{
        @Override
        public void access() {
            var pseudo = PseudoClass.getPseudoClass("requestOnChange");

            accessModeGroupBox.pseudoClassStateChanged(pseudo, true);

        }

        @Override
        public String message() {
            return null;
        }

        @Override
        public String toString() {
            return "Request On Change Access Mode";
        }
    }

    private class FullAccessMode implements AccessMode{
        @Override
        public void access() {

        }

        @Override
        public String message() {
            return null;
        }

        @Override
        public String toString() {
            return "Full Access";
        }
    }



    private class SyncData extends Thread{

        @Override
        public void run() {
            System.out.println("Thread Start");
            while (dataSyncProperty.getValue()){
                try {
                    var items = DBController.getBookList();
                    bookTableView.setItems(FXCollections.observableList(items));

                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Thread Stop");
        }

    }



}
