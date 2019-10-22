package debugger.collisions;

import debugger.support.Vec2f;
import debugger.support.shapes.Shape;

public class AABShape extends Shape {
	
	protected Vec2f topLeft;
	protected Vec2f size;

	public AABShape(Vec2f topLeft, Vec2f size) {
		this.topLeft = topLeft;
		this.size = size;
	}
	
	public void setTopLeft(Vec2f tl) {
	  topLeft = tl;
	}
	
	public float getMinX() {
	  return topLeft.x;
	}
	
	public float getMaxX() {
    return topLeft.x + size.x;
  }
	
	public float getMinY() {
    return topLeft.y;
  }
	
	public float getMaxY() {
    return topLeft.y + size.y;
  }
	
	public Vec2f getTopLeft() {
		return topLeft;
	}
	
	public Vec2f getSize() {
		return size;
	}

  @Override
  public void move(Vec2f distance) {
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
