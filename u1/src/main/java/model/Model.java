/**
 * Created by Sefa on 05.05.2017.
 */
package model;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.cert.Extension;
import java.util.ArrayList;
import java.util.List;

public class Model{
    public SongList allsongs,playlist,songList;
    private File file;
    private Path dir;
    private DirectoryChooser dirChooser;
    private FileChooser fileChooser;

    public void setAllsongs(SongList allsongs){
        this.allsongs = allsongs;

    }
    public SongList getAllsongs(){
        return this.allsongs;
    }
    public void setPlaylist(SongList playlist){
        this.playlist = playlist;
    }
    public SongList getPlaylist(){
        return this.playlist;
    }

    //ermöglicht uns das auswählen eines Ordners mit Songs.
    public void handleAddSongsButton(){
        dirChooser = new DirectoryChooser ();
        dirChooser.setTitle("Select directory with mp3 files..");

        file = dirChooser.showDialog(new Stage());
        dir = Paths.get(file.getPath());
        songList  = new SongList();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir))
        {
            for (Path p : stream)
            {
                String name = p.getFileName().toString();
                if (name.endsWith(".mp3"))
                {
                    String track, interpret, album;
                    interpret = "";
                    album = "";
                    String songPath = file.getPath() + "\\" + name;
                    Mp3File song = new Mp3File(songPath);
                    track = name.substring(0, name.length() - 4);
                    if(song.hasId3v2Tag())
                    {
                        ID3v2 id3v2 = song.getId3v2Tag();
                        if(id3v2.getTitle() != null)
                            track = id3v2.getTitle();
                        if(id3v2.getArtist() != null)
                            interpret = id3v2.getArtist();
                        if(id3v2.getAlbum() != null)
                            album = id3v2.getAlbum();
                    }
                    songList.add(new Song(songPath, track, album, interpret));
                }
            }
            this.allsongs.setAll(songList);
        } catch (Exception e) {
            System.out.println("Exception in MODEL-HASB-METHOD");
            e.printStackTrace();
        }
    }

    public void handleAddToPlaylistButton(ObservableList<Song> songs)
    {
        this.playlist.addAll(songs);
    }

    //Die Pfade der Lieder im Playlist, werden in eine *.pl Datei abgespeichert.
    public void handleSavePlaylist(ArrayList<Song> songs){
        try{
            fileChooser = new FileChooser();
            fileChooser.setTitle("Select directory with mp3 files..");

            //zeigt ein bevorzugtes format an , nähmlich *.pl
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("*.pl", ".pl");
            fileChooser.getExtensionFilters().add(extFilter);

            //zeigt den "save" Fenster
            File file = fileChooser.showSaveDialog(new Stage());

            //solange fenster offen
            if(file!=null)
                //speichere die Datei mit dem extension "*.pl"
                save(songs,file.getPath());

        }
        catch(Exception e) {
            System.out.println("Exception in HATPB-METHOD");
            e.printStackTrace();
        }

    }
    //Die save Methode bekommt die Songs und einen pfad zum speichern einer "*.pl" datei.
    private void save(ArrayList<Song> songs,String path){
        try( BufferedWriter bw = new BufferedWriter(new FileWriter(path))){
            for(int i = 0; i<songs.size(); i++) {
                bw.write(songs.get(i).getPath()+"\n");
            }
        }catch(IOException i){
            System.out.println(path);
            System.out.println("Exception in MODEL-S-METHOD");
            i.printStackTrace();
        }
    }
    private void load(List<String> extension){
        try(BufferedReader br = new BufferedReader(new FileReader(/*f.getCanonicalPath()+*/"playlist"+extension.get(0)))){

            String temp ="";

         }catch(Exception e){
            e.printStackTrace();

        }
    }
}

