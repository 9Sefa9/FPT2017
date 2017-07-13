package model;

import com.mpatric.mp3agic.*;
import com.sun.javafx.applet.ExperimentalExtensions;
import interfaces.SerializableStrategy;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import strategyPattern.*;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.JDBCType;
import java.sql.SQLException;
import java.util.ArrayList;

public class Model{
    private SongList allsongs,playlist,songList;
    private Song current;
    private File file,fileSave,fileLoad;
    private MediaPlayer mediaPlayer;

    private Path dir;
    private DirectoryChooser dirChooser;
    private FileChooser fileChooser;
    private ArrayList<Song> songFromPLFile;
    private double currentVolume = 1;
    private int currentPlaylistSong = 0;
    private OpenJPAStrategy o;


    public Model(){
        o = new OpenJPAStrategy();
    }
    //SETTER
    public void setDir(Path dir) {
        this.dir = dir;
    }
    public void setSongFromPLFile(ArrayList<Song> songFromPLFile) {
        this.songFromPLFile = songFromPLFile;
    }
    public void setFileChooser(FileChooser fileChooser) {
        this.fileChooser = fileChooser;
    }
    public void setDirChooser(DirectoryChooser dirChooser) {
        this.dirChooser = dirChooser;
    }
    public void setCurrentVolume(double currentVolume) {
        this.currentVolume = currentVolume;
    }
    public void setCurrentPlaylistSong(int currentPlaylistSong) {
        this.currentPlaylistSong = currentPlaylistSong;
    }
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
    public void setSongList(SongList songList){
        this.songList = songList;
    }
    public void setFile(File file){
        this.file = file;
    }
    public void setFileSave(File fileSave){
        this.fileSave = fileSave;
    }
    public void setFileLoad(File fileLoad){
        this.fileLoad = fileLoad;
    }
    public void setMediaPlayer(MediaPlayer mediaplayer){
        this.mediaPlayer = mediaplayer;
    }
    //GETTER
    public File getFile(){
        return this.file;
    }
    public File getFileSave(){
        return this.fileSave;
    }
    public File getFileLoad(){
        return this.fileLoad;
    }
    public SongList getAllsongs(){
        return this.allsongs;
    }
    public SongList getPlaylist(){
        return this.playlist;
    }
    public SongList getSongList(){
        return this.songList;
    }
    public Song getCurrent(){
        return this.current;
    }
    public MediaPlayer getMediaPlayer()
    {
        return mediaPlayer;
    }
    public FileChooser getFileChooser() {
        return fileChooser;
    }
    public Path getDir() {
        return dir;
    }
    public DirectoryChooser getDirChooser() {
        return dirChooser;
    }
    public ArrayList<Song> getSongFromPLFile() {
        return songFromPLFile;
    }
    public double getCurrentVolume() {
        return currentVolume;
    }
    public int getCurrentPlaylistSong() {
        return currentPlaylistSong;
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

            setDirChooser(new DirectoryChooser());
            getDirChooser().setTitle("Select directory with mp3 files..");

            try {
                file = getDirChooser().showDialog(new Stage());
                setDir(Paths.get(file.getPath()));

            songList = new SongList();
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(getDir())) {
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

                                if(intArr.contains(songList.get(i).getId())){
                                    intArr.remove(songList.get(i).getId());
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
                if(getDir() !=null)
                    setDir(null);
            }
        }

    public void handleAddToPlaylistButton(ObservableList<Song> songs)
    {
        this.playlist.addAll(songs);

    }

    public void deletesongFromPlaylist(ListView<Song> listviewplaylist){
        if(listviewplaylist.getSelectionModel().isSelected(listviewplaylist.getSelectionModel().getSelectedIndex())) {
            System.out.println(this.playlist.size() + " " + listviewplaylist.getSelectionModel().getSelectedIndex());
            System.out.println(this.playlist);
            this.playlist.remove(listviewplaylist.getSelectionModel().getSelectedIndex());
            System.out.println(this.playlist);
        }
    }


    //Die Pfade der Lieder im Playlist, werden in eine *.pl Datei abgespeichert.
    public void handleSavePlaylist(ArrayList<Song> songs){
        try{
            setFileChooser(new FileChooser());

            //zeigt ein bevorzugtes format an , nämlich *.pl
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("*.pl", ".pl");
            getFileChooser().getExtensionFilters().add(extFilter);

            //zeigt den "save" Fenster
            fileSave = getFileChooser().showSaveDialog(new Stage());
            getFileChooser().setTitle("Save Playlist in" +fileSave.getPath());

            //solange fenster offen
            if(fileSave!=null)
                //speichere die Datei mit dem extension "*.pl"
                savePlaylist(songs,fileSave.getPath());
        }
        catch(Exception e) {
            System.out.println("Exception in HATPB-METHOD");
            e.printStackTrace();
        }

    }

    public void handleLoadPlaylist(ArrayList<Song> songs){
        try{
            setFileChooser(new FileChooser());

            //zeigt ein bevorzugtes format an , nähmlich *.pl
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("*.pl", "*.pl");
            getFileChooser().getExtensionFilters().add(extFilter);

            //zeigt den "save" Fenster
            fileLoad = getFileChooser().showOpenDialog(new Stage());
            getFileChooser().setTitle("Load Playlist *.pl file from: "+fileLoad.getPath());

            //solange fenster offen
            if(fileLoad!=null)
                //ladet die Datei mit dem extension "*.pl"
                loadPlaylist(fileLoad.getPath());
        }
        catch(Exception e) {
            System.out.println("Exception in HATPB-METHOD");
            e.printStackTrace();
        }

    }

    public void handleSaveSonglist(ArrayList<Song> songs){
        try{
            setFileChooser(new FileChooser());

            //zeigt ein bevorzugtes format an , nämlich *.xml
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("*.xml", ".xml");
            getFileChooser().getExtensionFilters().add(extFilter);

            //zeigt den "save" Fenster
            fileSave = getFileChooser().showSaveDialog(new Stage());
            getFileChooser().setTitle("Save Songlist in" +fileSave.getPath());

            //solange fenster offen
            if(fileSave!=null)
                //speichere die Datei mit dem extension "*.pl"
                saveSonglist(fileSave.getPath());
        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }

    public void handleLoadSonglist(ArrayList<Song> songs){
        try{
            setFileChooser(new FileChooser());

            //zeigt ein bevorzugtes format an , nähmlich *.xml
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("*.xml", "*.xml");
            getFileChooser().getExtensionFilters().add(extFilter);

            //zeigt den "save" Fenster
            fileLoad = getFileChooser().showOpenDialog(new Stage());
            getFileChooser().setTitle("Load Songlist *.xml file from: "+fileLoad.getPath());

            //solange fenster offen
            if(fileLoad!=null)
                //ladet die Datei mit dem extension "*.pl"
                loadSonglist(fileLoad.getPath());
        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }

    //Die save Methode bekommt die playlistSongs und einen pfad zum speichern einer "*.pl" datei.
    private void savePlaylist(ArrayList<Song> songs,String path) {

        //Binary Serialization, only Playlist
        SerializableStrategy bs = null;

        try {
            bs= new BinaryStrategy(path);

            bs.openWriteablePlaylist();

            for(Song i : songs)
                bs.writeSong(i);
            }
             catch(Exception e){
                e.printStackTrace();
            }

        }

    //Die load Methode ladet die playlistSongs aus der Festplatte zum Programm.
    private void loadPlaylist(String path){

      //BinaryStrategy, load only playlist
      SerializableStrategy bs = null;
      try{
          bs = new BinaryStrategy(path);
          bs.openReadablePlaylist();
          Song songbs = null;
          while((songbs = (Song)bs.readSong()) != null)
          {
              this.playlist.add(songbs);
          }
          bs.closeReadable();
      }catch(Exception e){
          e.printStackTrace();
      }
    }
    //XML Serialization , only Songlist
    public void saveSonglist(String path){

        SerializableStrategy xml = null;
             try{
                 xml = new XMLStrategy(path);

                 xml.openWriteableSongs();
                 for(Song i : this.getAllsongs()) {
                     xml.writeSong(i);
                 }
                 xml.closeWriteable();

             }catch(Exception e){
                 e.printStackTrace();
             }
    }
    //XML Serialization, only Songlist
    public void loadSonglist(String path){

        SerializableStrategy xml = null;

        try{
            xml = new XMLStrategy(path);
            xml.openReadableSongs();

            Song songxml = (Song)xml.readSong();
            while((songxml != null)) {
                this.getAllsongs().add(songxml);
                songxml = (Song)xml.readSong();

            }
            xml.closeReadable();
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
                if(this.playlist.size() <= this.getCurrentPlaylistSong())
                    this.setCurrentPlaylistSong(0);

                mediaPlayer = new MediaPlayer(new Media(new File(this.playlist.get(this.getCurrentPlaylistSong()).getPath()).toURI().toString()));
                System.out.println(current);
                current.setTitle(this.playlist.get(this.getCurrentPlaylistSong()).getTitle());
                current.setInterpret(this.playlist.get(this.getCurrentPlaylistSong()).getInterpret());
                mediaPlayer.setVolume(this.getCurrentVolume());
                mediaPlayer.setOnEndOfMedia(() -> {

                    this.setCurrentPlaylistSong(this.getCurrentPlaylistSong() + 1);
                    playMp3(listviewsong, listviewplaylist);
                });

                listviewplaylist.getSelectionModel().select(this.getCurrentPlaylistSong());
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
                    mediaPlayer.setVolume(this.getCurrentVolume());
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

    public void nextMP3(ListView<Song> listviewplaylist){
        if(this.playlist.size() > 1)
        {
            if(mediaPlayer != null) {
                this.mediaPlayer.stop();
                this.mediaPlayer = null;
                this.setCurrentPlaylistSong(this.getCurrentPlaylistSong() + 1);
                this.playMp3(null, listviewplaylist);
            }
        } else if(playlist.size() < 2)
        {
            this.mediaPlayer.stop();
        }
    }

    public void setVolume(double volume)
    {
        this.setCurrentVolume(volume);
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

    public void saveDBSonglist() {
        JDBCStrategy j = null;
        try {

            j = new JDBCStrategy();
            j.openWriteableSongs();
            j.deleteLibraryContent();

            for (Song i : this.allsongs)
                j.writeSong(i);

            j.closeWriteable();

        } catch (IOException i) {
            i.printStackTrace();
        }
    }
    public void loadDBSonglist() {
        JDBCStrategy j = null;
        try{
            j = new JDBCStrategy();
            j.openReadableSongs();

            Song temp = null;
            while((temp = (Song)j.readSong())!= null){
                this.allsongs.add(temp);
            }

            j.closeReadable();

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void saveDBPlaylist() {
        JDBCStrategy j = null;
        try {

            j = new JDBCStrategy();
            j.openWriteablePlaylist();

            for (Song i : this.playlist)
                j.writePLSong(i);

            j.closeWriteable();

        } catch (IOException i) {
            i.printStackTrace();
        }
    }
    public void loadDBPlaylist() {
        JDBCStrategy j = null;
        try{
            j = new JDBCStrategy();
            j.openReadablePlaylist();

            Song temp;
            while((temp = (Song)j.readSong())!= null){
                this.playlist.add(temp);
            }

            j.closeReadable();

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void saveJPASonglist(){
        //OpenJPAStrategy o = new OpenJPAStrategy();
        try {
            o.openWriteableSongs();
            o.deleteContent();
            for(Song s : this.allsongs)
            {
                o.writeSong(s);
                System.out.println(s);
            }
            o.closeWriteable();
        } catch (Exception e){

        }
    }

    public void loadJPASonglist(){
        //OpenJPAStrategy o = new OpenJPAStrategy();
        try {
            o.openReadableSongs();
            //o.readSongs();
           /* Song s = new Song("","","","",0);
            while((s = (Song) o.readSong()) != null)
            {
                this.allsongs.add(s);
                System.out.println(s);
            }*/
            this.allsongs.setAll(o.readSongs());
            o.closeWriteable();
        } catch (Exception e){

        }
    }

}

