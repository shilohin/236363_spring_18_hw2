package technify;

import org.omg.PortableInterceptor.NON_EXISTENT;
import technify.business.*;

import static technify.business.ReturnValue.*;

import technify.data.DBConnector;
import technify.data.PostgreSQLErrorCodes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class   Solution {

    public static void createTables() {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            //Create table user
            pstmt = connection.prepareStatement("CREATE TABLE Users\n" +
                    "(\n" +
                    "    UserId integer NOT NULL,\n" +
                    "    name text NOT NULL,\n" +
                    "    country text NOT NULL,\n" +
                    "    premium boolean NOT NULL,\n" +
                    "    PRIMARY KEY (UserId),\n" +
                    "    CHECK (UserId > 0)\n" +
                    ")");
            pstmt.execute();
            //Create Song table
            pstmt = connection.prepareStatement("CREATE TABLE Song\n" +
                    "(\n" +
                    "       SongId integer NOT NULL,\n" +
                    "       name text NOT NULL,\n" +
                    "       genre text NOT NULL,\n" +
                    "       country text ,\n" +
                    "       playCount integer ,\n" +
                    "       PRIMARY KEY (SongId),\n" +
                    "       CHECK (SongId > 0),\n" +
                    "       CHECK (playCount >= 0)\n" +
                    ")");
            pstmt.execute();
            pstmt = connection.prepareStatement("CREATE TABLE Playlist\n" +
                    "(\n" +
                    "       PlaylistId integer NOT NULL,\n" +
                    "       genre text NOT NULL,\n" +
                    "       description text NOT NULL,\n" +
                    "       PRIMARY KEY (PlaylistId),\n" +
                    "       CHECK (PlaylistId> 0)\n" +
                    ")");
            pstmt.execute();
            pstmt = connection.prepareStatement("CREATE TABLE SongInPlaylist\n" +
                    "(\n" +
                    "       PlaylistId integer NOT NULL,\n" +
                    "       SongId integer NOT NULL,\n" +
                    "       PlaylistGenre text NOT NULL,\n" +
                    "       SongGenre text NOT NULL,\n" +
                    "       FOREIGN KEY (PlaylistId) REFERENCES Playlist(PlaylistId) ON DELETE CASCADE, \n" +
                    "       FOREIGN KEY (SongId) REFERENCES Song(SongId) ON DELETE CASCADE, \n" +
                    "       PRIMARY KEY (PlaylistId,SongId),\n" +
                    "       CHECK (PlaylistGenre = SongGenre)\n" +
                    ")");
            pstmt.execute();
            pstmt = connection.prepareStatement("CREATE TABLE UserFollowPlaylist\n" +
                    "(\n" +
                    "       PlaylistId integer NOT NULL,\n" +
                    "       UserId integer NOT NULL,\n" +
                    "       FOREIGN KEY (PlaylistId) REFERENCES Playlist(PlaylistId) ON DELETE CASCADE, \n" +
                    "       FOREIGN KEY (UserId) REFERENCES Users(UserId) ON DELETE CASCADE, \n" +
                    "       PRIMARY KEY (PlaylistId,UserId)\n" +
                    ")");
            pstmt.execute();

            // Create SongInPlaylistView view MATERIALIZED
            String statment1 =" "+
                    "CREATE  VIEW  SongInPlaylistView AS "+
                    "SELECT SongInPlaylist.PlaylistId, SongInPlaylist.SongId, Song.playCount  "+
                    "FROM SongInPlaylist, Song "+
                    "WHERE SongInPlaylist.SongId = Song.SongId";
            pstmt = connection.prepareStatement(statment1);
            pstmt.execute();
            // Create PlaylistView view
            String statment =" "+
                    "CREATE  VIEW PlaylistView AS "+
                    "SELECT PlaylistId, COUNT(SongId) AS num_song, CAST( SUM(playCount) AS INTEGER ) AS TotalPlayCount  "+
                    "FROM SongInPlaylistView "+
                    "GROUP BY PlaylistId ";
            pstmt = connection.prepareStatement(statment);
            pstmt.execute();

            // Create PlaylistView view
            String statment2 =" "+
                    "CREATE  VIEW SongInNumPlaylist AS "+
                    "SELECT  SongId, COUNT(PlaylistId) As TotalPlaylist " +
                    "FROM SongInPlaylist " +
                    "GROUP BY SongId " +
                    "ORDER BY SongId DESC ";
            pstmt = connection.prepareStatement(statment2);
            pstmt.execute();

            String statment4 =" "+
                    "CREATE  VIEW ListenToSamePlaylist AS "+
                    "SELECT  U1.UserId AS UID1, U2.UserId AS UID2 " +
                    "FROM UserFollowPlaylist AS U1, UserFollowPlaylist AS U2 " +
                    "WHERE U1.PlaylistId = U2.PlaylistId AND NOT U1.UserId = U2.UserId ";

            pstmt = connection.prepareStatement(statment4);
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static void clearTables()
    {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("DELETE FROM SongInPlaylist");
            pstmt.execute();
            pstmt = connection.prepareStatement("DELETE FROM UserFollowPlaylist");
            pstmt.execute();
            pstmt = connection.prepareStatement("DELETE FROM Users");
            pstmt.execute();
            pstmt = connection.prepareStatement("DELETE FROM Song");
            pstmt.execute();
            pstmt = connection.prepareStatement("DELETE FROM Playlist");
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static void dropTables()
    {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {

            pstmt = connection.prepareStatement("DROP VIEW IF EXISTS ListenToSamePlaylist CASCADE");
            pstmt.execute();
            pstmt = connection.prepareStatement("DROP TABLE IF EXISTS SongInPlaylist CASCADE");
            pstmt.execute();
            pstmt = connection.prepareStatement("DROP TABLE IF EXISTS UserFollowPlaylist CASCADE");
            pstmt.execute();
            pstmt = connection.prepareStatement("DROP TABLE IF EXISTS Users CASCADE");
            pstmt.execute();
            pstmt = connection.prepareStatement("DROP TABLE IF EXISTS Song CASCADE");
            pstmt.execute();
            pstmt = connection.prepareStatement("DROP TABLE IF EXISTS Playlist CASCADE");
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static ReturnValue addUser(User user)
    {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ReturnValue ret = ReturnValue.OK;
        try {
            pstmt = connection.prepareStatement("INSERT INTO Users(UserId,name,country,premium)" +
                    " VALUES (?, ?, ?, ?)");
            pstmt.setInt(1, user.getId());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getCountry());
            pstmt.setBoolean(4, user.getPremium());
            pstmt.execute();


        } catch (SQLException e) {
            e.printStackTrace();
            ret = getReturnValueFromSQLException(e);
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
                return ret;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;

    }

    public static User getUserProfile(Integer userId)
    {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ResultSet user = null;
        User new_user = User.badUser();
        boolean bad_user = false;
        try {
            pstmt = connection.prepareStatement("SELECT * " +
                    " FROM Users" +
                    " WHERE Users.UserId = ?");
            pstmt.setInt(1, userId);
            user = pstmt.executeQuery();
            if(!user.next()){
                bad_user = true;
            }
        } catch (SQLException e) {
        e.printStackTrace();
        bad_user = true;
        } finally {
            try {
                if (!bad_user) new_user = convertResultSetToUser(user);
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
                return new_user;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static ReturnValue deleteUser(User user)
    {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ReturnValue ret = ReturnValue.OK;

        try {
            pstmt = connection.prepareStatement("DELETE FROM Users" +
                    " WHERE UserId = ? ");

            pstmt.setInt(1, user.getId());
            Integer deleted = pstmt.executeUpdate();
            if (deleted == 0) {
                ret = ReturnValue.NOT_EXISTS;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            ret = getReturnValueFromSQLException(e);
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
                return ret;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static ReturnValue updateUserPremium(Integer userId)
    {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ReturnValue ret = ReturnValue.OK;
        try { {
                pstmt = connection.prepareStatement("UPDATE Users" +
                        " SET premium = ?" +
                        " WHERE UserId = ? AND NOT premium");
                pstmt.setBoolean(1, true);
                pstmt.setInt(2, userId);
                int updated = pstmt.executeUpdate();
                if (updated == 0){
                    pstmt = connection.prepareStatement("UPDATE Users" +
                            " SET premium = ?" +
                            " WHERE UserId = ?");
                    pstmt.setBoolean(1, true);
                    pstmt.setInt(2, userId);
                    updated = pstmt.executeUpdate();
                    if (updated > 0) ret = ALREADY_EXISTS;
                    else
                        ret = NOT_EXISTS;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            ret = getReturnValueFromSQLException(e);
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
                return ret;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            connection.close();
            return ret;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ReturnValue updateUserNotPremium(Integer userId)
    {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ReturnValue ret = ReturnValue.OK;
        try { {
            pstmt = connection.prepareStatement("UPDATE Users" +
                    " SET premium = ?" +
                    " WHERE UserId = ? AND premium");
            pstmt.setBoolean(1, false);
            pstmt.setInt(2, userId);
            int updated = pstmt.executeUpdate();
            if (updated == 0){
                pstmt = connection.prepareStatement("UPDATE Users" +
                        " SET premium = ?" +
                        " WHERE UserId = ?");
                pstmt.setBoolean(1, false);
                pstmt.setInt(2, userId);
                updated = pstmt.executeUpdate();
                if (updated > 0) ret = ALREADY_EXISTS;
                else
                    ret = NOT_EXISTS;
            }
        }
        } catch (SQLException e) {
            e.printStackTrace();
            ret = getReturnValueFromSQLException(e);
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
                return ret;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            connection.close();
            return ret;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    public static ReturnValue addSong(Song song)
    {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ReturnValue ret = ReturnValue.OK;
        try {
            pstmt = connection.prepareStatement("INSERT INTO Song(SongId,name,genre,country,playCount)" +
                    " VALUES (?, ?, ?, ?,?)");
            pstmt.setInt(1, song.getId());
            pstmt.setString(2,song.getName());
            pstmt.setString(3, song.getGenre());
            pstmt.setString(4, song.getCountry());
            pstmt.setInt(5, 0);
            pstmt.execute();


        } catch (SQLException e) {
            e.printStackTrace();
            ret = getReturnValueFromSQLException(e);
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
                return ret;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Song getSong(Integer songId)
    {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ResultSet song = null;
        Song new_song = Song.badSong();
        boolean bad_song = false;
        try {
            pstmt = connection.prepareStatement("SELECT * " +
                    " FROM Song" +
                    " WHERE Song.SongId = ?");
            pstmt.setInt(1, songId);;
            song = pstmt.executeQuery();
            if(!song.next()){
                bad_song = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            bad_song = true;
        } finally {
            try {
                if (!bad_song) new_song = convertResultSetToSong(song);
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
                return new_song;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static ReturnValue deleteSong(Song song)
    {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ReturnValue ret = ReturnValue.OK;

        try {
            pstmt = connection.prepareStatement("DELETE FROM Song" +
                    " WHERE Song.SongId = ? ");

            pstmt.setInt(1, song.getId());
            Integer deleted = pstmt.executeUpdate();
            if (deleted == 0) {
                ret = ReturnValue.NOT_EXISTS;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            ret = getReturnValueFromSQLException(e);
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
                return ret;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static ReturnValue updateSongName(Song song)
    {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ReturnValue ret = ReturnValue.OK;

        try {
            pstmt = connection.prepareStatement("UPDATE Song" +
                    " SET name = ?" +
                    " WHERE Song.SongId = ?");
            pstmt.setString(1, song.getName());
            pstmt.setInt(2, song.getId());
            int updated = pstmt.executeUpdate();
            if (updated == 0) ret = NOT_EXISTS;
        } catch (SQLException e) {
            e.printStackTrace();
            ret = getReturnValueFromSQLException(e);
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
                return ret;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static ReturnValue addPlaylist(Playlist playlist)
    {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ReturnValue ret = ReturnValue.OK;
        try {
            pstmt = connection.prepareStatement("INSERT INTO Playlist(PlaylistId,genre,description)" +
                    " VALUES (?, ?, ?)");
            pstmt.setInt(1, playlist.getId());
            pstmt.setString(2,playlist.getGenre());
            pstmt.setString(3, playlist.getDescription());
            pstmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            ret = getReturnValueFromSQLException(e);
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
                return ret;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Playlist getPlaylist(Integer playlistId)
    {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ResultSet playlist = null;
        Playlist new_playlist = Playlist.badPlaylist();
        boolean bad_playlist = false;
        try {
            pstmt = connection.prepareStatement("SELECT * " +
                    " FROM Playlist" +
                    " WHERE Playlist.PlaylistId = ?");
            pstmt.setInt(1, playlistId);;
            playlist = pstmt.executeQuery();
            if(!playlist.next()){
                bad_playlist = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            bad_playlist = true;
        } finally {
            try {
                if (!bad_playlist) new_playlist = convertResultSetToPlaylist(playlist);
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
                return new_playlist;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static ReturnValue deletePlaylist(Playlist playlist)
    {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ReturnValue ret = ReturnValue.OK;

        try {
            pstmt = connection.prepareStatement("DELETE FROM Playlist" +
                    " WHERE Playlist.PlaylistId = ? ");

            pstmt.setInt(1, playlist.getId());
            Integer deleted = pstmt.executeUpdate();
            if (deleted == 0) {
                ret = ReturnValue.NOT_EXISTS;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            ret = getReturnValueFromSQLException(e);
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
                return ret;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static ReturnValue updatePlaylist(Playlist playlist)
    {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ReturnValue ret = ReturnValue.OK;

        try {
            pstmt = connection.prepareStatement("SELECT description FROM Playlist" +
                    " WHERE Playlist.playlistId = ?");

            pstmt.setInt(1, playlist.getId());
            ResultSet resSet = pstmt.executeQuery();
            if (!resSet.next()) {
                ret = ReturnValue.NOT_EXISTS;
            }else {
                pstmt = connection.prepareStatement("UPDATE playlist" +
                        " SET description = ?" +
                        " WHERE playlist.PlaylistId = ?");
                pstmt.setString(1, playlist.getDescription());
                pstmt.setInt(2, playlist.getId());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            ret = getReturnValueFromSQLException(e);
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
                return ret;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static ReturnValue addSongToPlaylist(Integer songid, Integer playlistId){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ReturnValue ret = ReturnValue.OK;
        try {
            pstmt = connection.prepareStatement("INSERT INTO SongInPlaylist(PlaylistId,SongId,PlaylistGenre,SongGenre)" +
                    " VALUES (?, ?, (SELECT genre FROM Playlist WHERE PlaylistId = ?), " +
                    "(SELECT genre FROM Song WHERE SongId = ?))");
            pstmt.setInt(1, playlistId);
            pstmt.setInt(2, songid);
            pstmt.setInt(3, playlistId);
            pstmt.setInt(4, songid);
            pstmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            ret = getReturnValueFromSQLException(e);
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
                return ret;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            connection.close();
            return ret;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ReturnValue removeSongFromPlaylist(Integer songid, Integer playlistId){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ReturnValue ret = ReturnValue.OK;

        try {
            pstmt = connection.prepareStatement("DELETE FROM SongInPlaylist" +
                    " WHERE SongInPlaylist.SongId = ?  AND SongInPlaylist.PlaylistId = ?");

            pstmt.setInt(1, songid);
            pstmt.setInt(2, playlistId);
            Integer deleted = pstmt.executeUpdate();
            if (deleted == 0) {
                ret = ReturnValue.NOT_EXISTS;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            ret = getReturnValueFromSQLException(e);
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
                return ret;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static ReturnValue followPlaylist(Integer userId, Integer playlistId){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ReturnValue ret = ReturnValue.OK;

            try {
                pstmt = connection.prepareStatement("INSERT INTO UserFollowPlaylist(UserId,playlistId)" +
                        " VALUES (?, ?)");
                pstmt.setInt(1, userId);
                pstmt.setInt(2, playlistId);
                pstmt.execute();

            } catch (SQLException e) {
                e.printStackTrace();
                ret = getReturnValueFromSQLException(e);
            } finally {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    connection.close();
                    return ret;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        return null;
    }

    public static ReturnValue stopFollowPlaylist(Integer userId, Integer playlistId){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ReturnValue ret = ReturnValue.OK;

        try {
            pstmt = connection.prepareStatement("DELETE FROM UserFollowPlaylist" +
                    " WHERE UserFollowPlaylist.UserId = ?  AND UserFollowPlaylist.PlaylistId = ?");

            pstmt.setInt(1, userId);
            pstmt.setInt(2, playlistId);
            Integer deleted = pstmt.executeUpdate();
            if (deleted == 0) {
                ret = ReturnValue.NOT_EXISTS;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            ret = getReturnValueFromSQLException(e);
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
                return ret;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static ReturnValue songPlay(Integer songId, Integer times){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ReturnValue ret = ReturnValue.OK;
        try {
            pstmt = connection.prepareStatement("UPDATE Song" +
                    " SET playCount = playCount + ?" +
                    " WHERE Song.SongId = ?");
            pstmt.setInt(1,times);
            pstmt.setInt(2, songId);
            int affected = pstmt.executeUpdate();
            if (affected == 0)
                ret = NOT_EXISTS;
            if (affected == 1)
                ret = OK;
        } catch (SQLException e) {
            e.printStackTrace();
            ret = getReturnValueFromSQLException(e);
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
                return ret;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            connection.close();
            return ret;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Integer getPlaylistTotalPlayCount(Integer playlistId){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ResultSet result  = null;
        int res = -1;
        try {
            pstmt = connection.prepareStatement("SELECT TotalPlayCount " +
                    " FROM PlaylistView" +
                    " WHERE PlaylistView.PlaylistId = ?");
            pstmt.setInt(1, playlistId);;
            result = pstmt.executeQuery();
            if(!result.next()){
                res = 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            res = 0;
        } finally {
            try {
                if (res == -1) res = convertResultSetToint(result, "TotalPlayCount");
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
                return res;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Integer getPlaylistFollowersCount(Integer playlistId){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ResultSet result  = null;
        int res = -1;
        try {
            pstmt = connection.prepareStatement("SELECT COUNT (UserId) AS TOTALFOLLOWS" +
                    " FROM UserFollowPlaylist" +
                    " WHERE PlaylistId = ? ");
            pstmt.setInt(1, playlistId);
            result = pstmt.executeQuery();
            if(!result.next()){
                res = 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            res = 0;
        } finally {
            try {
                if (res == -1) res = convertResultSetToint(result, "TOTALFOLLOWS");
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
                return res;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String getMostPopularSong(){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ResultSet result  = null;
        String res = null;
        int total = -1 ;
        try {
            pstmt = connection.prepareStatement("SELECT SongId " +
                    " FROM SongInNumPlaylist " +
                    "WHERE TotalPlaylist = ( SELECT MAX(TotalPlaylist) FROM SongInNumPlaylist )  ");
            result = pstmt.executeQuery();
            if(!result.next()){
                    res = "No songs";
                total = 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            res = null;
        } if(total == -1){
            try {
                pstmt = connection.prepareStatement("SELECT name " +
                        " FROM Song " +
                        "WHERE SongId = ?  ");
                pstmt.setInt(1, convertResultSetToint(result,"SongId"));;
                result = pstmt.executeQuery();
                while (result.next()) {
                    res = convertResultSetToString(result, "name");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                res = null;
            }
        }
                 try {
                     pstmt.close();
                 } catch (SQLException e) {
                     e.printStackTrace();
                 }
                 try {
                     connection.close();
                     return res;
                 } catch (SQLException e) {
                     e.printStackTrace();
                 }


        return null;
    }

    public static Integer getMostPopularPlaylist(){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ResultSet result  = null;
        int res = -1;
        try {
            String statement = " SELECT RES, TC FROM (" +
                    " SELECT Playlist.PlaylistId AS RES, COALESCE(TotalPlayCount,0) AS TC"+
                    " FROM Playlist" +
                    " LEFT OUTER JOIN PlaylistView ON PlaylistView.PlaylistId = Playlist.PlaylistId ) as RESES" +
                    " ORDER BY TC DESC, RES DESC";
            pstmt = connection.prepareStatement(statement);
            result = pstmt.executeQuery();
            if (!result.next()){
                res = 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            res = 0;
        } finally {
            try {
                if (res == -1)
                    res = convertResultSetToint(result, "RES");
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
                return res;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static ArrayList<Integer> hottestPlaylistsOnTechnify(){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ResultSet mov_recom = null;
        ArrayList<Integer> output = new ArrayList<Integer>();

        try{
            String statment =" "+
                    " SELECT PlaylistId, (TotalPlayCount/num_song) AS rating" +
                    " FROM PlaylistView" +
                    " ORDER BY rating DESC, PlaylistId ASC " +
                    " LIMIT 10";
            pstmt = connection.prepareStatement(statment);
            mov_recom = pstmt.executeQuery();
            //DBConnector.printResults(mov_recom);
            while(mov_recom.next()){
                output.add(convertResultSetToint(mov_recom,"PlaylistId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            output = new ArrayList<>();
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
                return output;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return output;
    }

    public static ArrayList<Integer> getSimilarUsers(Integer userId){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ResultSet mov_recom = null;
        ArrayList<Integer> output = new ArrayList<Integer>();

        try{
                String count_user_playlists = " SELECT COUNT(UserId) FROM UserFollowPlaylist WHERE UserId = ? ";
                String count_similar = " SELECT UID1, COUNT(UID1) as SIM FROM ListenToSamePlaylist WHERE UID2 = ? GROUP BY UID1";
                String statment = "" +
                        " SELECT UID1 " +
                        " FROM (" + count_similar + ") as SIMILARS " +
                        " WHERE SIMILARS.SIM >= 0.75 * (" + count_user_playlists + ") " +
                        " ORDER BY UID1 ASC LIMIT 10";
                pstmt = connection.prepareStatement(statment);
                pstmt.setInt(1, userId);
                pstmt.setInt(2, userId);
                mov_recom = pstmt.executeQuery();
                //DBConnector.printResults(mov_recom);
                while (mov_recom.next()) {
                    output.add(convertResultSetToint(mov_recom, "UID1"));
                }
        } catch (SQLException e) {
            e.printStackTrace();
            output = new ArrayList<>();
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
                return output;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return output;
    }

    public static ArrayList<Integer> getTopCountryPlaylists(Integer userId) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ResultSet mov_recom = null;
        ArrayList<Integer> output = new ArrayList<Integer>();

        try{
            String relevant_songs = "" +
                    " SELECT SongId FROM Song, Users WHERE Song.country = Users.country AND Users.UserId = ? AND Users.premium";
            String relevat_playlists = "" +
                    " SELECT PlaylistId " +
                    " FROM PlaylistView" +
                    " WHERE EXISTS ( SELECT * FROM SongInPlaylist, (" + relevant_songs + ") AS RelSongs " +
                    "                WHERE PlaylistView.PlaylistId = SongInPlaylist.PlaylistId " +
                    "                AND SongInPlaylist.SongId = RelSongs.SongId ) " +
                    " ORDER BY TotalPlayCount DESC, PlaylistId ASC "+
                    " LIMIT 10 ";
            pstmt = connection.prepareStatement(relevat_playlists);
            pstmt.setInt(1,userId);
            mov_recom = pstmt.executeQuery();

            while(mov_recom.next()){
                output.add(convertResultSetToint(mov_recom,"PlaylistId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            output = new ArrayList<>();
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
                return output;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return output;
    }

    public static ArrayList<Integer> getPlaylistRecommendation (Integer userId){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ResultSet mov_recom = null;
        ArrayList<Integer> output = new ArrayList<Integer>();
        try{
            String count_user_playlists = " SELECT COUNT(UserId) FROM UserFollowPlaylist WHERE UserId = ? ";
            String count_similar = " SELECT UID1, COUNT(UID1) as SIM FROM ListenToSamePlaylist WHERE UID2 = ? GROUP BY UID1";
            String similarUsers = "" +
                    " SELECT UID1 AS UID" +
                    " FROM (" + count_similar + ") as SIMILARS " +
                    " WHERE SIMILARS.SIM >= 0.75 * (" + count_user_playlists + ") ";
            String followedBySimilar = "" +
                    " SELECT PlaylistId" +
                    " FROM UserFollowPlaylist, (" + similarUsers + ") AS similarIds" +
                    " WHERE UserFollowPlaylist.UserId = similarIds.UID GROUP BY PlaylistId ORDER BY PlaylistId ASC ";
            String filterUserPlaylists = "" +
                    "SELECT PlaylistId AS FPL FROM (" + followedBySimilar + ") AS SPL WHERE NOT EXISTS ( SELECT " +
                    " * FROM UserFollowPlaylist WHERE UserFollowPlaylist.PlaylistId = SPL.PlaylistId AND " +
                    " UserFollowPlaylist.UserID = ? )";
            String similarFollowersCount =" "+
                    "SELECT  PlaylistId, COUNT(UserId) As NumFollowers " +
                    "FROM UserFollowPlaylist " +
                    "WHERE EXISTS ( SELECT * FROM (" + similarUsers +") as SIM2 WHERE UserFollowPlaylist.UserId = SIM2.UID )" +
                    "GROUP BY PlaylistId " +
                    "ORDER BY NumFollowers DESC, PlaylistId ASC";

            String statment = "" +
                    "SELECT PlaylistId, NumFollowers "+
                    "FROM ("+ filterUserPlaylists +") AS nonUser, (" + similarFollowersCount + ") AS PlaylistNumFollowers " +
                    "WHERE nonUser.FPL = PlaylistNumFollowers.PlaylistId " +
                    "ORDER BY NumFollowers DESC, PlaylistId ASC LIMIT 5";
            pstmt = connection.prepareStatement(statment);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, userId);
            pstmt.setInt(3, userId);
            pstmt.setInt(4, userId);
            pstmt.setInt(5, userId);
            mov_recom = pstmt.executeQuery();
            //DBConnector.printResults(mov_recom);
            while (mov_recom.next()) {
                output.add(convertResultSetToint(mov_recom, "PlaylistId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            output = new ArrayList<>();
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
                return output;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return output;
    }

    public static ArrayList<Integer> getSongsRecommendationByGenre(Integer userId, String genre){

        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ResultSet mov_recom = null;
        ArrayList<Integer> output = new ArrayList<Integer>();
        try{
            String songInUserPlayList = " SELECT * FROM SongInPlaylist, UserFollowPlaylist WHERE " +
                    " SongInPlaylist.SongId = Song.SongId AND UserFollowPlaylist.PlaylistId = SongInPlaylist.PlaylistId " +
                    " AND UserFollowPlaylist.UserId = ? ";
            String statment = "" +
                    " SELECT SongId, PlayCount" +
                    " FROM Song " +
                    " WHERE Song.genre = ? AND NOT EXISTS (" + songInUserPlayList + ") " +
                    " ORDER BY PlayCount DESC, SongId ASC" +
                    " LIMIT 10";
            pstmt = connection.prepareStatement(statment);
            pstmt.setString(1, genre);
            pstmt.setInt(2, userId);
            mov_recom = pstmt.executeQuery();
            //DBConnector.printResults(mov_recom);
            while (mov_recom.next()) {
                output.add(convertResultSetToint(mov_recom, "SongId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            output = new ArrayList<>();
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
                return output;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return output;
    }

    public static ReturnValue getReturnValueFromSQLException(SQLException e) {
        if (Integer.valueOf(e.getSQLState()) == PostgreSQLErrorCodes.NOT_NULL_VIOLATION.getValue()) {
            return ReturnValue.BAD_PARAMS;
        }
        if (Integer.valueOf(e.getSQLState()) == PostgreSQLErrorCodes.FOREIGN_KEY_VIOLATION.getValue()) {
            return ReturnValue.NOT_EXISTS;
        }
        if (Integer.valueOf(e.getSQLState()) == PostgreSQLErrorCodes.UNIQUE_VIOLATION.getValue()) {
            return ReturnValue.ALREADY_EXISTS;
        }
        if (Integer.valueOf(e.getSQLState()) == PostgreSQLErrorCodes.CHECK_VIOLATION.getValue()) {
            return ReturnValue.BAD_PARAMS;
        }
        return ReturnValue.ERROR;
    }

    public static User convertResultSetToUser(ResultSet result) throws SQLException {
        User user = new User();
        user.setId(result.getInt("UserId"));
        user.setName(result.getString("name"));
        user.setCountry(result.getString("country"));
        user.setPremium(result.getBoolean("premium"));
        return user;
    }

    public static Song convertResultSetToSong(ResultSet result) throws SQLException {
        Song song = new Song();
        song.setId(result.getInt("SongId"));
        song.setName(result.getString("name"));
        song.setCountry(result.getString("country"));
        song.setGenre(result.getString("genre"));
        song.setPlayCount(result.getInt("playCount"));
        return song;
    }

    public static Playlist convertResultSetToPlaylist(ResultSet result) throws SQLException {
        Playlist playlist = new Playlist();
        playlist.setId(result.getInt("PlaylistId"));
        playlist.setGenre(result.getString("genre"));
        playlist.setDescription(result.getString("description"));
        return playlist;
    }

    public static int convertResultSetToint(ResultSet result, String collumn) throws SQLException {
        int playCount = (result.getInt(collumn));
        return playCount;
    }

    public static String convertResultSetToString(ResultSet result, String string) throws SQLException {
        String playCount = (result.getString(string));
        return playCount;
    }
}

