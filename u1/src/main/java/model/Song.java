/**
 * Created by Sefa on 05.05.2017.
 */
package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

import java.io.*;

public class Song implements interfaces.Song, Externalizable {

    private StringProperty path = new SimpleStringProperty();
    private StringProperty album = new SimpleStringProperty();
    private StringProperty title = new SimpleStringProperty();
    private StringProperty interpreter = new SimpleStringProperty();
    private long id;

    public Song(String path, String title, String album, String interpreter,long id)
    {
        this.path.set(path);
        this.title.set(title);
        this.album.set(album);
        this.interpreter.set(interpreter);
        this.id = id;
       // this.setProps();
    }
    /*
    public Song(){
        this.setProps();
    }

    private void setProps(){
        this.pathp = new SimpleStringProperty(this, "pathp", this.title);
        this.albump = new SimpleStringProperty(this, "albump", this.album);
        this.interpreterp = new SimpleStringProperty(this, "interpreterp", this.interpreter);
    }
    */

    public Long getUniqueID(){
        return this.id;
    }

    @Override
    public String getAlbum() {
        return this.album.get();
    }

    @Override
    public void setAlbum(String album) {
        this.album.set(album);
    }

    @Override
    public String getInterpret() {
        return this.interpreter.get();
    }

    @Override
    public void setInterpret(String interpret) {
        this.interpreter.set(interpret);
    }

    @Override
    public String getPath() {
        return this.path.get();
    }

    @Override
    public void setPath(String path) {
        this.path.set(path);
    }

    @Override
    public String getTitle() {
        return this.title.get();
    }

    @Override
    public void setTitle(String title) {
        String s = this.path.get().replace(this.title.get(), this.title.get());
        this.setPath(s);
        this.title.set(title);
        this.path.set(title);
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
        return this.path;
    }

    @Override
    public ObservableValue<String> albumProperty() {
        return this.album;
    }

    @Override
    public ObservableValue<String> interpretProperty() {
        return this.interpreter;
    }

    @Override
    public String toString() {
        String output = this.title.get();
        if (this.interpreter != null && !this.interpreter.equals(""))
            output = output  + " by " + this.interpreter;
        if (this.album != null && !this.album.equals(""))
            output = output + " (" + this.album + ")";
        return output;
    }


    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
            //alle metadaten schreiben mit utf
        out.writeUTF(path.get());
        out.writeUTF(title.get());
        out.writeUTF(album.get());
        out.writeUTF(interpreter.get());
        out.writeLong(id);
        out.close();
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        path.set(in.readUTF());
        title.set(in.readUTF());
        album.set(in.readUTF());
        interpreter.set(in.readUTF());
        id = in.readLong();
        in.close();
    }
}
