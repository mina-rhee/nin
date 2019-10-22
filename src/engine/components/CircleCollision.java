package engine.components;

import debugger.collisions.CircleShape;
import debugger.support.Vec2f;
import engine.ShapeUtil;
import template.Vec2d;

public abstract class CircleCollision extends CollisionComponent {
  
  public CircleCollision(Vec2d coordinate) {
    super(coordinate);
  }

  private CircleShape bound;
  
  public CircleShape getBound() {
    return bound;
  }
  
  @Override
  public Vec2f collides(CollisionComponent o) {
    return o.collidesCircle(this);
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
    bound.setCenter(bound.getCenter().plus(inc.toVec2f()));
  }
  
  public void incrementCoord(Vec2f inc) {
    super.incrementCoord(inc.toVec2d());
    bound.setCenter(bound.getCenter().plus(inc));
  }
  
}
