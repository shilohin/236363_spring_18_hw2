package technify;

public class getTopCountryPlaylistsTest {
    public void basiccountry() {


        Song s = new Song();
        s.setId(1);
        s.setName("Winning song");
        s.setGenre("A");
        s.setCountry("A");
        s.setPlayCount(0);
        assertEquals(OK, Solution.addSong(s));

        Song s = new Song();
        s.setId(2);
        s.setName("Winning song");
        s.setGenre("A");
        s.setCountry("B");
        s.setPlayCount(0);
        assertEquals(OK, Solution.addSong(s));

        User v = new User();
        v.setId(1);
        v.setName("A");
        v.setCountry("A");
        v.setPremium(true);

        User v = new User();
        v.setId(2);
        v.setName("A");
        v.setCountry("B");
        v.setPremium(true);

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
        //add song 1 to all playlists

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
        assertEquals(OK, Solution.songPlay(2, 4));
//add song 2 to half of the palylists
        assertEquals(OK, Solution.addSongToPlaylist(2, 6));
        assertEquals(OK, Solution.addSongToPlaylist(2, 7));
        assertEquals(OK, Solution.addSongToPlaylist(2, 8));
        assertEquals(OK, Solution.addSongToPlaylist(2, 9));
        assertEquals(OK, Solution.addSongToPlaylist(2, 10));

        assertEquals(OK, Solution.followPlaylist(1,0));
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
        sol = solution.getTopCountryPlaylist();
        assertEquals(6, sol.get(0));
        assertEquals(7, sol.get(1));
        assertEquals(8, sol.get(2));
        assertEquals(9, sol.get(3));
        assertEquals(10, sol.get(4));
        assertEquals(1, sol.get(5));
        assertEquals(3, sol.get(6));



    }
}
