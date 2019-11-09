package debugger.collisions;

import debugger.support.Vec2f;
import debugger.support.shapes.Shape;

public class PolygonShape extends Shape {
	
	public Vec2f[] points;
	
	public PolygonShape(Vec2f ... points) {
		this.points = points;
	}
	
	public int getNumPoints() {
		return points.length;
	}
	
	public Vec2f getPoint(int i) {
		return points[i];
	}

  @Override
  public void move(Vec2f distance) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public Vec2f getCenter() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean atLeftEdge() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean atTopEdge() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean atRightEdge() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean atBottomEdge() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void bindToCanvas() {
    // TODO Auto-generated method stub
    
  }
	
}
