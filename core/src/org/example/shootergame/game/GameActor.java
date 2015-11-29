package org.example.shootergame.game;

import org.example.shootergame.math.Vector;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class GameActor implements RenderableProvider{
	private ModelInstance modelInstance;
	private GameEntity gameEntity;
	
	public GameActor(Model model) {
		this(new ModelInstance(model), new BasicGameEntity());
	}
	public GameActor(ModelInstance instance, GameEntity gameEntity) {
		super();
		this.modelInstance = instance;
		this.gameEntity = gameEntity;
	}
	
	public ModelInstance getModelInstance() {
		return modelInstance;
	}
	public GameActor setModelInstance(ModelInstance instance) {
		this.modelInstance = instance;
		return this;
	}
	public GameEntity getGameEntity() {
		return gameEntity;
	}
	public GameActor setGameEntity(GameEntity gameEntity) {
		this.gameEntity = gameEntity;
		return this;
	}
	
	public Vector getVelocity(){
		return getGameEntity().getVelocity();
	}
	public GameActor setVelocity(float x, float y, float z){
		getGameEntity().setVelocity(x, y, z);
		return this;
	}
	
	public void update(float delta){
		gameEntity.update(delta);
		gameEntity.apply(modelInstance);
	}
	
	@Override
	public void getRenderables(Array<Renderable> renderables, Pool<Renderable> pool) {
		modelInstance.getRenderables(renderables, pool);
	}
}
