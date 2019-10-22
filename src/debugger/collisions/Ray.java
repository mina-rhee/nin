package debugger.collisions;

import debugger.support.Vec2f;

public final class Ray {
	
	public final Vec2f src;
	public final Vec2f dir;
	
	public Ray(Vec2f src, Vec2f dir) {
		this.src = src;
		this.dir = dir;
	}

}
