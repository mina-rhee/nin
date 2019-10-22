package debugger.support.interfaces;

import debugger.collisions.AABShape;
import debugger.collisions.CircleShape;
import debugger.collisions.PolygonShape;
import debugger.collisions.Ray;
import debugger.support.Vec2f;

public abstract class Week3Reqs implements CollisionFunctions {
	
	public abstract Vec2f collision(AABShape s1, AABShape s2);
	public abstract Vec2f collision(AABShape s1, CircleShape s2);
	public abstract Vec2f collision(AABShape s1, Vec2f s2);
	public final Vec2f collision(AABShape s1, PolygonShape s2) {
		return null;
	}
	
	public abstract Vec2f collision(CircleShape s1, AABShape s2);
	public abstract Vec2f collision(CircleShape s1, CircleShape s2);
	public abstract Vec2f collision(CircleShape s1, Vec2f s2);
	public final Vec2f collision(CircleShape s1, PolygonShape s2) {
		return null;
	}
	
	public final Vec2f collision(PolygonShape s1, AABShape s2) {
		return null;
	}
	public final Vec2f collision(PolygonShape s1, CircleShape s2) {
		return null;
	}
	public final Vec2f collision(PolygonShape s1, Vec2f s2) {
		return null;
	}
	public final Vec2f collision(PolygonShape s1, PolygonShape s2) {
		return null;
	}
	
	public final float raycast(AABShape s1, Ray s2) {
		return -1;
	}
	public final float raycast(CircleShape s1, Ray s2) {
		return -1;
	}
	public final float raycast(PolygonShape s1, Ray s2) {
		return -1;
	}

}
