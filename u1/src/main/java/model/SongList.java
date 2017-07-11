/**
 * Created by Sefa on 05.05.2017.
 */
package model;


import javafx.beans.value.ObservableValue;
import javafx.collections.ModifiableObservableListBase;

import java.io.Serializable;
import java.util.ArrayList;

public class SongList extends ModifiableObservableListBase<Song> implements interfaces.Song, Serializable {

    public ArrayList<Song> list = new ArrayList<>();

    @Override
    public String getAlbum() {
        return this.list.get(0).getAlbum();
    }

    @Override
    public void setAlbum(String album) {

    }

    @Override
    public String getInterpret() {
        return this.list.get(0).getInterpret();
    }

    @Override
    public void setInterpret(String interpret) {

    }

    @Override
    public String getPath() {
        return this.list.get(0).getPath();
    }

    @Override
    public void setPath(String path) {

    }

    @Override
    public String getTitle() {
        return this.list.get(0).getTitle();
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
        return this.list.get(index);
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    protected void doAdd(int index, Song element) {
        this.list.add(index, element);
    }


    @Override
    protected Song doSet(int index, Song element) {
        this.list.set(index, element);
        return null;
    }

    @Override
    protected Song doRemove(int index) {
        this.list.remove(index);
        return null;
    }

    @Override
    public String toString() {
        return this.list.toString();
    }

    public Song findSongByID(long id){
        for(Song u: list){
            if(u.getId() == id){
                return u;
            }
        }
        return null;
    }
}
