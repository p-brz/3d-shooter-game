package org.example.game.math;

import static org.junit.Assert.*;

import org.junit.Test;

public class VectorTest {

	private static final float DELTA = 0.0001f;

	@Test
	public void testLen() {
		checkLen(1f, vec(1f, 0, 0));
		checkLen(0f, vec(0, 0, 0));
		checkLen(5f, vec(-3f, 0f, 4f));
	}
	
	@Test
	public void testNormalize() {
		checkLen(0f, vec(0, 0, 0).normalize());
		
		checkLen(1f, vec(1f, 0, 0).normalize());
		checkLen(1f, vec(-3f, 0f, 4f).normalize());
		checkLen(1f, vec(-100f, 20f, 30f).normalize());
	}

	private Vector vec(float x, float y, float z) {
		return new Vector(x, y, z);
	}

	private void checkLen(float f, Vector vector) {
		assertEquals(f, vector.len(), DELTA);
	}

}
