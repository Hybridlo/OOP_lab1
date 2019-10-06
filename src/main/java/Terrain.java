import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;

import java.util.ArrayList;
import java.util.List;

public class Terrain extends AbstractAppState {

    private SimpleApplication app;
    private Node rootNode;
    private AssetManager assetManager;

    private TerrainQuad terrain;
    private Material mat_terrain;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.rootNode     = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();

        mat_terrain = new Material(assetManager,
                "Common/MatDefs/Terrain/Terrain.j3md");         //Create terrain material and load four textures into it.

        mat_terrain.setTexture("Alpha", assetManager.loadTexture(
                "Textures/Terrain/splat/alphamap.png"));           //Add ALPHA map (for red-blue-green coded splat textures)

        Texture grass = assetManager.loadTexture(
                "Textures/Terrain/splat/grass.jpg");
        grass.setWrap(Texture.WrapMode.Repeat);
        mat_terrain.setTexture("Tex1", grass);
        mat_terrain.setFloat("Tex1Scale", 64f);              //Add GRASS texture into the red layer (Tex1).

        Texture dirt = assetManager.loadTexture(
                "Textures/Terrain/splat/dirt.jpg");
        dirt.setWrap(Texture.WrapMode.Repeat);
        mat_terrain.setTexture("Tex2", dirt);
        mat_terrain.setFloat("Tex2Scale", 32f);              //Add DIRT texture into the green layer (Tex2)

        Texture rock = assetManager.loadTexture(
                "Textures/Terrain/splat/road.jpg");
        rock.setWrap(Texture.WrapMode.Repeat);
        mat_terrain.setTexture("Tex3", rock);
        mat_terrain.setFloat("Tex3Scale", 128f);             //Add ROAD texture into the blue layer (Tex3)

        Texture heightMapImage = assetManager.loadTexture(
                "Textures/Terrain/splat/mountains512.png");
        AbstractHeightMap heightmap = new ImageBasedHeightMap(heightMapImage.getImage());   //Create the height map
        heightmap.load();

        /* We have prepared material and heightmap.
          Now we create the actual terrain
         */
        terrain = new TerrainQuad("my terrain", 65, 513, heightmap.getHeightMap());

        terrain.setMaterial(mat_terrain);
        terrain.setLocalTranslation(0, -100, 0);
        terrain.setLocalScale(2f, 1f, 2f);
        rootNode.attachChild(terrain);                                    //We give the terrain its material, position & scale it, and attach it.

        List<Camera> cameras = new ArrayList<>();
        cameras.add(app.getCamera());
        TerrainLodControl control = new TerrainLodControl(terrain, cameras);
        terrain.addControl(control);                                      //The LOD (level of detail) depends on were the camera is

        terrain.addControl(new RigidBodyControl(0));                //Add physics
    }
}
