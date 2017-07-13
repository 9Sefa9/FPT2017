package main;

import controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Model;
import networking.ContainerImpl;
import networking.TCPServer;
import networking.UDPServer;
import view.View;

public class MainServer extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        View v = new View();
        Scene s = new Scene(v);
        Model m = new Model();

        Controller controller = new Controller();

        ContainerImpl container = new ContainerImpl(controller);

        TCPServer tcpServer = new TCPServer(container);
        //TCPServer tcpServer = new TCPServer(controller);
        tcpServer.start();

        controller.setContainer(container);
        controller.link(m, v);
        //UDPServer udpClient = new UDPServer(controller);
        //udpClient.start();

        primaryStage.setTitle("FPT Player v1.0");
        primaryStage.setScene(s);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
