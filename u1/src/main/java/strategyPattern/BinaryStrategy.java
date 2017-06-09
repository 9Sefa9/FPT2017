package strategyPattern;

import interfaces.SerializableStrategy;
import interfaces.Song;

import java.io.*;

public class BinaryStrategy implements SerializableStrategy{

    private String path;

    private FileOutputStream fos;
    private FileInputStream fis;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public BinaryStrategy(String path){
        this.path = path;

    }
    public void openWriteablePlaylist() throws IOException {
        fos = new FileOutputStream(path);
        oos = new ObjectOutputStream(fos);
    }

    @Override
    public void openReadablePlaylist() throws IOException {
        fis = new FileInputStream(path);
        ois = new ObjectInputStream(fis);
    }

    @Override
    public void writeSong(Song s) throws IOException {
            oos.writeObject(s);
            oos.flush();
    }
    @Override
    public Song readSong() throws IOException, ClassNotFoundException {
        try{
            return (Song) ois.readObject();
        }
        catch(EOFException e){
            return null;
        }
    }

    @Override
    public void closeReadable() {

        try{
            ois.close();
            fis.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void closeWriteable() {
            try{
                    oos.close();
                    fos.close();
            }catch(IOException e){
                e.printStackTrace();
            }
    }
    @Override
    public void openWriteableSongs() throws IOException {


    }

    @Override
    public void openReadableSongs() throws IOException {


    }


}
