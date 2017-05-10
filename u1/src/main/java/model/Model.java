/**
 * Created by Sefa on 05.05.2017.
 */
package model;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import javafx.collections.ObservableList;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    public void handleAddAllButton(){
        DirectoryChooser dirChooser = new DirectoryChooser();
        File file = dirChooser.showDialog(new Stage());
        Path dir = Paths.get(file.getPath());
        SongList songList = new SongList();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir))
        {
            for (Path p : stream)
            {
                String name = p.getFileName().toString();
                if (name.endsWith(".mp3"))
                {
                    String track, interpret, album;
                    interpret = "";
                    album = "";
                    String songPath = file.getPath() + "\\" + name;
                    Mp3File song = new Mp3File(songPath);
                    track = name.substring(0, name.length() - 4);
                    if(song.hasId3v2Tag())
                    {
                        ID3v2 id3v2 = song.getId3v2Tag();
                        if(id3v2.getTitle() != null)
                            track = id3v2.getTitle();
                        if(id3v2.getArtist() != null)
                            interpret = id3v2.getArtist();
                        if(id3v2.getAlbum() != null)
                            album = id3v2.getAlbum();
                    }
                    songList.add(new Song(songPath, track, album, interpret));
                }
            }
            this.allsongs.setAll(songList);
        } catch (IOException | InvalidDataException | UnsupportedTagException e) {
            e.printStackTrace();
        }
    }

    public void handleAddToPlaylistButtion(ObservableList<Song> songs)
    {
        this.playlist.addAll(songs);
    }

}

