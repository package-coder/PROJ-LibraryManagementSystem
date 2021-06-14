package sample.controller;

import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.classes.Loader;
import sample.model.Account;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public abstract class AccountWindowController implements Initializable, Loader {

    private final static String SOURCE_DISPLAY = "../view/fxml/mainWindow.fxml";

    @FXML private AnchorPane menuForm;
    @FXML private ListView<String> menuItems;

    @FXML private Label lblLeftNavBarTitle;
    @FXML private Label lblWindowTitle;

    private Account account;
    private FXMLLoader loader;
    private Stage primaryStage;
    private Stage currentStage;

    private final ObservableMap<String, SceneController> items = FXCollections.observableHashMap();

    public void show() {
        primaryStage.hide();
        var stage = new Stage();

        var scene = new Scene(loader.getRoot());
        stage.setScene(scene);

        stage.setMinWidth(800.0);
        stage.setMinHeight(500.0);

        stage.show();

        setCurrentStage(stage);
        currentStage.setOnCloseRequest(e -> {
            e.consume();
            //todo Alert OnClose
            currentStage.close();
            primaryStage.show();
        });

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initializeEvents();
        initializeSubClass();

        menuItems.getSelectionModel().select(0);
    }

    @Override
    public void load(Object sender) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(SOURCE_DISPLAY));

            loader.setController(this);

            //Todo connect to loginController
            var account = (sender instanceof Account) ? (Account) sender : null;

            setAccount(account);
            setLoader(loader);

            loader.load();

        } catch (IOException e) {
            System.err.println("$$$$$$$$ Logger::MainDashboardController.load");
            System.err.println("$$$$$$$$ Logger::FILE NOT FOUND (" + SOURCE_DISPLAY + ")");
            e.printStackTrace();
        }
    }

    private void initializeEvents(){

        menuItems.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        menuItems.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            var node = items.get(newValue);
            //Todo:FIXED Viewing
            showSelectedMenuItem(node.getLoader().getRoot());
        });
    }

    private void showSelectedMenuItem(Node node){
        menuForm.getChildren().setAll(node);
    }

    private Node loadParentNode(SceneController item){

        item.load(this);

        var loader = item.getLoader();

        if(loader == null)
            return null;

        Node node = loader.getRoot();

        AnchorPane.setBottomAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);
        AnchorPane.setTopAnchor(node, 0.0);

        return node;
    }

    protected void addMenuItem(SceneController item){
        var node = loadParentNode(item);

        if(node == null)
            return;

        menuItems.getItems().add(item.getTitle());
        items.put(item.getTitle(), item);
    }

    protected void addMenuItemAll(List<SceneController> items){

        for(var item : items){
            addMenuItem(item);
        }
    }

    protected abstract void initializeSubClass();

    public ListView<String> getMenuItemsListView(){
        return menuItems;
    }

    public ObservableMap<String, SceneController> getMenuItems() {
        return items;
    }

    public FXMLLoader getLoader() {
        return loader;
    }

    public void setLoader(FXMLLoader loader) {
        this.loader = loader;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Stage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

}
