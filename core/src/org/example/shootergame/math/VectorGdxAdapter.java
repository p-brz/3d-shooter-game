package org.example.shootergame.math;

import com.badlogic.gdx.math.Vector3;

public class VectorGdxAdapter extends Vector{
	public VectorGdxAdapter() {
		super();
	}
	public VectorGdxAdapter(float x, float y, float z) {
		super(x, y, z);
	}
	public VectorGdxAdapter(Vector3 vec){
		super(vec.x, vec.y, vec.z);
	}
	
	public Vector3 get(){
		return new Vector3(x(), y(), z());
	}
}
