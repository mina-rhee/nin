package engine.components;

import debugger.collisions.AABShape;
import debugger.support.Vec2f;
import engine.ShapeUtil;
import template.Vec2d;

public abstract class AABCollision extends CollisionComponent {
  
  public AABCollision(Vec2d coordinate) {
    super(coordinate);
  }

  private AABShape bound;
  
  
  @Override
  public Vec2f collides(CollisionComponent o) {
    return o.collidesAAB(this);
  }
  
  @Override
  public Vec2f collidesCircle(CircleCollision c) {
    return ShapeUtil.collision(bound, c.getBound());
  }
  
  @Override
  public Vec2f collidesAAB(AABCollision aab) {
    return ShapeUtil.collision(bound, aab.getBound());
  }
  
  @Override
  public void incrementCoord(Vec2d inc) {
    super.incrementCoord(inc);
    bound.setTopLeft(bound.getTopLeft().plus(inc.toVec2f()));
  }
  
  public void incrementCoord(Vec2f inc) {
    super.incrementCoord(inc.toVec2d());
    bound.setTopLeft(bound.getTopLeft().plus(inc));
  }
  
  public AABShape getBound() {
    return bound;
  }
  
  public void setBound(AABShape b) {
    bound = b;
  }
  
}
