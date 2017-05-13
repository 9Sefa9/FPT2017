/**
 * Created by Sefa on 05.05.2017.
 */
package model;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Model{
    public SongList allsongs,playlist,songList;
    private File file;
    private Path dir;
    private DirectoryChooser dirChooser;
    private FileChooser fileChooser;
    private ArrayList<Song> songFromPLFile;
    private MediaPlayer mediaPlayer;
    private Media m;

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

            //zeigt ein bevorzugtes format an , nämlich *.pl
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("*.pl", ".pl");
            fileChooser.getExtensionFilters().add(extFilter);

            //zeigt den "save" Fenster
            File file = fileChooser.showSaveDialog(new Stage());
            fileChooser.setTitle("Save Playlist in" +file.getPath());

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

    public void handleLoadPlaylist(ArrayList<Song> songs){
        try{
            fileChooser = new FileChooser();

            //zeigt ein bevorzugtes format an , nähmlich *.pl
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("*.pl", "*.pl");
            fileChooser.getExtensionFilters().add(extFilter);

            //zeigt den "save" Fenster
            File file = fileChooser.showOpenDialog(new Stage());
            fileChooser.setTitle("Load *.pl file from: "+file.getPath());

            //solange fenster offen
            if(file!=null)
                //ladet die Datei mit dem extension "*.pl"
                load(file.getPath());
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

    //Die load Methode ladet die Songs aus der Festplatte zum Programm.
    private void load(String path){
        try(BufferedReader br = new BufferedReader(new FileReader(path))){

            songFromPLFile = new ArrayList<>();

            Mp3File loadmp3 = null;
            String loadsongpath ="";
            String loadtrack="", loadinterpret="", loadalbum="", loadname="";

            while((loadsongpath= br.readLine())!= null) {
                loadmp3 = new Mp3File(loadsongpath);
                loadname = loadsongpath;
                loadtrack = loadsongpath.substring(0, loadsongpath.length() - 4);//loadname.substring(0, loadname.length() - 4);
                String[] name =loadtrack.split("\\.");
                System.out.println(name[name.length-1]);
                System.out.println(loadtrack);

                songFromPLFile.add(new Song(loadsongpath, loadtrack, loadalbum, loadinterpret));
            }

            this.playlist.addAll(songFromPLFile);

         }catch(Exception e){
            e.printStackTrace();

        }
    }

    //abspielen der Mp3
    public void playMp3(ListView<Song> listviewsong, ListView<Song> listviewplaylist) {

        m = new Media(new File(listviewsong.getSelectionModel().getSelectedItem().getPath()).toURI().toString());
        mediaPlayer = new MediaPlayer(m);

        mediaPlayer.statusProperty().addListener(new ChangeListener<MediaPlayer.Status>() {
          @Override
          public void changed(ObservableValue<? extends MediaPlayer.Status> observable, MediaPlayer.Status oldValue, MediaPlayer.Status newValue) {
              mediaPlayer.play();

          }
      });
        /*

        if (listviewsong.getFocusModel().getFocusedIndex() >= 0) {

            mediaPlayer.stop();
            mediaPlayer.play();

        }
        if (listviewplaylist.getFocusModel().getFocusedIndex() >= 0) {
            mediaPlayer.stop();
            mediaPlayer.play();
        }
        */
    }

}

