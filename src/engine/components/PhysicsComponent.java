package engine.components;

import javafx.scene.canvas.GraphicsContext;
import template.Vec2d;

public class PhysicsComponent implements Component {
  
  public static final String TAG = "physics";
  
  public boolean staticObj;
  public double r; //restitution
  public float mass; //mass
  public Vec2d vel; //velocity
  public Vec2d imp; //impulse
  public Vec2d force; //force
  public Vec2d pos; //position
  
  public PhysicsComponent(boolean isStatic, double restitution, float m, Vec2d position) {
    staticObj = isStatic;
    r = restitution;
    mass = m;
    vel = new Vec2d(0);
    imp = new Vec2d(0);
    force = new Vec2d(0);
    pos = position;
  }

  @Override
  public String getTag() {
    return TAG;
  }
  
  public void applyForce(Vec2d f) {
    force = force.plus(f);
  }
  
  public void applyImpulse(Vec2d impulse) {
    imp = imp.plus(impulse);
  }

  @Override
  public void tick(long nanos) {
    if(staticObj) {
      return;
    }
    double dnanos = (double) nanos / 1000000000;
    if(dnanos > 1) {
      return;
    }
    vel = vel.plus(force.smult(dnanos / mass)).plus(imp.sdiv(mass));
    pos = pos.plus(vel.smult(dnanos));
    force = new Vec2d(0);
    imp = new Vec2d(0);
  }
  
  public void setPos(Vec2d position) {
    pos = position;
  }
  
  public void incPos(Vec2d inc) {
    pos = pos.plus(inc);
  }
  
  public Vec2d collisionImpulse(PhysicsComponent other) {
    if(staticObj) {
      return new Vec2d(0);
    } else if (other.staticObj) {
      double multval = -mass * (1 + Math.sqrt(r * other.r));
      return vel.smult(multval);
      } else {
      double cor = Math.sqrt(r * other.r);
      double multval = (mass * other.mass) * (1 + cor) / (mass + other.mass);
      return other.vel.minus(vel).smult(multval);
    }
  }

  @Override
  public void draw(GraphicsContext g) {
  }

}
