/**
 * Created by Sefa on 05.05.2017.
 */
package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import org.apache.openjpa.persistence.Persistent;
import org.apache.openjpa.persistence.jdbc.Strategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.*;
@Entity()
@Table(name = "Library")
public class Song implements interfaces.Song, Externalizable {

    @Persistent
    @Strategy("ValueHandler.StringPropertyValueHandler")
    @Column(name = "path")
    private StringProperty path = new SimpleStringProperty();

    @Persistent
    @Strategy("ValueHandler.StringPropertyValueHandler")
    @Column(name = "album")
    private StringProperty album = new SimpleStringProperty();

    @Persistent
    @Strategy("ValueHandler.StringPropertyValueHandler")
    @Column(name = "title")
    private StringProperty title = new SimpleStringProperty();

    @Persistent
    @Strategy("ValueHandler.StringPropertyValueHandler")
    @Column(name = "artist")
    private StringProperty interpreter = new SimpleStringProperty();

    @Id
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


    public Song(){
        this.title.set("");
        this.path.set("");
        this.album.set("");
        this.interpreter.set("");
    }
    /*
    private void setProps(){
        this.pathp = new SimpleStringProperty(this, "pathp", this.title);
        this.albump = new SimpleStringProperty(this, "albump", this.album);
        this.interpreterp = new SimpleStringProperty(this, "interpreterp", this.interpreter);
    }
    */

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
        return this.id;
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
        if (this.interpreter.get() != null && !this.interpreter.get().equals(""))
            output = output  + " by " + this.interpreter.get();
        if (this.album.get() != null && !this.album.get().equals(""))
            output = output + " (" + this.album.get() + ")";
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

    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        path.set(in.readUTF());
        title.set(in.readUTF());
        album.set(in.readUTF());
        interpreter.set(in.readUTF());
        id = in.readLong();

    }
}
