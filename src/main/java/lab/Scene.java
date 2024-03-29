package lab;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

public class Scene extends AbstractAppState {

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        SimpleApplication theApp = (SimpleApplication) app;
        Node rootNode = theApp.getRootNode();
        AssetManager assetManager = theApp.getAssetManager();

        Box box = new Box(100.0f,0.5f,5.0f);
        Spatial floor = new Geometry("Box", box );
        Material matBrick = new Material(
                assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matBrick.setTexture("ColorMap",
                assetManager.loadTexture("Textures/Terrain/BrickWall/BrickWall.jpg"));
        floor.setMaterial(matBrick);
        floor.setLocalTranslation(0.0f,0.0f,-100.0f);

        CollisionShape sceneShape =
                CollisionShapeFactory.createMeshShape(floor);
        RigidBodyControl floorPhys = new RigidBodyControl(sceneShape, 0);

        floor.addControl(floorPhys);

        rootNode.attachChild(floor);

        app.getStateManager().getState(Physics.class).addToPhysicsSpace(floorPhys);
    }
}
