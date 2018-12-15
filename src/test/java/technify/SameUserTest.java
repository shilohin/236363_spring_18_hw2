package technify;

import org.junit.Test;
import technify.business.Playlist;
import technify.business.User;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static technify.business.ReturnValue.*;
import static technify.printer.selectTable;


public class SameUserTest extends  AbstractTest {

    @Test
    public void twoUsersGoodAddRemove()
    {
        User v = new User();
        v.setId(1);
        v.setName("A");
        v.setCountry("a");
        v.setPremium(true);

        User v2 = new User();
        v2.setId(2);
        v2.setName("B");
        v2.setCountry("b");
        v2.setPremium(false);

        assertEquals(OK, Solution.addUser(v));
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

        assertEquals(OK, Solution.followPlaylist(1,1));
        assertEquals(OK, Solution.followPlaylist(1,2));
        ArrayList<Integer> sol = Solution.getSimilarUsers(2);
        assertEquals(1, (long)sol.get(0));
        assertEquals(OK, Solution.followPlaylist(2,1));
        sol = Solution.getSimilarUsers(1);
        assertEquals(0, sol.size());
        assertEquals(OK, Solution.followPlaylist(2,2));
        //selectTable("ListenToSamePlaylist");

        sol = Solution.getSimilarUsers(1);
        assertEquals((long)2, (long)sol.get(0));
    }
}