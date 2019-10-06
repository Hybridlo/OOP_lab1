import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;

public class Controls extends AbstractAppState
        implements ActionListener {

    private SimpleApplication app;
    private InputManager inputManager;
    private AssetManager assetManager;
    private Camera cam;
    private Node guiNode;
    private BitmapFont guiFont;
    private BitmapText num;

    private boolean left = false, right = false, up = false, down = false;
    private Vector3f walkDirection = new Vector3f();
    private CharacterControl player;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.assetManager = this.app.getAssetManager();
        this.inputManager = this.app.getInputManager();
        this.cam = this.app.getCamera();
        this.guiNode = this.app.getGuiNode();

        this.player = this.app.getStateManager().getState(Player.class).getPlayer();

        setUpKeys();

        initCrossHairs();

        initGravityCounter();
    }

    private void initCrossHairs() {
        guiNode.detachAllChildren();
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText ch = new BitmapText(guiFont, false);
        ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
        ch.setText("+");        // fake crosshairs
        ch.setLocalTranslation( // center
                app.getCamera().getWidth() / 2 - guiFont.getCharSet().getRenderedSize() / 3 * 2,
                app.getCamera().getHeight() / 2 + (ch.getLineHeight() / 2), 0);
        guiNode.attachChild(ch);
    }

    private void initGravityCounter() {
        num = new BitmapText(guiFont, false);
        num.setSize(guiFont.getCharSet().getRenderedSize() * 2);
        num.setText(Integer.toString(app.getStateManager().getState(Cannonball.class).getGravity()));
        num.setLocalTranslation( // center bottom
                app.getCamera().getWidth() / 2 - guiFont.getCharSet().getRenderedSize() / 3 * 2,
                num.getLineHeight(), 0);
        guiNode.attachChild(num);
    }

    private void updateGravityCounter() {
        num.setText(Integer.toString(app.getStateManager().getState(Cannonball.class).getGravity()));
    }

    private void setUpKeys() {
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A)); //init keys
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Shoot", new KeyTrigger(KeyInput.KEY_SPACE),
                new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addMapping("GravityUp", new KeyTrigger(KeyInput.KEY_Q));
        inputManager.addMapping("GravityDown", new KeyTrigger(KeyInput.KEY_E));
        inputManager.addListener(this, "Left");
        inputManager.addListener(this, "Right");
        inputManager.addListener(this, "Up");
        inputManager.addListener(this, "Down");
        inputManager.addListener(this, "Shoot");
        inputManager.addListener(this, "GravityUp");
        inputManager.addListener(this, "GravityDown");
    }

    @Override
    public void onAction(String binding, boolean isPressed, float tpf) {
        switch (binding) {
            case "Left":
                left = isPressed;
                break;
            case "Right":
                right = isPressed;
                break;
            case "Up":
                up = isPressed;
                break;
            case "Down":
                down = isPressed;
                break;
            case "Shoot":
                if (!isPressed) {
                    app.getStateManager().getState(Cannonball.class).makeCannonBall();
                    break;
                }
            case "GravityUp":
                if (!isPressed) {
                    app.getStateManager().getState(Cannonball.class).gravityUp();
                    updateGravityCounter();
                    break;
                }
            case "GravityDown":
                if (!isPressed) {
                    app.getStateManager().getState(Cannonball.class).gravityDown();
                    updateGravityCounter();
                    break;
                }
        }

    }

    @Override
    public void update(float tpf) {
        Vector3f camDir = cam.getDirection().clone().multLocal(0.6f);
        Vector3f camLeft = cam.getLeft().clone().multLocal(0.4f);
        walkDirection.set(0, 0, 0);
        if (left)  { walkDirection.addLocal(camLeft); }
        if (right) { walkDirection.addLocal(camLeft.negate()); }
        if (up)    { walkDirection.addLocal(camDir); }
        if (down)  { walkDirection.addLocal(camDir.negate()); }
        player.setWalkDirection(walkDirection);
        cam.setLocation(player.getPhysicsLocation().add(new Vector3f(0, 5, 0)));
    }
}
