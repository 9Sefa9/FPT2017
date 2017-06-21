package strategyPattern;

import interfaces.SerializableStrategy;
import interfaces.Song;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class OpenJPAStrategy implements SerializableStrategy{

    private EntityManagerFactory emf;
    private EntityManager em;
    List<model.Song> songs;

    public OpenJPAStrategy(){
        emf = Persistence.createEntityManagerFactory("perUnit", System.getProperties());
        em = emf.createEntityManager();
    }

    @Override
    public void openWriteableSongs() throws IOException {
        em.getTransaction().begin();
    }

    @Override
    public void openReadableSongs() throws IOException {
        em.getTransaction().begin();
    }

    public void setProperties(){
        Properties properties = new Properties();
        properties.put("openjpa.MetaDataFactory", "jpa(Types=" + model.Song.class.getName() + ")");
        properties.put("openjpa.ConnectionDriverName", "org.postgresql.Driver");
        properties.put("openjpa.ConnectionURL", "jdbc:sqlite:C:\\Users\\Leon\\Documents\\SQLite\\SongDB.db");
        properties.put("openjpa.RuntimeUnenhancedClasses", "supported");
        properties.put("openjpa.jdbc.SynchronizeMappings", "false");
        em = emf.createEntityManager(properties);
    }

    @Override
    public void openWriteablePlaylist() throws IOException {

    }

    @Override
    public void openReadablePlaylist() throws IOException {

    }

    @Override
    public void writeSong(Song s) throws IOException {
        em.persist(s);
    }

    public void deleteContent(){
        Query q = em.createQuery("DELETE FROM Song");
        q.executeUpdate();
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
        em.getTransaction().commit();
        em.close();
        emf.close();
    }

    @Override
    public void closeWriteable() {
        em.getTransaction().commit();
        em.close();
        emf.close();
    }



}
