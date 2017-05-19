
package view;
import model.Song;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import model.SongList;

public class View extends BorderPane{

    public ListView<Song> listviewsong,listviewplaylist;
    public Button addsongs,load,save,addtoplaylist,deletesong,commit,play,pause,next;
    public ChoiceBox<String> choicebox;
    private GridPane upperframe,downframe,rightframe;
    public TextField title,interpret,album;
    private Text titleText, intepretText, albumText;
    public View(){
        setMaxSize(1024,1024);

        //instanziierungen
        choicebox = new ChoiceBox<>();
        listviewsong = new ListView<>();
        listviewsong.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listviewplaylist = new ListView<>();
        rightframe = new GridPane();
        upperframe = new GridPane();
        downframe = new GridPane();

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
        rightframe.add(deletesong,0,9);

        rightframe.setHgap(10);
        rightframe.setVgap(10);

        rightframe.getColumnConstraints().add(new ColumnConstraints(125));


        //upperframe
        load = new Button("Load");
        load.setPadding(new Insets(10,10,10,10));
        upperframe.add(load,2,0);

        save = new Button("Save");
        save.setPadding(new Insets(10,10,10,10));
        upperframe.add(save,3,0);

        choicebox.setPadding(new Insets(5,5,5,160));
        choicebox.getItems().addAll("Example1","Example2","Example3");
        upperframe.add(choicebox,0,0);
        upperframe.setHgap(30);
        upperframe.setVgap(5);

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


        setRight(rightframe);
        setBottom(downframe);
        setTop(upperframe);
        setCenter(listviewplaylist);
        setLeft(listviewsong);


    }

    public Button getAddall() {
        return addsongs;
    }

    public Button getLoad() {
        return load;
    }

    public void updateLVSong(SongList sl)
    {
        listviewsong.getItems().removeAll(listviewsong.getItems());
        for (Song s : sl)
        {
            listviewsong.getItems().add(s);
        }
    }

    public void updateLVPlaylist(SongList sl)
    {
        listviewplaylist.getItems().removeAll(listviewplaylist.getItems());
        for (Song s : sl)
        {
            listviewplaylist.getItems().add(s);
        }
    }

    public ObservableList<Song> getSelectedSongs() {

        return listviewsong.getSelectionModel().getSelectedItems();
    }
}
