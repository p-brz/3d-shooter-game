package org.example.shootergame.game.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.PerspectiveCamera;

public class CamKeyboardController extends InputAdapter implements CameraController{
	private CamRotationController rotationController;
    
	private float horizontalRot, verticalRot;
	private int rightKey, leftKey, upKey, downKey;
	
	public CamKeyboardController(PerspectiveCamera cam) {
		rotationController = new CamRotationController(cam);
		
		horizontalRot = 1.5f;
		verticalRot = 1.5f;

		rightKey = Keys.RIGHT;
		leftKey = Keys.LEFT;
		upKey = Keys.UP;
		downKey = Keys.DOWN;
	}
	
	@Override
	public void update() {
		if(Gdx.input.isKeyPressed(rightKey)){
			rotationController.rotateHorizontal(-horizontalRot);
		}
		else if(Gdx.input.isKeyPressed(leftKey)){
			rotationController.rotateHorizontal(horizontalRot);
		}

		if(Gdx.input.isKeyPressed(upKey)){
			rotationController.rotateVertical(verticalRot);
		}
		else if(Gdx.input.isKeyPressed(downKey)){
			rotationController.rotateVertical(-verticalRot);
		}
	}

	@Override
	public InputProcessor getInputProcessor() {
		return this;
	}

}
