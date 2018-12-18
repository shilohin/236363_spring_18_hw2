package technify;

import org.junit.Test;
import technify.business.Playlist;
import technify.business.Song;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static technify.business.ReturnValue.*;
import static technify.printer.selectTable;

public class HottestPlaylistOnTechnify extends  AbstractTest{
    @Test
    public void BasicTest(){
        Song s = new Song();
        s.setId(1);
        s.setName("Radio GAGA");
        s.setGenre("A");
        s.setCountry("Aa");
        s.setPlayCount(0);
        assertEquals(OK, Solution.addSong(s));
        s.setId(2);
        assertEquals(OK, Solution.addSong(s));
        s.setId(3); //No PlayCount
        assertEquals(OK, Solution.addSong(s));
        s.setId(4); //No PlayCount
        assertEquals(OK, Solution.addSong(s));

        Playlist p = new Playlist();
        p.setId(1);
        p.setGenre("A");
        p.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p));
        p.setId(2);
        assertEquals(OK, Solution.addPlaylist(p));
        p.setId(3);
        assertEquals(OK, Solution.addPlaylist(p));
        p.setId(4);
        assertEquals(OK, Solution.addPlaylist(p));
        p.setId(5);
        assertEquals(OK, Solution.addPlaylist(p));
        p.setId(6);
        assertEquals(OK, Solution.addPlaylist(p));
        p.setId(7);
        assertEquals(OK, Solution.addPlaylist(p));
        p.setId(8);
        assertEquals(OK, Solution.addPlaylist(p));
        p.setId(9);
        assertEquals(OK, Solution.addPlaylist(p));
        p.setId(10);
        assertEquals(OK, Solution.addPlaylist(p));
        p.setId(11);
        assertEquals(OK, Solution.addPlaylist(p));
        p.setId(12); // Empty Playlist
        assertEquals(OK, Solution.addPlaylist(p));


        ArrayList<Integer> sol = Solution.hottestPlaylistsOnTechnify();
        assertEquals(0, sol.size());
        assertEquals(OK, Solution.addSongToPlaylist(1, 6));
        assertEquals(OK, Solution.addSongToPlaylist(1, 7));
        assertEquals(OK, Solution.addSongToPlaylist(1, 8));
        assertEquals(OK, Solution.addSongToPlaylist(1, 9));
        assertEquals(OK, Solution.addSongToPlaylist(1, 10));
        assertEquals(OK, Solution.addSongToPlaylist(1, 11));
        assertEquals(OK, Solution.addSongToPlaylist(2, 6));
        assertEquals(OK, Solution.addSongToPlaylist(2, 7));
        assertEquals(OK, Solution.addSongToPlaylist(2, 8));
        assertEquals(OK, Solution.addSongToPlaylist(2, 9));
        assertEquals(OK, Solution.addSongToPlaylist(2, 10));
        assertEquals(OK, Solution.addSongToPlaylist(2, 11));
        assertEquals(OK, Solution.addSongToPlaylist(3, 6));
        assertEquals(OK, Solution.addSongToPlaylist(3, 7));
        assertEquals(OK, Solution.addSongToPlaylist(3, 8));
        assertEquals(OK, Solution.addSongToPlaylist(3, 9));
        assertEquals(OK, Solution.addSongToPlaylist(4, 6));
        assertEquals(OK, Solution.addSongToPlaylist(4, 7));

        sol = Solution.hottestPlaylistsOnTechnify();
        assertEquals(6, sol.size());
        assertEquals(6, (long) sol.get(0));
        assertEquals(7, (long)sol.get(1));
        assertEquals(8, (long)sol.get(2));
        assertEquals(9, (long)sol.get(3));
        assertEquals(10, (long)sol.get(4));
        assertEquals(11, (long)sol.get(5));

        assertEquals(OK, Solution.songPlay(1,40));
        //selectTable("PlaylistView");
        sol = Solution.hottestPlaylistsOnTechnify();
        assertEquals(6, sol.size());
        assertEquals(10, (long) sol.get(0));
        assertEquals(11, (long)sol.get(1));
        assertEquals(8, (long)sol.get(2));
        assertEquals(9, (long)sol.get(3));
        assertEquals(6, (long)sol.get(4));
        assertEquals(7, (long)sol.get(5));
    }

    @Test
    public void AllPlaylistsEmpty(){
        Playlist p = new Playlist();
        p.setId(1);
        p.setGenre("A");
        p.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p));

        ArrayList<Integer> sol = Solution.hottestPlaylistsOnTechnify();
        assertEquals(0, sol.size());
    }

    @Test
    public void NoPlaylists(){
        ArrayList<Integer> sol = Solution.hottestPlaylistsOnTechnify();
        assertEquals(0, sol.size());
    }

    @Test
    public void MoreThan10Playlists(){
        Song s = new Song();
        s.setId(1);
        s.setName("Radio GAGA");
        s.setGenre("A");
        s.setCountry("Aa");
        s.setPlayCount(0);
        assertEquals(OK, Solution.addSong(s));
        s.setId(2);
        assertEquals(OK, Solution.addSong(s));
        s.setId(3); //No PlayCount
        assertEquals(OK, Solution.addSong(s));
        s.setId(4); //No PlayCount
        assertEquals(OK, Solution.addSong(s));

        Playlist p = new Playlist();
        p.setId(1);
        p.setGenre("A");
        p.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p));
        p.setId(2);
        assertEquals(OK, Solution.addPlaylist(p));
        p.setId(3);
        assertEquals(OK, Solution.addPlaylist(p));
        p.setId(4);
        assertEquals(OK, Solution.addPlaylist(p));
        p.setId(5);
        assertEquals(OK, Solution.addPlaylist(p));
        p.setId(6);
        assertEquals(OK, Solution.addPlaylist(p));
        p.setId(7);
        assertEquals(OK, Solution.addPlaylist(p));
        p.setId(8);
        assertEquals(OK, Solution.addPlaylist(p));
        p.setId(9);
        assertEquals(OK, Solution.addPlaylist(p));
        p.setId(10);
        assertEquals(OK, Solution.addPlaylist(p));
        p.setId(11);
        assertEquals(OK, Solution.addPlaylist(p));
        p.setId(12);
        assertEquals(OK, Solution.addPlaylist(p));
        p.setId(13); // Empty Playlist
        assertEquals(OK, Solution.addPlaylist(p));


        ArrayList<Integer> sol = Solution.hottestPlaylistsOnTechnify();
        assertEquals(0, sol.size());
        assertEquals(OK, Solution.addSongToPlaylist(1, 6));
        assertEquals(OK, Solution.addSongToPlaylist(1, 7));
        assertEquals(OK, Solution.addSongToPlaylist(1, 8));
        assertEquals(OK, Solution.addSongToPlaylist(1, 9));
        assertEquals(OK, Solution.addSongToPlaylist(1, 10));
        assertEquals(OK, Solution.addSongToPlaylist(1, 11));
        assertEquals(OK, Solution.addSongToPlaylist(1, 12));
        assertEquals(OK, Solution.addSongToPlaylist(2, 6));
        assertEquals(OK, Solution.addSongToPlaylist(2, 7));
        assertEquals(OK, Solution.addSongToPlaylist(2, 8));
        assertEquals(OK, Solution.addSongToPlaylist(2, 9));
        assertEquals(OK, Solution.addSongToPlaylist(2, 10));
        assertEquals(OK, Solution.addSongToPlaylist(2, 11));
        assertEquals(OK, Solution.addSongToPlaylist(3, 6));
        assertEquals(OK, Solution.addSongToPlaylist(3, 7));
        assertEquals(OK, Solution.addSongToPlaylist(3, 8));
        assertEquals(OK, Solution.addSongToPlaylist(3, 9));
        assertEquals(OK, Solution.addSongToPlaylist(4, 6));
        assertEquals(OK, Solution.addSongToPlaylist(4, 7));

        sol = Solution.hottestPlaylistsOnTechnify();
        assertEquals(7, sol.size());
        assertEquals(6, (long) sol.get(0));
        assertEquals(7, (long)sol.get(1));
        assertEquals(8, (long)sol.get(2));
        assertEquals(9, (long)sol.get(3));
        assertEquals(10, (long)sol.get(4));
        assertEquals(11, (long)sol.get(5));
        assertEquals(12, (long)sol.get(6));

        assertEquals(OK, Solution.addSongToPlaylist(1, 1));
        assertEquals(OK, Solution.addSongToPlaylist(1, 2));
        assertEquals(OK, Solution.addSongToPlaylist(1, 3));
        assertEquals(OK, Solution.addSongToPlaylist(1, 4));
        assertEquals(OK, Solution.addSongToPlaylist(1, 5));


        sol = Solution.hottestPlaylistsOnTechnify();
        assertEquals(10, sol.size());
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


        assertEquals(OK, Solution.songPlay(1, 40));

        sol = Solution.hottestPlaylistsOnTechnify();
        assertEquals(10, sol.size());
        assertEquals(1, (long)sol.get(0));
        assertEquals(2, (long)sol.get(1));
        assertEquals(3, (long)sol.get(2));
        assertEquals(4, (long)sol.get(3));
        assertEquals(5, (long)sol.get(4));
        assertEquals(12, (long)sol.get(5));
        assertEquals(10, (long)sol.get(6));
        assertEquals(11, (long)sol.get(7));
        assertEquals(8, (long)sol.get(8));
        assertEquals(9, (long)sol.get(9));
    }
}
