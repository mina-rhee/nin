package engine.components;

import debugger.collisions.AABShape;
import debugger.support.Vec2f;
import engine.ShapeUtil;
import template.Vec2d;

public class AABCollision extends CollisionComponent<AABShape> {
  
  public AABCollision(Vec2d coordinate, AABShape b) {
    super(coordinate, b);
  }
  
  
  @Override
  public Vec2f collides(CollisionComponent o) {
    return o.collidesAAB(this);
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
    shape.setTopLeft(shape.getTopLeft().plus(inc.toVec2f()));
  }


  @Override
  public Vec2f collidesPolygon(PolygonCollision poly) {
    return ShapeUtil.collision(shape, poly.getShape());
  }


  @Override
  public void setPos(Vec2d pos) {
    shape.setTopLeft(pos.toVec2f());
  }


  @Override
  public Vec2d getCoord() {
    return shape.getTopLeft().toVec2d();
  }
  
}
