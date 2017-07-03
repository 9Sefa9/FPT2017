package networking;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class TCPClient {

    private String password;
    public void initialization() throws RemoteException, NotBoundException,MalformedURLException{
        IContainer container = (IContainer) Naming.lookup("//localhost/tcpcontainer");

        //zum server senden
        container.setName("d");

        boolean checkpassw = container.checkPassword(password);

    }

}
