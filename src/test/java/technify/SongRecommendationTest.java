package technify;

import org.junit.Test;
import technify.business.Playlist;
import technify.business.Song;
import technify.business.User;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static technify.business.ReturnValue.OK;


public class SongRecommendationTest extends  AbstractTest {

    @Test
    public void SimpleRecommendationTest()
    {
        User v = new User();
        v.setId(1);
        v.setName("A");
        v.setCountry("a");
        v.setPremium(true);
        assertEquals(OK, Solution.addUser(v));

        Song s = new Song();
        s.setId(1);
        s.setName("A");
        s.setGenre("a");
        s.setCountry("Aa");
        s.setPlayCount(0);
        assertEquals(OK, Solution.addSong(s));

        Song s2 = new Song();
        s2.setId(2);
        s2.setName("B");
        s2.setGenre("a");
        s2.setCountry("Bb");
        s2.setPlayCount(0);
        assertEquals(OK, Solution.addSong(s2));

        Playlist p1 = new Playlist();
        p1.setId(1);
        p1.setGenre("a");
        p1.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p1));

        Playlist p2 = new Playlist();
        p2.setId(2);
        p2.setGenre("a");
        p2.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p2));

        assertEquals(OK, Solution.followPlaylist(1,1));
        assertEquals(OK, Solution.addSongToPlaylist(1,1));
        assertEquals(OK, Solution.addSongToPlaylist(2,2));

        ArrayList<Integer> sol = Solution.getSongsRecommendationByGenre(1, "a");
        assertEquals(1, (long)sol.size());
        assertEquals(2, (long)sol.get(0));
        assertEquals(OK, Solution.addSongToPlaylist(2,1));
        sol = Solution.getSongsRecommendationByGenre(1, "a");
        assertEquals(0, (long)sol.size());
        //selectTable("PlaylistNumFollowers");
    }

    @Test
    public void SimpleRecognition()
    {
        User v = new User();
        v.setId(1);
        v.setName("A");
        v.setCountry("a");
        v.setPremium(true);
        assertEquals(OK, Solution.addUser(v));

        Song s = new Song();
        s.setId(1);
        s.setName("A");
        s.setGenre("a");
        s.setCountry("Aa");
        s.setPlayCount(0);
        assertEquals(OK, Solution.addSong(s));
        s.setId(2); //In playlist not followed by user
        assertEquals(OK, Solution.addSong(s));
        s.setId(3); //Not in playlist
        assertEquals(OK, Solution.addSong(s));

        Playlist p1 = new Playlist();
        p1.setId(1);
        p1.setGenre("a");
        p1.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p1));
        p1.setId(2);
        assertEquals(OK, Solution.addPlaylist(p1));

        assertEquals(OK, Solution.followPlaylist(1,1));
        assertEquals(OK, Solution.addSongToPlaylist(1,1));
        assertEquals(OK, Solution.addSongToPlaylist(2,2));
        assertEquals(OK, Solution.songPlay(2,2));
        assertEquals(OK, Solution.songPlay(3,2));
        ArrayList<Integer> sol;
        sol = Solution.getSongsRecommendationByGenre(1, "a");
        assertEquals(2, (long)sol.size());
        assertEquals(2, (long)sol.get(0));
        assertEquals(3, (long)sol.get(1));

        assertEquals(OK, Solution.songPlay(3,2));
        sol = Solution.getSongsRecommendationByGenre(1, "a");
        assertEquals(2, (long)sol.size());
        assertEquals(3, (long)sol.get(0));
        assertEquals(2, (long)sol.get(1));
    }

    @Test
    public void AllSongsInUserPlaylist()
    {
        User v = new User();
        v.setId(1);
        v.setName("A");
        v.setCountry("a");
        v.setPremium(true);
        assertEquals(OK, Solution.addUser(v));

        Song s = new Song();
        s.setId(1);
        s.setName("A");
        s.setGenre("a");
        s.setCountry("Aa");
        s.setPlayCount(0);
        assertEquals(OK, Solution.addSong(s));
        s.setId(2);
        assertEquals(OK, Solution.addSong(s));
        s.setId(3);
        assertEquals(OK, Solution.addSong(s));

        Playlist p1 = new Playlist();
        p1.setId(1);
        p1.setGenre("a");
        p1.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p1));
        p1.setId(2);
        assertEquals(OK, Solution.addPlaylist(p1));

        assertEquals(OK, Solution.followPlaylist(1,1));
        assertEquals(OK, Solution.followPlaylist(1,2));
        assertEquals(OK, Solution.addSongToPlaylist(1,1));
        assertEquals(OK, Solution.addSongToPlaylist(2,2));
        assertEquals(OK, Solution.addSongToPlaylist(3,2));
        assertEquals(OK, Solution.songPlay(2,2));
        assertEquals(OK, Solution.songPlay(3,2));
        ArrayList<Integer> sol;
        sol = Solution.getSongsRecommendationByGenre(1, "a");
        assertEquals(0, (long)sol.size());
    }

    @Test
    public void MoreThan10Songs()
    {
        User v = new User();
        v.setId(1);
        v.setName("A");
        v.setCountry("a");
        v.setPremium(true);
        assertEquals(OK, Solution.addUser(v));

        Song s = new Song();
        s.setId(1);
        s.setName("A");
        s.setGenre("a");
        s.setCountry("Aa");
        s.setPlayCount(0);
        assertEquals(OK, Solution.addSong(s));
        s.setId(10); // In playlist not followed by user
        assertEquals(OK, Solution.addSong(s));
        s.setId(11); // In playlist not followed by user
        assertEquals(OK, Solution.addSong(s));
        s.setId(12); // Not in playlist
        assertEquals(OK, Solution.addSong(s));
        s.setId(13); // Not in playlist
        assertEquals(OK, Solution.addSong(s));

        Playlist p1 = new Playlist();
        p1.setId(1);
        p1.setGenre("a");
        p1.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p1));
        p1.setId(2);
        assertEquals(OK, Solution.addPlaylist(p1));

        assertEquals(OK, Solution.followPlaylist(1,1));
        assertEquals(OK, Solution.addSongToPlaylist(1,1));
        assertEquals(OK, Solution.addSongToPlaylist(10,2));
        assertEquals(OK, Solution.addSongToPlaylist(11,2));
        assertEquals(OK, Solution.songPlay(10,2));
        assertEquals(OK, Solution.songPlay(12,2));
        assertEquals(OK, Solution.songPlay(11,3));
        assertEquals(OK, Solution.songPlay(13,3));
        ArrayList<Integer> sol;
        sol = Solution.getSongsRecommendationByGenre(1, "a");
        assertEquals(4, (long)sol.size());
        assertEquals(11, (long)sol.get(0));
        assertEquals(13, (long)sol.get(1));
        assertEquals(10, (long)sol.get(2));
        assertEquals(12, (long)sol.get(3));

        s.setId(2);// In playlist not followed by user
        assertEquals(OK, Solution.addSong(s));
        s.setId(3);// In playlist followed by user
        assertEquals(OK, Solution.addSong(s));
        s.setId(4);// In playlist not followed by user
        assertEquals(OK, Solution.addSong(s));
        s.setId(5);// In playlist not followed by user
        assertEquals(OK, Solution.addSong(s));
        s.setId(6);// Not in playlist
        assertEquals(OK, Solution.addSong(s));
        s.setId(7);// Not in playlist
        assertEquals(OK, Solution.addSong(s));
        s.setId(8);// Not in playlist
        assertEquals(OK, Solution.addSong(s));
        s.setId(9);// Not in playlist
        assertEquals(OK, Solution.addSong(s));

        assertEquals(OK, Solution.addSongToPlaylist(2,2));
        assertEquals(OK, Solution.addSongToPlaylist(3,1));
        assertEquals(OK, Solution.addSongToPlaylist(3,2));
        assertEquals(OK, Solution.addSongToPlaylist(4,2));
        assertEquals(OK, Solution.addSongToPlaylist(5,2));
        assertEquals(OK, Solution.songPlay(2,1));
        assertEquals(OK, Solution.songPlay(3,4));
        assertEquals(OK, Solution.songPlay(4,2));
        assertEquals(OK, Solution.songPlay(5,3));
        assertEquals(OK, Solution.songPlay(6,1));
        assertEquals(OK, Solution.songPlay(7,4));
        assertEquals(OK, Solution.songPlay(8,5));
        assertEquals(OK, Solution.songPlay(9,3));

        sol = Solution.getSongsRecommendationByGenre(1, "a");
        assertEquals(10, (long)sol.size());
        assertEquals(8, (long)sol.get(0));
        assertEquals(7, (long)sol.get(1));
        assertEquals(5, (long)sol.get(2));
        assertEquals(9, (long)sol.get(3));
        assertEquals(11, (long)sol.get(4));
        assertEquals(13, (long)sol.get(5));
        assertEquals(4, (long)sol.get(6));
        assertEquals(10, (long)sol.get(7));
        assertEquals(12, (long)sol.get(8));
        assertEquals(2, (long)sol.get(9));
    }
}