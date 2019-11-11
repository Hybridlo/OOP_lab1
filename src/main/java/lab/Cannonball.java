package lab;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;

public class Cannonball extends AbstractAppState {

    private SimpleApplication app;
    private Node rootNode;

    private static final Sphere sphere;
    private Material stoneMat;
    private int gravity = 1;
    private Geometry arrowGeometry;

    static {
        sphere = new Sphere(32, 32, 2f, true, false);
        sphere.setTextureMode(Sphere.TextureMode.Projected);        //Initialize the cannon ball geometry
    }



    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.rootNode = this.app.getRootNode();
        AssetManager assetManager = this.app.getAssetManager();

        arrowGeometry = app.getStateManager().getState(Player.class).getArrowGeometry();

        stoneMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        TextureKey key2 = new TextureKey("Textures/Terrain/Rock/Rock.PNG");
        key2.setGenerateMips(true);
        Texture tex2 = assetManager.loadTexture(key2);
        stoneMat.setTexture("ColorMap", tex2);
    }

    public void makeCannonBall() {
        Geometry ballGeo = new Geometry("cannon ball", sphere); //Create a cannon ball geometry and attach to scene graph
        ballGeo.setMaterial(stoneMat);
        rootNode.attachChild(ballGeo);

        ballGeo.setLocalTranslation(arrowGeometry.getLocalTranslation()); //Position the cannon ball

        RigidBodyControl ballPhy = new RigidBodyControl(1f); //Make the ball physical with a mass > 0.0f

        ballGeo.addControl(ballPhy); //Add physical ball to physics space
        app.getStateManager().getState(Physics.class).addToPhysicsSpace(ballPhy);

        float[] angles = new float[3];
        arrowGeometry.getLocalRotation().toAngles(angles);

        Vector3f arrowVector = new Vector3f((float) -Math.cos(angles[2]), (float) -Math.sin(angles[2]), 0);

        ballPhy.setLinearVelocity(arrowVector.mult(50)); //Accelerate the physical ball to shoot it

        ballPhy.setGravity(new Vector3f(0,-10f * gravity,0));
    }

    public void gravityUp() {
        if (gravity < 10) {
            this.gravity++;
        }
    }

    public void gravityDown() {
        if (gravity > 1) {
            this.gravity--;
        }
    }

    public int getGravity() {
        return gravity;
    }
}
