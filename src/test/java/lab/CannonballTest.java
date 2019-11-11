package lab;

import lab.Cannonball;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CannonballTest {
    @Test
    void TestGravityIncrement() {
        Cannonball cannonball = new Cannonball();
        cannonball.gravityUp();
        cannonball.gravityUp();
        
        assertEquals(3, cannonball.getGravity());
    }

    @Test
    void TestGravityIncrementMax() {
        Cannonball cannonball = new Cannonball();
        for(int i = 0; i < 15; i++){
            cannonball.gravityUp();
        }
        
        assertEquals(10, cannonball.getGravity());
    }

    @Test
    void TestGravityDecrement() {
        Cannonball cannonball = new Cannonball();
        cannonball.gravityUp();
        cannonball.gravityUp();

        cannonball.gravityDown();

        assertEquals(2, cannonball.getGravity());
    }

    @Test
    void TestGravityDecrementMin() {
        Cannonball cannonball = new Cannonball();
        cannonball.gravityUp();
        cannonball.gravityUp();

        for(int i = 0; i < 15; i++){
            cannonball.gravityDown();
        }

        assertEquals(1, cannonball.getGravity());
    }
}