package org.example.game.math;

import java.util.Arrays;

public class Vector {
	float[] coords;

	public Vector() {
		this(0,0,0);
	}
	public Vector(float x, float y, float z) {
		coords = new float[]{x,y,z};
	}

	public float x() {
		return coords[0];
	}
	public float y() {
		return coords[1];
	}
	public float z() {
		return coords[2];
	}

	public Vector x(float f) {
		coords[0] = f;
		return this;
	}
	public Vector y(float f) {
		coords[1] = f;
		return this;
	}
	public Vector z(float f) {
		coords[2] = f;
		return this;
	}

	public Vector set(float x, float y, float z) {
		coords[0] = x;
		coords[1] = y;
		coords[2] = z;
		
		return this;
	}

	public Vector set(Vector v) {
		return set(v.x(), v.y(), v.z());
	}


	public Vector scl(float k) {
		for(int i=0; i < coords.length; ++i){
			coords[i] *= k;
		}
		return this;
	}

	public Vector add(Vector vec) {
		for(int i=0; i < coords.length; ++i){
			coords[i] += vec.coords[i];
		}
		return this;
	}

	public double len() {
		return Math.sqrt(len2());
	}
	public float len2() {
		float sum=0f;
		for(float f : coords){
			sum += f*f;
		}
		return sum;
	}
	
	public Vector normalize() {
		double len = this.len();
		if(Double.compare(len, 0f) !=0){
			scl(1f/(float)len);
		}
		return this;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(coords);
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass()){
			return false;
		}
		Vector other = (Vector) obj;
		return Arrays.equals(coords, other.coords);
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		for(float f : coords){
			if(b.length() != 0){
				b.append(", ");
			}
			b.append(f);
		}
		return b.toString();
	}
	
}
