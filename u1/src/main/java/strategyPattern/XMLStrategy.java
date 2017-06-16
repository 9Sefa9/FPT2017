package strategyPattern;


import interfaces.SerializableStrategy;
import interfaces.Song;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.Optional;

public class XMLStrategy implements SerializableStrategy {
    private String path;
    private FileInputStream fis;
    private FileOutputStream fos;
    private XMLEncoder xmle;
    private XMLDecoder xmlde;

    public XMLStrategy(String path){
        this.path = path;
    }
    @Override

    public void openWriteableSongs() throws IOException {
            fos = new FileOutputStream(this.path);
            xmle = new XMLEncoder(fos);
}

    @Override
    public void openReadableSongs() throws IOException {
            fis = new FileInputStream(this.path);
            xmlde = new XMLDecoder(fis);
    }

    @Override
    public void writeSong(Song s) throws IOException {
            xmle.writeObject(s);
    }

    @Override
    public Song readSong() throws IOException, ClassNotFoundException {
        try{
            return (Song)xmlde.readObject();
        }catch(IndexOutOfBoundsException e){
            return null;
        }
    }

    @Override
    public void closeReadable() {
            try{
                fis.close();
                xmlde.close();
            }catch(Exception e){
                e.printStackTrace();
            }
    }

    @Override
    public void closeWriteable() {
            try{
                fos.flush();
                xmle.close();
                fos.close();
            }catch(Exception e){
                e.printStackTrace();
            }
    }
    @Override
    public void openWriteablePlaylist() throws IOException {

    }

    @Override
    public void openReadablePlaylist() throws IOException {

    }

}
