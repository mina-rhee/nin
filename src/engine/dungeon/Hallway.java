package engine.dungeon;

import java.util.ArrayList;

import debugger.collisions.AABShape;
import debugger.support.Vec2f;
import template.Vec2i;

public class Hallway extends DungeonSpace<AABShape> {
  
  private DungeonSpace<AABShape> r1;
  private DungeonSpace<AABShape> r2;
  private Door d;
  private AABShape shape;
  private boolean horiz;
  
  public Hallway(Vec2f tl, Vec2f s, DungeonSpace<AABShape> s1, 
      DungeonSpace<AABShape> s2, boolean h) {
    shape = new AABShape(tl, s);
    r1 = s1;
    r2 = s2;
    horiz = h;
  }
  
  public Hallway(Vec2i tl, Vec2i s, DungeonSpace<AABShape> s1,
      DungeonSpace<AABShape> s2, boolean h) {
    shape = new AABShape(new Vec2f(tl.x, tl.y), new Vec2f(s.x, s.y));
    r1 = s1;
    r2 = s2;
    horiz = h;
  }
  

  @Override
  public AABShape getShape() {
    return shape;
  }

  public Door getDoor() {
    return d;
  }
  
  public void setDoor(Door door) {
    d = door;
  }
  
  public DungeonSpace<AABShape> getR1() {
    return r1;
  }
  
  public DungeonSpace<AABShape> getR2() {
    return r2;
  }
  
  public boolean getHoriz() {
    return horiz;
  }
  
  public AABShape topWall() {
    return new AABShape(shape.getTopLeft(), 
        new Vec2f(shape.getSize().x, .1));
  }
  
  public AABShape botWall() {
    return new AABShape(new Vec2f(shape.getMinX(), shape.getMaxY()),
        new Vec2f(shape.getSize().x, .1));
  }
  
  public AABShape rightWall() {
    return new AABShape(new Vec2f(shape.getMaxX(), shape.getMinY()), 
        new Vec2f(.1, shape.getSize().y));
  }

  public AABShape leftWall() {
    return new AABShape(shape.getTopLeft(), 
        new Vec2f(.1, shape.getSize().y));
  }

  
  public ArrayList<AABShape> getWalls() {
    ArrayList<AABShape> walls = new ArrayList<>();
    if(d == null) {
      if(horiz) {
        walls.add(topWall());
        walls.add(botWall());
      } else {
        walls.add(leftWall());
        walls.add(rightWall());
      }
    } else {
      if(horiz) {
        if(d.getAxisPos() == (int) shape.getMinY()) {
          walls.add(botWall());
          walls.add(new AABShape(shape.getTopLeft(), 
              new Vec2f(d.getMin() - shape.getMinX(), .1)));
          walls.add(new AABShape(new Vec2f(d.getMax(), d.getAxisPos()), 
              new Vec2f(shape.getMaxX() - d.getMax(), .1)));
        } else {
          walls.add(topWall());
          walls.add(new AABShape(new Vec2f(shape.getMinX(), d.getAxisPos()),
              new Vec2f(d.getMin() - shape.getMinX(), .1)));
          walls.add(new AABShape(new Vec2f(d.getMax(), d.getAxisPos()), 
              new Vec2f(shape.getMaxX() - d.getMax(), .1)));
        }
      } else {
        if(d.getAxisPos() == (int) shape.getMinX()) {
          walls.add(rightWall());
          walls.add(new AABShape(shape.getTopLeft(),
              new Vec2f(.1, d.getMin() - shape.getMinY())));
          walls.add(new AABShape(new Vec2f(d.getAxisPos(), d.getMax()),
              new Vec2f(.1, shape.getMaxY() - d.getMax())));
        } else {
          walls.add(leftWall());
          walls.add(new AABShape(new Vec2f(d.getAxisPos(), shape.getMinY()),
              new Vec2f(.1, d.getMin() - shape.getMinY())));
          walls.add(new AABShape(new Vec2f(d.getAxisPos(), d.getMax()),
              new Vec2f(.1, shape.getMaxY() - d.getMax())));
        }
      }
    }
    return walls;
  }

}
