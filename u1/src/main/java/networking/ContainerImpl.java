package networking;

import controller.Controller;
import model.SongList;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ContainerImpl extends UnicastRemoteObject implements Container {

    private Controller c;

    private ArrayList<Container> containers = new ArrayList<Container>();

    public ContainerImpl() throws RemoteException{

    }

    public ContainerImpl(Controller c) throws RemoteException{
        this.c = c;
    }
    @Override
    public String test(String str) throws RemoteException{
        return str;
    }

    @Override
    public synchronized SongList getAllSongs() {
        return this.c.getAllSongs();
    }

    @Override
    public synchronized SongList getPlaylist() {
        return this.c.getPlaylist();
    }

    @Override
    public synchronized void addAllButton() throws RemoteException {
        this.c.addAllButton();
    }

    @Override
    public synchronized void addToPlaylistButton() throws RemoteException {
        this.c.addToPlaylistButton();
    }

    @Override
    public synchronized void deleteSongButton() throws RemoteException {
        this.c.deleteSongButton();
    }

    @Override
    public synchronized void savePlaylistButton() throws RemoteException {
        this.c.savePlaylistButton();
    }

    @Override
    public synchronized void loadPlaylistButton() throws RemoteException {
        this.c.loadPlaylistButton();
    }

    @Override
    public synchronized void loadSongListButton() throws RemoteException {
        this.c.loadSongListButton();
    }

    @Override
    public synchronized void saveSongListButton() throws RemoteException {
        this.c.saveSongListButton();
    }

    @Override
    public synchronized void playButton() throws RemoteException {
        this.c.playButton();
    }

    @Override
    public synchronized void pauseButton() throws RemoteException {
        this.c.pauseButton();
    }

    @Override
    public synchronized void nextButton() throws RemoteException {
        this.c.nextButton();
    }

    @Override
    public synchronized void updateAllSongs(SongList songs) throws RemoteException {
        this.c.updateAllSongs(songs);
    }

    @Override
    public synchronized void updatePlaylist(SongList songs) throws RemoteException {
        System.out.println(containers);
        for(Container cs : containers)
            cs.updatePlaylist(songs);
        this.c.updatePlaylist(songs);
    }

    @Override
    public synchronized void registerClient(Container c) throws RemoteException {
        containers.add(c);
    }

}