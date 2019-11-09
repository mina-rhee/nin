package engine.components;

import debugger.collisions.PolygonShape;
import debugger.support.Vec2f;
import engine.ShapeUtil;
import template.Vec2d;

public class PolygonCollision extends CollisionComponent<PolygonShape> {

  public PolygonCollision(Vec2d coordinate, PolygonShape b) {
    super(coordinate, b);
  }

  @Override
  public Vec2f collides(CollisionComponent o) {
    return o.collidesPolygon(this);
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
  public Vec2f collidesPolygon(PolygonCollision poly) {
    return ShapeUtil.collision(shape, poly.getShape());
  }
  
  @Override
  public void incrementCoord(Vec2d inc) {
    Vec2f increment = inc.toVec2f();
    for(int i = 0; i < shape.points.length; i++) {
      shape.points[i] = shape.points[i].plus(increment);
    }
  }

  @Override
  public void setPos(Vec2d pos) {
    Vec2d inc = pos.minus(shape.points[0].toVec2d());
    incrementCoord(inc);
  }

  @Override
  public Vec2d getCoord() {
    return super.coord;
  }
 
}
