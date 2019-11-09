package game.object;

import debugger.collisions.CircleShape;
import engine.components.CircleCollision;
import engine.components.DrawComponent;
import engine.components.PhysicsComponent;
import engine.components.TransformComponent;
import game.world.NinWorld;
import javafx.scene.image.Image;
import template.Vec2d;

public class BallObject extends NinObject {
  
  public BallObject(Vec2d pos,int type) {
    double radius = NinWorld.BALL1_SIZE.x / 2;
    Image ballImage = NinWorld.BALL1;
    Vec2d ballSize = NinWorld.BALL1_SIZE;
    double scale = NinWorld.BALL1_SCALE;
    double rest = NinWorld.BALL_1REST;
    float mass = NinWorld.BALL1_MASS;
    int fnum = NinWorld.BALL1_FNUM;
    Vec2d center;
    if(type == 2) {
      radius = NinWorld.BALL2_SIZE.x / 2;
      ballImage = NinWorld.BALL2;
      ballSize = NinWorld.BALL2_SIZE;
      scale = NinWorld.BALL2_SCALE;
      rest = NinWorld.BALL2_REST;
      mass = NinWorld.BALL2_MASS;
    } else if (type == 3) {
      ballImage = NinWorld.BALL3;
      ballSize = NinWorld.BALL3_SIZE;
      scale = NinWorld.BALL3_SCALE;
      rest = NinWorld.BALL3_REST;
      mass = NinWorld.BALL3_MASS;
      radius = NinWorld.BALL3_SIZE.x * scale/ 2;
      fnum = NinWorld.BALL3_FNUM;
    }
    center = pos.plus(new Vec2d(radius));
    CircleCollision cc = new CircleCollision(center,
        new CircleShape(center.toVec2f(), (float) radius));
    addComponent(cc);
    BallSprite dc = new BallSprite(ballImage, new Vec2d(0),
        ballSize, fnum, scale);
    addComponent(dc);
    PhysicsComponent pc = new PhysicsComponent(false, rest,
        mass, pos);
    addComponent(pc);
  }

  @Override
  public DrawComponent getDrawComponent() {
    return (DrawComponent) getComponent("draw");
  }

  @Override
  public CircleCollision getCollisionComponent() {
    return (CircleCollision) getComponent("collision");
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
