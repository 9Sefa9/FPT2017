/**
 * Created by Sefa on 05.05.2017.
 */
package controller;

import javafx.collections.ListChangeListener;
import model.Model;
import model.Song;
import model.SongList;
import view.View;

public class Controller{

    public Model model;
    public View view;
    public SongList sl, sl2;

    public Controller(){

    }
    public void link(Model model, View view) {
        try {
            this.model = model;
            this.view = view;

            //Lauscht und Updated regelmäßig die hinzugefügten Songs zum View.
            this.sl = new SongList();
            this.model.setAllsongs(this.sl);
            this.sl.addListener((ListChangeListener<? super Song>) c -> this.view.updateLVSong(this.sl));

            //Lauscht und Updated regelmäßig die Playlist mit den zuvor "geaddeten" Songs.
            this.sl2 = new SongList();
            this.model.setPlaylist(this.sl2);
            this.sl2.addListener((ListChangeListener<? super Song>) c -> this.view.updateLVPlaylist(this.sl2));

            //SetOnAction Methode für das auffinden/auswählen eines Musik Ordners
            this.view.getAddall().setOnAction(e -> this.model.handleAddSongsButton());

            //SetOnAction Methode für das hinzufügen der selektiv ausgewählten Song´s in die Playlist View
            this.view.addtoplaylist.setOnAction(e -> this.model.handleAddToPlaylistButton(this.view.getSelectedSongs()));

            //Speichert die Playlist in eine *.ps Datei ab.
            this.view.save.setOnAction(e -> this.model.handleSavePlaylist(this.model.getPlaylist().list));

            //ladet die Playlist und packt es in die Playlist-View
            this.view.load.setOnAction(e -> this.model.handleLoadPlaylist(this.model.getPlaylist().list));

            this.view.play.setOnAction(e -> this.model.playMp3(this.view.listviewsong, this.view.listviewplaylist));

            this.view.pause.setOnAction(e -> this.model.pauseMp3());

            this.view.next.setOnAction(e -> this.model.nextMP3());

            this.view.volumeSlider.valueProperty().addListener(e -> this.model.setVolume(this.view.volumeSlider.getValue() / 100));

        }catch(Exception e){
            System.out.println("Exception in CONTROLLER-L-METHOD");
            e.printStackTrace();
        }

    }

}
