
package view;
import interfaces.Song;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class View extends BorderPane{
    private ListView<Song> listviewsong,listviewplaylist;
    private Button addall,load,save,addtoplaylist,commit,play,pause;
    private SplitPane rightframe;
    private ChoiceBox<String> choicebox;
    private GridPane upperframe,downframe;
    public View(){
        setMaxSize(1024,1024);


        //instanziierungen
        choicebox = new ChoiceBox<>();
        listviewsong = new ListView<>();
        listviewplaylist = new ListView<>();
        rightframe = new SplitPane();
        upperframe = new GridPane();
        downframe = new GridPane();

        //splittpane
        rightframe.setMinSize(200,500);
        rightframe.setDividerPosition(200,200);
        setRight(rightframe);


        //Add All button
        addall= new Button("Add all");
        addall.setPadding(new Insets(10,10,10,10));
        downframe.add(addall,0,0);

        //Load button
        load = new Button("Load");
        load.setPadding(new Insets(10,10,10,10));
        upperframe.add(load,8,0);


        //Save button
        save = new Button("Save");
        save.setPadding(new Insets(10,10,10,10));
        upperframe.add(save,9,0);


        //Choicebox
        choicebox.setPadding(new Insets(5,5,5,5));
        choicebox.getItems().addAll("Song1","Song2","Song3");
        upperframe.add(choicebox,0,0);
        upperframe.setHgap(30);
        upperframe.setVgap(5);

        setBottom(downframe);
        setTop(upperframe);
        setCenter(listviewplaylist);
        setLeft(listviewsong);

    }

}
