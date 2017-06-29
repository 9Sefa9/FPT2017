package main;

import controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Model;
import networking.UDPClient;
import view.View;

public class MainClient extends Application {

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

        UDPClient udpClient = new UDPClient(controller);
        udpClient.start();

        primaryStage.setTitle("FPT Player v1.0");
        primaryStage.setScene(s);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


   /* class UDPClientThread extends Thread{

        public void run()
        {
            UDPClient udpClient = new UDPClient();
            udpClient.startClient();
        }

    }*/

}
