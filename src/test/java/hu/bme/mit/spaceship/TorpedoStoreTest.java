package hu.bme.mit.spaceship;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
 
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
 
class TorpedoStoreTest {
 
    private TorpedoStore store;
 
    @BeforeEach
    void init() {
        store = new TorpedoStore(5, 0.0);
    }
 
    @AfterEach
    void cleanUp() {
        store = null;
    }
 
    @Test
    void getTorpedoCount_newStore_returnsInitialCount() {
        assertEquals(5, store.getTorpedoCount());
    }
 
    @Test
    void isEmpty_storeWithTorpedoes_returnsFalse() {
        assertFalse(store.isEmpty());
    }
 
    @Test
    void isEmpty_storeWithZeroTorpedoes_returnsTrue() {
        TorpedoStore empty = new TorpedoStore(0, 0.0);
        assertTrue(empty.isEmpty());
    }
 
    @Test
    void fire_one_succeedsAndDecreasesCount() {
        assertTrue(store.fire(1));
        assertEquals(4, store.getTorpedoCount());
    }
 
    @Test
    void fire_several_succeedsAndDecreasesCountByThatMany() {
        assertTrue(store.fire(3));
        assertEquals(2, store.getTorpedoCount());
    }
 
    @Test
    void fire_allTorpedoes_makesStoreEmpty() {
        assertTrue(store.fire(5));
        assertEquals(0, store.getTorpedoCount());
        assertTrue(store.isEmpty());
    }
 
    @Test
    void fire_zero_throwsExceptionAndKeepsCount() {
        assertThrows(IllegalArgumentException.class, () -> store.fire(0));
        assertEquals(5, store.getTorpedoCount());
    }
 
    @Test
    void fire_negative_throwsExceptionAndKeepsCount() {
        assertThrows(IllegalArgumentException.class, () -> store.fire(-1));
        assertEquals(5, store.getTorpedoCount());
    }
 
    @Test
    void fire_moreThanAvailable_throwsExceptionAndKeepsCount() {
        assertThrows(IllegalArgumentException.class, () -> store.fire(6));
        assertEquals(5, store.getTorpedoCount());
    }
 
    @Test
    void fire_fromEmptyStore_throwsException() {
        TorpedoStore empty = new TorpedoStore(0, 0.0);
        assertThrows(IllegalArgumentException.class, () -> empty.fire(1));
    }
 
    @Test
    void fire_failingStore_returnsFalseAndKeepsCount() {
        // failure rate 1.0: always fails
        TorpedoStore failing = new TorpedoStore(5, 1.0);
        assertFalse(failing.fire(1));
        assertEquals(5, failing.getTorpedoCount());
        assertFalse(failing.isEmpty());
    }
}
