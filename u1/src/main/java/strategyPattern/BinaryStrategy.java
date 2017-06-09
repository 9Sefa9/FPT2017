package strategyPattern;

import interfaces.SerializableStrategy;
import interfaces.Song;

import java.io.*;
import java.util.ArrayList;

public class BinaryStrategy implements SerializableStrategy, Externalizable{

    private String savepath,loadpath;
    private ArrayList<model.Song> songArray;
    private FileOutputStream fos;
    private FileInputStream fis;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    //für save
    public BinaryStrategy(String savepath, ArrayList<model.Song> songArray) throws IOException{
        this.savepath = savepath;
        this.songArray = songArray;

    }
    //für load
    public BinaryStrategy(String loadpath){
        this.loadpath = loadpath;
    }
    @Override
    public void openWriteableSongs() throws IOException {
        fos = new FileOutputStream(savepath);
        oos = new ObjectOutputStream(fos);

    }

    @Override
    public void openReadableSongs() throws IOException {
        fis = new FileInputStream(loadpath);
        ois = new ObjectInputStream(fis);

    }

    @Override
    public void writeSong(Song s) throws IOException {
            oos.writeObject(s);

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
                    oos.flush();
                    oos.close();
                    fos.close();
            }catch(IOException e){
                e.printStackTrace();
            }
    }
    @Override
    public void openWriteablePlaylist() throws IOException {

    }

    @Override
    public void openReadablePlaylist() throws IOException {

    }


    @Override
    public void writeExternal(ObjectOutput out) throws IOException {

    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {

    }
}
