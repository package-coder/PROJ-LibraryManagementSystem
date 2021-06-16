package sample.controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.classes.Loader;
import sample.model.Account;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BookInformationFormController implements Initializable, Loader {

    private final static String SOURCE_DISPLAY = "../view/fxml/bookInformationForm.fxml";
    private FXMLLoader loader;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void show(){

        var stage = new Stage();
        stage.setScene(new Scene(loader.getRoot()));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.showAndWait();
        
    }

    @Override
    public void load(Object sender) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(SOURCE_DISPLAY));

            loader.setController(this);
            setLoader(loader);
            loader.load();

        } catch (IOException e) {
            System.err.println("$$$$$$$$ Logger::MainDashboardController.load");
            System.err.println("$$$$$$$$ Logger::FILE NOT FOUND (" + SOURCE_DISPLAY + ")");
            e.printStackTrace();
        }
    }

    public FXMLLoader getLoader() {
        return loader;
    }

    public void setLoader(FXMLLoader loader) {
        this.loader = loader;
    }
}
