/**
 * Created by Sefa on 05.05.2017.
 */
package model;

import javafx.beans.value.ObservableValue;

public class Song implements interfaces.Song{

    public String path,title,album,interpreter;

    @Override
    public String getAlbum() {
        return null;
    }

    @Override
    public void setAlbum(String album) {

    }

    @Override
    public String getInterpret() {
        return null;
    }

    @Override
    public void setInterpret(String interpret) {

    }

    @Override
    public String getPath() {
        return null;
    }

    @Override
    public void setPath(String path) {

    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public void setTitle(String title) {

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
        return null;
    }
}
