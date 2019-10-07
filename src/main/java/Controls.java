import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.scene.Node;

public class Controls extends AbstractAppState
        implements ActionListener {

    private SimpleApplication app;
    private InputManager inputManager;
    private AssetManager assetManager;
    private Node guiNode;
    private BitmapFont guiFont;
    private BitmapText num;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.assetManager = this.app.getAssetManager();
        this.inputManager = this.app.getInputManager();
        this.guiNode = this.app.getGuiNode();

        setUpKeys();

        initGravityCounter();
    }

    private void initGravityCounter() {
        guiNode.detachAllChildren();
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
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
        inputManager.addMapping("GravityDown", new KeyTrigger(KeyInput.KEY_A)); //init keys
        inputManager.addMapping("GravityUp", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("ArrowUp", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("ArrowDown", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Shoot", new KeyTrigger(KeyInput.KEY_SPACE),
                new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(this, "GravityDown");
        inputManager.addListener(this, "GravityUp");
        inputManager.addListener(this, "ArrowUp");
        inputManager.addListener(this, "ArrowDown");
        inputManager.addListener(this, "Shoot");
    }

    @Override
    public void onAction(String binding, boolean isPressed, float tpf) {
        if (!isPressed) {
            switch (binding) {
                case "GravityDown":
                    app.getStateManager().getState(Cannonball.class).gravityDown();
                    updateGravityCounter();
                    break;
                case "GravityUp":
                    app.getStateManager().getState(Cannonball.class).gravityUp();
                    updateGravityCounter();
                    break;
                case "ArrowUp":
                    app.getStateManager().getState(Player.class).arrowUp();
                    break;
                case "ArrowDown":
                    app.getStateManager().getState(Player.class).arrowDown();
                    break;
                case "Shoot":
                    app.getStateManager().getState(Cannonball.class).makeCannonBall();
                    break;
            }
        }
    }
}
