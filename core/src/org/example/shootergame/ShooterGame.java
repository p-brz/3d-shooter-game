package org.example.shootergame;

import com.badlogic.gdx.Game;

public class ShooterGame extends Game {
	private GameScreen screen;
	
	@Override
	public void create() {
		screen =new GameScreen();
		setScreen(screen);
	}

}
