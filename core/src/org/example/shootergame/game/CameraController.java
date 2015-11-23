package org.example.shootergame.game;

import org.example.shootergame.math.Vector;
import org.example.shootergame.math.VectorGdxAdapter;
import org.example.shootergame.math.Vectors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;

public class CameraController extends InputAdapter{
	public PerspectiveCamera cam;
    final Vector upAxis;
    
	final Vector relativeMousePos;

    final float relativeMaxMargin;
    final float outOfScreenThreshold;
    
	public CameraController(PerspectiveCamera cam) {
		this.cam = cam;
    	upAxis = new Vector(0,1f,0f);

    	relativeMousePos = new Vector(0.5f,0.5f,0);
    	relativeMaxMargin = 0.9f;
    	outOfScreenThreshold = 0.02f;
	}
	
	public void update(){
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
	
	@Override
	public boolean scrolled(int amount) {
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
}
