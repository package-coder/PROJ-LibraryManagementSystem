package sample;

import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;
import sample.controller.DirectorAccountWindowController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//
//        var scene = new Scene(root);
//        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("design/css/fontStyle.css")).toExternalForm());
//        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("design/css/loginStyle.css")).toExternalForm());
//
//        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("design/images/logo_ico.png"))));
//        primaryStage.setOnCloseRequest(e -> onClose());
//
//        primaryStage.setTitle("Libre: Library Management System");
//        primaryStage.setScene(scene);
//        primaryStage.setResizable(false);
//        primaryStage.show();

//        MainController.load(null, primaryStage, new DirectorDashboardController()).show();


        var window = new DirectorAccountWindowController();
        window.load(null);
        window.setPrimaryStage(primaryStage);
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void onClose(){
        System.out.println("Main.onClose");
    }
}
