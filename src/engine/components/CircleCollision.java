package engine.components;

import debugger.collisions.CircleShape;
import debugger.support.Vec2f;
import engine.ShapeUtil;
import template.Vec2d;

public class CircleCollision extends CollisionComponent<CircleShape> {
  
  public CircleCollision(Vec2d coordinate, CircleShape b) {
    super(coordinate, b);
  }
  
  @Override
  public Vec2f collides(CollisionComponent o) {
    return o.collidesCircle(this);
  }
  
  @Override
  public Vec2f collidesCircle(CircleCollision c) {
    return ShapeUtil.collision(shape, c.getShape());
  }
  
  @Override
  public Vec2f collidesAAB(AABCollision aab) {
    return ShapeUtil.collision(shape, aab.getShape());
  }
  
  @Override
  public void incrementCoord(Vec2d inc) {
    shape.setCenter(shape.getCenter().plus(inc.toVec2f()));
  }

  @Override
  public Vec2f collidesPolygon(PolygonCollision poly) {
    return ShapeUtil.collision(shape, poly.getShape());
  }

  @Override
  public void setPos(Vec2d pos) {
    shape.setCenter(pos.toVec2f());
  }

  @Override
  public Vec2d getCoord() {
    return shape.getCenter().toVec2d().minus(new Vec2d(shape.getRadius()));
  }
  
}
