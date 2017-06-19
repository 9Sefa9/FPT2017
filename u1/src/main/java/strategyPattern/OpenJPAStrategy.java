package strategyPattern;

import interfaces.SerializableStrategy;
import interfaces.Song;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.IOException;
import java.util.List;

public class OpenJPAStrategy implements SerializableStrategy{

    private EntityManagerFactory emf;
    private EntityManager em;
    List<model.Song> songs;

    @Override
    public void openWriteableSongs() throws IOException {
        emf = Persistence.createEntityManagerFactory("perUnit", System.getProperties());
        em = emf.createEntityManager();
    }

    @Override
    public void openReadableSongs() throws IOException {
        emf = Persistence.createEntityManagerFactory("perUnit", System.getProperties());
        em = emf.createEntityManager();
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
        if (songs.size() > 0) {
            Song s = songs.get(0);
            System.out.println(s);
            songs.remove(0);
            System.out.println(songs.size());
            return s;
        }
        return null;
    }

    public List<model.Song> readSongs(){
        Query q = em.createQuery("SELECT s FROM Song s");
        songs = (List<model.Song>)q.getResultList();
        return songs;
    }

    @Override
    public void closeReadable() {
        em.close();
        emf.close();
    }

    @Override
    public void closeWriteable() {
        em.close();
        emf.close();
    }
}
