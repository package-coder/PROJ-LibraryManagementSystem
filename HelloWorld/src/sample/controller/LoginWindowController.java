package sample.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sample.classes.*;
import sample.dbutil.DBController;
import sample.model.Account;
import sample.model.AccountType;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;



public class LoginWindowController implements Initializable{

    @FXML private Label lblTextError;
    @FXML private ImageView imgUsernameWarning;
    @FXML private ImageView imgPasswordWarning;
    @FXML private ChoiceBox<AccountType> cbAccountTypes;
    @FXML private Button btnLogin;
    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;

    private final SimpleObjectProperty<AccountType> accountType = new SimpleObjectProperty<>();

    private Stage primaryStage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeEvents();
        initializeValues();
    }

    public static void loadAndShow(Stage primaryStage){
        try {
            FXMLLoader loader = new FXMLLoader(LoginWindowController.class.getResource("../view/fxml/loginDisplay"));

            loader.load();
            LoginWindowController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);

            var scene = new Scene(loader.getRoot());
            primaryStage.setTitle("Library Management System");
            primaryStage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void txtUsername_OnAction(ActionEvent actionEvent) {
        txtPassword.requestFocus();
    }

    @FXML
    private void btnLogin_Submit(ActionEvent actionEvent) throws IOException {

        System.out.println("Submit: " + accountType.getValue() + " " +
                                        txtUsername.getText() + " " +
                                        txtPassword.getText());

        var username = txtUsername.getText();
        var password = txtPassword.getText();
        var type = accountType.getValue();

        var request = DBController.requestLogin(username, password, type);

        var isInvalidCredential= request.equals(Request.INVALID_CREDENTIALS);
        var isInvalidPassword = request.equals(Request.INVALID_PASSWORD);

        imgPasswordWarning.setVisible(isInvalidPassword || isInvalidCredential);
        imgUsernameWarning.setVisible(isInvalidCredential);
        lblTextError.setVisible(isInvalidPassword || isInvalidCredential);
        lblTextError.setText(request.getMessage());


        if(!request.equals(Request.SUCCESS))
            return;



        //@get the account details from db
        var account = DBController.login(username, password, type);
        var loader = loadDashboard(account, accountType.get());
        showNextStage(loader);


        //@reset
        txtUsername.requestFocus();
        txtUsername.setText("");
        txtPassword.setText("");
    }

    private void initializeEvents(){


        var invalid = PseudoClass.getPseudoClass("invalid");
        imgUsernameWarning.visibleProperty().addListener((observable, oldValue, newValue) -> {
            txtUsername.pseudoClassStateChanged(invalid, newValue);
        });

        imgPasswordWarning.visibleProperty().addListener((observable, oldValue, newValue) -> {
            txtPassword.pseudoClassStateChanged(invalid, newValue);
        });

        txtUsername.textProperty().addListener(e -> {
            txtPropertyListener(List.of(txtUsername, txtPassword), btnLogin);
            imgUsernameWarning.setVisible(false);
            lblTextError.setVisible(false);
        });
        txtPassword.textProperty().addListener(e -> {
            txtPropertyListener(List.of(txtUsername, txtPassword), btnLogin);
            imgPasswordWarning.setVisible(false);
            lblTextError.setVisible(false);
        });


        cbAccountTypes.getSelectionModel().selectedItemProperty().addListener(
                    (args, oldV, newV) -> accountType.setValue(newV));
    }

    private void initializeValues() {

        lblTextError.setVisible(false);
        imgUsernameWarning.setVisible(false);
        imgPasswordWarning.setVisible(false);
        btnLogin.setDisable(true);

        cbAccountTypes.getItems().setAll(AccountType.values());
        cbAccountTypes.setValue(AccountType.DIRECTOR);
    }

    private void txtPropertyListener(List<TextField> controls, Button button){
        for(var control : controls){
            if(control.getText().isEmpty()){
                button.setDisable(true);
                return;
            }
        }
        button.setDisable(false);
    }

    private void showNextStage(AccountWindowController window){
        window.show();
    }

    private AccountWindowController loadDashboard(Account account, ICreateWindowByAccountType dashboard){
        var window = dashboard.createWindow();

        window.load(account);
        window.setPrimaryStage(primaryStage);

        return window;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
