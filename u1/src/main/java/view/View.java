/**
 * Created by Sefa on 05.05.2017.
 */
package view;
import interfaces.Song;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

public class View extends BorderPane{
    private ListView<Song> lv;

    public View(){
        lv = new ListView<>();
        lv.setPrefSize(300,300);
        setLeft(lv);
        setCenter(new Button("hi"));

    }

}
