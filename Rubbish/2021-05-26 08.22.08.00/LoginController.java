package sample.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sample.classes.Request;
import sample.classes.RequestController;
import sample.classes.StyleEvent;
import sample.model.Account;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;



public class LoginController implements Initializable{

    @FXML private Label lblTextError;
    @FXML private ImageView imgUsernameWarning;
    @FXML private ImageView imgPasswordWarning;
    @FXML private ChoiceBox<String> cbAccountTypes;
    @FXML private Button btnLogin;
    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;

    private final SimpleStringProperty accountType = new SimpleStringProperty();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Executing program...");

        initializeEvents();
        initializeValues();
    }


    @FXML
    private void txtUsername_OnAction(ActionEvent actionEvent) {
        txtPassword.requestFocus();
    }

    @FXML
    private void btnLogin_Submit(ActionEvent actionEvent) throws IOException {

        System.out.println("Submit: " + cbAccountTypes.getValue() + " " +
                                        txtUsername.getText() + " " +
                                        txtPassword.getText());

        var account = Account.of(txtUsername.getText(), txtPassword.getText());
        var request = RequestController.requestLogin(account, accountType.getValue());

        var isInvalidCredential = request.equals(Request.INVALID_CREDENTIALS);
        var isInvalidPassword = request.equals(Request.INVALID_PASSWORD);

        imgPasswordWarning.setVisible(isInvalidPassword || isInvalidCredential);
        imgUsernameWarning.setVisible(isInvalidCredential);
        lblTextError.setVisible(isInvalidPassword || isInvalidCredential);
        lblTextError.setText(request.getMessage());

        if(request.equals(Request.SUCCESS)){

            showAccountDashboard(actionEvent, account);

            JOptionPane.showMessageDialog(null, "Successfully Login!");
            txtUsername.requestFocus();
            txtUsername.setText("");
            txtPassword.setText("");
        }
    }

    private void initializeEvents(){
        System.out.println("Initializing Events...");

        final var defaultPalette =  "-fx-border-color: #D9D8D9;";
        final var onHoverPalette =   "-fx-border-color: #1C1C1C;";
        final var onWarningPalette =  "-fx-border-color: #FF6347;" +
                                    "-fx-background-color: #FFE2DC;";

        //@Trigger When Hover or Pressed the control
        //When trigger the background color changes(depending if there's a waring)
        StyleEvent.onActive(txtUsername, imgUsernameWarning, defaultPalette,
                onHoverPalette, onWarningPalette);
        StyleEvent.onActive(txtPassword, imgPasswordWarning, defaultPalette,
                onHoverPalette, onWarningPalette);

        //@Trigger When the control text property changes
        //When trigger the warning is sets to hide
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

        //@Trigger when the choiceBox value changes
        //When trigger it's set to the account type
        cbAccountTypes.getSelectionModel().selectedItemProperty().addListener(
                    (args, oldV, newV) -> accountType.setValue(newV));
    }

    private void initializeValues() {
        System.out.println("Initializing Values...");

        lblTextError.setVisible(false);
        imgUsernameWarning.setVisible(false);
        imgPasswordWarning.setVisible(false);
        btnLogin.setDisable(true);


        cbAccountTypes.getItems().setAll("Librarian", "Clerk", "Admin");
        cbAccountTypes.setValue("Librarian");
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

    private void showAccountDashboard(ActionEvent event, Account account) throws IOException {
        var root = (Parent)FXMLLoader.load(getClass().getResource("../design/fxml/dashboard.fxml"));
        var primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        primaryStage.hide();

        var stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

        stage.setOnCloseRequest(e -> {
            // log-out
            e.consume();
            var s = JOptionPane.showConfirmDialog(null, "Do you want to log-out?");
            if(s == 0){
                stage.close();
                primaryStage.show();
            }
        });
    }

    private DashboardController createAccountDashboard(String fxml, String username,
                                                       String password, String accountType){


    }
}
