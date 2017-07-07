package networking;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Container extends Remote{
    public String test(String str) throws RemoteException;
}
