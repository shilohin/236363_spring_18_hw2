package technify;

import org.junit.Test;
import technify.business.Playlist;
import technify.business.Song;
import technify.business.User;

import static org.junit.Assert.assertEquals;
import static technify.business.ReturnValue.*;
import static technify.printer.selectTable;


public class MostPopularPlaylist extends  AbstractTest {
    @Test
    public void addRemoveSongToPlaylistGoodPath()
    {
        Song s = new Song();
        s.setId(1);
        s.setName("A");
        s.setGenre("A");
        s.setCountry("Aa");
        s.setPlayCount(0);
        assertEquals(OK, Solution.addSong(s));

        Playlist p = new Playlist();
        p.setId(2);
        p.setGenre("A");
        p.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p));

        assertEquals(OK, Solution.addSongToPlaylist(1,2));
        assertEquals(OK, Solution.removeSongFromPlaylist(1,2));
    }

    @Test
    public void addRemoveSongToPlaylistBadPath()
    {
        Song s = new Song();
        s.setId(1);
        s.setName("A");
        s.setGenre("A");
        s.setCountry("Aa");
        s.setPlayCount(0);
        assertEquals(OK, Solution.addSong(s));

        Playlist p = new Playlist();
        p.setId(2);
        p.setGenre("B");
        p.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p));

        Playlist p2 = new Playlist();
        p2.setId(3);
        p2.setGenre("A");
        p2.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p2));

        assertEquals(NOT_EXISTS, Solution.addSongToPlaylist(1,1));
        assertEquals(NOT_EXISTS, Solution.addSongToPlaylist(2,1));
        assertEquals(BAD_PARAMS, Solution.addSongToPlaylist(1,2));

        assertEquals(OK, Solution.addSongToPlaylist(1,3));
        assertEquals(ALREADY_EXISTS, Solution.addSongToPlaylist(1,3));
        assertEquals(NOT_EXISTS, Solution.removeSongFromPlaylist(1,1));
        assertEquals(NOT_EXISTS, Solution.removeSongFromPlaylist(3,1));
        assertEquals(OK, Solution.removeSongFromPlaylist(1,3));
        assertEquals(NOT_EXISTS, Solution.removeSongFromPlaylist(1,3));
    }

    @Test
    public void followPlaylistGoodPath()
    {
        Playlist p = new Playlist();
        p.setId(1);
        p.setGenre("A");
        p.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p));

        User v = new User();
        v.setId(1);
        v.setName("A");
        v.setCountry("a");
        v.setPremium(true);
        assertEquals(OK, Solution.addUser(v));

        assertEquals(OK, Solution.followPlaylist(1,1));
        assertEquals(OK, Solution.stopFollowPlaylist(1,1));
    }

    @Test
    public void followPlaylistBadPath()
    {
        Playlist p = new Playlist();
        p.setId(1);
        p.setGenre("A");
        p.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p));

        User v = new User();
        v.setId(1);
        v.setName("A");
        v.setCountry("a");
        v.setPremium(true);
        assertEquals(OK, Solution.addUser(v));

        assertEquals(NOT_EXISTS, Solution.followPlaylist(1,2));
        assertEquals(NOT_EXISTS, Solution.followPlaylist(2,1));
        assertEquals(OK, Solution.followPlaylist(1,1));
        assertEquals(ALREADY_EXISTS, Solution.followPlaylist(1,1));
        assertEquals(NOT_EXISTS, Solution.stopFollowPlaylist(2,1));
        assertEquals(NOT_EXISTS, Solution.stopFollowPlaylist(1,2));
        assertEquals(OK, Solution.stopFollowPlaylist(1,1));
        assertEquals(NOT_EXISTS, Solution.stopFollowPlaylist(1,1));
    }

    @Test
    public void playlistPlayCount(){
        Song s = new Song();
        s.setId(1);
        s.setName("A");
        s.setGenre("A");
        s.setCountry("Aa");
        s.setPlayCount(0);
        assertEquals(OK, Solution.addSong(s));
        Song s2 = new Song();
        s2.setId(2);
        s2.setName("A");
        s2.setGenre("A");
        s2.setCountry("Aa");
        s2.setPlayCount(0);
        assertEquals(OK, Solution.addSong(s2));

        Playlist p = new Playlist();
        p.setId(1);
        p.setGenre("A");
        p.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p));

        assertEquals((long) 0,(long) Solution.getPlaylistTotalPlayCount(1));
        assertEquals(OK, Solution.addSongToPlaylist(1,1));
        assertEquals(OK, Solution.addSongToPlaylist(2,1));
        assertEquals((long) 0,(long) Solution.getPlaylistTotalPlayCount(1));
        assertEquals(OK, Solution.songPlay(1, 10));
        assertEquals((long) 10,(long) Solution.getPlaylistTotalPlayCount(1));
        assertEquals(OK, Solution.songPlay(2, 10));
        assertEquals((long) 20,(long) Solution.getPlaylistTotalPlayCount(1));
        assertEquals(OK, Solution.songPlay(1, -5));
        assertEquals((long) 15,(long) Solution.getPlaylistTotalPlayCount(1));
        assertEquals(OK, Solution.removeSongFromPlaylist(2, 1));
        assertEquals((long) 5,(long) Solution.getPlaylistTotalPlayCount(1));
    }

    @Test
    public void playlistFollowersCount(){
        User v = new User();
        v.setId(1);
        v.setName("A");
        v.setCountry("a");
        v.setPremium(true);
        assertEquals(OK, Solution.addUser(v));
        User v2 = new User();
        v2.setId(2);
        v2.setName("A");
        v2.setCountry("a");
        v2.setPremium(true);
        assertEquals(OK, Solution.addUser(v2));

        Playlist p = new Playlist();
        p.setId(1);
        p.setGenre("A");
        p.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p));

        assertEquals((long) 0,(long) Solution.getPlaylistFollowersCount(1));
        assertEquals(OK, Solution.followPlaylist(1,1));
        assertEquals(OK, Solution.followPlaylist(2,1));
        selectTable("UserFollowPlaylist");
        assertEquals((long) 2,(long) Solution.getPlaylistFollowersCount(1));
        assertEquals(OK, Solution.stopFollowPlaylist(1,1));
        assertEquals((long) 1,(long) Solution.getPlaylistFollowersCount(1));
    }
}