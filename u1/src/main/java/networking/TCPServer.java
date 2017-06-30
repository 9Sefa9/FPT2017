package networking;

import java.io.*;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class TCPServer {

    public void initialization() throws RemoteException,MalformedURLException{
        LocateRegistry.createRegistry(5020);

        Remote tcpcontainer = new TCPContainer();
        Naming.rebind("//localhost:1099/tcpcontainer", tcpcontainer);
        System.out.println("tcpcontainer started...");
    }

}






















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

