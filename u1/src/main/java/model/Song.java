/**
 * Created by Sefa on 05.05.2017.
 */
package model;

import javafx.beans.value.ObservableValue;

public class Song implements interfaces.Song{

    public String path,title,album,interpreter;

    public Song (String path, String title, String album, String interpreter)
    {
        this.path = path;
        this.title = title;
        this.album = album;
        this.interpreter = interpreter;
    }

    @Override
    public String getAlbum() {
        return null;
    }

    @Override
    public void setAlbum(String album) {
        this.album = album;
    }

    @Override
    public String getInterpret() {
        return this.interpreter;
    }

    @Override
    public void setInterpret(String interpret) {
        this.interpreter = interpret;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public void setPath(String path) {
        this.path = path;
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

    @Override
    public String toString() {
        String output = this.title;
        if (!this.interpreter.equals(""))
            output = output  + " by " + this.interpreter;
        if (!this.album.equals(""))
            output = output + " (" + this.album + ")";
        return output;
    }
}
