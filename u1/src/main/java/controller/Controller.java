package controller;

import javafx.collections.ListChangeListener;
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
    private String currentTime;

    public Controller(){
        currentTime = "00:00";
    }
    public void link(Model model, View view) {
        try {
            this.model = model;
            this.view = view;
            //FEHLER!, sollte nicht leer sein.. wie zur hölle, sollen wir das machen ?
            this.cs = new Song();
            this.model.setCurrent(cs);


            //Lauscht und Updated regelmäßig die hinzugefügten Songs zum View.
            this.sl = new SongList();
            this.model.setAllsongs(this.sl);
            this.sl.addListener((ListChangeListener<? super Song>) c -> this.model.updateLVSong(this.sl,this.view.getListviewsong(),this.view.getListviewplaylist()));

            //Lauscht und Updated regelmäßig die Playlist mit den zuvor "geaddeten" Songs.
            this.sl2 = new SongList();
            this.model.setPlaylist(this.sl2);
            this.sl2.addListener((ListChangeListener<? super Song>) c -> this.model.updateLVPlaylist(this.sl2,this.view.getListviewsong(),this.view.getListviewplaylist()));

            //SetOnAction Methode für das auffinden/auswählen eines Musik Ordners
            this.view.getAddall().setOnAction(e -> {
                this.model.handleAddSongsButton();

            });

            //SetOnAction Methode für das hinzufügen der selektiv ausgewählten Song´s in die Playlist View
            this.view.getAddtoplaylist().setOnAction(e -> this.model.handleAddToPlaylistButton(this.view.getSelectedSongs()));

            this.view.getDeletesong().setOnAction(e -> this.model.deletesongFromPlaylist(this.view.getListviewplaylist()));

            //Speichert die Playlist in eine *.ps Datei ab.
            this.view.getSavePlaylist().setOnAction(e -> this.model.handleSavePlaylist(this.model.getPlaylist().list));

            //ladet die Playlist und packt es in die Playlist-View
            this.view.getLoadPlaylist().setOnAction(e -> this.model.handleLoadPlaylist(this.model.getPlaylist().list));

            //speichert die Songlist in eine *.xml datei
            this.view.getLoadSonglist().setOnAction(e -> this.model.loadJPASonglist());

            //laded die Songlist in die Library
            this.view.getSaveSonglist().setOnAction(e -> this.model.saveJPASonglist());

            //abspielen eines MP3 files
            this.view.getPlay().setOnAction(e -> {this.model.playMp3(this.view.getListviewsong(), this.view.getListviewplaylist());});

            //pausieren ines MP3 files
            this.view.getPause().setOnAction(e -> this.model.pauseMp3());

            //naechste mp3
            this.view.getNext().setOnAction(e -> this.model.nextMP3(this.view.getListviewplaylist()));

            //volume slider
            this.view.getVolumeSlider().valueProperty().addListener(e -> this.model.setVolume(this.view.getVolumeSlider().getValue() / 100));

            //meta daten auslesen
            this.view.getListviewsong().setOnMouseClicked(e -> {
                Song s = this.model.getAllsongs().get(this.view.getListviewsong().getSelectionModel().getSelectedIndex());

                this.view.getTitle().setText(s.getTitle());
                this.view.getInterpret().setText(s.getInterpret());
                this.view.getAlbum().setText(s.getAlbum());

            });

            //commiten der Meta daten
            this.view.getCommit().setOnAction(e -> {
                if(this.view.getSelectedSongs().size() == 1)
                {
                    this.model.commitSongDetails(this.view.getSelectedSongs().get(0), this.view.getTitle().getText(), this.view.getInterpret().getText(), this.view.getAlbum().getText());
                    this.model.updateLVSong(this.model.getAllsongs(),this.view.getListviewsong(),this.view.getListviewplaylist());
                    this.model.updateLVPlaylist(this.model.getPlaylist(),this.view.getListviewsong(),this.view.getListviewplaylist());
                }
            });

            //song slider und Minuten/Sekunden anzeiger
            this.cs.pathProperty().addListener((v, oldV, newV) -> {
                this.view.setCurrentTitle(this.model.getCurrent().getTitle());
                this.model.getMediaPlayer().currentTimeProperty().addListener((o, oldVa, newVa) -> {
                    this.view.getSongSlider().setValue((newVa.toSeconds()/this.model.getMediaPlayer().getTotalDuration().toSeconds()));
                    this.currentTime = this.songDurationToMinString(newVa) + ":" + this.songDurationToSecString(newVa);
                    this.view.getSongTime().setText(currentTime);
                    this.view.getSongDuration().setText(this.songDurationToMinString(this.model.getMediaPlayer().getTotalDuration()) + ":" + this.songDurationToSecString(this.model.getMediaPlayer().getTotalDuration()));
                });
            });

            //interpreter setten
            this.cs.interpretProperty().addListener((v, oldV, newV) -> this.view.setCurrentInterpret(this.model.getCurrent().getInterpret()));

            //song slider
            this.view.getSongSlider().setOnMouseClicked(e -> this.model.getMediaPlayer().seek(new Duration(this.view.getSongSlider().getValue()*this.model.getMediaPlayer().getTotalDuration().toMillis())));


        }catch(Exception e){
            System.out.println("Exception in CONTROLLER-L-METHOD");
            e.printStackTrace();
        }

    }

    private String songDurationToMinString(Duration dur)
    {
        int mins = (int)dur.toMinutes();
        String min;
        if(mins < 10)
        {
            min = "0" + Integer.toString(mins);
        }
        else
        {
            min = Integer.toString(mins);
        }
        return min;
    }

    private String songDurationToSecString(Duration dur)
    {
        int secs = (int)dur.toSeconds();
        String sec;
        while(secs > 59)
        {
            secs = secs - 60;
        }
        if(secs < 10)
        {
            sec = "0" + Integer.toString(secs);
        }
        else
        {
            sec = Integer.toString(secs);
        }
        return sec;
    }

    public String getCurrentTime(){
        return this.currentTime;
    }

    public void updateCurrentTime(String time){
        this.view.getSongTime().setText(time);
    }

}
