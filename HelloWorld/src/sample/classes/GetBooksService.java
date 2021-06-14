package sample.classes;

import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import sample.model.Book;

public class GetBooksService extends Service<ObservableList<Book>> {
    @Override
    protected Task<ObservableList<Book>> createTask() {
        return new GetBooksTask();
    }
}
