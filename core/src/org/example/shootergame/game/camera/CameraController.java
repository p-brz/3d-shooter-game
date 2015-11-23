package org.example.shootergame.game.camera;

import com.badlogic.gdx.InputProcessor;

public interface CameraController {
	public void update();
	public InputProcessor getInputProcessor();
}
