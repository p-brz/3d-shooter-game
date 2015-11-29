package org.example.shootergame;

import org.example.shootergame.game.DisposableManager;
import org.example.shootergame.game.GameActor;
import org.example.shootergame.game.GameEntity;
import org.example.shootergame.game.ActorsManager;
import org.example.shootergame.game.BasicGameEntity;
import org.example.shootergame.game.ModelRender;
import org.example.shootergame.game.camera.CamKeyboardController;
import org.example.shootergame.game.camera.CameraController;
import org.example.shootergame.game.camera.MouseMarginCameraController;
import org.example.shootergame.math.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

public class GameScreen extends BaseScreen {
	private PerspectiveCamera cam;
    private CameraController cameraController;
    private Environment environment;
    
    private Model greenCube, blueCube, bulletModel, axisModel;
    private ModelRender modelRender;
    
    private GameActor runner;
    private ActorsManager actorsManager;
    private InputMultiplexer multiplexer;
    
    private DisposableManager disposableManager;
    
    public GameScreen() {
    	disposableManager = new DisposableManager();
    	multiplexer = new InputMultiplexer();
    	
    	create();
    }
    
    public void create () {
        setupCam();
        setupEnvironment();
        setupScene(cam, environment);
        setupInput();
    }

	private void setupCam() {
    	cam = new PerspectiveCamera(90, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(0f, 0f, 10f);
        cam.lookAt(0f, 0f, 0f);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();

        setCamInput();
	}

	private void setCamInput() {
//		cameraController = new MouseMarginCameraController(cam);
		cameraController = new CamKeyboardController(cam);
        InputProcessor camInput = cameraController.getInputProcessor();
        if(camInput != null){
        	multiplexer.addProcessor(camInput);
        }
	}
	
	private void setupEnvironment() {
		environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
	}

	private void setupScene(PerspectiveCamera camera, Environment environment) {
		ModelBuilder modelBuilder = new ModelBuilder();
        greenCube = modelBuilder.createBox(1f, 1f, 1f, 
                new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                Usage.Position | Usage.Normal);

        blueCube = modelBuilder.createBox(1f, 1f, 1f, 
                new Material(ColorAttribute.createDiffuse(Color.BLUE)),
                Usage.Position | Usage.Normal);
        axisModel = modelBuilder.createXYZCoordinates(3
        		, new Material(ColorAttribute.createDiffuse(Color.GOLD))
        		, Usage.Position | Usage.Normal);
        
        
        bulletModel = modelBuilder.createSphere(1f, 1f, 1f
        			, 16, 16
        			, new Material(ColorAttribute.createDiffuse(Color.FIREBRICK))
        			, Usage.Position | Usage.Normal);
//        for (float x = -5f; x <= 5f; x += 2f) {
//            for (float z = -5f; z <= 5f; z += 2f) {
//                ModelInstance shipInstance = new ModelInstance(model);
//                shipInstance.transform.setToTranslation(x, 0, z);
//                instances.add(shipInstance);
//            }
//        }

    	modelRender = new ModelRender(camera, environment);
    	
        ModelInstance reference = new ModelInstance(axisModel);
        modelRender.add(reference);

    	actorsManager = new ActorsManager(modelRender);
    	
        runner = new GameActor(greenCube);
        runner.getGameEntity().setPosition(0, 1, 0).setVelocity(5f, 0, 0);
        
        actorsManager.add(runner);
        actorsManager.add(new GameActor(blueCube)
        					.setGameEntity(new BasicGameEntity().setPosition(0, -1, 0)));
        
        disposableManager.add(greenCube, blueCube, modelRender);
	}

	private void setupInput() {
		multiplexer.addProcessor(new InputAdapter(){
			Vector camDir = new Vector(), camPosition = new Vector();
			
        	@Override
        	public boolean keyDown(int keycode) {
        		if(Keys.SPACE == keycode){
        			System.out.println("Fire");
//        			modelRender.add(new ModelInstance(blueCube));

        			camDir.set(cam.direction.x, cam.direction.y, cam.direction.z);
        			camPosition.set(cam.position.x, cam.position.y, cam.position.z);
        			
        			GameEntity entity = new BasicGameEntity();
        			entity.getPosition().set(camPosition.add(camDir.scl(cam.near * 2)));
        			entity.getVelocity().set(camDir.normalize().scl(15));

        			actorsManager.add(new GameActor(bulletModel).setGameEntity(entity), 2f);
        		}
        		return true;
        	}
        });
        
        Gdx.input.setInputProcessor(multiplexer);
	}
    
	@Override
	public void render(float delta) {
		update(delta);
		draw(delta);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		
		disposableManager.dispose();
	}
	
	public void update(float delta){
		cameraController.update();
//		runner.update(delta);
		actorsManager.update(delta);
	}
	
	public void draw(float delta){
    	Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        
//		modelRender.render();
        actorsManager.render();
	}
}
