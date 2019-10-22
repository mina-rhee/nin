package engine.dungeon;

import java.util.ArrayList;

import debugger.collisions.AABShape;
import template.Vec2i;

public class Dungeon {
 
  private Dungeon parent;
  private Dungeon left = null;
  private Dungeon right = null;
  private Vec2i topLeft;
  private Vec2i size;
  private DungeonSpace<AABShape> space;
  //true if the split line is horizontal 
  //(or should be in the case of dungeons with no children)
  private boolean horizSplit;
  
  public Dungeon(Vec2i tl, Vec2i s, boolean split) {
    setTopLeft(tl);
    setSize(s);
    setParent(null);
    horizSplit = split;
  }
  
  public Dungeon(Vec2i tl, Vec2i s, Dungeon p, boolean split) {
    setTopLeft(tl);
    setSize(s);
    setParent(p);
    horizSplit = split;
  }

  public Dungeon getLeft() {
    return left;
  }

  public void setLeft(Dungeon left) {
    this.left = left;
  }

  public Dungeon getRight() {
    return right;
  }

  public void setRight(Dungeon right) {
    this.right = right;
  }

  public Vec2i getTopLeft() {
    return topLeft;
  }

  public void setTopLeft(Vec2i topLeft) {
    this.topLeft = topLeft;
  }

  public Vec2i getSize() {
    return size;
  }

  public void setSize(Vec2i size) {
    this.size = size;
  }
  
  public boolean getSplit() {
    return horizSplit;
  }

  public DungeonSpace<AABShape> getSpace() {
    return space;
  }

  public void setSpace(DungeonSpace<AABShape> space) {
    this.space = space;
  }

  public Dungeon getParent() {
    return parent;
  }

  public void setParent(Dungeon parent) {
    this.parent = parent;
  }
  
  public ArrayList<AABShape> getWalls() {
    ArrayList<AABShape> walls = new ArrayList<>();
    walls.addAll(space.getWalls());
    if(left != null) {
      walls.addAll(left.getWalls());
      walls.addAll(right.getWalls());
    }
    return walls;
  }

}
