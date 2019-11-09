package game.object;

import debugger.collisions.AABShape;
import engine.components.AABCollision;
import engine.components.DrawComponent;
import engine.components.PhysicsComponent;
import engine.components.SpriteComponent;
import engine.components.TransformComponent;
import game.world.NinWorld;
import template.Vec2d;

public class RockPlatform extends NinObject {
  
  public RockPlatform(Vec2d pos, double scale) {
    AABCollision cc = new AABCollision(pos, new AABShape(pos.toVec2f(), 
        NinWorld.ROCK_SIZE.smult(scale * NinWorld.ROCK_SCALE).toVec2f()));
    addComponent(cc);
    PhysicsComponent pc = new PhysicsComponent(true, .03, 10, pos);
    addComponent(pc);
    DrawComponent dc = new SpriteComponent(NinWorld.ROCK, NinWorld.ROCK_LOC,
        NinWorld.ROCK_SIZE, NinWorld.ROCK_SCALE * scale);
    addComponent(dc);
  }

  @Override
  public DrawComponent getDrawComponent() {
    return (DrawComponent) getComponent("draw");
  }

  @Override
  public AABCollision getCollisionComponent() {
    return (AABCollision) getComponent("collision");
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
