/**
 * Created by Sefa on 05.05.2017.
 */
package model;

public class Model{
    public SongList allsongs,playlist;

    public void setAllsongs(SongList allsongs){
        this.allsongs = allsongs;

    }
    public SongList getAllsongs(){
        return this.allsongs;
    }
    public void setPlaylist(SongList playlist){
        this.playlist = playlist;
    }
    public SongList getPlaylist(){
        return this.playlist;
    }
}

