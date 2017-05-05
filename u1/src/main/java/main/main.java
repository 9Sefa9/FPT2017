package main;

import controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Model;
import view.View;

public class main extends Application {

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

        primaryStage.setScene(s);
        primaryStage.show();
    }
}
