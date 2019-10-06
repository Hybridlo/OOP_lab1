import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.scene.Spatial;

public class Physics extends AbstractAppState {

    private SimpleApplication app;
    private AppStateManager stateManager;

    private BulletAppState bulletAppState;

    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.stateManager = this.app.getStateManager();

        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
    }

    void addToPhysicsSpace(Spatial spatial) {
        bulletAppState.getPhysicsSpace().add(spatial);
    }
}
