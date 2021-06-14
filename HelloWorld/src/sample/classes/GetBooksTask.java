package sample.classes;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import sample.dbutil.DBHandler;
import sample.model.Book;

public class GetBooksTask extends Task<ObservableList<Book>> {
    @Override
    protected ObservableList<Book> call() throws Exception {

        var progress = new DBHandler.GetBookProgress();
        progress.progressProperty().addListener(((observable, oldValue, newValue) -> {
            updateProgress(newValue.doubleValue(), 1);
        }));

        return progress.getBooksFromDB();
    }
}
