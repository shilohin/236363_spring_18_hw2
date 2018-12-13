package technify;

import org.junit.Test;
import technify.business.Playlist;
import technify.business.Song;

import static org.junit.Assert.assertEquals;
import static technify.business.ReturnValue.*;


public class BasicRelationalTesting extends  AbstractTest {
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

        assertEquals(NOT_EXISTS, Solution.addSongToPlaylist(1,1));
        assertEquals(NOT_EXISTS, Solution.addSongToPlaylist(2,1));
        assertEquals(BAD_PARAMS, Solution.addSongToPlaylist(1,2));
        p.setGenre("A");
        assertEquals(OK, Solution.updatePlaylist(p));
        assertEquals(OK, Solution.addSongToPlaylist(1,2));
        assertEquals(ALREADY_EXISTS, Solution.removeSongFromPlaylist(1,2));
    }

    @Test
    public void followPlaylistGoodPath()
    {

    }

    @Test
    public void followPlaylistBadPath()
    {

    }

}