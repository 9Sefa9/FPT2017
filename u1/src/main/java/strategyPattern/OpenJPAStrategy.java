package strategyPattern;

import interfaces.SerializableStrategy;
import interfaces.Song;

import java.io.IOException;

public class OpenJPAStrategy implements SerializableStrategy{

    @Override
    public void openWriteableSongs() throws IOException {

    }

    @Override
    public void openReadableSongs() throws IOException {

    }

    @Override
    public void openWriteablePlaylist() throws IOException {

    }

    @Override
    public void openReadablePlaylist() throws IOException {

    }

    @Override
    public void writeSong(Song s) throws IOException {

    }

    @Override
    public Song readSong() throws IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public void closeReadable() {

    }

    @Override
    public void closeWriteable() {

    }
}
