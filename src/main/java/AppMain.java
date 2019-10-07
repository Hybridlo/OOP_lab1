import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapFont;

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

        Scene scene = new Scene();
        stateManager.attach(scene);
        scene.initialize(stateManager, this);

        Player player = new Player();
        stateManager.attach(player);
        player.initialize(stateManager, this);

        Cannonball cannonball = new Cannonball();
        stateManager.attach(cannonball);
        cannonball.initialize(stateManager, this);

        Controls controls = new Controls();
        stateManager.attach(controls);
        controls.initialize(stateManager, this);
    }
}
