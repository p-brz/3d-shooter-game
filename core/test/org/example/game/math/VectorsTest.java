package org.example.game.math;

import static org.junit.Assert.assertEquals;

import org.example.shootergame.math.Vector;
import org.example.shootergame.math.Vectors;
import org.junit.Test;

public class VectorsTest {

	private static final float DELTA = 0.001f;
	

	@Test
	public void testSub() {
		checkSub(Vec(0,0,0), Vec(100,0,-1), Vec(100,0,-1));
		checkSub(Vec(-3,-1.5f, 1f), Vec(1,2,3), Vec(4, 3.5f,2));
	}

	@Test
	public void testSquareMag() {
		assertEquals(1f, Vectors.squareMag(new Vector(1,0,0)),0.001f);
		assertEquals(2f, Vectors.squareMag(new Vector(1,1,0)),0.001f);
		assertEquals(100f, Vectors.squareMag(new Vector(-10,0,0)),0.001f);
		assertEquals(14f, Vectors.squareMag(new Vector(1,-2,3)),0.001f);
	}

	@Test
	public void testDot(){
		checkDot(0, Vec(1, 0, 0), Vec(0, 1, 0));
		checkDot(0, Vec(0, 0, 0), Vec(100, -1, 10));
		checkDot(100f, Vec(10, 0, 0), Vec(10, 0, 0));
		checkDot(14f, Vec(5, 4, 3), Vec(2, 1, 0));
	}
	
	@Test
	public void testProject(){
		checkProject(Vec(0,2.5f,0), Vec(-10, 2.5f, 8f), Vec(0, 1, 0));
		checkProject(Vec(0,0,0), Vec(0, 0, 0), Vec(0, 1, 0));
		checkProject(Vec(0,0,0), Vec(1, 0, 0), Vec(0, 0, 1));
	}
	
	@Test
	public void testCross(){
		checkCross(Vec(0, 1, 0), Vec(0, 0, 1), Vec(1, 0, 0));
		checkCross(Vec(0, 0, -1), Vec(0, 1, 0), Vec(1, 0, 0));
		checkCross(Vec(1f, 5f, 3f), Vec(2f, -1f, 1f), Vec(1f, 1f, -2f));
	}

	@Test
	public void testRotation(){
		checkRotation(Vec(0,1,0), Vec(1,0,0), Vec(0,0,1), 90);
		checkRotation(Vec(-1,0,0), Vec(0,1,0), Vec(0,0,1), 90);
		checkRotation(Vec(-1,0,0), Vec(1,0,0), Vec(0,0,1), 180);
		checkRotation(Vec(0,-1,0), Vec(1,0,0), Vec(0,0,1), 270);
		checkRotation(Vec(0,0,1), Vec(0,1f,0), Vec(1,0,0), 90);
	}
	
	private void checkRotation(Vector result, Vector v1, Vector v2, float degree) {
		assertEqualsVectors(result, Vectors.rotate(v1, v2, degree));
	}

	private void assertEqualsVectors(Vector expected, Vector current) {
		assertEquals(expected.x(), current.x(),DELTA);
		assertEquals(expected.y(), current.y(),DELTA);
		assertEquals(expected.z(), current.z(),DELTA);
	}

	Vector Vec(float x, float y, float z){
		return new Vector(x,y,z);
	}

	private void checkSub(Vector result, Vector vec1, Vector vec2) {
		assertEquals(result, Vectors.sub(vec1, vec2, new Vector()));
		
	}
	private void checkCross(Vector result, Vector vec1, Vector vec2) {
		assertEquals(result, Vectors.cross(vec1, vec2, new Vector()));
	}

	private void checkProject(Vector result, Vector vec1, Vector vec2) {
		assertEquals(result, Vectors.project(vec1, vec2, new Vector()));
	}
	void checkDot(float result, Vector vec1, Vector vec2){
		assertEquals(result, Vectors.dot(vec1, vec2),DELTA);
	}
}
