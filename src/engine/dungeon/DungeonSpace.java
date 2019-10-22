package engine.dungeon;

import java.util.ArrayList;

import debugger.collisions.AABShape;
import debugger.support.shapes.Shape;

public abstract class DungeonSpace<E extends Shape> {
  
  public static enum DIR {
    LEFT, RIGHT, UP, DOWN
  }
  
  public abstract E getShape();
  
  public abstract ArrayList<AABShape> getWalls();
}
