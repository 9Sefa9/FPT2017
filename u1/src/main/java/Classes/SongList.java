/**
 * Created by Sefa on 05.05.2017.
 */
package Classes;


import javafx.beans.value.ObservableValue;
import javafx.collections.ModifiableObservableListBase;

import java.util.ArrayList;

public class SongList extends ModifiableObservableListBase implements interfaces.Song {

    public ArrayList<String> list;

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
    public Object get(int index) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    protected void doAdd(int index, Object element) {

    }

    @Override
    protected Object doSet(int index, Object element) {
        return null;
    }

    @Override
    protected Object doRemove(int index) {
        return null;
    }
}
