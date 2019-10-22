package debugger.collisions;

import debugger.support.Vec2f;
import debugger.support.shapes.Shape;

public abstract class PolygonShape extends Shape {
	
	protected Vec2f[] points;
	
	public PolygonShape(Vec2f ... points) {
		this.points = points;
	}
	
	public int getNumPoints() {
		return points.length;
	}
	
	public Vec2f getPoint(int i) {
		return points[i];
	}
	
}
