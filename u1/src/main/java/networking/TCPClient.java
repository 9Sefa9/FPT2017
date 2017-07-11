package networking;

import controller.Controller;
import model.Model;
import view.View;

import java.io.*;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class TCPClient extends Thread{

    private static String password="123",remoteobject;
    private Container c;
    private Controller controller;

    public TCPClient(String password){
        this.password = password;
    }
    public TCPClient(Controller controller){
        this.controller = controller;
    }
    public void run(){
        try (Socket serverCon = new Socket("localhost", 5020);
             BufferedReader in = new BufferedReader(new InputStreamReader(serverCon.getInputStream()));
             PrintWriter out = new PrintWriter(serverCon.getOutputStream())) {

            //sendet passwort
            out.write("123\n");
            out.flush();

            //sendet Dienstnamen
            out.write(InetAddress.getLocalHost().getHostName() + "@" + InetAddress.getLocalHost().getHostAddress()+"\n");
            out.flush();

            remoteobject = in.readLine();
            System.out.println("RECEIVED REMOTEOBJECT::"+remoteobject);
            System.out.println("ACCESSING TO REMOTE OBJECT:: "+remoteobject);

            try {
                c = (Container) Naming.lookup("//127.0.0.1:5021/"+remoteobject);
                c.registerClient(new ContainerImpl(controller));
                //c.updateAllSongs();
                //c.updatePlaylist();
                System.out.println(c.getAllSongs());
            } catch (NotBoundException e) {
                e.printStackTrace();
            }
            System.out.println("Das ist ein : "+c.test("test")+"!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Container getC(){
        return c;
    }
}