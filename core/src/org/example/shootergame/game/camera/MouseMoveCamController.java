package org.example.shootergame.game.camera;

import org.example.shootergame.math.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.PerspectiveCamera;

public class MouseMoveCamController extends InputAdapter implements CameraController{
	private CamRotationController rotationController;
    Vector lastMousePos;
    
	public MouseMoveCamController(PerspectiveCamera cam) {
		rotationController = new CamRotationController(cam);
	}
	
	@Override
	public void update() {
	}

	@Override
	public InputProcessor getInputProcessor() {
		return this;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		float x = screenX/(float)Gdx.graphics.getWidth();
		float y = screenY/(float)Gdx.graphics.getHeight();

		if(lastMousePos == null){
			lastMousePos = new Vector(x, y, 0);
			return true;
		}
		
		float deltaX = x - lastMousePos.x();
		float deltaY = y - lastMousePos.y();

		float rotDegree = 30;
		
		rotationController.rotateHorizontal(deltaX * -rotDegree);
		rotationController.rotateVertical(deltaY * -rotDegree);
		
		lastMousePos.set(x, y, 0);
		
		return true;
	}
}
