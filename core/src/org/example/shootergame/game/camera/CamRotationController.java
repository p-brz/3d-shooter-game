package org.example.shootergame.game.camera;

import org.example.shootergame.math.Vector;
import org.example.shootergame.math.VectorGdxAdapter;
import org.example.shootergame.math.Vectors;

import com.badlogic.gdx.graphics.PerspectiveCamera;

public class CamRotationController{
	protected PerspectiveCamera cam;
    protected final Vector upAxis;

	public CamRotationController(PerspectiveCamera cam) {
		this(cam, new Vector(0,1f,0f));
	}
	public CamRotationController(PerspectiveCamera cam, Vector upAxis) {
		this.cam = cam;
    	this.upAxis = upAxis;
	}
	
	public PerspectiveCamera getCam() {
		return cam;
	}
	public void setCam(PerspectiveCamera cam) {
		this.cam = cam;
	}
	
	public Vector getUpAxis() {
		return upAxis;
	}
	public void setUpAxis(Vector v){
		upAxis.set(v);
	}
	
	public void rotateHorizontal(float degree){
		rotateCam(upAxis, degree);
	}
	public void rotateVertical(float degree){
		Vector rotationAxis = getVerticalRotationAxis();
		rotationAxis.normalize();
		
		rotateCam(rotationAxis, degree);
		//    	cam.normalizeUp();
	}
	private Vector getVerticalRotationAxis() {
		return Vectors.cross(new VectorGdxAdapter(cam.direction)
				, new VectorGdxAdapter(cam.up), new Vector());
	}

	public void rotateCam(Vector rotationAxis, float rotDegree) {
		Vector rotatedVector = Vectors.rotate(new VectorGdxAdapter(cam.direction)
										, rotationAxis
										, rotDegree).normalize();
		cam.direction.set(rotatedVector.x(), rotatedVector.y(), rotatedVector.z());
		cam.update();
	}
	
}
