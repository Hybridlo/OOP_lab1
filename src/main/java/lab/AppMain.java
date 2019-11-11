package lab;

import com.jme3.app.SimpleApplication;

public class AppMain  extends SimpleApplication {

    Physics physics;
    Scene scene;
    Player player;
    Cannonball cannonball;
    Controls controls;

    volatile boolean isInit = false;

    public static void main(String[] args) {
        AppMain app = new AppMain();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        physics = new Physics();
        stateManager.attach(physics);

        scene = new Scene();
        stateManager.attach(scene);

        player = new Player();
        stateManager.attach(player);

        cannonball = new Cannonball();
        stateManager.attach(cannonball);

        controls = new Controls();
        stateManager.attach(controls);

        isInit = true;
    }
}
