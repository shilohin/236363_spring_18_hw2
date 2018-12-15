package technify;

import org.junit.Test;
import technify.business.Playlist;
import technify.business.Song;
import technify.business.User;

import static org.junit.Assert.assertEquals;
import static technify.business.ReturnValue.*;
import static technify.printer.selectTable;


public class MostPopularSongTest extends  AbstractTest {
    @Test
    public void basicPopular()
    {
        Song s = new Song();
        s.setId(1);
        s.setName("Winning song");
        s.setGenre("A");
        s.setCountry("Aa");
        s.setPlayCount(0);
        assertEquals(OK, Solution.addSong(s));

        Song s2 = new Song();
        s2.setId(2);
        s2.setName("Losing song");
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

        assertEquals(OK, Solution.addSongToPlaylist(1,1));
        assertEquals(OK, Solution.addSongToPlaylist(1,2));
        assertEquals(OK, Solution.addSongToPlaylist(2,1));

        assertEquals("Winning song", Solution.getMostPopularSong());
    }

    public void realPopular(){
        Song s = new Song();
        s.setId(1);
        s.setName("Winning song");
        s.setGenre("A");
        s.setCountry("Aa");
        s.setPlayCount(0);
        assertEquals(OK, Solution.addSong(s));

        Song s2 = new Song();
        s2.setId(2);
        s2.setName("Losing song");
        s2.setGenre("A");
        s2.setCountry("Aa");
        s2.setPlayCount(0);
        assertEquals(OK, Solution.addSong(s2));


        Song s3 = new Song();
        s2.setId(3);
        s2.setName("Losing song");
        s2.setGenre("A");
        s2.setCountry("Aa");
        s2.setPlayCount(0);
        assertEquals(OK, Solution.addSong(s3));

        Song s4 = new Song();
        s2.setId(4);
        s2.setName("Losing song");
        s2.setGenre("A");
        s2.setCountry("Aa");
        s2.setPlayCount(0);
        assertEquals(OK, Solution.addSong(s4));

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


        assertEquals(OK, Solution.addSongToPlaylist(1,1));
        assertEquals(OK, Solution.addSongToPlaylist(2,1));
        assertEquals(OK, Solution.addSongToPlaylist(3,1));
        assertEquals(OK, Solution.addSongToPlaylist(4,1));
        assertEquals(OK, Solution.addSongToPlaylist(1,2));
        assertEquals(OK, Solution.addSongToPlaylist(2,2));
        assertEquals(OK, Solution.addSongToPlaylist(3,2));
        assertEquals(OK, Solution.removeSongFromPlaylist(4,1));


        assertEquals("Winning song", Solution.getMostPopularSong());


    }

}