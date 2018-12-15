package technify;

import org.junit.Test;
import technify.business.Playlist;

import static org.junit.Assert.assertEquals;
import static technify.business.ReturnValue.*;


public class CRUDPlaylistTest extends  AbstractTest {

    @Test
    public void twoPlaylistGoodAddRemove()
    {
        Playlist p = new Playlist();
        p.setId(1);
        p.setGenre("A");
        p.setDescription("aaa aaa");

        Playlist p2 = new Playlist();
        p2.setId(2);
        p2.setGenre("B");
        p2.setDescription("bbb bbb");

        assertEquals(OK, Solution.addPlaylist(p));
        assertEquals(OK, Solution.deletePlaylist(p));

        assertEquals(OK, Solution.addPlaylist(p));
        assertEquals(OK, Solution.addPlaylist(p2));
        assertEquals(OK, Solution.deletePlaylist(p));
        assertEquals(OK, Solution.deletePlaylist(p2));
    }

    @Test
    public void keyVerification()
    {
        Playlist p = new Playlist();
        p.setId(1);
        p.setGenre("A");
        p.setDescription("aaa aaa");

        Playlist p2 = new Playlist();
        p2.setId(1);
        p2.setGenre("B");
        p2.setDescription("bbb bbb");

        assertEquals(OK, Solution.addPlaylist(p));
        assertEquals(ALREADY_EXISTS, Solution.addPlaylist(p2));
        assertEquals(OK, Solution.deletePlaylist(p));
        assertEquals(OK, Solution.addPlaylist(p2));
    }

    @Test
    public void validateBadParamsInAdding(){
        Playlist p = new Playlist();
        p.setId(-1);
        p.setGenre("A");
        p.setDescription("aaa aaa");
        assertEquals(BAD_PARAMS, Solution.addPlaylist(p));

        p.setId(1);
        p.setGenre(null);
        assertEquals(BAD_PARAMS, Solution.addPlaylist(p));

        p.setGenre("A");
        p.setDescription(null);
        assertEquals(BAD_PARAMS, Solution.addPlaylist(p));
    }

    @Test
    public void removeNonExist(){
        Playlist p = new Playlist();
        p.setId(1);
        p.setGenre("A");
        p.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p));

        p.setId(2);
        assertEquals(NOT_EXISTS, Solution.deletePlaylist(p));
    }

    @Test
    public void getAndUpdateVerification(){
        Playlist p = new Playlist();
        p.setId(1);
        p.setGenre("A");
        p.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p));

        Playlist bad_playlist = Playlist.badPlaylist();

        assert(p.equals(Solution.getPlaylist(1)));
        assert(bad_playlist.equals(Solution.getPlaylist(3)));
        assert(bad_playlist.equals(Solution.getPlaylist(-1)));

        assertEquals(NOT_EXISTS, Solution.updatePlaylist(bad_playlist));

        p.setId(2);
        assertEquals(NOT_EXISTS, Solution.updatePlaylist(p));
        p.setId(1);

        p.setDescription("bbb bbb");
        assertEquals(OK, Solution.updatePlaylist(p));
        assert(p.equals(Solution.getPlaylist(1)));
    }
}