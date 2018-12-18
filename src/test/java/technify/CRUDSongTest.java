package technify;

import org.junit.Test;
import technify.business.Song;

import static org.junit.Assert.assertEquals;
import static technify.business.ReturnValue.*;


public class CRUDSongTest extends  AbstractTest {

    @Test
    public void twoSongsGoodAddRemove()
    {
        Song s = new Song();
        s.setId(1);
        s.setName("A");
        s.setGenre("a");
        s.setCountry("Aa");
        s.setPlayCount(0);

        Song s2 = new Song();
        s2.setId(2);
        s2.setName("B");
        s2.setGenre("b");
        s2.setCountry("Bb");
        s2.setPlayCount(0);

        assertEquals(OK, Solution.addSong(s));
        assertEquals(OK, Solution.deleteSong(s));

        assertEquals(OK, Solution.addSong(s));
        assertEquals(OK, Solution.addSong(s2));
        assertEquals(OK, Solution.deleteSong(s));
        assertEquals(OK, Solution.deleteSong(s2));
    }

    @Test
    public void keyVerification()
    {
        Song s = new Song();
        s.setId(1);
        s.setName("A");
        s.setGenre("a");
        s.setCountry("Aa");
        s.setPlayCount(0);

        Song s2 = new Song();
        s2.setId(1);
        s2.setName("B");
        s2.setGenre("b");
        s2.setCountry("Bb");
        s2.setPlayCount(0);

        assertEquals(OK, Solution.addSong(s));
        assertEquals(ALREADY_EXISTS, Solution.addSong(s2));
        assertEquals(OK, Solution.deleteSong(s));
        assertEquals(OK, Solution.addSong(s2));
    }

    @Test
    public void validateBadParamsInAdding(){
        Song s = new Song();
        s.setId(-1);
        s.setName("A");
        s.setGenre("a");
        s.setCountry("Aa");
        s.setPlayCount(0);
        assertEquals(BAD_PARAMS, Solution.addSong(s));

        s.setId(1);
        s.setName(null);
        assertEquals(BAD_PARAMS, Solution.addSong(s));

        s.setName("A");
        s.setGenre(null);
        assertEquals(BAD_PARAMS, Solution.addSong(s));

        s.setGenre("a");
        s.setCountry(null);
        assertEquals(OK, Solution.addSong(s));
    }

    @Test
    public void removeNonExist(){
        Song s = new Song();
        s.setId(1);
        s.setName("A");
        s.setGenre("a");
        s.setCountry("Aa");
        s.setPlayCount(0);
        assertEquals(OK, Solution.addSong(s));

        s.setId(2);
        assertEquals(NOT_EXISTS, Solution.deleteSong(s));
    }

    @Test
    public void getAndUpdateVerification(){
        Song s = new Song();
        s.setId(1);
        s.setName("A");
        s.setGenre("a");
        s.setCountry("Aa");
        s.setPlayCount(0);
        assertEquals(OK, Solution.addSong(s));

        Song bad_song = Song.badSong();

        assert(s.equals(Solution.getSong(1)));
        assert(bad_song.equals(Solution.getSong(3)));
        assert(bad_song.equals(Solution.getSong(-1)));

        assertEquals(NOT_EXISTS, Solution.updateSongName(bad_song));

        s.setId(2);
        assertEquals(NOT_EXISTS, Solution.updateSongName(s));
        s.setId(1);

        s.setName("B");
        assertEquals(OK, Solution.updateSongName(s));
        assert(s.equals(Solution.getSong(1)));

        s.setName(null);
        assertEquals(BAD_PARAMS, Solution.updateSongName(s));
    }

    @Test
    public void SongPlayVerification()
    {
        Song s = new Song();
        s.setId(1);
        s.setName("A");
        s.setGenre("a");
        s.setCountry("Aa");
        s.setPlayCount(0);
        assertEquals(OK, Solution.addSong(s));
        assert(s.equals(Solution.getSong(1)));

        assertEquals(BAD_PARAMS, Solution.songPlay(1, -1));
        assertEquals(NOT_EXISTS, Solution.songPlay(2, 5));
        assertEquals(OK, Solution.songPlay(1, 5));
        s.setPlayCount(5);
        assert(s.equals(Solution.getSong(1)));
        assertEquals(BAD_PARAMS, Solution.songPlay(1, -10));
    }

    @Test
    public void UpdateWithNullNameIdNotExists()
    {
        Song s = new Song();
        s.setId(1);
        s.setName(null);
        s.setGenre("a");
        s.setCountry("Aa");
        s.setPlayCount(0);

        assertEquals(NOT_EXISTS, Solution.updateSongName(s));
    }
}
