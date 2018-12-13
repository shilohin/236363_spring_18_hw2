package technify;

import org.junit.Test;
import technify.business.*;
import static org.junit.Assert.assertEquals;
import static technify.business.ReturnValue.*;


public class CRUDUserTest extends  AbstractTest {

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
        assertEquals(OK, Solution.deleteUser(v));

        assertEquals(OK, Solution.addUser(v));
        assertEquals(OK, Solution.addUser(v2));
        assertEquals(OK, Solution.deleteUser(v));
        assertEquals(OK, Solution.deleteUser(v2));
    }

    @Test
    public void keyVerification()
    {
        User v = new User();
        v.setId(1);
        v.setName("A");
        v.setCountry("a");
        v.setPremium(true);

        User v2 = new User();
        v2.setId(1);
        v2.setName("B");
        v2.setCountry("b");
        v2.setPremium(false);

        assertEquals(OK, Solution.addUser(v));
        assertEquals(ALREADY_EXISTS, Solution.addUser(v2));
        assertEquals(OK, Solution.deleteUser(v));
        assertEquals(OK, Solution.addUser(v2));
    }

    @Test
    public void validateBadParamsInAdding(){
        User v = new User();
        v.setId(-1);
        v.setName("A");
        v.setCountry("a");
        v.setPremium(true);
        assertEquals(BAD_PARAMS, Solution.addUser(v));

        v.setId(1);
        v.setName(null);
        assertEquals(BAD_PARAMS, Solution.addUser(v));

        v.setName("A");
        v.setCountry(null);
        assertEquals(BAD_PARAMS, Solution.addUser(v));
    }

    @Test
    public void removeNonExist(){
        User v = new User();
        v.setId(1);
        v.setName("A");
        v.setCountry("a");
        v.setPremium(true);
        assertEquals(OK, Solution.addUser(v));

        v.setId(2);
        assertEquals(NOT_EXISTS, Solution.deleteUser(v));
    }

    @Test
    public void getAndUpdateVerification(){
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

        User bad_user = User.badUser();

        assert(v.equals(Solution.getUserProfile(1)));
        assert(v2.equals(Solution.getUserProfile(2)));
        assert(bad_user.equals(Solution.getUserProfile(3)));
        assert(bad_user.equals(Solution.getUserProfile(-1)));

        assertEquals(ALREADY_EXISTS, Solution.updateUserPremium(1));
        assertEquals(ALREADY_EXISTS, Solution.updateUserNotPremium(2));

        assertEquals(OK, Solution.updateUserPremium(2));
        assertEquals(OK, Solution.updateUserNotPremium(1));
        assertEquals(NOT_EXISTS, Solution.updateUserPremium(3));
        assertEquals(NOT_EXISTS, Solution.updateUserNotPremium(3));
        assertEquals(NOT_EXISTS, Solution.updateUserPremium(-1));
        assertEquals(NOT_EXISTS, Solution.updateUserNotPremium(-1));

        v.setPremium(false);
        v2.setPremium(true);

        assert(v.equals(Solution.getUserProfile(1)));
        assert(v2.equals(Solution.getUserProfile(2)));
    }
}