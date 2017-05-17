package model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;

public class CurrentSong extends SimpleObjectProperty<Song> implements interfaces.Song{

    private String title, interpret, album;

    @Override
    public String getAlbum() {
        return this.album;
    }

    @Override
    public void setAlbum(String album) {
        this.album = album;
    }

    @Override
    public String getInterpret() {
        return this.interpret;
    }

    @Override
    public void setInterpret(String interpret) {
        this.interpret = interpret;
    }

    @Override
    public String getPath() {
        return "";
    }

    @Override
    public void setPath(String path) {

    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public void setId(long id) {

    }

    @Override
    public ObservableValue<String> pathProperty() {
        return null;
    }

    @Override
    public ObservableValue<String> albumProperty() {
        return null;
    }

    @Override
    public ObservableValue<String> interpretProperty() {
        return null;
    }
}
