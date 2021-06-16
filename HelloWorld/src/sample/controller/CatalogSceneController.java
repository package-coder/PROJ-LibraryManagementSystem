package sample.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import sample.classes.AccessMode;
import sample.dbutil.DBController;

import javax.management.NotificationBroadcaster;
import java.net.URL;
import java.util.ResourceBundle;

public abstract class CatalogSceneController extends SceneController implements Initializable{
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

    private final BooleanProperty dataSyncProperty = new SimpleBooleanProperty();

    protected CatalogSceneController(String title, String fxmlFilePath) {
        super(title, fxmlFilePath);
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
        dataSyncCheckBox.setSelected(true);


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

    @Override
    public void notify(boolean isSelected) {
        dataSyncCheckBox.setSelected(isSelected);
    }

    public abstract void notifySyncDataThread();


    public boolean getDataSyncProperty() {
        return dataSyncProperty.get();
    }

    public BooleanProperty dataSyncPropertyProperty() {
        return dataSyncProperty;
    }

    public VBox getAccessModeGroupBox() {
        return accessModeGroupBox;
    }

    public void setAccessModeGroupBox(VBox accessModeGroupBox) {
        this.accessModeGroupBox = accessModeGroupBox;
    }

    public Label getAccessModeGroupTitle() {
        return accessModeGroupTitle;
    }

    public void setAccessModeGroupTitle(Label accessModeGroupTitle) {
        this.accessModeGroupTitle = accessModeGroupTitle;
    }

    public Label getAccessModeGroupSubTitle() {
        return accessModeGroupSubTitle;
    }

    public void setAccessModeGroupSubTitle(Label accessModeGroupSubTitle) {
        this.accessModeGroupSubTitle = accessModeGroupSubTitle;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
    }

    public Button getUpdateButton() {
        return updateButton;
    }

    public void setUpdateButton(Button updateButton) {
        this.updateButton = updateButton;
    }

    public Button getResetButton() {
        return resetButton;
    }

    public void setResetButton(Button resetButton) {
        this.resetButton = resetButton;
    }

    public Button getRefreshTableDataButton() {
        return refreshTableDataButton;
    }

    public void setRefreshTableDataButton(Button refreshTableDataButton) {
        this.refreshTableDataButton = refreshTableDataButton;
    }

    public MenuButton getSearchFilterMenuButton() {
        return searchFilterMenuButton;
    }

    public void setSearchFilterMenuButton(MenuButton searchFilterMenuButton) {
        this.searchFilterMenuButton = searchFilterMenuButton;
    }

    public MenuButton getSortTableMenuButton() {
        return sortTableMenuButton;
    }

    public void setSortTableMenuButton(MenuButton sortTableMenuButton) {
        this.sortTableMenuButton = sortTableMenuButton;
    }

    public SplitMenuButton getAddItemButton() {
        return addItemButton;
    }

    public void setAddItemButton(SplitMenuButton addItemButton) {
        this.addItemButton = addItemButton;
    }

    public TextField getSearchTextField() {
        return searchTextField;
    }

    public void setSearchTextField(TextField searchTextField) {
        this.searchTextField = searchTextField;
    }

    public ChoiceBox<AccessMode> getAccessModeChoiceBox() {
        return accessModeChoiceBox;
    }

    public void setAccessModeChoiceBox(ChoiceBox<AccessMode> accessModeChoiceBox) {
        this.accessModeChoiceBox = accessModeChoiceBox;
    }

    public CheckBox getDataSyncCheckBox() {
        return dataSyncCheckBox;
    }

    public void setDataSyncCheckBox(CheckBox dataSyncCheckBox) {
        this.dataSyncCheckBox = dataSyncCheckBox;
    }

    private class ReadOnlyAccessMode implements AccessMode{
        @Override
        public void access() {
            getAccessModeGroupBox().setDisable(true);
        }

        @Override
        public String message() {
            return "You can only view this database.";
        }

        @Override
        public String toString() {
            return "Read Only Access Mode";
        }
    }

    private class RequestOnChangeAccessMode implements AccessMode{
        @Override
        public void access() {

        }

        @Override
        public String message() {
            return "You can view and send a request changes.";
        }

        @Override
        public String toString() {
            return "Request On Change Access Mode";
        }
    }

    private class FullAccessMode implements AccessMode{
        @Override
        public void access() {
            //empty definition
        }

        @Override
        public String message() {
            return "You have fully access to this database.";
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
            while (dataSyncCheckBox.selectedProperty().getValue()){
                try {

                    notifySyncDataThread();

                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Thread Stop");
        }

    }


}
