package strategyPattern;


import interfaces.SerializableStrategy;
import interfaces.Song;

import java.io.IOException;
import java.sql.Connection;
import java.sql.*;


public class JDBCStrategy implements SerializableStrategy{

    private Connection con;
    private Statement st;
    private ResultSet rs;
    public JDBCStrategy(){
        try {
            Class.forName("org.postgresql.Driver");
        } catch( ClassNotFoundException e ) {
            e.printStackTrace();
        }
    }
    @Override
    public void openWriteableSongs() throws IOException {
        try{
            con = DriverManager.getConnection("" +
                "jdbc:sqlite:C:\\Users\\Sefa\\Desktop\\SQLite\\Library.db" +
                "");

        }catch (SQLException s){
            s.printStackTrace();
        }
    }


    @Override
    public void openWriteablePlaylist() throws IOException {

    }

    @Override
    public void openReadablePlaylist() throws IOException {

    }

    @Override
    public void openReadableSongs() throws IOException {
        try{
            con = DriverManager.getConnection("" +
                    "jdbc:sqlite:C:\\Users\\Sefa\\Desktop\\SQLite\\Library.db" +
                    "");

            st = con.createStatement();
            rs = st.executeQuery("SELECT id, title, artist, album, path "+
                    "FROM Library");
        }catch (SQLException s){
            s.printStackTrace();
        }
    }
    @Override
    public void writeSong(Song s) throws IOException {
        try(PreparedStatement ps = con.prepareStatement("INSERT INTO Library" +
                "(id, title, artist, album, path) VALUES(?,?,?,?,?)")){
        ps.setLong(1,s.getId());
        ps.setString(2,s.getTitle());
        ps.setString(3,s.getInterpret());
        ps.setString(4,s.getAlbum());
        ps.setString(5,s.getPath());
        ps.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Song readSong() throws IOException, ClassNotFoundException {

        try{
            if(!rs.next())
                return null;

            Long id = rs.getLong("id");
            String title = rs.getString("title");
            String interpreter = rs.getString("artist");
            String album = rs.getString("album");
            String path = rs.getString("path");


            return new model.Song(path,title,album,interpreter,id);

        }catch(SQLException s){
            s.printStackTrace();
            return null;
        }
    }

    @Override
    public void closeReadable() {

        try{
            if(rs != null)
                rs.close();
            if(con != null)
                con.close();
        }catch (SQLException s){
            s.printStackTrace();
        }
    }

    @Override
    public void closeWriteable() {
        try{
            if(con!=null)
                con.close();
        }catch(SQLException s){
            s.printStackTrace();
        }
    }
}
