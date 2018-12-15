package technify;

import java.util.ArrayList;

public class hottestPlaylistOnTechnify {
    public void basichottest() {

        Song s = new Song();
        s.setId(1);
        s.setName("Winning song");
        s.setGenre("A");
        s.setCountry("Aa");
        s.setPlayCount(0);
        assertEquals(OK, Solution.addSong(s));

        Song s = new Song();
        s.setId(2);
        s.setName("Winning song");
        s.setGenre("A");
        s.setCountry("Aa");
        s.setPlayCount(0);
        assertEquals(OK, Solution.addSong(s));

        Playlist p1 = new Playlist();
        p.setId(1);
        p.setGenre("A");
        p.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p));

        Playlist p2 = new Playlist();
        p2.setId(2);
        p2.setGenre("A");
        p2.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p2));

        Playlist p3 = new Playlist();
        p2.setId(3);
        p2.setGenre("A");
        p2.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p3));

        Playlist p4 = new Playlist();
        p2.setId(4);
        p2.setGenre("A");
        p2.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p4));

        Playlist p5 = new Playlist();
        p2.setId(5);
        p2.setGenre("A");
        p2.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p5));

        Playlist p6 = new Playlist();
        p2.setId(6);
        p2.setGenre("A");
        p2.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p6));

        Playlist p7 = new Playlist();
        p2.setId(7);
        p2.setGenre("A");
        p2.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p7));

        Playlist p8 = new Playlist();
        p2.setId(8);
        p2.setGenre("A");
        p2.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p8));

        Playlist p9 = new Playlist();
        p2.setId(9);
        p2.setGenre("A");
        p2.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p9));

        Playlist p10 = new Playlist();
        p2.setId(10);
        p2.setGenre("A");
        p2.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p10));

        Playlist p0 = new Playlist();
        p2.setId(0);
        p2.setGenre("A");
        p2.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p0));



        assertEquals(OK, Solution.addSongToPlaylist(1, 0));
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
        ArrayList<integer> sol = solution.hottestPlaylistOnTechnify();
        assertEquals(0, sol.get(0));
        assertEquals(1, sol.get(1));
        assertEquals(2, sol.get(2));
        assertEquals(3, sol.get(3));
        assertEquals(4, sol.get(4));
        assertEquals(5, sol.get(5));
        assertEquals(6, sol.get(6));
        assertEquals(7, sol.get(7));
        assertEquals(8, sol.get(8));
        assertEquals(9, sol.get(9));

        assertEquals(OK, Solution.addSongToPlaylist(2, 10));
        assertEquals(OK, Solution.songPlay(2, 4));



         sol = solution.hottestPlaylistOnTechnify();
        assertEquals(10, sol.get(0));
        assertEquals(1, sol.get(1));
        assertEquals(2, sol.get(2));
        assertEquals(3, sol.get(3));
        assertEquals(4, sol.get(4));
        assertEquals(5, sol.get(5));
        assertEquals(6, sol.get(6));
        assertEquals(7, sol.get(7));
        assertEquals(8, sol.get(8));
        assertEquals(9, sol.get(9));
    }
}
