package strategyPattern;


import interfaces.SerializableStrategy;
import interfaces.Song;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

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
            xmle.flush();
    }

    @Override
    public Song readSong() throws IOException, ClassNotFoundException {
        return (Song)xmlde.readObject();
    }

    @Override
    public void closeReadable() {
            try{
                xmlde.close();
                fis.close();
            }catch(Exception e){
                e.printStackTrace();
            }
    }

    @Override
    public void closeWriteable() {
            try{
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
