package strategyPattern;

import interfaces.SerializableStrategy;
import interfaces.Song;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class BinaryStrategy implements SerializableStrategy{
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


       try(FileOutputStream fos = new FileOutputStream(".pl");
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
}
