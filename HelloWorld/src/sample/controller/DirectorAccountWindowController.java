package sample.controller;

import javafx.util.Pair;

public class DirectorAccountWindowController extends AccountWindowController {

    @Override
    protected void initializeSubClass() {

        addMenuItem(new BookCatalogSceneController());
        addMenuItem(new DirectorDashboardSceneController());
        addMenuItem(new BorrowBookSceneController());

    }

}
