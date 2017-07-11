package networking;

import controller.Controller;
import model.SongList;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ContainerImpl extends UnicastRemoteObject implements Container {

    private Controller c;

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
    public SongList getAllSongs() {
        return this.c.getAllSongs();
    }

    @Override
    public SongList getPlaylist() {
        return this.c.getPlaylist();
    }

    @Override
    public void addAllButton() throws RemoteException {
        this.c.addAllButton();
    }

    @Override
    public void addToPlaylistButton() throws RemoteException {
        this.c.addToPlaylistButton();
    }

    @Override
    public void deleteSongButton() throws RemoteException {
        this.c.deleteSongButton();
    }

    @Override
    public void savePlaylistButton() throws RemoteException {
        this.c.savePlaylistButton();
    }

    @Override
    public void loadPlaylistButton() throws RemoteException {
        this.c.loadPlaylistButton();
    }

    @Override
    public void loadSongListButton() throws RemoteException {
        this.c.loadSongListButton();
    }

    @Override
    public void saveSongListButton() throws RemoteException {
        this.c.saveSongListButton();
    }

    @Override
    public void playButton() throws RemoteException {
        this.c.playButton();
    }

    @Override
    public void pauseButton() throws RemoteException {
        this.c.pauseButton();
    }

    @Override
    public void nextButton() throws RemoteException {
        this.c.nextButton();
    }

    @Override
    public void updateAllSongs() throws RemoteException {
        this.c.updateAllSongs(this.getAllSongs());
    }

    @Override
    public void updatePlaylist() throws RemoteException {
        this.c.updatePlaylist(this.getPlaylist());
    }

}