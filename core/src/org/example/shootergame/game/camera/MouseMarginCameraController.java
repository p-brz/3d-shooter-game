package org.example.shootergame.game.camera;

import org.example.shootergame.math.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;

public class MouseMarginCameraController extends InputAdapter implements CameraController{
	private CamRotationController rotationController;
    
	final Vector relativeMousePos;

    final float relativeMaxMargin;
    final float outOfScreenThreshold;
    
	public MouseMarginCameraController(PerspectiveCamera cam) {
		rotationController = new CamRotationController(cam);

    	relativeMousePos = new Vector(0.5f,0.5f,0);
    	relativeMaxMargin = 0.9f;
    	outOfScreenThreshold = 0.02f;
	}

	@Override
	public InputProcessor getInputProcessor() {
		return this;
	}
	
	@Override
	public void update(){
	    if(isInMargin(relativeMousePos.x())){
	    	boolean positive = relativeMousePos.x() > relativeMaxMargin;
	    	rotationController.rotateHorizontal(getRotation(positive));;
		}

	    if(isInMargin(relativeMousePos.y())){
	    	boolean positive = relativeMousePos.y() > relativeMaxMargin;
	    	rotationController.rotateVertical(getRotation(positive));
	    }
        
//        if(isInMargin(relativeMousePos.x())){
//        	System.out.println(Gdx.input.getX() + "; " + relativeMousePos.x());
//        }
	}
	
	private float getRotation(boolean positive) {
		return positive ? -1f : 1;
	}

	private boolean isInMargin(float coord) {
		if(coord < outOfScreenThreshold || coord > 1.0f - outOfScreenThreshold){
			return false;
		}
		return coord < 1f - relativeMaxMargin || coord > relativeMaxMargin;
	}

	@Override
	public boolean scrolled(int amount) {
		float move = 5 * amount;
		PerspectiveCamera cam = rotationController.getCam();
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
