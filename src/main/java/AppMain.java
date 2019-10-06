import com.jme3.app.SimpleApplication;

public class AppMain  extends SimpleApplication {

    public static void main(String[] args) {
        AppMain app = new AppMain();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        Physics physics = new Physics();
        stateManager.attach(physics);
        physics.initialize(stateManager, this);

        Terrain terrain = new Terrain();
        stateManager.attach(terrain);
        terrain.initialize(stateManager, this);

        Player player = new Player();
        stateManager.attach(player);
        player.initialize(stateManager, this);

        Controls controls = new Controls();
        stateManager.attach(controls);
        controls.initialize(stateManager, this);
    }
}
