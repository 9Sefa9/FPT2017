/**
 * Created by Sefa on 05.05.2017.
 */
package model;


import javafx.beans.value.ObservableValue;
import javafx.collections.ModifiableObservableListBase;

import java.util.ArrayList;

public class SongList extends ModifiableObservableListBase<Song> implements interfaces.Song {

    public ArrayList<Song> list;

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
    public Song get(int index) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    protected void doAdd(int index, Song element) {

    }

    @Override
    protected Song doSet(int index, Song element) {
        return null;
    }

    @Override
    protected Song doRemove(int index) {
        return null;
    }
}
