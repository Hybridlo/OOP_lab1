package lab;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.BulletAppState;

public class Physics extends AbstractAppState {

    private BulletAppState bulletAppState;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
    }

    void addToPhysicsSpace(Object obj) {
        bulletAppState.getPhysicsSpace().add(obj);
    }
}
