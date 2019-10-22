package debugger.support.shapes;

import debugger.collisions.AABShape;
import debugger.support.Display;
import debugger.support.Vec2f;

public class AABShapeDefine extends AABShape {
	
	public AABShapeDefine(Vec2f topLeft, Vec2f bottomRight) {
		super(topLeft, bottomRight);
	}

	@Override
	public final void move(Vec2f distance) {
		topLeft = topLeft.plus(distance);
		bindToCanvas();
	}
	
	@Override
	public final Vec2f getCenter() {
		return getTopLeft().plus(size.sdiv(2));
	}
	
	@Override
	public void bindToCanvas() {
		Vec2f distance = new Vec2f(0);
		if(topLeft.x < 0) {
			distance = distance.plus(new Vec2f(-topLeft.x, 0));
		} else if(topLeft.x + size.x >= Display.getStageWidth()) {
			distance = distance.plus(new Vec2f(Display.getStageWidth() - topLeft.x - size.x, 0));
		}
		
		if(topLeft.y < 0) {
			distance = distance.plus(new Vec2f(0, -topLeft.y));
		} else if(topLeft.y + size.y >= Display.getStageHeight()) {
			distance = distance.plus(new Vec2f(0, Display.getStageHeight() - topLeft.y - size.y));
		}
		
		topLeft = topLeft.plus(distance);
	} 
	
	
	@Override
	public boolean atLeftEdge() {
		return topLeft.x <= 0;
	}

	@Override
	public boolean atTopEdge() {
		return topLeft.y <= 0;
	}
	
	@Override
	public boolean atRightEdge() {
		return topLeft.x + size.x >= Display.getStageWidth();
	}
	
	@Override
	public boolean atBottomEdge() {
		return topLeft.y + size.y >= Display.getStageHeight();
	}
	
}
