package technify;

import org.junit.Test;
import technify.business.Playlist;
import technify.business.User;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static technify.business.ReturnValue.OK;


public class PlaylistRecommendationTest extends  AbstractTest {

    @Test
    public void SimpleRecommendationTest()
    {
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

        User v3 = new User();
        v3.setId(3);
        v3.setName("B");
        v3.setCountry("b");
        v3.setPremium(false);
        assertEquals(OK, Solution.addUser(v3));

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

        assertEquals(OK, Solution.followPlaylist(1,1));
        assertEquals(OK, Solution.followPlaylist(1,2));
        assertEquals(OK, Solution.followPlaylist(1,3));
        assertEquals(OK, Solution.followPlaylist(1,4));
        assertEquals(OK, Solution.followPlaylist(2,1));
        assertEquals(OK, Solution.followPlaylist(2,2));
        assertEquals(OK, Solution.followPlaylist(2,3));
        assertEquals(OK, Solution.followPlaylist(3,1));
        assertEquals(OK, Solution.followPlaylist(3,2));
        assertEquals(OK, Solution.followPlaylist(3,3));

        ArrayList<Integer> sol = Solution.getSimilarUsers(1);
        assertEquals(2, (long)sol.get(0));
        sol = Solution.getPlaylistRecommendation(2);
        assertEquals(1, (long)sol.size());
        assertEquals(4, (long)sol.get(0));
    }

