import com.jme3.app.SimpleApplication;

public class AppMain  extends SimpleApplication {

    public static void main(String[] args) {
        AppMain app = new AppMain();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        Terrain terrain = new Terrain();
        terrain.initialize(stateManager, this);

        Physics physics = new Physics();
        physics.initialize(stateManager, this);
    }
}
