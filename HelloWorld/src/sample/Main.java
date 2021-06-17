package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import sample.classes.ObjectStream;
import sample.classes.Request;
import sample.dbutil.DBConnection;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public class Main extends Application {

    private static class Sample implements Serializable {
        private final String data;

        private Sample(String data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "Sample{" +
                    "data='" + data + '\'' +
                    '}';
        }
    }

    private void storeInDatabase(Object object){
        var query =     """
                            INSERT INTO AccessControl(SceneControl)
                            VALUES (?);
                        """;

        try(var connection = DBConnection.getConnection()) {

            if(connection == null)
                return;

            var bytes = ObjectStream.objectToByteArray(object);
            var preparedStatement = connection.prepareStatement(query);

            preparedStatement.setBytes(1, bytes);
            preparedStatement.executeUpdate();
            System.out.println("Stored");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Object readInDatabase(int ID){
        var query =     """
                            SELECT SceneControl
                            FROM AccessControl
                            WHERE AccessControlID = ?;
                        """;

        try(var connection = DBConnection.getConnection()) {

            if(connection == null)
                return null;

            var preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, ID);
            var rs = preparedStatement.executeQuery();

            if(rs.next()){
                return ObjectStream.byteArrayToObject(rs.getBytes(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



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



        var object = (List<Sample>)readInDatabase(1);

        for (var ob : object)
            System.out.println(ob.data);

//        var window = new DirectorAccountWindowController();
//        window.load(null);
//        window.setPrimaryStage(primaryStage);
//        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void onClose(){
        System.out.println("Main.onClose");
    }
}
