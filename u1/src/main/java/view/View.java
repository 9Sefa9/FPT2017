
package view;
import interfaces.Song;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class View extends BorderPane{
    private ListView<Song> listviewsong,listviewplaylist;
    private Button addall,load,save,addtoplaylist,commit,play,pause;
    private ChoiceBox<String> choicebox;
    private GridPane upperframe,downframe,rightframe;
    private TextField title,interpret,album;
    private Text titleText, intepretText, albumText;
    public View(){
        setMaxSize(1024,1024);

        //instanziierungen
        choicebox = new ChoiceBox<>();
        listviewsong = new ListView<>();
        listviewplaylist = new ListView<>();
        rightframe = new GridPane();
        upperframe = new GridPane();
        downframe = new GridPane();

        //splittpane
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

        play = new Button("►");
        play.setPadding(new Insets(10,20,10,20));
        rightframe.add(play,0,9);

        pause = new Button("||");
        pause.setPadding(new Insets(10,20,10,20));
        rightframe.add(pause,1,9);

        addtoplaylist = new Button("Add to Playlist");
        addtoplaylist.setPadding(new Insets(10,20,10,20));
        rightframe.add(addtoplaylist,0,8);


        rightframe.setHgap(10);
        rightframe.setVgap(10);

        rightframe.setGridLinesVisible(true);
        rightframe.getColumnConstraints().add(new ColumnConstraints(125));
        setRight(rightframe);


        //Add All button
        addall= new Button("Add all");
        addall.setPadding(new Insets(10,10,10,10));
        downframe.add(addall,0,0);

        //Load button
        load = new Button("Load");
        load.setPadding(new Insets(10,10,10,10));
        upperframe.add(load,2,0);


        //Save button
        save = new Button("Save");
        save.setPadding(new Insets(10,10,10,10));
        upperframe.add(save,3,0);


        //Choicebox
        choicebox.setPadding(new Insets(5,5,5,160));
        choicebox.getItems().addAll("Example1","Example2","Example3");
        upperframe.add(choicebox,0,0);
        upperframe.setHgap(30);
        upperframe.setVgap(5);

        setBottom(downframe);
        setTop(upperframe);
        setCenter(listviewplaylist);
        setLeft(listviewsong);

    }

}
