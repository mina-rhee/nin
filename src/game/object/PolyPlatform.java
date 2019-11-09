package game.object;

import debugger.collisions.PolygonShape;
import engine.components.DrawComponent;
import engine.components.PhysicsComponent;
import engine.components.PolygonCollision;
import engine.components.SpriteComponent;
import engine.components.TransformComponent;
import game.world.NinWorld;
import template.Vec2d;

public class PolyPlatform extends NinObject {
  
  public PolyPlatform(Vec2d pos, PolygonShape shape) {
    PolygonCollision cc = new PolygonCollision(pos, shape);
    addComponent(cc);
    PhysicsComponent pc = new PhysicsComponent(true, .03, 10, pos);
    addComponent(pc);
    DrawComponent dc = new SpriteComponent(NinWorld.POLYPLAT, new Vec2d(0),
        NinWorld.POLYPLATSIZE, NinWorld.POLYPLATSCALE);
    addComponent(dc);
  }

  @Override
  public DrawComponent getDrawComponent() {
    return (DrawComponent) getComponent("draw");
  }

  @Override
  public PolygonCollision getCollisionComponent() {
    return (PolygonCollision) getComponent("collision");
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
