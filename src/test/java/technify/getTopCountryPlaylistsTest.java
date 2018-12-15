package technify;

import org.junit.Test;
import technify.business.Playlist;
import technify.business.Song;
import technify.business.User;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static technify.business.ReturnValue.*;
import static technify.printer.selectTable;

public class getTopCountryPlaylistsTest extends  AbstractTest{
    @Test
    public void basiccountry() {


        Song s = new Song();
        s.setId(1);
        s.setName("Winning song");
        s.setGenre("A");
        s.setCountry("A");
        s.setPlayCount(0);
        assertEquals(OK, Solution.addSong(s));

        Song s2 = new Song();
        s2.setId(2);
        s2.setName("Winning song");
        s2.setGenre("A");
        s2.setCountry("B");
        s2.setPlayCount(0);
        assertEquals(OK, Solution.addSong(s2));

        User v = new User();
        v.setId(1);
        v.setName("A");
        v.setCountry("A");
        v.setPremium(true);
        assertEquals(OK, Solution.addUser(v));

        User v2 = new User();
        v2.setId(2);
        v2.setName("A");
        v2.setCountry("B");
        v2.setPremium(true);
        assertEquals(OK, Solution.addUser(v2));

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
        //add song 1 to all playlists

        assertEquals(OK, Solution.addSongToPlaylist(1, 11));
        assertEquals(OK, Solution.addSongToPlaylist(1, 1));
        assertEquals(OK, Solution.addSongToPlaylist(1, 2));
        assertEquals(OK, Solution.addSongToPlaylist(1, 3));
        assertEquals(OK, Solution.addSongToPlaylist(1, 4));
        assertEquals(OK, Solution.addSongToPlaylist(1, 5));
        assertEquals(OK, Solution.addSongToPlaylist(1, 6));
        assertEquals(OK, Solution.addSongToPlaylist(1, 7));
        assertEquals(OK, Solution.addSongToPlaylist(1, 8));
        assertEquals(OK, Solution.addSongToPlaylist(1, 9));
        assertEquals(OK, Solution.addSongToPlaylist(1, 10));

        assertEquals(OK, Solution.songPlay(1, 1));
        assertEquals(OK, Solution.songPlay(2, 4));
//add song 2 to half of the palylists
        assertEquals(OK, Solution.addSongToPlaylist(2, 6));
        assertEquals(OK, Solution.addSongToPlaylist(2, 7));
        assertEquals(OK, Solution.addSongToPlaylist(2, 8));
        assertEquals(OK, Solution.addSongToPlaylist(2, 9));
        assertEquals(OK, Solution.addSongToPlaylist(2, 10));

        assertEquals(OK, Solution.followPlaylist(1,11));
        assertEquals(OK, Solution.followPlaylist(1,1));
        assertEquals(OK, Solution.followPlaylist(1,2));
        assertEquals(OK, Solution.followPlaylist(1,3));
        assertEquals(OK, Solution.followPlaylist(1,4));
        assertEquals(OK, Solution.followPlaylist(1,5));
        assertEquals(OK, Solution.followPlaylist(1,6));
        assertEquals(OK, Solution.followPlaylist(1,7));
        assertEquals(OK, Solution.followPlaylist(1,8));
        assertEquals(OK, Solution.followPlaylist(1,9));
        assertEquals(OK, Solution.followPlaylist(1,10));
//add user 2 to playlist 5,6,7,2,4 but only playlist 6-10 hava song in his country.
        assertEquals(OK, Solution.followPlaylist(2,6));
        assertEquals(OK, Solution.followPlaylist(2,5));
        assertEquals(OK, Solution.followPlaylist(2,7));
        assertEquals(OK, Solution.followPlaylist(2,2));
        assertEquals(OK, Solution.followPlaylist(2,4));

//check the result
        ArrayList<Integer> sol = Solution.getTopCountryPlaylists(1);
        assertEquals((long)6, (long)sol.get(0));
        assertEquals((long)7, (long)sol.get(1));
        assertEquals(8, (long)sol.get(2));
        assertEquals(9, (long)sol.get(3));
        assertEquals(10, (long)sol.get(4));
        assertEquals(1, (long)sol.get(5));
        assertEquals(3, (long)sol.get(6));



    }
}
