package engine.dungeon;

import java.util.ArrayList;

import debugger.collisions.AABShape;
import debugger.support.Vec2f;
import template.Vec2i;

public class Room extends DungeonSpace<AABShape> {
  
  AABShape shape;
  private ArrayList<Door> doors = new ArrayList<>();
  private Hallway h = null;
  
  public Room(Vec2f tl, Vec2f s) {
    shape = new AABShape(tl, s);
  }

  @Override
  public AABShape getShape() {
    return shape;
  }

  public ArrayList<Door> getDoors() {
    return doors;
  }
  
  public void addDoor(Door door) {
    doors.add(door);
  }
  
  public void addHallway(Hallway hall) {
    h = hall;
    if(h.getHoriz()) {
      if(h.getShape().getTopLeft().x == shape.getMaxX()) {
        Door d = new Door(false, (int) shape.getMaxX(), 
            new Vec2i((int) h.getShape().getMinY(), (int) h.getShape().getMaxY()));
        doors.add(d);
      } else {
        Door d = new Door(false, (int) shape.getMinX(),
            new Vec2i((int) h.getShape().getMinY(), (int) h.getShape().getMaxY()));
        doors.add(d);
      }
    } else {
      if(h.getShape().getTopLeft().y == shape.getMaxY()) {
        Door d = new Door(true, (int) shape.getMaxY(),
            new Vec2i((int) h.getShape().getMinX(), (int) h.getShape().getMaxX()));
        doors.add(d);
      } else {
        Door d = new Door(true, (int) shape.getMinY(),
            new Vec2i((int) h.getShape().getMinX(), (int) h.getShape().getMaxX()));
        doors.add(d);
      }
    }
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
    boolean upWall = false;
    boolean downWall = false;
    boolean leftWall = false;
    boolean rightWall = false;
    for(int i = 0; i < doors.size(); i++) {
      Door d = doors.get(i);
      DIR dir = doorDirection(d);
      if(dir == DIR.UP) {
        upWall = true;
        addWalls(walls, d, new Vec2i((int) shape.getMinX(), 
            (int) shape.getMaxX()));
      } else if (dir == DIR.DOWN) {
        downWall = true;
        addWalls(walls, d, new Vec2i( (int) shape.getMinX(),
            (int) shape.getMaxX()));
      } else if (dir == DIR.LEFT) {
        leftWall = true;
        addWalls(walls, d, new Vec2i((int) shape.getMinY(), 
            (int) shape.getMaxY()));
      } else {
        rightWall = true;
        addWalls(walls, d, new Vec2i((int) shape.getMinY(), 
            (int) shape.getMaxY()));
      }
    }
    
    if(!upWall)
      walls.add(topWall());
    if(!downWall)
      walls.add(botWall());
    if(!leftWall)
      walls.add(leftWall());
    if(!rightWall)
      walls.add(rightWall());
    /**
    if(d == null) {
      walls.add(topWall());
      walls.add(botWall());
      walls.add(leftWall());
      walls.add(rightWall());
    } else {
      if(d.getXAxis()) {
        if(d.getAxisPos() == (int) shape.getMinY()) {
          walls.add(botWall());
          walls.add(new AABShape(shape.getTopLeft(), 
              new Vec2f(d.getMin() - shape.getMinX(), .1)));
          walls.add(new AABShape(new Vec2f(d.getMax(), d.getAxisPos()), 
              new Vec2f(shape.getMaxX() - d.getMax(), .1)));
          walls.add(rightWall());
          walls.add(leftWall());
        } else {
          walls.add(topWall());
          walls.add(new AABShape(new Vec2f(shape.getMinX(), d.getAxisPos()),
              new Vec2f(d.getMin() - shape.getMinX(), .1)));
          walls.add(new AABShape(new Vec2f(d.getMax(), d.getAxisPos()), 
              new Vec2f(shape.getMaxX() - d.getMax(), .1)));
          walls.add(rightWall());
          walls.add(leftWall());
        }
      } else {
        if(d.getAxisPos() == (int) shape.getMinX()) {
          walls.add(rightWall());
          walls.add(new AABShape(shape.getTopLeft(),
              new Vec2f(.1, d.getMin() - shape.getMinY())));
          walls.add(new AABShape(new Vec2f(d.getAxisPos(), d.getMax()),
              new Vec2f(.1, shape.getMaxY() - d.getMax())));
          walls.add(topWall());
          walls.add(botWall());
        } else {
          walls.add(leftWall());
          walls.add(new AABShape(new Vec2f(d.getAxisPos(), shape.getMinY()),
              new Vec2f(.1, d.getMin() - shape.getMinY())));
          walls.add(new AABShape(new Vec2f(d.getAxisPos(), d.getMax()),
              new Vec2f(.1, shape.getMaxY() - d.getMax())));
          walls.add(topWall());
          walls.add(botWall());
        }
      }
    }
    **/
    return walls;
  }
  
  private DIR doorDirection(Door d) {
    if(d.getXAxis()) {
      if (d.getAxisPos() == (int) shape.getMinY()) {
        return DIR.UP;
      } else {
        return DIR.DOWN;
      }
    } else {
      if (d.getAxisPos() == (int) shape.getMinX()) {
        return DIR.LEFT;
      } else {
        return DIR.RIGHT;
      }
    }
  }
  
  private void addWalls(ArrayList<AABShape> walls, Door d, Vec2i bound) {
    if(d.getXAxis()) {
      walls.add(new AABShape(new Vec2f(bound.x, d.getAxisPos()),
          new Vec2f(d.getMin() - bound.x, .1)));
      walls.add(new AABShape(new Vec2f(d.getMax(), d.getAxisPos()),
          new Vec2f(bound.y - d.getMax(), .1)));
    } else {
      walls.add(new AABShape(new Vec2f(d.getAxisPos(), bound.x),
          new Vec2f(.1, d.getMin() - bound.x)));
      walls.add(new AABShape(new Vec2f(d.getAxisPos(), d.getMax()),
          new Vec2f(.1, bound.y - d.getMax())));
    }
  }

}
