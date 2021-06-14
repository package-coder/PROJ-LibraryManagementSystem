package sample.classes;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;


public abstract class TrackProgress {
    private ReadOnlyDoubleWrapper progress = new ReadOnlyDoubleWrapper();

    public double getProgress() {
        return progress.get();
    }

    public void setProgress(double progress) {
        this.progress.set(progress);
    }

    public ReadOnlyDoubleProperty progressProperty() {
        return progress;
    }
}
