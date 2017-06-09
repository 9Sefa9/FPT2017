package model;

import com.mpatric.mp3agic.*;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import strategyPattern.BinaryStrategy;
import strategyPattern.IDGenerator;
import strategyPattern.IDOverFlowException;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class Model{
    private SongList allsongs,playlist,songList;
    private File file,fileSave,fileLoad;
    private Path dir;
    private DirectoryChooser dirChooser;
    private FileChooser fileChooser;
    private ArrayList<Song> songFromPLFile;
    private MediaPlayer mediaPlayer;
    private double currentVolume = 1;
    private int currentPlaylistSong = 0;
    private Song current;

    //setter
    public void setAllsongs(SongList allsongs){
        this.allsongs = allsongs;

    }
    public void setPlaylist(SongList playlist){
        this.playlist = playlist;
    }
    public void setCurrent(Song cs)
    {
        this.current = cs;
    }

    //getter
    public SongList getAllsongs(){
        return this.allsongs;
    }
    public SongList getPlaylist(){
        return this.playlist;
    }
    public MediaPlayer getMediaPlayer()
    {
        return mediaPlayer;
    }

    public void updateLVSong(SongList sl,ListView<Song> listviewsong,ListView<Song> listviewplaylist)
    {
        listviewsong.getItems().removeAll(listviewsong.getItems());
        for (Song s : sl)
            listviewsong.getItems().add(s);
    }

    public void updateLVPlaylist(SongList sl,ListView<Song> listviewsong,ListView<Song> listviewplaylist)
    {
        listviewplaylist.getItems().removeAll(listviewplaylist.getItems());
        for (Song s : sl)
            listviewplaylist.getItems().add(s);
    }

    //ermöglicht uns das auswählen eines Ordners mit Songs.
    //Jeder Song hat eine Unique-ID, beim überschreiten gibt es ein Exception.
    public void handleAddSongsButton(){

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

                        try{
                            Song test = new Song(songPath, track, album, interpret, IDGenerator.getNextID());

                            songList.add(test);
                        } catch(IDOverFlowException e){

                            ArrayList<Long> intArr = new ArrayList<>();
                            for (int k = 1; k<=9999; k++)
                            {
                                intArr.add((long)k);
                            }
                            for(int i = 0; i<=songList.size();i++)
                            {
                                if(intArr.size() == 0)
                                    break;

                                if(intArr.contains(songList.get(i).getUniqueID())){
                                    intArr.remove(songList.get(i).getUniqueID());
                                }
                            }

                            if(!intArr.isEmpty())
                            {
                                songList.add(new Song(songPath, track, album, interpret, intArr.get(0)));
                            }
                            else
                                System.out.println("");

                            e.printStackTrace();
                        }
                    }
                }
                this.allsongs.setAll(songList);
            }
            catch (Exception e ) {
                System.out.println("Exception in MODEL-HASB-METHOD");
                e.printStackTrace();
            }

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


    public void deletesongFromPlaylist(ListView<Song> listviewplaylist){
        if(listviewplaylist.getSelectionModel().isSelected(listviewplaylist.getSelectionModel().getSelectedIndex()))
            this.playlist.remove(currentPlaylistSong);
    }


    //Die Pfade der Lieder im Playlist, werden in eine *.pl Datei abgespeichert.
    public void handleSavePlaylist(ArrayList<Song> songs){
        try{
            fileChooser = new FileChooser();

            //zeigt ein bevorzugtes format an , nämlich *.pl
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("*.pl", ".pl");
            fileChooser.getExtensionFilters().add(extFilter);

            //zeigt den "save" Fenster
            fileSave = fileChooser.showSaveDialog(new Stage());
            fileChooser.setTitle("Save Playlist in" +fileSave.getPath());

            //solange fenster offen
            if(fileSave!=null)
                //speichere die Datei mit dem extension "*.pl"
                save(songs,fileSave.getPath());
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
            fileLoad = fileChooser.showOpenDialog(new Stage());
            fileChooser.setTitle("Load *.pl file from: "+fileLoad.getPath());

            //solange fenster offen
            if(fileLoad!=null)
                //ladet die Datei mit dem extension "*.pl"
                load(fileLoad.getPath());
        }
        catch(Exception e) {
            System.out.println("Exception in HATPB-METHOD");
            e.printStackTrace();
        }

    }

    //Die save Methode bekommt die playlistSongs und einen pfad zum speichern einer "*.pl" datei.
    private void save(ArrayList<Song> songs,String path) {

        BinaryStrategy bs = null;
        try {
            bs = new BinaryStrategy(path);

            bs.openWriteablePlaylist();

            for(Song i : songs) {
                bs.writeSong(i);
            }

            }
             catch(IOException e){
                e.printStackTrace();
            }
        }

    //Die load Methode ladet die playlistSongs aus der Festplatte zum Programm.
    private void load(String path){

      BinaryStrategy bs = null;
      try{
          bs = new BinaryStrategy(path);

          bs.openReadablePlaylist();
          Song s = null;
          while((s = (Song)bs.readSong()) != null)
          {
              this.playlist.add(s);
          }
          bs.closeReadable();


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

                mediaPlayer = new MediaPlayer(new Media(new File(this.playlist.get(this.currentPlaylistSong).getPath()).toURI().toString()));
                System.out.println(current);
                current.setTitle(this.playlist.get(this.currentPlaylistSong).getTitle());
                current.setInterpret(this.playlist.get(this.currentPlaylistSong).getInterpret());
                mediaPlayer.setVolume(this.currentVolume);
                mediaPlayer.setOnEndOfMedia(() -> {

                    this.currentPlaylistSong++;
                    playMp3(listviewsong, listviewplaylist);
                });

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
                    mediaPlayer = new MediaPlayer(new Media(new File(this.allsongs.get(listviewsong.getSelectionModel().getSelectedIndex()).getPath()).toURI().toString()));
                    current.setTitle(this.allsongs.get(listviewsong.getSelectionModel().getSelectedIndex()).getTitle());
                    current.setInterpret(this.allsongs.get(listviewsong.getSelectionModel().getSelectedIndex()).getInterpret());
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

    public Song getCurrent()
    {
        return current;
    }

    public void nextMP3(ListView<Song> listviewplaylist){
        if(this.playlist.size() > 1)
        {
            if(mediaPlayer != null) {
                this.mediaPlayer.stop();
                this.mediaPlayer = null;
                this.currentPlaylistSong++;
                this.playMp3(null, listviewplaylist);
            }
        } else if(playlist.size() < 2)
        {
            this.mediaPlayer.stop();
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

