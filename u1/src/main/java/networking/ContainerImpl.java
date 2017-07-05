package networking;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ContainerImpl extends Remote {
    public void setPassword(String pw) throws RemoteException;
    public String getPassword(String pw) throws RemoteException;
    public boolean checkPassword(String pw) throws RemoteException;
    public void setName(String name) throws RemoteException;
    public String getName() throws RemoteException;
}
