package strategyPattern;

import interfaces.SerializableStrategy;
import interfaces.Song;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

public class OpenJPAStrategy implements SerializableStrategy{

    private EntityManagerFactory emf;
    private EntityManager em;

    @Override
    public void openWriteableSongs() throws IOException {
        emf = Persistence.createEntityManagerFactory("perUnit");
        em = emf.createEntityManager();
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
        em.getTransaction().begin();
        em.persist(s);
        em.getTransaction().commit();
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
        em.close();
        emf.close();
    }
}
