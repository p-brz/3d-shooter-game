package org.example.shootergame.game;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.utils.Disposable;

public class ModelRender extends CollectionManager<RenderableProvider> implements Disposable{

	private ModelBatch modelBatch;

	private Camera cam;
	private Environment environment;
	
	public ModelRender() {
		this(null, null);
	}
	public ModelRender(Camera cam, Environment environment) {
		super();
		this.cam = cam;
		this.environment = environment;
	}
	
	public Camera getCam() {
		return cam;
	}
	public void setCam(Camera cam) {
		this.cam = cam;
	}

	public Environment getEnvironment() {
		return environment;
	}
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	@Override
	public void dispose() {
		modelBatch.dispose();
		modelBatch = null;
	}
	
	public void render(){
		if(modelBatch == null){
	    	modelBatch = new ModelBatch();
		}
		
		modelBatch.begin(cam);
		modelBatch.render(getItems(), environment);
		modelBatch.end();
	}

}
