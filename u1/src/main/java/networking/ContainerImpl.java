package networking;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ContainerImpl extends UnicastRemoteObject implements Container {

    public ContainerImpl() throws RemoteException{

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
}