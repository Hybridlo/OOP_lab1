import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Vector3f;

public class Player extends AbstractAppState {

    private SimpleApplication app;

    private CharacterControl player;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;

        CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1.5f, 6f, 1);
        player = new CharacterControl(capsuleShape, 0.05f);

        player.setPhysicsLocation(new Vector3f(-10, 10, 10));

        app.getStateManager().getState(Physics.class).addToPhysicsSpace(player);

        player.setGravity(new Vector3f(0,-30f,0));
    }

    CharacterControl getPlayer() {
        return player;
    }
}
