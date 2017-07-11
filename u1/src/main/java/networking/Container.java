package networking;

import model.SongList;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Container extends Remote{
    String test(String str) throws RemoteException;
    SongList getAllSongs() throws RemoteException;
    SongList getPlaylist() throws RemoteException;
    void addAllButton() throws RemoteException;
    void addToPlaylistButton() throws RemoteException;
    void deleteSongButton() throws RemoteException;
    void savePlaylistButton() throws RemoteException;
    void loadPlaylistButton() throws RemoteException;
    void loadSongListButton() throws RemoteException;
    void saveSongListButton() throws RemoteException;
    void playButton() throws RemoteException;
    void pauseButton() throws RemoteException;
    void nextButton() throws RemoteException;
    void updateAllSongs(SongList songs) throws RemoteException;
    void updatePlaylist(SongList songs) throws RemoteException;
}
