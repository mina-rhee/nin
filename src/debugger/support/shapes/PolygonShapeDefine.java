package debugger.support.shapes;

import debugger.collisions.PolygonShape;
import debugger.support.Display;
import debugger.support.Vec2f;

public class PolygonShapeDefine extends PolygonShape {
	
	Vec2f min;
	Vec2f max;

	public PolygonShapeDefine(Vec2f ... points) {
		super(points);
		float minX = Float.MAX_VALUE, minY = Float.MAX_VALUE, maxX = 0, maxY = 0;
		for(Vec2f v : points) {
			minX = Float.min(minX, v.x);
			minY = Float.min(minY, v.y);
			maxX = Float.max(maxX, v.x);
			maxY = Float.max(maxY, v.y);
		}
		min = new Vec2f(minX, minY);
		max = new Vec2f(maxX, maxY);
	}
	
	@Override
	public void move(Vec2f distance) {
		for(int i = 0; i < points.length; i++) {
			points[i] = points[i].plus(distance);
		}
		min = min.plus(distance);
		max = max.plus(distance);
		bindToCanvas();
	}
	
	@Override
	public void bindToCanvas() {
		Vec2f distance = new Vec2f(0);
		if(min.x < 0) {
			distance = distance.plus(new Vec2f(-min.x, 0));
		} else if(max.x >= Display.getStageWidth()) {
			distance = distance.plus(new Vec2f(Display.getStageWidth() - max.x, 0));
		}
		
		if(min.y < 0) {
			distance = distance.plus(new Vec2f(0, -min.y));
		} else if(max.y >= Display.getStageHeight()) {
			distance = distance.plus(new Vec2f(0, Display.getStageHeight() - max.y));
		}
		
		for(int i = 0; i < points.length; i++) {
			points[i] = points[i].plus(distance);
		}
		min = min.plus(distance);
		max = max.plus(distance);
	}

	@Override
	public Vec2f getCenter() {
		return min.plus(max).sdiv(2);
	}

	@Override
	public boolean atLeftEdge() {
		return min.x <= 0;
	}

	@Override
	public boolean atTopEdge() {
		return min.y <= 0;
	}

	@Override
	public boolean atRightEdge() {
		return max.x >= Display.getStageWidth();
	}

	@Override
	public boolean atBottomEdge() {
		return max.y >= Display.getStageHeight();
	}

}
