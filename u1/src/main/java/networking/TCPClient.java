package networking;

import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class TCPClient extends Thread{

    private String name="client",password="123";

    public TCPClient(String name, String password){
        this.name  = name;
        this.password = password;
    }
    public static void main(String[]args){
        try (Socket serverCon = new Socket("localhost", 5020);
             BufferedReader in = new BufferedReader(new InputStreamReader(serverCon.getInputStream()));
             PrintWriter out = new PrintWriter(serverCon.getOutputStream())) {

            //sendet passwort
            out.write("123\n");
            out.flush();

            //sendet Dienstnamen
            out.write("client\n");
            out.flush();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
/*
        ContainerImpl container = (ContainerImpl) Naming.lookup("//localhost/tcpcontainer");

        //zum server senden
        container.setName("d");

        boolean checkpassw = container.checkPassword(password);
        */