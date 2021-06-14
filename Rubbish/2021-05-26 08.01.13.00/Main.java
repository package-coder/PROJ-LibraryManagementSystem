package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("design/fxml/login.fxml"));

        var scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("design/css/font.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("design/css/login.css").toExternalForm());

        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("design/images/logo_ico.png")));
        primaryStage.setOnCloseRequest(e -> onClose());

        primaryStage.setTitle("Libre: Library Management System");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();


//        Parent root = FXMLLoader.load(getClass().getResource("design/fxml/webQR.fxml"));
//        primaryStage.setTitle("QR Generator");
//        primaryStage.setScene(new Scene(root));
//        primaryStage.setResizable(false);
//        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    private void onClose(){
        System.out.println("Main.onClose");
    }
}
