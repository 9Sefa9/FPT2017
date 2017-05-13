package main;

import controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Model;
import view.View;

public class MainClass extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        View v = new View();
        Scene s = new Scene(v);
        Model m = new Model();

        Controller controller = new Controller();
        controller.link(m, v);

        //primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("YinYang.png")));
        primaryStage.setTitle("FPT Player v1.0");
        primaryStage.setScene(s);
        primaryStage.show();
    }
}
