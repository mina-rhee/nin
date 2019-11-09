package debugger.collisions;

import debugger.support.Vec2f;
import debugger.support.shapes.Shape;

public class CircleShape extends Shape {
	
	public Vec2f center;
	public float radius;
	
	public CircleShape(Vec2f center, float radius) {
		this.center = center;
		this.radius = radius;
	}
	
	/////
	
	public Vec2f getCenter() {
		return center;
	}
	
	public float getRadius() {
		return radius;
	}
	
	public void setCenter(Vec2f c) {
	  center = c;
	}

  @Override
  public void move(Vec2f distance) {
    // TODO Auto-generated method stub
    
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
