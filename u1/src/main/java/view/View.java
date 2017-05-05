/**
 * Created by Sefa on 05.05.2017.
 */
package view;
import interfaces.Song;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class View extends BorderPane{
    private ListView<Song> lvsong,lvplaylist;
    private Button addall,load,save,addtoplaylist,commit,play,pause;
    private SplitPane sp;
    private HBox upperframe;
    public View(){
        setMaxSize(1024,1024);

        //listview
        sp = new SplitPane();
        lvsong = new ListView<>();
        lvplaylist = new ListView<>();
        setCenter(lvplaylist);
        setLeft(lvsong);

        //splittpane
        sp.setMinSize(200,500);
        sp.setDividerPosition(200,200);
        //sp.getItems().add(new Button("teeest"));
        setRight(sp);


        //Add All button
        addall= new Button("Add all");
        addall.setPadding(new Insets(10,10,10,10));
        setBottom(addall);

        //Load button
        load = new Button("Load");
        load.setPadding(new Insets(10,10,10,10));

        //Save button
        save = new Button("Save");
        load.setPadding(new Insets(10,10,10,10));

        //frame
        upperframe = new HBox();
        upperframe.setSpacing(10);
        upperframe.setAlignment(Pos.CENTER);
        upperframe.getChildren().add(load);
        upperframe.getChildren().add(save);
        setTop(upperframe);


    }

}
