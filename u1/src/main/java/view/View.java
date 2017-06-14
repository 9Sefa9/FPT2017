
package view;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Song;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.text.Text;

public class View extends BorderPane{
    private ListView<Song> listviewsong,listviewplaylist;
    private Button addsongs, loadPlaylist, loadSonglist, savePlaylist, saveSonglist,addtoplaylist,deletesong,commit,play,pause,next;
    private Slider volumeSlider, songSlider;
    private GridPane upperframe,downframe,rightframe;
    private TextField title,interpret,album;
    private Text playlistText;
    private Text titleText;
    private Text intepretText;
    private Text albumText;
    private Text currentTitle;
    private Text currentInterpret;
    private Text songTime;
    private Text songDuration;

    public View(){
        setMaxSize(1024,1024);
        //instanziierungen
        listviewsong = new ListView<>();
        listviewsong.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listviewplaylist = new ListView<>();
        rightframe = new GridPane();

        upperframe = new GridPane();
        downframe = new GridPane();

        //downframe
        downframe.setVgap(30);
        downframe.setHgap(30);

        next = new Button("→");
        next.setPadding(new Insets(10,20,10,20));
        downframe.add(next,3,0);

        play = new Button("►");
        play.setPadding(new Insets(10,20,10,20));
        downframe.add(play,5,0);

        pause = new Button("||");
        pause.setPadding(new Insets(10,20,10,20));
        downframe.add(pause,7,0);

        addsongs= new Button("Add Songs");
        addsongs.setPadding(new Insets(10,10,10,10));
        downframe.add(addsongs,0,0);

        volumeSlider = new Slider(0, 100, 100);

        downframe.add(volumeSlider, 9, 0);

        //upperframe
        loadPlaylist = new Button("Load");
        loadPlaylist.setPadding(new Insets(10,10,10,10));
        upperframe.add(loadPlaylist,2,0);

        savePlaylist = new Button("Save");
        savePlaylist.setPadding(new Insets(10,10,10,10));
        upperframe.add(savePlaylist,3,0);

        saveSonglist = new Button("Save");
        saveSonglist.setPadding(new Insets(10,10,10,10));
        upperframe.add(saveSonglist,9,0);

        loadSonglist = new Button("Load");
        loadSonglist.setPadding(new Insets(10,10,10,10));
        upperframe.add(loadSonglist,10,0);

        upperframe.setHgap(25);

        //rightframe
        rightframe.setMinSize(200,500);

        titleText = new Text("Title");
        rightframe.add(titleText,0,0);

        title = new TextField();
        title.setPadding(new Insets(5,20,5,5));
        rightframe.add(title,0,1);

        intepretText = new Text("Interpret");
        rightframe.add(intepretText,0,2);

        interpret = new TextField();
        interpret.setPadding(new Insets(5,20,5,5));
        rightframe.add(interpret,0,3);

        albumText = new Text("Album");
        rightframe.add(albumText,0,4);

        album = new TextField();
        album.setPadding(new Insets(5,20,5,5));
        rightframe.add(album,0,5);

        commit = new Button("Commit");
        commit.setPadding(new Insets(10,10,10,10));
        rightframe.add(commit,0,7);


        addtoplaylist = new Button("Add to Playlist");
        addtoplaylist.setPadding(new Insets(10,20,10,20));
        rightframe.add(addtoplaylist,0,8);

        deletesong = new Button("Delete Song");
        deletesong.setPadding(new Insets(10,20,10,20));
        rightframe.add(deletesong,0,11);

        VBox vBox = new VBox(5);
        vBox.setMinWidth(200);

        currentTitle = new Text("");
        currentTitle.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 20));
        currentInterpret = new Text("");
        currentTitle.setFont(new Font(15));

        songSlider = new Slider(0, 1, 0);

        HBox hBox = new HBox(150);

        //Song durations
        songTime = new Text("0:00");
        songDuration = new Text("0:00");
        hBox.getChildren().addAll(songTime, songDuration);

        vBox.setPadding(new Insets(40, 0, 0, 0));
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.getChildren().addAll(currentTitle, currentInterpret, songSlider, hBox);
        rightframe.add(vBox, 0, 9);

        rightframe.setHgap(10);
        rightframe.setVgap(10);

        rightframe.getColumnConstraints().add(new ColumnConstraints(125));


        //Alignments
        setRight(rightframe);
        setBottom(downframe);
        setTop(upperframe);
        setCenter(listviewplaylist);
        setLeft(listviewsong);

    }

    //getter
    public Button getAddall() {
        return addsongs;
    }
    public Button getLoadPlaylist() {
        return loadPlaylist;
    }
    public Button getLoadSonglist(){return loadSonglist;}
    public Button getAddsongs(){return addsongs;}
    public Button getDeletesong(){return deletesong;}
    public Button getSavePlaylist(){return savePlaylist;}
    public Button getSaveSonglist(){return saveSonglist;}
    public Button getAddtoplaylist(){return addtoplaylist;}
    public Button getCommit(){return commit;}
    public Button getPlay(){return play;}
    public Button getPause(){return pause;}
    public Button getNext(){return next;}
    public ListView<Song> getListviewsong(){return listviewsong;}
    public ListView<Song> getListviewplaylist(){return listviewplaylist;}
    public Slider getVolumeSlider(){return volumeSlider;}
    public Slider getSlider(){return songSlider;}
    public TextField getTitle(){return title;}
    public TextField getInterpret(){return interpret;}
    public TextField getAlbum(){return album;}
    public ObservableList<Song> getSelectedSongs() {

        return listviewsong.getSelectionModel().getSelectedItems();
    }
    public Slider getSongSlider(){
        return songSlider;
    }

    //setter
    public void setCurrentTitle(String title)
    {
        this.currentTitle.setText(title);
    }
    public void setCurrentInterpret(String interpret)
    {
        this.currentInterpret.setText(interpret);
    }
    public void setSliderVisibility(Boolean value){
        this.songSlider.setVisible(value);
    }
    public Text getSongTime() {
        return songTime;
    }
    public void setSongTime(Text songTime) {
        this.songTime = songTime;
    }
    public Text getSongDuration() {
        return songDuration;
    }
    public void setSongDuration(Text songDuration) {
        this.songDuration = songDuration;
    }



}
