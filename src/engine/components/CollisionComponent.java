package engine.components;

import debugger.support.Vec2f;
import debugger.support.shapes.Shape;
import template.Vec2d;

public abstract class CollisionComponent extends TransformComponent {

  public CollisionComponent(Vec2d coordinate) {
    super(coordinate);
  }

  public static final String TAG = "collision";
  
  @Override
  public String getTag() {
   return TAG;
  }
  
  public abstract Vec2f collides(CollisionComponent o);
  
  public abstract Vec2f collidesCircle(CircleCollision c);
  
  public abstract Vec2f collidesAAB(AABCollision aab);
   
  public abstract void onCollision(CollisionComponent o);
  
  public abstract Shape getBound();
  

}
