package sample.controller;

import com.google.zxing.WriterException;
import javafx.css.PseudoClass;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import sample.classes.QRCode.CodeGenerator;
import sample.dbutil.DBController;
import sample.model.Book;
import sample.model.Student;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class BorrowBookSceneController extends SceneController implements Initializable {

    @FXML private TextField txtBoxStudentName;
    @FXML private TextField txtBoxTransactionID;
    @FXML private TextField txtBoxStudentID;
    @FXML private ImageView imgViewerQR;
    @FXML private Button btnGenerate;
    @FXML private Button btnAdd;
    @FXML private Button btnCheckItems;

    public BorrowBookSceneController() {
        super("Borrow Book", "../view/fxml/borrowBookScene.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        txtBoxStudentID.requestFocus();

        var transactionID = String.valueOf(DBController.getTransactionCounter()+1);
        txtBoxTransactionID.setText(transactionID);

        initializeEvents();
    }

    private void initializeEvents(){

        final var pseudoClass = PseudoClass.getPseudoClass("invalid");


        txtBoxStudentID.setOnAction(event -> {

        });

        txtBoxStudentID.textProperty().addListener((observable, oldValue, newValue) -> {
            txtBoxStudentID.pseudoClassStateChanged(pseudoClass, false);
            txtBoxStudentName.pseudoClassStateChanged(pseudoClass, false);

            var student = parseStudent(newValue);

            var text = student.map(BorrowBookSceneController::formatName).orElse("");
            txtBoxStudentName.setText(text);
        });

        btnGenerate.setOnAction(event -> {

            try {
                var image = CodeGenerator.generateQRImage("data", 500, 500);
                imgViewerQR.setImage(SwingFXUtils.toFXImage(image, null));

            } catch (WriterException e) {
                e.printStackTrace();
            }

            var student = parseStudent(txtBoxStudentID.getText());

            if(student.isEmpty()) {
                txtBoxStudentID.pseudoClassStateChanged(pseudoClass, true);
                txtBoxStudentName.pseudoClassStateChanged(pseudoClass, true);
            }

        });
    }

    private Optional<Book> parseBook(String text){

        int ISBN = -1;
        try{
            ISBN = Integer.parseInt(text);
        }catch (NumberFormatException e){
            return Optional.empty();
        }

        var book = DBController.getBookByID(ISBN);
        return (book != null) ? Optional.of(book) : Optional.empty();
    }

    private Optional<Student> parseStudent(String text){

        int studentID = -1;
        try{
            studentID = Integer.parseInt(text);
        }catch (NumberFormatException e){
            return Optional.empty();
        }

        var student = DBController.getStudentByID(studentID);
        return (student != null) ? Optional.of(student) : Optional.empty();
    }

    private void TextBoxSetOnAction(TextField button, Control requestFocus){
        button.setOnAction(event -> {
            event.consume();
            if(!button.getText().isEmpty())
                requestFocus.requestFocus();
        });
    }

    private static String formatName(Student student){
        var firstName = student.getFirstName().toUpperCase();
        var lastName = student.getLastName().toUpperCase();

        return lastName + ", " + firstName;
    }
}
