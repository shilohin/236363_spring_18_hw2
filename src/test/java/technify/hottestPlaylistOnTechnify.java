package technify;

import org.junit.Test;
import technify.business.Playlist;
import technify.business.Song;
import technify.business.User;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static technify.business.ReturnValue.*;
import static technify.printer.selectTable;

public class hottestPlaylistOnTechnify extends  AbstractTest{
    @Test
    public void basichottest() {

        Song s = new Song();
        s.setId(1);
        s.setName("Winning song");
        s.setGenre("A");
        s.setCountry("Aa");
        s.setPlayCount(0);
        assertEquals(OK, Solution.addSong(s));

        Song s2 = new Song();
        s2.setId(2);
        s2.setName("Winning song");
        s2.setGenre("A");
        s2.setCountry("Aa");
        s2.setPlayCount(0);
        assertEquals(OK, Solution.addSong(s2));

        Playlist p1 = new Playlist();
        p1.setId(1);
        p1.setGenre("A");
        p1.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p1));

        Playlist p2 = new Playlist();
        p2.setId(2);
        p2.setGenre("A");
        p2.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p2));

        Playlist p3 = new Playlist();
        p3.setId(3);
        p3.setGenre("A");
        p3.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p3));

        Playlist p4 = new Playlist();
        p4.setId(4);
        p4.setGenre("A");
        p4.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p4));

        Playlist p5 = new Playlist();
        p5.setId(5);
        p5.setGenre("A");
        p5.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p5));

        Playlist p6 = new Playlist();
        p6.setId(6);
        p6.setGenre("A");
        p6.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p6));

        Playlist p7 = new Playlist();
        p7.setId(7);
        p7.setGenre("A");
        p7.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p7));

        Playlist p8 = new Playlist();
        p8.setId(8);
        p8.setGenre("A");
        p8.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p8));

        Playlist p9 = new Playlist();
        p9.setId(9);
        p9.setGenre("A");
        p9.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p9));

        Playlist p10 = new Playlist();
        p10.setId(10);
        p10.setGenre("A");
        p10.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p10));

        Playlist p0 = new Playlist();
        p0.setId(11);
        p0.setGenre("A");
        p0.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p0));
        ArrayList<Integer> sol = Solution.hottestPlaylistsOnTechnify();
        assertEquals(0, sol.size());
        assertEquals(OK, Solution.songPlay(1, 1));
        assertEquals(OK, Solution.addSongToPlaylist(1, 1));
        assertEquals(OK, Solution.addSongToPlaylist(1, 2));
        assertEquals(OK, Solution.addSongToPlaylist(1, 3));
        assertEquals(OK, Solution.addSongToPlaylist(1, 4));
        assertEquals(OK, Solution.addSongToPlaylist(1, 5));
        assertEquals(OK, Solution.addSongToPlaylist(1, 6));

        sol = Solution.hottestPlaylistsOnTechnify();
        assertEquals(1, (long) sol.get(0));
        assertEquals(2, (long)sol.get(1));
        assertEquals(3, (long)sol.get(2));
        assertEquals(4, (long)sol.get(3));
        assertEquals(5, (long)sol.get(4));
        assertEquals(6, (long)sol.get(5));
        assertEquals(6, sol.size());

        assertEquals(OK, Solution.addSongToPlaylist(1, 7));
        assertEquals(OK, Solution.addSongToPlaylist(1, 8));
        assertEquals(OK, Solution.addSongToPlaylist(1, 9));
        assertEquals(OK, Solution.addSongToPlaylist(1, 10));
        assertEquals(OK, Solution.addSongToPlaylist(1, 11));


        sol = Solution.hottestPlaylistsOnTechnify();
        assertEquals(1, (long) sol.get(0));
        assertEquals(2, (long)sol.get(1));
        assertEquals(3, (long)sol.get(2));
        assertEquals(4, (long)sol.get(3));
        assertEquals(5, (long)sol.get(4));
        assertEquals(6, (long)sol.get(5));
        assertEquals(7, (long)sol.get(6));
        assertEquals(8, (long)sol.get(7));
        assertEquals(9, (long)sol.get(8));
        assertEquals(10, (long)sol.get(9));
        assertEquals(10, sol.size());

        assertEquals(OK, Solution.addSongToPlaylist(2, 11));
        assertEquals(OK, Solution.songPlay(2, 4));

        sol = Solution.hottestPlaylistsOnTechnify();
        assertEquals(11, (long)sol.get(0));
        assertEquals(1, (long)sol.get(1));
        assertEquals(2, (long)sol.get(2));
        assertEquals(3, (long)sol.get(3));
        assertEquals(4, (long)sol.get(4));
        assertEquals(5, (long)sol.get(5));
        assertEquals(6, (long)sol.get(6));
        assertEquals(7, (long)sol.get(7));
        assertEquals(8, (long)sol.get(8));
        assertEquals(9, (long)sol.get(9));
    }
}
