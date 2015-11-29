package org.example.shootergame.game;

import java.util.Collection;
import java.util.LinkedList;

public class ActorsManager extends CollectionManager<GameActor>{
	public static class Expiration<T>{
		private float timeToLive;
		private T  data;
		
		public Expiration(float timeToLive, T data) {
			this.timeToLive = timeToLive;
			this.data = data;
		}
		
		public float getTimeToLive() {
			return timeToLive;
		}
		public void setTimeToLive(float timeToLive) {
			this.timeToLive = timeToLive;
		}

		public T getData() {
			return data;
		}
		public void setData(T data) {
			this.data = data;
		}

		public void count(float delta) {
			this.timeToLive -= delta;
		}

		public boolean hasExpired() {
			return timeToLive <= 0;
		}
	}
	public static class DeathManager<T> extends CollectionManager<Expiration<T>>{
		private Collection<Expiration<T>> expiredTemp;
		
		public DeathManager() {
			expiredTemp = new LinkedList<ActorsManager.Expiration<T>>();
		}
		
		public Collection<T> update(float delta, Collection<T> expiredItems){
			expiredItems.clear();
			
			if(hasItems()){
				updateExpirations(delta, expiredItems);
				this.removeAll(expiredTemp);
			}
			
			return expiredItems;
		}

		private void updateExpirations(float delta, Collection<T> expiredItems) {
			for(Expiration<T> expiration : getItems()){
				expiration.count(delta);
				if(expiration.hasExpired()){
					expiredItems.add(expiration.getData());
					this.expiredTemp.add(expiration);
				}
			}
		}
	}
	
	private DeathManager<GameActor> shortLivedEntities;
	private Collection<GameActor> deathEntitiesTemp;
	private ModelRender modelRender;

	public ActorsManager() {
		this(null);
	}
	public ActorsManager(ModelRender modelRender) {
		shortLivedEntities = new DeathManager<GameActor>();
		deathEntitiesTemp = new LinkedList<GameActor>();
		this.modelRender = modelRender;
	}
	
	public ModelRender getModelRender() {
		return modelRender;
	}
	public void setModelRender(ModelRender modelRender) {
		this.modelRender = modelRender;
	}
	
	@Override
	public void add(GameActor... items) {
		super.add(items);
		modelRender.add(items);
	}

	@SuppressWarnings("unchecked")
	public void add(GameActor actor, float secondsToLive) {
		this.add(actor);
		this.shortLivedEntities.add(new Expiration<GameActor>(secondsToLive, actor));
	}
	
	@Override
	public void remove(GameActor... items) {
		super.remove(items);
		modelRender.remove(items);
	}
	
	@Override
	public void removeAll(Collection<GameActor> items) {
		super.removeAll(items);
		for(GameActor item : items){
			modelRender.remove(item);
		}
	}
	
	public void update(float delta){
		removeDeathEntities(delta);
		
		for(GameActor item : getItems()){
			item.update(delta);
		}
	}

	public void render(){
		this.modelRender.render();
	}
	
	private void removeDeathEntities(float delta) {
		shortLivedEntities.update(delta, deathEntitiesTemp);
		this.removeAll(deathEntitiesTemp);
		
		deathEntitiesTemp.clear();
	}
}
