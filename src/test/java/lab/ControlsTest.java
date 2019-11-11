package lab;

import com.jme3.scene.Geometry;
import lab.AppMain;
import lab.Cannonball;
import lab.Controls;
import lab.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControlsTest {
    @Test
    void TestControls(){
        AppMain app = new AppMain();
        Runnable task = new Runnable() {
            public void run() {
                app.setShowSettings(false);
                app.start();
            }
        };
        Thread thread = new Thread(task);
        thread.start();

        while (!app.isInit || !app.controls.isInit)
        {
            Thread.yield();
        }
        app.getStateManager().getState(Controls.class).onAction("GravityUp", false, (float)0.033);
        assertEquals(2, app.getStateManager().getState(Cannonball.class).getGravity());

        app.getStateManager().getState(Controls.class).onAction("GravityDown", false, (float)0.033);
        assertEquals(1, app.getStateManager().getState(Cannonball.class).getGravity());

        app.getStateManager().getState(Controls.class).onAction("ArrowUp", false, (float)0.033);
        float[] angles = new float[3];
        Geometry arrowGeometry = app.getStateManager().getState(Player.class).getArrowGeometry();
        arrowGeometry.getLocalRotation().toAngles(angles);
        assertTrue(-angles[2] > Math.PI*10/180 - 0.01 && -angles[2] < Math.PI*10/180 + 0.01);

        app.getStateManager().getState(Controls.class).onAction("ArrowDown", false, (float)0.033);
        arrowGeometry.getLocalRotation().toAngles(angles);
        assertTrue(-angles[2] < 0.01);

        app.stop(true);
    }
}