package lab;

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
    private BitmapText num;

    private static final String GRAVITY_DOWN = "GravityDown";
    private static final String GRAVITY_UP = "GravityUp";
    private static final String ARROW_DOWN = "ArrowDown";
    private static final String ARROW_UP = "ArrowUp";
    private static final String SHOOT = "Shoot";

    volatile boolean isInit = false;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.assetManager = this.app.getAssetManager();
        this.inputManager = this.app.getInputManager();
        this.guiNode = this.app.getGuiNode();

        setUpKeys();

        initGravityCounter();

        isInit = true;
    }

    private void initGravityCounter() {
        guiNode.detachAllChildren();
        BitmapFont guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        num = new BitmapText(guiFont, false);
        num.setSize((float)guiFont.getCharSet().getRenderedSize() * 2);
        num.setText(Integer.toString(app.getStateManager().getState(Cannonball.class).getGravity()));
        num.setLocalTranslation( // center bottom
                (float)app.getCamera().getWidth() / 2 - (float)guiFont.getCharSet().getRenderedSize() / 3 * 2,
                num.getLineHeight(), 0);
        guiNode.attachChild(num);
    }

    private void updateGravityCounter() {
        num.setText(Integer.toString(app.getStateManager().getState(Cannonball.class).getGravity()));
    }

    private void setUpKeys() {
        inputManager.addMapping(GRAVITY_DOWN, new KeyTrigger(KeyInput.KEY_A)); //init keys
        inputManager.addMapping(GRAVITY_UP, new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping(ARROW_UP, new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping(ARROW_DOWN, new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping(SHOOT, new KeyTrigger(KeyInput.KEY_SPACE),
                new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(this, GRAVITY_DOWN);
        inputManager.addListener(this, GRAVITY_UP);
        inputManager.addListener(this, ARROW_UP);
        inputManager.addListener(this, ARROW_DOWN);
        inputManager.addListener(this, SHOOT);
    }

    @Override
    public void onAction(String binding, boolean isPressed, float tpf) {
        if (!isPressed) {
            switch (binding) {
                case GRAVITY_DOWN:
                    app.getStateManager().getState(Cannonball.class).gravityDown();
                    updateGravityCounter();
                    break;
                case GRAVITY_UP:
                    app.getStateManager().getState(Cannonball.class).gravityUp();
                    updateGravityCounter();
                    break;
                case ARROW_UP:
                    app.getStateManager().getState(Player.class).arrowUp();
                    break;
                case ARROW_DOWN:
                    app.getStateManager().getState(Player.class).arrowDown();
                    break;
                case SHOOT:
                    app.getStateManager().getState(Cannonball.class).makeCannonBall();
                    break;
                default:
                    break;
            }
        }
    }
}
