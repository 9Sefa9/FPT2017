package networking;

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

    public TCPClient(String password){
        this.password = password;
    }
    public static void main(String[]args)throws RemoteException,NotBoundException{
        try (Socket serverCon = new Socket("localhost", 5020);
             BufferedReader in = new BufferedReader(new InputStreamReader(serverCon.getInputStream()));
             PrintWriter out = new PrintWriter(serverCon.getOutputStream())) {

            //sendet passwort
            out.write("123\n");
            out.flush();

            //sendet Dienstnamen
            out.write(InetAddress.getLocalHost().getHostName()+"@"+InetAddress.getLocalHost().getHostAddress()+"\n");
            out.flush();

            remoteobject= in.readLine();
            System.out.println("RECEIVED REMOTEOBJECT::"+remoteobject);
            System.out.println("ACCESSING TO REMOTE OBJECT:: "+remoteobject);

            try {
                Thread.sleep(500);
            }catch (Exception e){
                e.printStackTrace();
            }
            Container c = (Container)Naming.lookup("//127.0.0.1:5020/"+remoteobject);
            System.out.println("Das ist ein : "+c.test("test")+"!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}