package strategyPattern;

import interfaces.SerializableStrategy;
import interfaces.Song;

import java.io.*;

public class BinaryStrategy implements SerializableStrategy, Externalizable{

    private String path;
    public BinaryStrategy(String path){
        this.path = path;
    }
    @Override
    public void openWriteableSongs() throws IOException {

    }

    @Override
    public void openReadableSongs() throws IOException {

    }

    @Override
    public void openWriteablePlaylist() throws IOException {

    }

    @Override
    public void openReadablePlaylist() throws IOException {

    }

    @Override
    public void writeSong(Song s) throws IOException {


       try(FileOutputStream fos = new FileOutputStream(this.path);
           ObjectOutputStream oos = new ObjectOutputStream(fos)){

           oos.writeObject(s);
           oos.flush();
       }catch(Exception e){
           e.printStackTrace();
       }

    }

    @Override
    public Song readSong() throws IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public void closeReadable() {

    }

    @Override
    public void closeWriteable() {

    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {

    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {

    }
}
