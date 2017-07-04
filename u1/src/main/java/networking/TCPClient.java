package networking;

import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class TCPClient extends Thread{

    private String name="NAME",password="PASSWORD";

    public TCPClient(String name, String password){
        this.name  = name;
        this.password = password;
    }
    public void writeToServer(){
        try (Socket serverCon = new Socket("localhost", 5020);
             BufferedReader in = new BufferedReader(new InputStreamReader(serverCon.getInputStream()));
             PrintWriter out = new PrintWriter(serverCon.getOutputStream())) {

            out.write(this.name);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
/*
        IContainer container = (IContainer) Naming.lookup("//localhost/tcpcontainer");

        //zum server senden
        container.setName("d");

        boolean checkpassw = container.checkPassword(password);
        */