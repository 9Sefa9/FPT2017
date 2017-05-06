/**
 * Created by Sefa on 05.05.2017.
 */
package controller;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
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
    public SongList sl, sl2;

    public Controller(){

    }
    public void link(Model model, View view) {
        this.model = model;
        this.view = view;

        this.sl = new SongList();
        this.model.setAllsongs(this.sl);
        this.sl.addListener((ListChangeListener<? super Song>) c -> this.view.updateLVSong(this.sl));

        this.sl2 = new SongList();
        this.model.setPlaylist(this.sl2);
        this.sl2.addListener((ListChangeListener<? super Song>) c -> this.view.updateLVPlaylist(this.sl2));

        this.view.getAddall().setOnAction(e -> this.model.handleAddAllButton());
        //this.view.getAddToPlaylist.setOnAction(e -> this.model.handleAddToPlaylistButtion(this.view.getSelectedSongs())); TODO: AddToPlaylist Button
    }

}
