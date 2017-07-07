package networking;

import controller.Controller;

import java.io.*;
import java.net.*;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

public class TCPServer extends Thread {

    private String ServerPassword = "123";
    private ArrayList<Object> clientlist;

    public static void main(String[] args){
        new TCPServer().start();
    }

    //nimmt Client entgegen.
    public void run() {
        try (ServerSocket server = new ServerSocket(5020)) {

            while (true) {
                try {
                    Socket socket = server.accept();
                    clientlist = new ArrayList<>();
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

    ArrayList<Object> clientlist;
    private String ServerPassword;
    private String ClientPassword;
    private String ClientName;
    private Socket socket;

    public TCPServerThread(Socket socket, String ServerPassword,ArrayList<Object> clientlist) {
        this.ServerPassword = ServerPassword;
        this.socket = socket;
        this.clientlist = clientlist;
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
                    System.out.println("SERVICENAME::REMOTE OBJECT TO "+this.ClientName);
                    // Führe rmi durch was ist ein DIENSTNAME ?


                    synchronized (clientlist){
                        clientlist.add(ClientName);
                    }

                    out.write("remObj");
                    out.flush();

                    LocateRegistry.createRegistry(5020);//TODO
                    Remote remObj = new ContainerImpl();
                    Naming.rebind("//localhost:5020/remObj", remObj);

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

    /*
    public TCPServer() throws RemoteException,MalformedURLException {
        super();
        initialization();
    }

    public void initialization() throws RemoteException,MalformedURLException{
        TCPServer tcp = new TCPServer();
        Registry registry = LocateRegistry.createRegistry(5020);
        registry.rebind("//localhost:1099/tcp", tcp);

    }

    @Override
    public void setPassword(String pw) throws RemoteException {

    }

    @Override
    public String getPassword(String pw) throws RemoteException {
        return null;
    }

    @Override
    public boolean checkPassword(String pw) throws RemoteException {
        return false;
    }

    @Override
    public void setName(String name) throws RemoteException {

    }

    @Override
    public String getName() throws RemoteException {
        return null;
    }
    */























/*
    public void run() {
        try (ServerSocket server = new ServerSocket(5020)) {
            int connections = 0;

            while (true) {
                try {
                    Socket socket = server.accept();
                    connections++;
                    new TCPServerThread(connections, socket).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    class TCPServerThread extends Thread {
        private int name;
        private Socket socket;

        public TCPServerThread(int name, Socket socket) {
            this.name = name;
            this.socket = socket;
        }

        public void run() {
            String msg = "EchoServer: Verbindung " + name;
            System.out.println(msg + " hergestellt");
            try (InputStream in = socket.getInputStream();
                 OutputStream out = socket.getOutputStream()) {

                out.write(2);
                out.flush();

                System.out.println("Verbindung " + name + " wird beendet");
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
    }
    */

