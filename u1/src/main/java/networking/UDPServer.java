package networking;

import controller.Controller;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

public class UDPServer extends Thread{

    private Controller controller;

    public UDPServer(Controller controller)
    {
        this.controller = controller;
    }

    public void run(){
        DatagramSocket datagramSocket = null;
        try {
            datagramSocket = new DatagramSocket(5000);
            while (true){
                DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
                try {
                    datagramSocket.receive(packet);
                    new ClientThread(packet, datagramSocket).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    class ClientThread extends Thread {

        private DatagramPacket packet;
        private DatagramSocket datagramSocket;

        public ClientThread(DatagramPacket packet, DatagramSocket datagramSocket){
            this.packet = packet;
            this.datagramSocket = datagramSocket;
        }

        public void run(){

            InetAddress ia = this.packet.getAddress();
            int port = packet.getPort();
            int length = packet.getLength();
            byte[] data = packet.getData();

            System.out.printf("Anfrage von %s vom Port %d mit der Länge %d:%n%s%n",
                    ia, port, length, new String(data, 0, length));

            String da = new String(packet.getData());
            Scanner scanner = new Scanner(da).useDelimiter(":");
            String command = scanner.next();

            if(command.equals("CURRENTTIME")){

                byte[] currentTime = controller.getCurrentTime().getBytes();
                packet = new DatagramPacket(currentTime, currentTime.length, ia, port);

            } else {

                byte[] unknown = "Unknown command".getBytes();
                packet = new DatagramPacket(unknown, unknown.length, ia, port);

            }

            try {
                datagramSocket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

}
