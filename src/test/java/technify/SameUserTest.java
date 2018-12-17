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
    public void goodRecognitionOfSimilar(){
        User v = new User();
        v.setId(1);
        v.setName("A");
        v.setCountry("a");
        v.setPremium(true);
        assertEquals(OK, Solution.addUser(v));

        User v2 = new User();
        v2.setId(2);
        v2.setName("B");
        v2.setCountry("b");
        v2.setPremium(false);
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

        ArrayList<Integer> sol;

        assertEquals(OK, Solution.followPlaylist(1,1));
        assertEquals(OK, Solution.followPlaylist(1,2));
        sol = Solution.getSimilarUsers(1);
        assertEquals(0, (long)sol.size());

        assertEquals(OK, Solution.followPlaylist(2,1));
        assertEquals(OK, Solution.followPlaylist(2,2));
        sol = Solution.getSimilarUsers(1);
        assertEquals(1, (long)sol.size());
        assertEquals(2, (long)sol.get(0));
        sol = Solution.getSimilarUsers(2);
        assertEquals(1, (long)sol.size());
        assertEquals(1, (long)sol.get(0));

        assertEquals(OK, Solution.followPlaylist(1,3));
        sol = Solution.getSimilarUsers(1);
        assertEquals(0, (long)sol.size());
        sol = Solution.getSimilarUsers(2);
        assertEquals(1, (long)sol.size());
        assertEquals(1, (long)sol.get(0));
    }

    @Test
    public void userNotFollowPlaylist(){
        User v = new User();
        v.setId(1);
        v.setName("A");
        v.setCountry("a");
        v.setPremium(true);
        assertEquals(OK, Solution.addUser(v));

        User v2 = new User();
        v2.setId(2);
        v2.setName("B");
        v2.setCountry("b");
        v2.setPremium(false);
        assertEquals(OK, Solution.addUser(v2));

        ArrayList<Integer> sol = Solution.getSimilarUsers(1);
        assertEquals(0, (long)sol.size());
    }

    @Test
    public void moreThan10Similar(){
        User v = new User();
        v.setId(1);
        v.setName("A");
        v.setCountry("a");
        v.setPremium(true);
        assertEquals(OK, Solution.addUser(v));

        v.setId(2);
        v.setName("B");
        v.setCountry("b");
        v.setPremium(false);
        assertEquals(OK, Solution.addUser(v));

        v.setId(3);
        v.setName("B");
        v.setCountry("b");
        v.setPremium(false);
        assertEquals(OK, Solution.addUser(v));

        v.setId(4);
        v.setName("B");
        v.setCountry("b");
        v.setPremium(false);
        assertEquals(OK, Solution.addUser(v));

        v.setId(5);
        v.setName("B");
        v.setCountry("b");
        v.setPremium(false);
        assertEquals(OK, Solution.addUser(v));

        v.setId(6);
        v.setName("B");
        v.setCountry("b");
        v.setPremium(false);
        assertEquals(OK, Solution.addUser(v));

        v.setId(7);
        v.setName("B");
        v.setCountry("b");
        v.setPremium(false);
        assertEquals(OK, Solution.addUser(v));

        v.setId(8);
        v.setName("B");
        v.setCountry("b");
        v.setPremium(false);
        assertEquals(OK, Solution.addUser(v));

        v.setId(9);
        v.setName("B");
        v.setCountry("b");
        v.setPremium(false);
        assertEquals(OK, Solution.addUser(v));

        v.setId(10);
        v.setName("B");
        v.setCountry("b");
        v.setPremium(false);
        assertEquals(OK, Solution.addUser(v));

        v.setId(11);
        v.setName("B");
        v.setCountry("b");
        v.setPremium(false);
        assertEquals(OK, Solution.addUser(v));

        v.setId(12);
        v.setName("B");
        v.setCountry("b");
        v.setPremium(false);
        assertEquals(OK, Solution.addUser(v));

        v.setId(13);
        v.setName("B");
        v.setCountry("b");
        v.setPremium(false);
        assertEquals(OK, Solution.addUser(v));

        Playlist p1 = new Playlist();
        p1.setId(1);
        p1.setGenre("A");
        p1.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p1));

        ArrayList<Integer> sol;

        assertEquals(OK, Solution.followPlaylist(1,1));
        assertEquals(OK, Solution.followPlaylist(9,1));
        assertEquals(OK, Solution.followPlaylist(10,1));
        assertEquals(OK, Solution.followPlaylist(11,1));
        assertEquals(OK, Solution.followPlaylist(12,1));
        assertEquals(OK, Solution.followPlaylist(13,1));
        sol = Solution.getSimilarUsers(1);
        assertEquals(5, (long)sol.size());
        assertEquals(9, (long)sol.get(0));
        assertEquals(10, (long)sol.get(1));
        assertEquals(11, (long)sol.get(2));
        assertEquals(12, (long)sol.get(3));
        assertEquals(13, (long)sol.get(4));
        assertEquals(OK, Solution.followPlaylist(2,1));
        assertEquals(OK, Solution.followPlaylist(3,1));
        assertEquals(OK, Solution.followPlaylist(4,1));
        assertEquals(OK, Solution.followPlaylist(5,1));
        assertEquals(OK, Solution.followPlaylist(6,1));
        assertEquals(OK, Solution.followPlaylist(7,1));
        assertEquals(OK, Solution.followPlaylist(8,1));
        sol = Solution.getSimilarUsers(1);
        assertEquals(10, (long)sol.size());
        assertEquals(2, (long)sol.get(0));
        assertEquals(3, (long)sol.get(1));
        assertEquals(4, (long)sol.get(2));
        assertEquals(5, (long)sol.get(3));
        assertEquals(6, (long)sol.get(4));
        assertEquals(7, (long)sol.get(5));
        assertEquals(8, (long)sol.get(6));
        assertEquals(9, (long)sol.get(7));
        assertEquals(10, (long)sol.get(8));
        assertEquals(11, (long)sol.get(9));
    }
}