package engine.systems;

import engine.GameWorld;
import engine.components.PhysicsComponent;
import engine.objects.PhysicsObject;
import javafx.scene.canvas.GraphicsContext;
import template.Vec2d;

public abstract class PhysicsSystem extends GameSystem<PhysicsObject> {

  public PhysicsSystem(GameWorld<?> world) {
    super(world);
  }
  
  public static final String TAG = "PHYSICS";

  @Override
  public String getTag() {
    return TAG;
  }

  @Override
  public void onDraw(GraphicsContext g) {
  }

  public abstract void onTick(long nanos);
  
  public void handleCollision(PhysicsObject p1, PhysicsObject p2, Vec2d mtv) {
    if(Double.isNaN(mtv.x)) {
      mtv = new Vec2d(0, mtv.y);
    }
    if(Double.isNaN(mtv.y)) {
      mtv = new Vec2d(mtv.x, 0);
    }
    PhysicsComponent pc1 = p1.getPhysicsComponent();
    PhysicsComponent pc2 = p2.getPhysicsComponent();
    if(pc1.staticObj) {
      Vec2d imp = pc2.collisionImpulse(pc1);
      Vec2d projimp = imp.projectOnto(mtv);
      if(Double.isNaN(projimp.x) || Double.isNaN(projimp.y)) {
        projimp = new Vec2d(0);
      }
      p2.getCollisionComponent().incrementCoord(mtv);
      pc2.incPos(mtv);
      pc2.applyImpulse(projimp);
    } else if (pc2.staticObj) {
      Vec2d imp = pc1.collisionImpulse(pc2);
      Vec2d projimp = imp.projectOnto(mtv);
      if(Double.isNaN(projimp.x) || Double.isNaN(projimp.y)) {
        projimp = new Vec2d(0);
      }
      p1.getCollisionComponent().incrementCoord(mtv);
      pc1.incPos(mtv);
      pc1.applyImpulse(projimp);
    } else {
      System.out.println();
      Vec2d imp = pc1.collisionImpulse(pc2);
      Vec2d projimp = imp.projectOnto(mtv);
      if(!Double.isNaN(projimp.x) && !Double.isNaN(projimp.y)) {
        pc1.applyImpulse(projimp.smult(.5));
        pc2.applyImpulse(projimp.smult(-.5));
      }
      
      p1.getCollisionComponent().incrementCoord(mtv.smult(.5));
      pc1.incPos(mtv.smult(.5));
      
      p2.getCollisionComponent().incrementCoord(mtv.smult(-.5));
      pc2.incPos(mtv.smult(-.5));
    }
  }

}
