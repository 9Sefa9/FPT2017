package networking;


import java.io.*;
import java.net.*;

public class TCPServer extends Thread {

    protected String password;
    //nimmt Client entgegen.
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
}
  //Stellt eine Verbindung mit dem Client auf.
  class TCPServerThread extends Thread {
    private int name;
    private Socket socket;

    public TCPServerThread(int name, Socket socket) {
        this.name = name;
        this.socket = socket;
    }

    public void run() {
        System.out.println("Connected to Client #:"+name+"...");
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             OutputStream out = socket.getOutputStream()) {

            while(true){
            if((in.readLine().equals(this.password));

            }
            out.write(2);
            out.flush();

            System.out.println("Connection #"+ name +" closed");
            socket.close();
        } catch (IOException e1) {
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

