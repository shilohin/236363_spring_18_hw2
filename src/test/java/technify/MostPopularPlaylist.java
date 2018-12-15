package technify;

import org.junit.Test;
import technify.business.Playlist;
import technify.business.Song;

import static org.junit.Assert.assertEquals;
import static technify.business.ReturnValue.*;


public class MostPopularPlaylist extends  AbstractTest {
    @Test
    public void mostPopularBasicScenario(){
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

        Playlist p2 = new Playlist();
        p2.setId(2);
        p2.setGenre("A");
        p2.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p2));
        assertEquals((long)2, (long)Solution.getMostPopularPlaylist());
        assertEquals(OK, Solution.addSongToPlaylist(1,1));
        assertEquals(OK, Solution.addSongToPlaylist(2,2));
        assertEquals((long)2, (long)Solution.getMostPopularPlaylist());
        assertEquals(OK, Solution.songPlay(1, 10));
        assertEquals(OK, Solution.songPlay(2, 9));
        assertEquals((long)1, (long)Solution.getMostPopularPlaylist());
        assertEquals(OK, Solution.songPlay(2, 1));
        assertEquals((long)2, (long)Solution.getMostPopularPlaylist());
        assertEquals(OK, Solution.removeSongFromPlaylist(2,2));
        assertEquals((long)1, (long)Solution.getMostPopularPlaylist());
        assertEquals(OK, Solution.removeSongFromPlaylist(1,1));
        assertEquals((long)2, (long)Solution.getMostPopularPlaylist());
        assertEquals(OK, Solution.deletePlaylist(p2));
        assertEquals((long)1, (long)Solution.getMostPopularPlaylist());
        assertEquals(OK, Solution.deletePlaylist(p));
        assertEquals((long)0, (long)Solution.getMostPopularPlaylist());
    }
}