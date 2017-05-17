/**
 * Created by Sefa on 05.05.2017.
 */
package model;

import com.mpatric.mp3agic.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
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

import model.Song;

public class Model{
    private SongList allsongs,playlist,songList;
    private File file;
    private Path dir;
    private DirectoryChooser dirChooser;
    private FileChooser fileChooser;
    private ArrayList<Song> songFromPLFile;
    private MediaPlayer mediaPlayer;
    private double currentVolume = 1;
    private int currentPlaylistSong = 0;
    private Media m;
    //private CurrentSong current;
    private ObservableValue<Song> observer;
    private int indexForSongs;

    public Model(){
        //current = new CurrentSong();
    }

    public ObservableValue<Song> currentProperty(){
        return observer;
    }

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

           // mp3listSong = new ArrayList<>();
           // mp3listPlaylist = new ArrayList<>();
            dirChooser = new DirectoryChooser();
            dirChooser.setTitle("Select directory with mp3 files..");

            try {
                file = dirChooser.showDialog(new Stage());
                dir = Paths.get(file.getPath());

            songList = new SongList();
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
                for (Path p : stream) {
                    String name = p.getFileName().toString();
                    if (name.endsWith(".mp3")) {
                        //sollte vielleicht später public gemacht werden
                        String track, interpret, album;
                        interpret = "";
                        album = "";
                        String songPath = file.getPath() + "\\" + name;
                        Mp3File song = new Mp3File(songPath);
                        track = name.substring(0, name.length() - 4);
                        if (song.hasId3v2Tag()) {
                            ID3v2 id3v2 = song.getId3v2Tag();
                            if (id3v2.getTitle() != null)
                                track = id3v2.getTitle();
                            if (id3v2.getArtist() != null)
                                interpret = id3v2.getArtist();
                            if (id3v2.getAlbum() != null)
                                album = id3v2.getAlbum();
                        }
                        //m = new Media(new File(songPath).toURI().toString());
                        //mediaPlayer = new MediaPlayer(m);

                        //speichert sowohl Song als auch die abspiel faehigkeit
                        //mp3listSong.add(mediaPlayer);
                        songList.add(new Song(songPath, track, album, interpret));
                    }
                }
                this.allsongs.setAll(songList);
            } catch (Exception e) {
                System.out.println("Exception in MODEL-HASB-METHOD");
                e.printStackTrace();
            }
            }catch(Exception e)
            {

            }finally{
                //falls kein Pfad ausgegeben wurde: setze es auf null
                if(file!=null)
                    file = null;
                if(dir!=null)
                    dir = null;
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
            for (Song song : songs) {
                bw.write(song.getPath() + "\n");
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
                String lt = loadtrack.replace("\\", "\\\\");
                String[] name =lt.split("\\\\");
                System.out.println(name[name.length-1]);
                System.out.println(loadtrack);

                songFromPLFile.add(new Song(loadsongpath, name[name.length-1], loadalbum, loadinterpret));
            }

            this.playlist.addAll(songFromPLFile);

         }catch(Exception e){
            e.printStackTrace();

        }
    }

    //abspielen der Mp3. für linke und rechte seite werden jeweils listen erstellt.
    //es wird abgespielt/gestoppt sofern die list nicht leer ist und umgekehrt.
    public void playMp3(ListView<Song> listviewsong, ListView<Song> listviewplaylist) {

        if(!this.playlist.isEmpty())
        {
            if(mediaPlayer != null)
                System.out.println(mediaPlayer.getCurrentTime() + " " + mediaPlayer.getTotalDuration());
            if(mediaPlayer == null || mediaPlayer.getCurrentTime().equals(mediaPlayer.getTotalDuration()) || mediaPlayer.getStatus().equals(MediaPlayer.Status.STOPPED)) {
                if(this.playlist.size() <= this.currentPlaylistSong)
                    this.currentPlaylistSong = 0;
                //current = this.playlist.get(this.currentPlaylistSong);
                //current.setTitle(this.playlist.get(this.currentPlaylistSong).getTitle());
                mediaPlayer = new MediaPlayer(new Media(new File(this.playlist.get(this.currentPlaylistSong).getPath()).toURI().toString()));
                mediaPlayer.setVolume(this.currentVolume);
                mediaPlayer.setOnEndOfMedia(() -> {
                    //playlist.remove(0);
                    this.currentPlaylistSong++;
                    playMp3(listviewsong, listviewplaylist);
                });
                System.out.println(this.currentPlaylistSong);
                listviewplaylist.getSelectionModel().select(this.currentPlaylistSong);
                mediaPlayer.play();
            } else
                if(mediaPlayer.getStatus().equals(MediaPlayer.Status.PAUSED))
            {
                mediaPlayer.play();
            }
        } else
            if (listviewsong != null && listviewsong.getFocusModel().isFocused(listviewsong.getSelectionModel().getSelectedIndex())) {
                if(mediaPlayer == null || mediaPlayer.getCurrentTime().equals(mediaPlayer.getTotalDuration()) || mediaPlayer.getStatus().equals(MediaPlayer.Status.STOPPED)) {
                    //current = this.songList.get(listviewsong.getSelectionModel().getSelectedIndex());
                    mediaPlayer = new MediaPlayer(new Media(new File(this.songList.get(listviewsong.getSelectionModel().getSelectedIndex()).getPath()).toURI().toString()));
                    mediaPlayer.setVolume(this.currentVolume);
                    mediaPlayer.play();
                } else
                    if(mediaPlayer.getStatus().equals(MediaPlayer.Status.PAUSED))
                {
                    mediaPlayer.play();
                }
        }
    }

    public void pauseMp3(){
        if(mediaPlayer != null)
            mediaPlayer.pause();
    }

   /* public CurrentSong getCurrent()
    {
        return current;
    }*/

    public void nextMP3(ListView<Song> listviewplaylist){
        if(this.playlist.size() > 1)
        {
            if(mediaPlayer != null) {
                this.mediaPlayer.stop();
                this.mediaPlayer = null;
                //this.playlist.remove(0);
                this.currentPlaylistSong++;
                this.playMp3(null, listviewplaylist);
            }
        }
    }

    public void setVolume(double volume)
    {
        this.currentVolume = volume;
        if(this.mediaPlayer != null)
        {
            this.mediaPlayer.setVolume(volume);
        }
    }

    public void commitSongDetails(Song old, String title, String interpret, String album)
    {
        if(old.getTitle().equalsIgnoreCase(title))
            title = title + " (modified)";
        try {
            Mp3File mp3File = new Mp3File(old.getPath());
            ID3v2 id3v2;
            if(mp3File.hasId3v2Tag())
            {
                id3v2 = mp3File.getId3v2Tag();
            } else {
                id3v2 = new ID3v24Tag();
            }
            id3v2.setTitle(title);
            id3v2.setArtist(interpret);
            id3v2.setAlbum(album);
            mp3File.setId3v2Tag(id3v2);
            mp3File.save(old.getPath().replace(old.getTitle(), title));

        } catch (IOException | InvalidDataException | UnsupportedTagException | NotSupportedException e) {
            e.printStackTrace();
        }
        for(Song s : this.allsongs)
        {
            if (s.getId() == old.getId())
            {
                s.setTitle(title);
                s.setInterpret(interpret);
                s.setAlbum(album);
            }
        }
        for(Song s : this.playlist)
        {
            if (s.getId() == old.getId())
            {
                s.setTitle(title);
                s.setInterpret(interpret);
                s.setAlbum(album);
            }
        }
    }

}

