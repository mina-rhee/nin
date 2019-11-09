package game.object;

import debugger.collisions.AABShape;
import engine.components.AABCollision;
import engine.components.CollisionComponent;
import engine.components.DrawComponent;
import engine.components.PhysicsComponent;
import engine.components.SpriteComponent;
import engine.components.TransformComponent;
import game.world.NinWorld;
import template.Vec2d;

public class Player extends NinObject {
  
  public boolean canJump = false;
  
  public Player(Vec2d pos) {
    SpriteComponent sc = new SpriteComponent(NinWorld.ASTRO, 
        NinWorld.ASTRO_POS, NinWorld.ASTRO_SIZE, NinWorld.ASTRO_SCALE);
    addComponent(sc);
    CollisionComponent<AABShape> cc = new AABCollision(pos, new AABShape(pos.toVec2f(), 
        NinWorld.ASTRO_SIZE.smult(NinWorld.ASTRO_SCALE).toVec2f()));
    addComponent(cc);
    addComponent(new PhysicsComponent(false, 1, 10, pos));
  }

  @Override
  public DrawComponent getDrawComponent() {
    return (SpriteComponent) getComponent("draw");
  }

  @SuppressWarnings("unchecked")
  @Override
  public CollisionComponent<AABShape> getCollisionComponent() {
    return (CollisionComponent<AABShape>) getComponent("collision");
  }

  @Override
  public TransformComponent getTransformComponent() {
    return getCollisionComponent();
  }

  @Override
  public PhysicsComponent getPhysicsComponent() {
    return (PhysicsComponent) getComponent("physics");
  }

}
