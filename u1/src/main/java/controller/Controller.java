/**
 * Created by Sefa on 05.05.2017.
 */
package controller;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Model;
import model.Song;
import model.SongList;
import view.View;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Controller{

    public Model model;
    public View view;
    public SongList sl;

    public Controller(){

    }
    public void link(Model model, View view) {
        this.model = model;
        this.view = view;

        this.sl = new SongList();
        this.model.setAllsongs(this.sl);
        this.sl.addListener((ListChangeListener<? super Song>) c -> this.view.updateLVSong(this.sl));

        this.view.getAddall().setOnAction(new addAllButtonEventHandler());
    }

    class addAllButtonEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            DirectoryChooser dirChooser = new DirectoryChooser();
            File file = dirChooser.showDialog(new Stage());
            Path dir = Paths.get(file.getPath());
            SongList songList = new SongList();
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
                            if(id3v2.getTrack() != null)
                                track = id3v2.getTrack();
                            if(id3v2.getArtist() != null)
                                interpret = id3v2.getArtist();
                            if(id3v2.getAlbum() != null)
                                album = id3v2.getAlbum();
                        }
                        Song s = new Song(songPath, track, album, interpret);
                        songList.add(s);
                    }
                }
                model.getAllsongs().setAll(songList);
            } catch (IOException | InvalidDataException | UnsupportedTagException e) {
                e.printStackTrace();
            }
        }
    }

}
