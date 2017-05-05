/**
 * Created by Sefa on 05.05.2017.
 */
package view;
import interfaces.Song;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;

public class View extends BorderPane{
    private ListView<Song> lv;
    private SplitPane song;
    private SplitPane playlist;
    private ScrollPane scrollp;

    public View(){
        setMaxSize(1024,1024);

        lv = new ListView<>();
        song = new SplitPane();
        playlist = new  SplitPane();
        song.setMinSize(500,500);


        playlist.setMinSize(500,500);


        setLeft(song);
        setCenter(playlist);


    }

}
