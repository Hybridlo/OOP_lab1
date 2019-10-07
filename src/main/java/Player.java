import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.debug.Arrow;

public class Player extends AbstractAppState {

    private SimpleApplication app;
    private AssetManager assetManager;
    private Node rootNode;

    private Geometry arrowGeometry;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.assetManager = this.app.getAssetManager();
        this.rootNode = this.app.getRootNode();

        this.app.getFlyByCamera().setMoveSpeed(0);

        Arrow arrow = new Arrow(Vector3f.UNIT_X);
        arrowGeometry = new Geometry("coordinate axis", arrow);
        arrowGeometry.scale(-10, 10, 0);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.getAdditionalRenderState().setWireframe(true);
        mat.getAdditionalRenderState().setLineWidth(4);
        mat.setColor("Color", ColorRGBA.Green);
        arrowGeometry.setMaterial(mat);
        arrowGeometry.setLocalTranslation(new Vector3f(97f, 10f, -100f));
        rootNode.attachChild(arrowGeometry);
    }

    Geometry getArrowGeometry() {
        return arrowGeometry;
    }

    void arrowUp() {
        float[] angles = new float[3];
        arrowGeometry.getLocalRotation().toAngles(angles);
        if (angles[2] * FastMath.RAD_TO_DEG > -89) {
            arrowGeometry.rotate(0, 0, (float) Math.toRadians(-10));
        }
    }

    void arrowDown() {
        float[] angles = new float[3];
        arrowGeometry.getLocalRotation().toAngles(angles);
        if (angles[2] * FastMath.RAD_TO_DEG < -1) {
            arrowGeometry.rotate(0, 0, (float) Math.toRadians(10));
        }
    }
}
