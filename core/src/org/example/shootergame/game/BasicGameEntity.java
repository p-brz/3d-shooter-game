package org.example.shootergame.game;

import org.example.shootergame.math.Vector;

import com.badlogic.gdx.graphics.g3d.ModelInstance;

public class BasicGameEntity implements GameEntity {
	private final Vector velocity;
	private final Vector position;
	
	private final Vector posIncrement;
	
	public BasicGameEntity() {
		velocity = new Vector();
		position = new Vector();
		posIncrement = new Vector();
	}
	
	/* (non-Javadoc)
	 * @see org.example.shootergame.game.GameEnt#getVelocity()
	 */
	@Override
	public Vector getVelocity(){
		return velocity;
	}
	
	@Override
	public GameEntity setVelocity(float x, float y, float z) {
		velocity.set(x, y, z);
		return this;
	}

	@Override
	public Vector getPosition() {
		return position;
	}

	@Override
	public GameEntity setPosition(float x, float y, float z) {
		position.set(x, y, z);
		return this;
	}
	
	/* (non-Javadoc)
	 * @see org.example.shootergame.game.GameEnt#update(float)
	 */
	@Override
	public void update(float delta) {
		position.add(posIncrement.set(velocity).scl(delta));
	}
	
	/* (non-Javadoc)
	 * @see org.example.shootergame.game.GameEnt#apply(com.badlogic.gdx.graphics.g3d.ModelInstance)
	 */
	@Override
	public void apply(ModelInstance modelInstance){
		modelInstance.transform.setTranslation(position.x(), position.y(), position.z());
	}
}
