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

        Scene scene = new Scene();
        stateManager.attach(scene);

        Player player = new Player();
        stateManager.attach(player);

        Cannonball cannonball = new Cannonball();
        stateManager.attach(cannonball);

        Controls controls = new Controls();
        stateManager.attach(controls);
    }
}