    @Test
    public void SimpleRecognition(){
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

        Playlist p4 = new Playlist();
        p4.setId(4);
        p4.setGenre("A");
        p4.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p4));

        assertEquals(OK, Solution.followPlaylist(1,1));
        assertEquals(OK, Solution.followPlaylist(1,2));
        assertEquals(OK, Solution.followPlaylist(1,3));
        assertEquals(OK, Solution.followPlaylist(1,4));
        assertEquals(OK, Solution.followPlaylist(2,1));
        assertEquals(OK, Solution.followPlaylist(2,2));
        assertEquals(OK, Solution.followPlaylist(2,3));

        sol = Solution.getPlaylistRecommendation(2);
        assertEquals(1, (long)sol.size());
        assertEquals(4, (long)sol.get(0));
    }

    @Test
    public void GetEmptyListIfNoSimilar(){
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

        Playlist p4 = new Playlist();
        p4.setId(4);
        p4.setGenre("A");
        p4.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p4));

        assertEquals(OK, Solution.followPlaylist(1,1));
        assertEquals(OK, Solution.followPlaylist(1,3));
        assertEquals(OK, Solution.followPlaylist(1,4));
        assertEquals(OK, Solution.followPlaylist(2,1));
        assertEquals(OK, Solution.followPlaylist(2,2));

        sol = Solution.getPlaylistRecommendation(1);
        assertEquals(0, (long)sol.size());
    }

    @Test
    public void UserFollowsAllSimilarFollows(){
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

        Playlist p4 = new Playlist();
        p4.setId(4);
        p4.setGenre("A");
        p4.setDescription("aaa aaa");
        assertEquals(OK, Solution.addPlaylist(p4));

        assertEquals(OK, Solution.followPlaylist(1,1));
        assertEquals(OK, Solution.followPlaylist(1,2));
        assertEquals(OK, Solution.followPlaylist(1,3));
        assertEquals(OK, Solution.followPlaylist(1,4));
        assertEquals(OK, Solution.followPlaylist(2,1));
        assertEquals(OK, Solution.followPlaylist(2,2));
        assertEquals(OK, Solution.followPlaylist(2,3));

        sol = Solution.getPlaylistRecommendation(1);
        assertEquals(0, (long)sol.size());
    }

    @Test
    public void MoreThan5Recommended(){
        User v = new User();
        v.setId(1);
        v.setName("A");
        v.setCountry("a");
        v.setPremium(true);
        assertEquals(OK, Solution.addUser(v));
        v.setId(2);
        assertEquals(OK, Solution.addUser(v));
        v.setId(3);
        assertEquals(OK, Solution.addUser(v));
        v.setId(4);
        assertEquals(OK, Solution.addUser(v));
        v.setId(5);
        assertEquals(OK, Solution.addUser(v));
        v.setId(6);
        assertEquals(OK, Solution.addUser(v));
        v.setId(7);
        assertEquals(OK, Solution.addUser(v));
        v.setId(8);
        assertEquals(OK, Solution.addUser(v));
        v.setId(9);
        assertEquals(OK, Solution.addUser(v));
        v.setId(10);
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
        ArrayList<Integer> sol;

        /* Users 2-5 Will be 1's similar users*/
        assertEquals(OK, Solution.followPlaylist(1,1));
        assertEquals(OK, Solution.followPlaylist(2,1));
        assertEquals(OK, Solution.followPlaylist(3,1));
        assertEquals(OK, Solution.followPlaylist(4,1));
        assertEquals(OK, Solution.followPlaylist(5,1));

        /* Playlist 10 will be most followed, but no follower is similar to user 1*/
        assertEquals(OK, Solution.followPlaylist(6,10));
        assertEquals(OK, Solution.followPlaylist(7,10));
        assertEquals(OK, Solution.followPlaylist(8,10));
        assertEquals(OK, Solution.followPlaylist(9,10));
        assertEquals(OK, Solution.followPlaylist(10,10));

        /* Playlist 2 followed by 4, 1 similar*/
        assertEquals(OK, Solution.followPlaylist(2,2));
        assertEquals(OK, Solution.followPlaylist(7,2));
        assertEquals(OK, Solution.followPlaylist(8,2));
        assertEquals(OK, Solution.followPlaylist(9,2));

        /* Playlist 3 followed by 4, 2 similar*/
        assertEquals(OK, Solution.followPlaylist(2,3));
        assertEquals(OK, Solution.followPlaylist(4,3));
        assertEquals(OK, Solution.followPlaylist(8,3));
        assertEquals(OK, Solution.followPlaylist(9,3));

        /* Playlist 4 followed by 4, 1 similar*/
        assertEquals(OK, Solution.followPlaylist(5,4));
        assertEquals(OK, Solution.followPlaylist(7,4));
        assertEquals(OK, Solution.followPlaylist(8,4));
        assertEquals(OK, Solution.followPlaylist(9,4));

        /* Playlist 5 followed by 4, 4 similar*/
        assertEquals(OK, Solution.followPlaylist(3,5));
        assertEquals(OK, Solution.followPlaylist(4,5));
        assertEquals(OK, Solution.followPlaylist(5,5));
        assertEquals(OK, Solution.followPlaylist(2,5));

        sol = Solution.getPlaylistRecommendation(1);
        assertEquals(4, (long)sol.size());
        assertEquals(5, (long)sol.get(0));
        assertEquals(3, (long)sol.get(1));
        assertEquals(2, (long)sol.get(2));
        assertEquals(4, (long)sol.get(3));

        /* Playlist 6 followed by 4, 2 similar*/
        assertEquals(OK, Solution.followPlaylist(4,6));
        assertEquals(OK, Solution.followPlaylist(5,6));
        assertEquals(OK, Solution.followPlaylist(8,6));
        assertEquals(OK, Solution.followPlaylist(9,6));

        /* Playlist 7 followed by 4, 3 similar*/
        assertEquals(OK, Solution.followPlaylist(2,7));
        assertEquals(OK, Solution.followPlaylist(3,7));
        assertEquals(OK, Solution.followPlaylist(5,7));
        assertEquals(OK, Solution.followPlaylist(9,7));

        sol = Solution.getPlaylistRecommendation(1);
        assertEquals(5, (long)sol.size());
        assertEquals(5, (long)sol.get(0));
        assertEquals(7, (long)sol.get(1));
        assertEquals(3, (long)sol.get(2));
        assertEquals(6, (long)sol.get(3));
        assertEquals(2, (long)sol.get(4));
    }
}