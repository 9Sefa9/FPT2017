package networking;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ContainerImpl extends UnicastRemoteObject implements Container {

    public ContainerImpl() throws RemoteException{

    }
    @Override
    public String test(String str) throws RemoteException{
        return str;
    }
}