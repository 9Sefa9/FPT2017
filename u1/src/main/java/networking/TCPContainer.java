package networking;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class TCPContainer  extends UnicastRemoteObject {
    private String pw,name;
    public TCPContainer() throws RemoteException{

    }
    public void setPassword(String pw){
        this.pw = pw;
    }
    public String getPassword(String pw){
        return this.pw;
    }
    public boolean checkPassword(String pw){
        if(pw.equals(this.pw))
            return true;

        return false;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
}
