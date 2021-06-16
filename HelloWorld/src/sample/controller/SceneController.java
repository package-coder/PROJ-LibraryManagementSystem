package sample.controller;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import sample.classes.Loader;
import sample.classes.NotifyOnSelectProperty;
import sample.model.Account;

import java.io.IOException;

public abstract class SceneController implements Loader, NotifyOnSelectProperty {

    private final String title;
    private final String fxmlFilePath;

    private Stage primaryStage;
    private Account account;
    private FXMLLoader loader;
    private Object parent;

    protected SceneController(String title, String fxmlFilePath) {
        this.title = title;
        this.fxmlFilePath = fxmlFilePath;
    }

    @Override
    public void load(Object sender) {

        try {
            var caller = (AccountWindowController) sender;

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFilePath));

            loader.setController(this);
            setAccount(caller.getAccount());
            setPrimaryStage(primaryStage);
            setLoader(loader);

            loader.load();

            return;
        } catch (IOException e) {
            System.err.println("@@@@@ SceneController.load");
            System.err.println("@@@@@ [title= '" + title + "']");
            System.err.println("@@@@@ [file path= '" + fxmlFilePath + "']");
            e.printStackTrace();
        }

        setLoader(null);
    }

    public String getTitle() {
        return title;
    }

    public String getFxmlFilePath() {
        return fxmlFilePath;
    }

    public FXMLLoader getLoader() {
        return loader;
    }

    public void setLoader(FXMLLoader loader) {
        this.loader = loader;
    }

    public Object getParent() {
        return parent;
    }

    public void setParent(Object parent) {
        this.parent = parent;
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
}
