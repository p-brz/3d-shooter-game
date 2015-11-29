package org.example.shootergame.math;

import com.badlogic.gdx.math.MathUtils;

public class Vectors {


	/** Rotaciona vetor 'v' em 'rotDegree' ângulos ao redor do eixo 'rotAxis'
	 * */
	public static Vector rotate(Vector v, Vector rotAxis, float rotDegree) {
		/*Implementação utiliza fórmula de rotação de rodrigues:
		 * https://en.wikipedia.org/wiki/Rodrigues'_rotation_formula
		 * */
		Vector projOverAxis = project(v, rotAxis, new Vector());
		Vector projOrthogonal = sub(v, projOverAxis, new Vector());
		
		Vector crossAxis = cross(rotAxis, v, new Vector());
		float sin = MathUtils.sinDeg(rotDegree);
		Vector crossRotComponent = crossAxis.scl(sin);
		
		float cos = MathUtils.cosDeg(rotDegree);
		Vector orthComponent = projOrthogonal.scl(cos);
		
		Vector result = crossRotComponent.add(orthComponent).add(projOverAxis);
		return result;
	}
	public static Vector sub(Vector v1, Vector v2, Vector dest) {
		return dest.set(v1.x() - v2.x(), v1.y() - v2.y(), v1.z() - v2.z());
	}
	/** Calcula a projeção de v sobre o eixo 'axis'*/
	public static Vector project(Vector v, Vector u, Vector dest) {
		/**
		 * Seguindo a fórmula da projeção de v sobre u:
		 * proj(v, u): ( (u.v)/|u|**2 )* u 
		 * */
		float k = dot(v, u)/squareMag(u);
		dest.set(u).scl(k);
		return dest;
	}

	public static float squareMag(Vector axis) {
		return axis.x() * axis.x() + axis.y() * axis.y() + axis.z() * axis.z();
	}

	public static float dot(Vector v1, Vector v2) {
		return v1.x() * v2.x() + v1.y() * v2.y() + v1.z() * v2.z();
	}
	public static Vector cross(Vector v1, Vector v2, Vector dest) {
		/** Realizando regra de sarrus (cálculo do determinante de matriz 3x3) em:
		 *     |  î     ĵ      k  |
		 *     | v1.x  v1.y  v1.z |
		 *     | v2.x  v2.y  v2.z |
		 * */

		dest.x(v1.y() * v2.z() - v1.z() * v2.y());
		dest.y(v1.z() * v2.x() - v1.x() * v2.z());
		dest.z(v1.x() * v2.y() - v1.y() * v2.x());
		
		return dest;
	}
}
