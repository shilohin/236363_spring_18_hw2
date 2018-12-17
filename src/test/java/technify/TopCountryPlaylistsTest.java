package technify;

import org.junit.Test;
import technify.business.Playlist;
import technify.business.Song;
import technify.business.User;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static technify.business.ReturnValue.*;

public class TopCountryPlaylistsTest extends  AbstractTest{
    @Test
    public void BasicCountry() {
        Song s = new Song();
        s.setId(1);
        s.setName("Winning song");
        s.setGenre("A");
        s.setCountry("A");
        s.setPlayCount(0);
        assertEquals(OK, Solution.addSong(s));

        User v = new User();
        v.setId(1);
        v.setName("A");
        v.setCountry("A");
        v.setPremium(true);
        assertEquals(OK, Solution.addUser(v));

        Playlist p1 = new Playlist();
        p1.setId(1);
        p1.setGenre("A");
        p1.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p1));
        p1.setId(2);
        assertEquals(OK, Solution.addPlaylist(p1));
        p1.setId(3);
        assertEquals(OK, Solution.addPlaylist(p1));
        p1.setId(4);
        assertEquals(OK, Solution.addPlaylist(p1));

        assertEquals(OK, Solution.addSongToPlaylist(1, 1));
        assertEquals(OK, Solution.addSongToPlaylist(1, 2));
        assertEquals(OK, Solution.addSongToPlaylist(1, 3));

        ArrayList<Integer> sol = Solution.getTopCountryPlaylists(1);
        assertEquals(3, (long)sol.size());
        assertEquals(1, (long)sol.get(0));
        assertEquals(2, (long)sol.get(1));
        assertEquals(3, (long)sol.get(2));
    }

    @Test
    public void NoPlaylists(){
        User v = new User();
        v.setId(1);
        v.setName("A");
        v.setCountry("A");
        v.setPremium(true);
        assertEquals(OK, Solution.addUser(v));

        ArrayList<Integer> sol;
        sol = Solution.getTopCountryPlaylists(1);
        assertEquals(0, (long)sol.size());
    }

    @Test
    public void NoUser(){
        ArrayList<Integer> sol;
        sol = Solution.getTopCountryPlaylists(1);
        assertEquals(0, (long)sol.size());
    }

    @Test
    public void NoPlaylistContainSongWIthCountry(){
        User v = new User();
        v.setId(1);
        v.setName("A");
        v.setCountry("A");
        v.setPremium(true);
        assertEquals(OK, Solution.addUser(v));

        Song s = new Song();
        s.setId(1);
        s.setName("Winning song");
        s.setGenre("A");
        s.setCountry("B");
        s.setPlayCount(0);
        assertEquals(OK, Solution.addSong(s));

        Playlist p1 = new Playlist();
        p1.setId(1); // Will contain song with different country than user
        p1.setGenre("A");
        p1.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p1));
        p1.setId(2); // Will be empty
        assertEquals(OK, Solution.addPlaylist(p1));

        assertEquals(OK, Solution.addSongToPlaylist(1,1));

        ArrayList<Integer> sol;
        sol = Solution.getTopCountryPlaylists(1);
        assertEquals(0, (long)sol.size());
    }

    @Test
    public void NotPremiumUser(){
        Song s = new Song();
        s.setId(1);
        s.setName("Winning song");
        s.setGenre("A");
        s.setCountry("A");
        s.setPlayCount(0);
        assertEquals(OK, Solution.addSong(s));

        User v = new User();
        v.setId(1);
        v.setName("A");
        v.setCountry("A");
        v.setPremium(false);
        assertEquals(OK, Solution.addUser(v));

        Playlist p1 = new Playlist();
        p1.setId(1);
        p1.setGenre("A");
        p1.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p1));
        p1.setId(2);
        assertEquals(OK, Solution.addPlaylist(p1));
        p1.setId(3);
        assertEquals(OK, Solution.addPlaylist(p1));
        p1.setId(4);
        assertEquals(OK, Solution.addPlaylist(p1));

        assertEquals(OK, Solution.addSongToPlaylist(1, 1));
        assertEquals(OK, Solution.addSongToPlaylist(1, 2));
        assertEquals(OK, Solution.addSongToPlaylist(1, 3));
        assertEquals(OK, Solution.addSongToPlaylist(1, 4));

        ArrayList<Integer> sol = Solution.getTopCountryPlaylists(1);
        assertEquals(0, (long)sol.size());
    }

    @Test
    public void MoreThan10Playlists(){
        Song s = new Song();
        s.setId(1); // Same country as user
        s.setName("song1");
        s.setGenre("A");
        s.setCountry("A");
        s.setPlayCount(0);
        assertEquals(OK, Solution.addSong(s));
        s.setId(2); // Diff country than user
        s.setCountry("B");
        assertEquals(OK, Solution.addSong(s));

        User v = new User();
        v.setId(1);
        v.setName("A");
        v.setCountry("A");
        v.setPremium(true);
        assertEquals(OK, Solution.addUser(v));

        Playlist p1 = new Playlist();
        p1.setId(1);
        p1.setGenre("A");
        p1.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p1));
        p1.setId(2);
        assertEquals(OK, Solution.addPlaylist(p1));
        p1.setId(3);
        assertEquals(OK, Solution.addPlaylist(p1));
        p1.setId(4);
        assertEquals(OK, Solution.addPlaylist(p1));
        p1.setId(5);
        assertEquals(OK, Solution.addPlaylist(p1));
        p1.setId(6);
        assertEquals(OK, Solution.addPlaylist(p1));
        p1.setId(7);
        assertEquals(OK, Solution.addPlaylist(p1));
        p1.setId(8);
        assertEquals(OK, Solution.addPlaylist(p1));
        p1.setId(9);
        assertEquals(OK, Solution.addPlaylist(p1));
        p1.setId(10);
        assertEquals(OK, Solution.addPlaylist(p1));
        p1.setId(11);
        assertEquals(OK, Solution.addPlaylist(p1));
        p1.setId(12);
        assertEquals(OK, Solution.addPlaylist(p1));
        // Playlists 1-10 will contain only song 1, 11 will contain both songs and 12 will contain only 2
        ArrayList<Integer> sol;

        assertEquals(OK, Solution.addSongToPlaylist(1,6));
        assertEquals(OK, Solution.addSongToPlaylist(1,7));
        assertEquals(OK, Solution.addSongToPlaylist(1,8));
        assertEquals(OK, Solution.addSongToPlaylist(1,9));
        assertEquals(OK, Solution.addSongToPlaylist(1,10));
        assertEquals(OK, Solution.addSongToPlaylist(1,11));
        assertEquals(OK, Solution.addSongToPlaylist(2,11));
        assertEquals(OK, Solution.addSongToPlaylist(2,12));
        sol = Solution.getTopCountryPlaylists(1);
        assertEquals(6, (long)sol.size());
        assertEquals(6, (long)sol.get(0));
        assertEquals(7, (long)sol.get(1));
        assertEquals(8, (long)sol.get(2));
        assertEquals(9, (long)sol.get(3));
        assertEquals(10, (long)sol.get(4));
        assertEquals(11, (long)sol.get(5));

        assertEquals(OK, Solution.songPlay(2,10));
        sol = Solution.getTopCountryPlaylists(1);
        assertEquals(6, (long)sol.size());
        assertEquals(11, (long)sol.get(0));
        assertEquals(6, (long)sol.get(1));
        assertEquals(7, (long)sol.get(2));
        assertEquals(8, (long)sol.get(3));
        assertEquals(9, (long)sol.get(4));
        assertEquals(10, (long)sol.get(5));

        assertEquals(OK, Solution.addSongToPlaylist(1,1));
        assertEquals(OK, Solution.addSongToPlaylist(1,2));
        assertEquals(OK, Solution.addSongToPlaylist(1,3));
        assertEquals(OK, Solution.addSongToPlaylist(1,4));
        assertEquals(OK, Solution.addSongToPlaylist(1,5));

        sol = Solution.getTopCountryPlaylists(1);
        assertEquals(10, (long)sol.size());
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
