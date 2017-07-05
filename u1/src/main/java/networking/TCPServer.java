package networking;


import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class TCPServer extends Thread {

    private String password = "123";

    public static void main(String[] args){
        new TCPServer().start();
    }

    //nimmt Client entgegen.
    public void run() {
        try (ServerSocket server = new ServerSocket(5020)) {
            int connections = 0;

            while (true) {
                try {
                    Socket socket = server.accept();
                    connections+=1;
                    new TCPServerThread(socket,password).start();
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
    private ArrayList<Object> ar;
    private String password;
    private Socket socket;

    public TCPServerThread(Socket socket, String password) {
        this.password = password;
        this.socket = socket;
    }

    public void run() {
        System.out.println("Connected with Client...");
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream())) {

            String incomingmsg = "";
            try {
            //while((incomingmsg = in.readLine()) != null){

                System.out.println("CLIENT NAME: "+in.readLine());
                if(in.readLine().equals(this.password)) {
                    System.out.println("PASSWORD::CORRECT");
                    System.out.println("CREATING RMI FOR"/*greif auf arraylist zu*/);
                    // Führe rmi durch o.ä..
                    ar.

                }else{
                    System.out.println("PASSWORD::INCORRECT, CLOSING CONNECTION...");
                    out.flush();
                    socket.close();
                }
            //}
                /*
                    if (incomingmsg.equals(this.password)) {
                        System.out.println(incomingmsg);
                        System.out.println("PASSSWORD CORRECT\n");
                        //eventuell teilaufgabe e)

                    } else {
                        System.out.println("PASSWORD INCORRECT!");

                    }
                    */


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

