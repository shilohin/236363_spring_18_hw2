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
                    "       FOREIGN KEY (PlaylistId) REFERENCES Playlist(PlaylistId) ON DELETE CASCADE, \n" +
                    "       FOREIGN KEY (SongId) REFERENCES Song(SongId) ON DELETE CASCADE, \n" +
                    "       PRIMARY KEY (PlaylistId,SongId)\n" +
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
                    "CREATE VIEW  SongInPlaylistView AS "+
                    "SELECT SongInPlaylist.PlaylistId, SongInPlaylist.SongId, Song.playCount  "+
                    "FROM SongInPlaylist, Song "+
                    "WHERE SongInPlaylist.SongId = Song.SongId";
            pstmt = connection.prepareStatement(statment1);
            pstmt.execute();
            // Create PlaylistView view
            String statment =" "+
                    "CREATE VIEW PlaylistView AS "+
                    "SELECT PlaylistId, COUNT(SongId) AS num_song, SUM(playCount) AS TotalPlayCount  "+
                    "FROM SongInPlaylistView "+
                    "GROUP BY PlaylistId ";
            pstmt = connection.prepareStatement(statment);
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
            pstmt = connection.prepareStatement("DROP TABLE IF EXISTS SongInPlaylist CASCADE");
            pstmt.execute();
            pstmt = connection.prepareStatement("DROP TABLE IF EXISTS UserFollowPlaylist");
            pstmt.execute();
            pstmt = connection.prepareStatement("DROP TABLE IF EXISTS Users");
            pstmt.execute();
            pstmt = connection.prepareStatement("DROP TABLE IF EXISTS Song");
            pstmt.execute();
            pstmt = connection.prepareStatement("DROP TABLE IF EXISTS Playlist");
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
        User user = getUserProfile(userId);
        if(user.equals(User.badUser())){
            ret = ReturnValue.NOT_EXISTS;
        }else if (user.getPremium() == true) {
            ret = ALREADY_EXISTS;
        }
        else {
            try { {
                    pstmt = connection.prepareStatement("UPDATE Users" +
                            " SET premium = ?" +
                            " WHERE UserId = ?");
                    pstmt.setBoolean(1, true);
                    pstmt.setInt(2, userId);
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
        User user = getUserProfile(userId);
        if(user.equals(User.badUser())){
            ret = ReturnValue.NOT_EXISTS;
        }else if (user.getPremium() == false) {
            ret = ALREADY_EXISTS;
        }
        else {
            try { {
                pstmt = connection.prepareStatement("UPDATE Users" +
                        " SET premium = ?" +
                        " WHERE UserId = ?");
                pstmt.setBoolean(1, false);
                pstmt.setInt(2, userId);
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
            pstmt = connection.prepareStatement("SELECT name FROM Song" +
                    " WHERE Song.SongId = ?");

            pstmt.setInt(1, song.getId());
            ResultSet resSet = pstmt.executeQuery();
            if (!resSet.next()) {
                ret = ReturnValue.NOT_EXISTS;
            }else {
                pstmt = connection.prepareStatement("UPDATE Song" +
                        " SET name = ?" +
                        " WHERE Song.SongId = ?");
                pstmt.setString(1, song.getName());
                pstmt.setInt(2, song.getId());
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
        Song song = getSong(songid);
        Playlist playlist = getPlaylist(playlistId);
        if(song.getId() == -1 || playlist.getId() == -1 ){
            ret = NOT_EXISTS;
        }
        else if(!song.getGenre().equals(playlist.getGenre())){
            ret = BAD_PARAMS;
        }
        else {
            try {
                pstmt = connection.prepareStatement("INSERT INTO SongInPlaylist(PlaylistId,SongId)" +
                        " VALUES (?, ?)");
                pstmt.setInt(1, playlistId);
                pstmt.setInt(2, songid);
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
        Song song = getSong(songId);
        if(song.getId() == -1 ){
            ret = NOT_EXISTS;
        } else {
            try {
                pstmt = connection.prepareStatement("UPDATE Song" +
                        " SET playCount = ?" +
                        " WHERE Song.SongId = ?");
                pstmt.setInt(1,song.getPlayCount() +times);
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
            /*pstmt = connection.prepareStatement("SELECT TotalFollows " +
                    " FROM (SELECT COUNT(UserId) AS TotalFollows " +
                    " FROM UserFollowPlaylist" +
                    " WHERE PlaylistId = ? ) AS foo");*/
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

    /*public static String getMostPopularSong(){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ResultSet result  = null;
        int res = -1;
        try {
            pstmt = connection.prepareStatement("SELECT MAX(TotalPlaylist) " +
                    " FROM (SELECT  SongId,COUNT(PlaylistId) as TotalPlaylist  " +
                    " FROM SongInPlaylist" +
                    "GROUP BY SongId ");
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
                if (res == -1) res = convertResultSetToint(result);
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
    }*/

    public static Integer getMostPopularPlaylist(){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ResultSet result  = null;
        int res = -1;
        try {
            //Step 1: Check there is any playlist:
            pstmt = connection.prepareStatement("SELECT COUNT (PlaylistId) AS numPlaylists" +
                    " FROM PlaylistId");
            result = pstmt.executeQuery();
            if(!result.next()){
                res = 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            res = 0;
        }
        try {
            if (res == -1)
                res = convertResultSetToint(result, "numPlaylists");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (res == 0) return res;
        res = -1;
        try {
            //Step 2: get max:
            pstmt = connection.prepareStatement("SELECT MAX(PlaylistId) AS bestPlaylist " +
                                                    "FROM (SELECT PlaylistId " +
                                                          "FROM PlaylistView " +
                                                          "WHERE TotalPlayCount = (SELECT MAX(TotalPlayCount) " +
                                                                                  "FROM PlaylistView)" +
                "                                         ) AS foo");
            result = pstmt.executeQuery();
            if(!result.next()){
                res = 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            res = 0;
        } finally {
            try {
                if (res == -1) res = convertResultSetToint(result, "bestPlaylist");
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
        return null;
    }

    public static ArrayList<Integer> getSimilarUsers(Integer userId){
        return null;
    }

    public static ArrayList<Integer> getTopCountryPlaylists(Integer userId) {
        return null;
    }

    public static ArrayList<Integer> getPlaylistRecommendation (Integer userId){
        return null;
    }

    public static ArrayList<Integer> getSongsRecommendationByGenre(Integer userId, String genre){
        return null;
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
}

