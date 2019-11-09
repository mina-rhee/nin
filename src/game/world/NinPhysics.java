package game.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import debugger.support.Vec2f;
import engine.components.CollisionComponent;
import engine.components.PhysicsComponent;
import engine.objects.PhysicsObject;
import engine.systems.PhysicsSystem;
import game.object.NinObject;
import game.object.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import template.Vec2d;

public class NinPhysics extends PhysicsSystem {
  
  public static final String TAG = "PHYSICS";
  
  public static final int STATIC_LAYER = 0;
  public static final int OBJECT_LAYER = 1;
  private Player p;
  private NinWorld world;

  public NinPhysics(NinWorld world, Player player) {
    super(world);
    addLayer(STATIC_LAYER);
    addLayer(OBJECT_LAYER);
    this.world = world;
    p = player;
  }

  @Override
  public String getTag() {
    return TAG;
  }
  
  public void addStaticObject(NinObject no) {
    addObject(no, STATIC_LAYER);
  }
  
  public void addObject(NinObject no) {
    addObject(no, OBJECT_LAYER);
  }
  
  @Override
  public void onTick(long nanos) {
    Set<PhysicsObject> staticobj = getLayer(STATIC_LAYER);
    Set<PhysicsObject> otherobj = getLayer(OBJECT_LAYER);
    
    PhysicsComponent pPhysics = p.getPhysicsComponent();
    
    //checking keypresses for the player
    if(world.isKeyDown(KeyCode.UP) && p.canJump) {
      pPhysics.applyImpulse(new 
          Vec2d(0, -NinWorld.JUMP_IMP));
    }
    if(world.isKeyDown(KeyCode.RIGHT) && 
        pPhysics.vel.x < NinWorld.TERMINAL_SPEED) {
      pPhysics.applyImpulse(new Vec2d(NinWorld.SIDE_SPEED, 0));
    }
    if(world.isKeyDown(KeyCode.LEFT) && 
        pPhysics.vel.x > -NinWorld.TERMINAL_SPEED) {
      pPhysics.applyImpulse(new Vec2d(-NinWorld.SIDE_SPEED, 0));
    }
    
  //apply gravity and tick for non static objects
    for(PhysicsObject obj : otherobj) {
      PhysicsComponent pc = obj.getPhysicsComponent();
      if(!pc.staticObj) {
        pc.applyForce(new Vec2d(0, NinWorld.GRAVITY * pc.mass));
        pc.tick(nanos);
        obj.getCollisionComponent().setPos(pc.pos);
      }
    }
    PhysicsComponent pc = p.getPhysicsComponent();
    pc.applyForce(new Vec2d(0, NinWorld.GRAVITY * pc.mass));
    pc.tick(nanos);
    p.getCollisionComponent().setPos(pc.pos);
    
    //check collisions between static objects and nonstatic objects
    for(PhysicsObject obj : otherobj) {
      for(PhysicsObject sObj : staticobj) {
        CollisionComponent pc1 = obj.getCollisionComponent();
        CollisionComponent pc2 = sObj.getCollisionComponent();
        Vec2f mtv = pc2.collides(pc1);
        if(mtv != null) {
          handleCollision(sObj, obj, mtv.toVec2d());
        }
      }
    }
    
    p.canJump = false;
    for(PhysicsObject sObj : staticobj) {
      CollisionComponent pc2 = sObj.getCollisionComponent();
      Vec2f mtv = pc2.collides(p.getCollisionComponent());
      if(mtv != null) {
        if(mtv.x == 0 && mtv.y <= 0) {
          p.canJump = true;
          p.getCollisionComponent().incrementCoord(mtv.toVec2d());
          PhysicsComponent playerP = p.getPhysicsComponent();
          playerP.incPos(mtv.toVec2d());
          playerP.vel = new Vec2d(playerP.vel.x, 0);
        } else {
          handleCollision(p, sObj, mtv.toVec2d());
        }
      }
    }
    
    //check for collisions between nonstatic objects
    List<PhysicsObject> nonstatic = new ArrayList<>();
    nonstatic.add(p);
    nonstatic.addAll(otherobj);
    for(int i = 0; i < nonstatic.size(); i++) {
      for(int x = i + 1; x < nonstatic.size(); x++) {
        PhysicsObject p1 = nonstatic.get(i);
        PhysicsObject p2 = nonstatic.get(x);
        Vec2f mtv = p2.getCollisionComponent().
            collides(p1.getCollisionComponent());
        if(mtv != null) {
          handleCollision(p1, p2, mtv.toVec2d());
        }
      }
    }
  }

  @Override
  public void onDraw(GraphicsContext g) {
  }


}
