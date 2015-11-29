package org.example.shootergame.game;

import org.example.shootergame.math.Vector;

import com.badlogic.gdx.graphics.g3d.ModelInstance;

public interface GameEntity {

	Vector getVelocity();
	GameEntity setVelocity(float x, float y, float z);

	Vector getPosition();
	GameEntity setPosition(float x, float y, float z);

	void update(float delta);

	void apply(ModelInstance modelInstance);

}