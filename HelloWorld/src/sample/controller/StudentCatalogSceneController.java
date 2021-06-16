package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sample.model.Student;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentCatalogSceneController extends CatalogSceneController implements Initializable {
    @FXML private TableView<Student> studentTableView;
    @FXML private TableColumn<Student, String> studentIDTableColumn;
    @FXML private TableColumn<Student, String> firstNameTableColumn;
    @FXML private TableColumn<Student, String> lastNameTableColumn;
    @FXML private TableColumn<Student, String> genderTableColumn;
    @FXML private TableColumn<Student, String> emailTableColumn;
    @FXML private TableColumn<Student, String> yearLevelTableColumn;
    @FXML private TableColumn<Student, String> departmentTableColumn;
    @FXML private TableColumn<Student, String> courseTableColumn;
    @FXML private TableColumn<Student, String> addressTableColumn;
    @FXML private TableColumn<Student, String> contactNumberTableColumn;

    protected StudentCatalogSceneController() {
        super("Student Catalog", "../view/fxml/studentCatalogScene.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        super.initialize(location, resources);
    }

    @Override
    public void notifySyncDataThread() {
        System.out.println("Sync Thread");
    }
}
