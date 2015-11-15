package org.example.game;

import java.util.Collection;
import java.util.LinkedList;

import org.example.game.math.Vector;
import org.example.game.math.VectorGdxAdapter;
import org.example.game.math.Vectors;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;

public class ShooterGame extends ApplicationAdapter {
	public PerspectiveCamera cam;
	public ModelBatch modelBatch;
    public Model model;
    public final Collection<ModelInstance> instances;
    Environment environment;
    
    final Vector upAxis;
    
    final Vector relativeMousePos;
    final float relativeMaxMargin;
    final float outOfScreenThreshold;
    
    public ShooterGame() {
    	instances = new LinkedList<ModelInstance>();
    	upAxis = new Vector(0,1f,0f);
    	
    	relativeMousePos = new Vector(0.5f,0.5f,0);
    	relativeMaxMargin = 0.9f;
    	outOfScreenThreshold = 0.02f;
    }
    
    @Override
    public void create () {
        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(0f, 0f, 10f);
        cam.lookAt(0f, 0f, 0f);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();
        
        modelBatch = new ModelBatch();
        
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
        
        ModelBuilder modelBuilder = new ModelBuilder();
        model = modelBuilder.createBox(1f, 1f, 1f, 
                new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                Usage.Position | Usage.Normal);
        
        for (float x = -5f; x <= 5f; x += 2f) {
            for (float z = -5f; z <= 5f; z += 2f) {
                ModelInstance shipInstance = new ModelInstance(model);
                shipInstance.transform.setToTranslation(x, 0, z);
                instances.add(shipInstance);
            }
        }
        
        Gdx.input.setInputProcessor(new ShooterInput());
    }
    
    
    
    @Override
    public void render() {
    	Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

//      if(Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.D)){
//    	boolean positive = Gdx.input.isKeyPressed(Keys.D);
	    if(isInMargin(relativeMousePos.x())){
	    	boolean positive = relativeMousePos.x() > relativeMaxMargin;
	    	rotateCam(positive, upAxis);
		}
//    else if(Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.S)){
//    	boolean positive = Gdx.input.isKeyPressed(Keys.S);
	    if(isInMargin(relativeMousePos.y())){
	    	boolean positive = relativeMousePos.y() > relativeMaxMargin;
	    	Vector rotationAxis = Vectors.cross(new VectorGdxAdapter(cam.direction)
	    							, new VectorGdxAdapter(cam.up), new Vector());
	    	rotationAxis.normalize();
//	    	System.out.print("rotation axis: " + rotationAxis);
//			System.out.print("; current dir: " + cam.direction);
//			System.out.println("; current up: " + cam.up);
	    	
	    	rotateCam(positive, rotationAxis);
	//    	cam.normalizeUp();
	    }
        
        if(isInMargin(relativeMousePos.x())){
        	System.out.println(Gdx.input.getX() + "; " + relativeMousePos.x());
        }
        
        modelBatch.begin(cam);
        for(ModelInstance instance : instances){
        	modelBatch.render(instance, environment);
        }
        modelBatch.end();
    }

	private boolean isInMargin(float coord) {
		if(coord < outOfScreenThreshold || coord > 1.0f - outOfScreenThreshold){
			return false;
		}
		return coord < 1f - relativeMaxMargin || coord > relativeMaxMargin;
	}

	private void rotateCam(boolean positive, Vector rotationAxis) {
		float rotDegree =  positive ? -1f : 1f;
		
		Vector rotatedVector = Vectors.rotate(new VectorGdxAdapter(cam.direction)
										, rotationAxis
										, rotDegree).normalize();
		cam.direction.set(rotatedVector.x(), rotatedVector.y(), rotatedVector.z());
		cam.update();
	}
    

	class ShooterInput implements InputProcessor {
		final float[][] rotationMatrix;
		
		public ShooterInput() {
			rotationMatrix = new float[3][3];
			for(int i =0; i < 3; ++i){
				for(int j=0; j < 3; ++j){
					rotationMatrix[i][j] = i == j ? 1 : 0;
				}
			}
		}
		
		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchDown(int screenX, int screenY, int pointer, int button) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean scrolled(int amount) {
//			Gdx.app.log(getClass().getName(), "scroll: " + amount);
			float move = 5 * amount;
			Vector3 vecMove = cam.direction.cpy().scl(move);
			cam.position.add(vecMove);
			cam.update();
			return false;
		}

		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			float x = screenX/(float)Gdx.graphics.getWidth();
			float y = screenY/(float)Gdx.graphics.getHeight();
			
			relativeMousePos.set(x,y,0);
        
			return false;
		}

		@Override
		public boolean keyUp(int keycode) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean keyTyped(char character) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean keyDown(int keycode) {
			
			return false;
		}

	}

}
