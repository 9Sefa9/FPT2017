/**
 * Created by Sefa on 05.05.2017.
 */
package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

public class Song implements interfaces.Song{

    private String path = "",title = "",album = "",interpreter = "";
    private StringProperty pathp, albump, interpreterp;

    public Song(String path, String title, String album, String interpreter)
    {
        this.path = path;
        this.title = title;
        this.album = album;
        this.interpreter = interpreter;
        this.setProps();
    }

    public Song(){
        this.setProps();
    }

    private void setProps(){
        this.pathp = new SimpleStringProperty(this, "pathp", this.title);
        this.albump = new SimpleStringProperty(this, "albump", this.album);
        this.interpreterp = new SimpleStringProperty(this, "interpreterp", this.interpreter);
    }

    @Override
    public String getAlbum() {
        return this.album;
    }

    @Override
    public void setAlbum(String album) {
        this.album = album;
        this.albump.set(album);
    }

    @Override
    public String getInterpret() {
        return this.interpreter;
    }

    @Override
    public void setInterpret(String interpret) {
        this.interpreter = interpret;
        this.interpreterp.set(interpret);
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
        String s = this.path.replace(this.title, title);
        this.setPath(s);
        this.title = title;
        this.pathp.set(title);
    }

    @Override
    public long getId() {
        return this.hashCode();
    }

    @Override
    public void setId(long id) {

    }

    @Override
    public ObservableValue<String> pathProperty() {
        return this.pathp;
    }

    @Override
    public ObservableValue<String> albumProperty() {
        return this.albump;
    }

    @Override
    public ObservableValue<String> interpretProperty() {
        return this.interpreterp;
    }

    @Override
    public String toString() {
        String output = this.title;
        if (this.interpreter != null && !this.interpreter.equals(""))
            output = output  + " by " + this.interpreter;
        if (this.album != null && !this.album.equals(""))
            output = output + " (" + this.album + ")";
        return output;
    }
}
