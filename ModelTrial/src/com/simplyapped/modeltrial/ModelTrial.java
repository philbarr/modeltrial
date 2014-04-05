package com.simplyapped.modeltrial;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.lights.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.lights.Lights;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

public class ModelTrial implements ApplicationListener {

    private static final String DATA = "data/Market-Place.g3db";
	public PerspectiveCamera cam;
    public CameraInputController camController;
    public ModelBatch modelBatch;
    public SpriteBatch spriteBatch;
    public AssetManager assets;
    public Array<ModelInstance> instances = new Array<ModelInstance>();
    public Lights lights;
    public boolean loading;
    private Touchpad touchpadStrafe;
    private Touchpad touchpadLook;
    private Skin skin;
	private Stage stage;
	private float speed = 2;
	private TextButton reset;
	private Label progress;
     
    @Override
    public void create () {
    	Texture.setEnforcePotImages(false);
    	skin = new Skin(Gdx.files.internal("data/modeltrial.json"));
        modelBatch = new ModelBatch();
        lights = new Lights();
        lights.ambientLight.set(0.4f, 0.4f, 0.4f, 1f);
        lights.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
         
        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        resetAll();
 
        camController = new PhilCameraInputController(cam);
        camController.scrollFactor = 100f;
        camController.translateUnits = 1000f;
         
        assets = new AssetManager(); 
        assets.load(ModelTrial.DATA, Model.class);
        
        spriteBatch = new SpriteBatch();
    	stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true, spriteBatch);
    	float buttonSize = Gdx.graphics.getWidth() / 5;
    	int offset = 15;
    	touchpadStrafe = new Touchpad(5f, skin, "model");
		touchpadStrafe.setBounds(offset, offset, buttonSize, buttonSize);
    	touchpadLook = new Touchpad(5f, skin, "model");
    	touchpadLook.setBounds(Gdx.graphics.getWidth() - buttonSize - offset, offset, buttonSize, buttonSize);
    	reset = new TextButton("Reset", skin, "button");
    	reset.setSize(Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 8);
    	reset.setPosition(Gdx.graphics.getWidth() / 2 - reset.getWidth() / 2, offset);
    	reset.addListener(new ClickListener(){
    		@Override
    		public void clicked(InputEvent event, float x, float y)
    		{
    	        resetAll();
    		}
    	});
    	reset.setVisible(false);
    	progress = new Label("Loading...",skin,"progress");
    	progress.setPosition(Gdx.graphics.getWidth() / 2 - progress.getWidth() / 2, offset);
    	progress.setSize(Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 8);
    	stage.addActor(touchpadStrafe);
    	stage.addActor(touchpadLook);
    	stage.addActor(reset);
    	stage.addActor(progress);
    	stage.addListener(new ClickListener()
		{
			@Override
			public boolean keyDown(InputEvent event, int keycode)
			{
				if (keycode == Keys.BACK || keycode == Keys.BACKSPACE)
				{
					Gdx.app.exit();
				}
				return false;
			}
		});
        Gdx.input.setInputProcessor(stage);

        loading = true;
    }

	private void resetAll()
	{
		cam.position.set(1f, 1f, 100f);
        cam.lookAt(0,0,0);
        cam.near = 0.1f; 
        cam.far = 300f;
        cam.update();
	}
 
    private void doneLoading() {
        Model ship = assets.get(ModelTrial.DATA, Model.class);
        ModelInstance shipInstance = new ModelInstance(ship);
        instances.add(shipInstance);
        loading = false;
        progress.setVisible(false);
        reset.setVisible(true);
    }
     
    @Override
    public void render () {
        if (loading && assets.update())
        {
        	doneLoading();
        }
        else
        {
        	// touchpadLook
        	cam.direction.rotate(new Vector3(0,1,0), -touchpadLook.getKnobPercentX() * speed * 3);
        	cam.direction.y += touchpadLook.getKnobPercentY() * 0.2f;
        	
	        // touchpadStrafe
	        Vector3 xVector = cam.direction.cpy();
	        xVector.rotate(-90,  0,1,0);
	        xVector.scl(touchpadStrafe.getKnobPercentX() * speed);
	        Vector3 forward = cam.direction.cpy();
	        forward.scl(touchpadStrafe.getKnobPercentY() * speed);
        	Vector3 movement = xVector.add(forward);
			cam.translate(movement);
        	
            cam.update();
        }

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);        
 
        modelBatch.begin(cam);
        for (ModelInstance instance : instances)
        {
        	modelBatch.render(instance, lights);
        }
        modelBatch.end();

        stage.act(Gdx.graphics.getDeltaTime());        
        stage.draw();
    }
     
    @Override
    public void dispose () {
        modelBatch.dispose();
        instances.clear();
        stage.dispose();
    }
 
    public void resume () {
    }
 
    public void resize (int width, int height) {
    }
 
    public void pause () {
    }
    
}