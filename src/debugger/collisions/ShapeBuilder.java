package debugger.collisions;

import debugger.support.Vec2f;
import debugger.support.shapes.AABShapeDefine;
import debugger.support.shapes.CircleShapeDefine;
import debugger.support.shapes.PolygonShapeDefine;

public class ShapeBuilder {

	public static AABShapeDefine[] getBoxes() {
		return new AABShapeDefine[] {
				new AABShapeDefine(new Vec2f(100, 120), new Vec2f(60, 35)),
				new AABShapeDefine(new Vec2f(400,  10), new Vec2f(35,  60)),
				new AABShapeDefine(new Vec2f(330, 410), new Vec2f(45, 45))
			};
	}
	
	public static CircleShapeDefine[] getCircles() {
		return new CircleShapeDefine[] {
				new CircleShapeDefine(new Vec2f(150, 200), 10),
				new CircleShapeDefine(new Vec2f(500, 380), 30),
				new CircleShapeDefine(new Vec2f(300, 220), 20)
			};
	}
	
	public static PolygonShapeDefine[] getPolygons() {
		return new PolygonShapeDefine[] {
			new PolygonShapeDefine(new Vec2f(210, 195), new Vec2f(230, 195), new Vec2f(240, 170),
					new Vec2f(220, 160), new Vec2f(200, 170))	
		};
	}
	
}
