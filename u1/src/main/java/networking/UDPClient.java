package networking;

import controller.Controller;
import javafx.application.Platform;
import java.io.IOException;
import java.net.*;

public class UDPClient extends Thread{

    private Controller controller;

    public UDPClient (Controller controller)
    {
        this.controller = controller;
    }

    public void run(){
        InetAddress ia = null;

        try {
            ia = InetAddress.getByName("localhost");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        try (DatagramSocket datagramSocket = new DatagramSocket(5001)){

            try {
                while( true) {
                    String command = "CURRENTTIME:";
                    byte buffer[] = null;
                    buffer = command.getBytes();
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, ia, 5000);
                    datagramSocket.send(packet);

                    byte answer[] = new byte[1024];
                    packet = new DatagramPacket(answer, answer.length);
                    datagramSocket.receive(packet);

                    String time = new String(packet.getData(), 0, packet.getLength());

                    Platform.runLater(() -> controller.updateCurrentTime(time));

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

}
