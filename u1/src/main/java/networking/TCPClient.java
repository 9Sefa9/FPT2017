package networking;

import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class TCPClient {

    private String password;
    public TCPClient(){

    }
    public static void main(String[] args) throws RemoteException, NotBoundException,MalformedURLException{
        /*
        IContainer container = (IContainer) Naming.lookup("//localhost/tcpcontainer");

        //zum server senden
        container.setName("d");

        boolean checkpassw = container.checkPassword(password);
        */
        try (Socket serverCon = new Socket("localhost", 5020);
             BufferedReader in = new BufferedReader(new InputStreamReader(serverCon.getInputStream()));
             PrintWriter out = new PrintWriter(serverCon.getOutputStream())) {

            Scanner eingabe = new Scanner(System.in);
            out.write(eingabe.nextLine());
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
