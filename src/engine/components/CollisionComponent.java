package engine.components;

import debugger.support.Vec2f;
import debugger.support.shapes.Shape;
import template.Vec2d;

public abstract class CollisionComponent<E extends Shape> extends TransformComponent {
  
  public static final String TAG = "collision";
  protected E shape;
  
  public CollisionComponent(Vec2d coordinate, E bound) {
    super(coordinate);
    shape = bound;
  }
  
  public E getShape() {
    return shape;
  }
  
  @Override
  public String getTag() {
   return TAG;
  }
  
  public abstract Vec2f collides(CollisionComponent o);
  
  public abstract Vec2f collidesCircle(CircleCollision c);
  
  public abstract Vec2f collidesAAB(AABCollision aab);
  
  public abstract Vec2f collidesPolygon(PolygonCollision poly);
  
  public abstract void setPos(Vec2d pos);
  
  @Override
  public void setCoord(Vec2d pos) {
    setPos(pos);
  }
  
  public abstract void incrementCoord(Vec2d pos);
  
  public abstract Vec2d getCoord();

}
