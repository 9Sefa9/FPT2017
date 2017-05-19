/**
 * Created by Sefa on 05.05.2017.
 */
package controller;

import javafx.collections.ListChangeListener;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import model.Model;
import model.Song;
import model.SongList;
import view.View;

public class Controller{

    private Model model;
    private View view;
    private SongList sl, sl2;
    private Song cs;
    //private MediaPlayer mp;

    public Controller(){

    }
    public void link(Model model, View view) {
        try {
            this.model = model;
            this.view = view;
            this.cs = new Song();
            this.model.setCurrent(cs);
            //this.mp = this.model.getMediaPlayer();

            //Lauscht und Updated regelmäßig die hinzugefügten Songs zum View.
            this.sl = new SongList();
            this.model.setAllsongs(this.sl);
            this.sl.addListener((ListChangeListener<? super Song>) c -> this.view.updateLVSong(this.sl));

            //Lauscht und Updated regelmäßig die Playlist mit den zuvor "geaddeten" Songs.
            this.sl2 = new SongList();
            this.model.setPlaylist(this.sl2);
            this.sl2.addListener((ListChangeListener<? super Song>) c -> this.view.updateLVPlaylist(this.sl2));

            //SetOnAction Methode für das auffinden/auswählen eines Musik Ordners
            this.view.getAddall().setOnAction(e -> {
                this.model.handleAddSongsButton();
                /*for(Song s : this.model.getAllsongs())
                {
                    System.out.println(s);
                    s.pathProperty().addListener(c -> this.view.updateLVSong(this.model.getAllsongs()));
                    s.albumProperty().addListener(c -> this.view.updateLVSong(this.model.getAllsongs()));
                    s.interpretProperty().addListener(c -> this.view.updateLVSong(this.model.getAllsongs()));
                }*/
            });

            //SetOnAction Methode für das hinzufügen der selektiv ausgewählten Song´s in die Playlist View
            this.view.addtoplaylist.setOnAction(e -> this.model.handleAddToPlaylistButton(this.view.getSelectedSongs()));

            this.view.deletesong.setOnAction(e -> this.model.deletesongFromPlaylist(this.view.listviewplaylist));
            //Speichert die Playlist in eine *.ps Datei ab.
            this.view.save.setOnAction(e -> this.model.handleSavePlaylist(this.model.getPlaylist().list));

            //ladet die Playlist und packt es in die Playlist-View
            this.view.load.setOnAction(e -> this.model.handleLoadPlaylist(this.model.getPlaylist().list));

            this.view.play.setOnAction(e -> {this.model.playMp3(this.view.listviewsong, this.view.listviewplaylist);});

            this.view.pause.setOnAction(e -> this.model.pauseMp3());

            this.view.next.setOnAction(e -> this.model.nextMP3(this.view.listviewplaylist));

            this.view.volumeSlider.valueProperty().addListener(e -> this.model.setVolume(this.view.volumeSlider.getValue() / 100));

            this.view.listviewsong.setOnMouseClicked(e -> {
                Song s = this.model.getAllsongs().get(this.view.listviewsong.getSelectionModel().getSelectedIndex());
                this.view.title.setText(s.getTitle());
                this.view.interpret.setText(s.getInterpret());
                this.view.album.setText(s.getAlbum());
            });

            this.view.commit.setOnAction(e -> {
                if(this.view.getSelectedSongs().size() == 1)
                {
                    this.model.commitSongDetails(this.view.getSelectedSongs().get(0), this.view.title.getText(), this.view.interpret.getText(), this.view.album.getText());
                    this.view.updateLVSong(this.model.getAllsongs());
                    this.view.updateLVPlaylist(this.model.getPlaylist());
                }
            });

            this.cs.pathProperty().addListener((v, oldV, newV) -> {
                this.view.setCurrentTitle(this.model.getCurrent().getTitle());
                this.model.getMediaPlayer().currentTimeProperty().addListener((o, oldVa, newVa) -> this.view.getSongSlider().setValue((newVa.toSeconds()/this.model.getMediaPlayer().getTotalDuration().toSeconds())));
            });

            this.cs.interpretProperty().addListener((v, oldV, newV) -> this.view.setCurrentInterpret(this.model.getCurrent().getInterpret()));

            //this.mp.currentTimeProperty().addListener((o, oldV, newV) -> this.view.getSongSlider().setValue((newV.toSeconds()/this.mp.getTotalDuration().toSeconds())));

            this.view.getSongSlider().setOnMouseClicked(e -> this.model.getMediaPlayer().seek(new Duration(this.view.getSongSlider().getValue()*this.model.getMediaPlayer().getTotalDuration().toMillis())));


        }catch(Exception e){
            System.out.println("Exception in CONTROLLER-L-METHOD");
            e.printStackTrace();
        }

    }

}
