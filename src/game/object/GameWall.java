package game.object;

import debugger.collisions.AABShape;
import engine.components.AABCollision;
import engine.components.CollisionComponent;
import engine.components.DrawComponent;
import engine.components.PhysicsComponent;
import engine.components.TransformComponent;
import template.Vec2d;

public class GameWall extends NinObject {
  
  public GameWall(Vec2d pos, Vec2d size, double rest) {
    CollisionComponent<AABShape> cc = new AABCollision(pos, 
        new AABShape(pos.toVec2f(), size.toVec2f()));
    addComponent(cc);
    PhysicsComponent pc = new PhysicsComponent(true, rest, 10, pos);
    addComponent(pc);
  }

  @Override
  public DrawComponent getDrawComponent() {
    return null;
  }

  @SuppressWarnings("unchecked")
  @Override
  public CollisionComponent<AABShape> getCollisionComponent() {
    return (CollisionComponent<AABShape>) getComponent("collision");
  }

  @Override
  public PhysicsComponent getPhysicsComponent() {
    return (PhysicsComponent) getComponent("physics");
  }

  @Override
  public TransformComponent getTransformComponent() {
    return getCollisionComponent();
  }

}
