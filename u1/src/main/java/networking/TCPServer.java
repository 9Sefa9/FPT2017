package networking;

import controller.Controller;

import java.io.*;
import java.net.*;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

import static networking.TCPServer.clientlist;

public class TCPServer extends Thread {

    private String ServerPassword = "123";
    private Remote remObj;
    public static ArrayList<Object> clientlist;

    /*public static void main(String[] args){
        new TCPServer().start();
    }*/

    //nimmt Client entgegen.

    public TCPServer(Remote remObj) throws RemoteException, MalformedURLException {
        this.remObj = remObj;
        LocateRegistry.createRegistry(5021);
        //Remote remObj = new Controller();
        Naming.rebind("//127.0.0.1:5021/remObj", this.remObj);
    }

    public void run() {
        try (ServerSocket server = new ServerSocket(5020)) {


            clientlist = new ArrayList<>();
            while (true) {
                try {
                    Socket socket = server.accept();

                    new TCPServerThread(socket,ServerPassword,clientlist).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
  //Stellt eine Verbindung mit dem Client auf.
  class TCPServerThread extends Thread {

    private String ServerPassword;
    private String ClientPassword;
    private String ClientName;
    private Socket socket;

    public TCPServerThread(Socket socket, String ServerPassword,ArrayList<Object> clientlists) {
        this.ServerPassword = ServerPassword;
        this.socket = socket;
        clientlist = clientlists;
    }

    public void run() {
        System.out.println("CLIENT CONNECTED...");
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream())) {

            try {
                this.ClientPassword = in.readLine();
                this.ClientName = in.readLine();

                System.out.println("CLIENTPASWORD: "+ClientPassword);
                System.out.println("ClIENTNAME: "+ClientName);

                if(ClientPassword.equals(this.ServerPassword)) {
                    System.out.println("PASSWORD::CORRECT");
                    System.out.println("SERVICENAME::CREATE REMOTE OBJECT FOR "+this.ClientName);
                    // Führe rmi durch was ist ein DIENSTNAME ?

                    synchronized (clientlist){
                        clientlist.add(ClientName);
                        System.out.println(clientlist);
                    }

                    out.write("remObj");
                    out.flush();

                    this.socket.close();

                    System.out.println("RMI STARTED...");
                }else{
                    System.out.println("PASSWORD::INCORRECT, CLOSING CONNECTION...");
                    out.flush();
                    socket.close();
                }

            }catch(SocketException s){
                System.out.println("Client Disconnected...");
                socket.close();
            }
        } catch (IOException  e1) {
            e1.printStackTrace();
        }

    }

}
