package lab;

import com.jme3.scene.Geometry;
import lab.AppMain;
import lab.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    @Test
    void TestArrowUp(){
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

        app.getStateManager().getState(Player.class).arrowUp();
        app.getStateManager().getState(Player.class).arrowUp();

        float[] angles = new float[3];
        Geometry arrowGeometry = app.getStateManager().getState(Player.class).getArrowGeometry();
        arrowGeometry.getLocalRotation().toAngles(angles);
        assertTrue(-angles[2] > Math.PI*20/180 - 0.01 && -angles[2] < Math.PI*20/180 + 0.01);

        app.stop(true);
    }
    @Test
    void TestArrowDown(){
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

        app.getStateManager().getState(Player.class).arrowUp();
        app.getStateManager().getState(Player.class).arrowUp();

        app.getStateManager().getState(Player.class).arrowDown();

        float[] angles = new float[3];
        Geometry arrowGeometry = app.getStateManager().getState(Player.class).getArrowGeometry();
        arrowGeometry.getLocalRotation().toAngles(angles);
        assertTrue(-angles[2] > Math.PI*10/180 - 0.01 && -angles[2] < Math.PI*10/180 + 0.01);

        app.stop(true);
    }
    @Test
    void TestArrowUpMax(){
        AppMain app = new AppMain();
        Runnable task = new Runnable() {
            public void run() {
                app.setShowSettings(false);
                app.start();
            }
        };
        Thread thread = new Thread(task);
        thread.start();

        while (!app.isInit ||!app.controls.isInit)
        {
            Thread.yield();
        }

        for (int i = 0; i < 15; i++) {
            app.getStateManager().getState(Player.class).arrowUp();
        }

        float[] angles = new float[3];
        Geometry arrowGeometry = app.getStateManager().getState(Player.class).getArrowGeometry();
        arrowGeometry.getLocalRotation().toAngles(angles);
        assertTrue(-angles[2] > Math.PI*90/180 - 0.01 && -angles[2] < Math.PI*90/180 + 0.01);

        app.stop(true);
    }
    @Test
    void TestArrowDownMax(){
        AppMain app = new AppMain();
        Runnable task = new Runnable() {
            public void run() {
                app.setShowSettings(false);
                app.start();
            }
        };
        Thread thread = new Thread(task);
        thread.start();

        while (!app.isInit ||!app.controls.isInit)
        {
            Thread.yield();
        }

        app.getStateManager().getState(Player.class).arrowUp();
        app.getStateManager().getState(Player.class).arrowUp();

        for (int i = 0; i < 5; i++) {
            app.getStateManager().getState(Player.class).arrowDown();
        }

        float[] angles = new float[3];
        Geometry arrowGeometry = app.getStateManager().getState(Player.class).getArrowGeometry();
        arrowGeometry.getLocalRotation().toAngles(angles);
        assertTrue(-angles[2] < 0.01);

        app.stop(true);
    }
}